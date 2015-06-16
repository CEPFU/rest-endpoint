package de.fu_berlin.agdb.crepe.rest.controller;

import de.fu_berlin.agdb.crepe.algebra.Profile;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
public class ProfileController {


    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public void sendUserProfile(@RequestBody Profile profile) {

    }
}
