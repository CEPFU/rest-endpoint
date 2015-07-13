package de.fu_berlin.agdb.crepe.rest.controller;

import de.fu_berlin.agdb.crepe.core.Configuration;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void sendUserProfile(@RequestBody String profile) {

        File profilesFolder = new File(configuration.getProfilesFolder());

        if (!profilesFolder.exists()) {
            logger.info(MARKER, "Profile directory does not exists, creating...");
            profilesFolder.mkdirs();
        }

        try {
            File profileFile = File.createTempFile("user_", ".profile", profilesFolder);
            logger.info(MARKER, "Writing received user profile to file {}.", profileFile);

            PrintWriter out = new PrintWriter(new FileOutputStream(profileFile));
            out.println(profile);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
