package de.fu_berlin.agdb.crepe.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.fu_berlin.agdb.crepe.core.Configuration;
import de.fu_berlin.agdb.crepe.json.algebra.JSONProfile;
import de.fu_berlin.agdb.crepe.json.algebra.notifications.JSONIonicPushNotification;
import de.fu_berlin.agdb.crepe.json.algebra.notifications.JSONNotification;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private static final Marker MARKER = MarkerManager.getMarker(ProfileController.class.getSimpleName());
    @Autowired
    private Configuration configuration;
    @Autowired
    private Logger logger;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private String ionicPushPrivateApiKey;

    private File profilesFolder;

    private File getProfilesFolder() {
        return getProfilesFolder(null);
    }

    private File getProfilesFolder(String subfolder) {
        if (profilesFolder == null) {
            profilesFolder = new File(configuration.getProfilesFolder());
        }

        File resultFolder = profilesFolder;
        if (subfolder != null) {
            resultFolder = new File(resultFolder, subfolder);
        }

        createFolderIfNecessary(resultFolder);

        return resultFolder;
    }

    private void createFolderIfNecessary(File folder) {
        if (!folder.exists()) {
            logger.info(MARKER, "Directory {} does not exist, creating...", folder);
            boolean mkdirs = folder.mkdirs();
            if (!mkdirs)
                logger.error(MARKER, "Error creating directory: {}", folder);
        }
    }

    /**
     * Receives a plain text profile written in the CREPE DSL.
     *
     * @param profile The profile, written in the CREPE DSL.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.TEXT_PLAIN_VALUE)
    public void sendUserProfile(@RequestBody String profile) {
        File profilesFolder = getProfilesFolder();

        try {
            File profileFile = File.createTempFile("user_", ".profile", profilesFolder);
            logger.info(MARKER, "Writing received user profile to file {}.", profileFile);

            PrintWriter out = new PrintWriter(new FileOutputStream(profileFile));
            out.println(profile);
            out.flush();
            out.close();
        } catch (IOException ex) {
            logger.error(MARKER, "Error while writing profile to file.", ex);
        }
    }

    // TODO: Rename to receiveUserProfile or similar?

    /**
     * Receives a JSON encoded profile, checks its integrity (by deserializing it)
     * and writes it into the profile directory.
     *
     * @param request The profile to save
     * @return HTTP 204 No Content if successful, 500 Internal Server error if not
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendUserProfile(@RequestBody ProfileRequest request) {
        JSONProfile profile = request.getProfile();
        List<JSONNotification<?>> notifications = profile.getNotifications();

        if (notifications == null) {
            notifications = new ArrayList<>();
            profile.setNotifications(notifications);
        } else {
            for (JSONNotification<?> notif : notifications) {
                if (notif instanceof JSONIonicPushNotification) {
                    ((JSONIonicPushNotification) notif).setPrivateApiKey(ionicPushPrivateApiKey);
                }
            }
        }

        notifications.add(new JSONIonicPushNotification(
                request.getUserId(),
                String.format("Profile %s activated!", request.getName()),
                request.getAppId(),
                ionicPushPrivateApiKey
        ));

        File profilesFolder = getProfilesFolder();

        try {
            File profileFile;
            profileFile = new File(profilesFolder, getProfileFilename(request));
            logger.info(MARKER, "Writing received user profile to file {}.", profileFile);

            objectMapper.writeValue(profileFile, profile);
        } catch (IOException ex) {
            logger.error(MARKER, "Error while writing profile to file.", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Nonnull
    private String getProfileFilename(@RequestBody ProfileRequest request) {
        return "user_" + request.getUserId() + "_" + request.getId() + ".json";
    }

    /**
     * Deletes a profile.
     *
     * @param request The profile to delete, must contain userId and id.
     * @return HTTP 204 No Content if successful, 404 Not Found if the profile could not be deleted.
     */
    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    public ResponseEntity<?> deleteUserProfile(@RequestBody ProfileRequest request) {
        File profileFile = new File(profilesFolder, getProfileFilename(request));
        logger.info(MARKER, "Deleting user profile {}", profileFile);
        boolean success = profileFile.delete();
        if (success) {
            logger.info("Successfully deleted user profile.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
