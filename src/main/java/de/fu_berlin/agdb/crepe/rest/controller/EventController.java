package de.fu_berlin.agdb.crepe.rest.controller;

import de.fu_berlin.agdb.crepe.outputadapters.JSONOutputAdapter;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/event")
public class EventController {

    @RequestMapping(method = RequestMethod.POST)
    public String receiveEvents(@RequestBody JSONOutputAdapter.JsonEvents events) {
        return "Received " + events.getEvents().size() + " events";
    }
}
