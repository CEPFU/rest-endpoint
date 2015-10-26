package de.fu_berlin.agdb.crepe.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.fu_berlin.agdb.crepe.core.Configuration;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

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

    /**
     * Receives a JSON encoded profile, checks its integrity (by deserializing it)
     * and writes it into the <em>json</em> subfolder of the profile directory.
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void sendUserProfile(@RequestBody ProfileRequest profile) {
        File profilesFolder = getProfilesFolder("json");

        try {
            File profileFile;
            profileFile = File.createTempFile("user_", ".json", profilesFolder);
            logger.info(MARKER, "Writing received user profile to file {}.", profileFile);

            objectMapper.writeValue(profileFile, profile.getProfile());
        } catch (IOException ex) {
            logger.error(MARKER, "Error while writing profile to file.", ex);
        }

    }

}
