package de.fu_berlin.agdb.crepe.rest.controller;

import de.fu_berlin.agdb.crepe.data.LocationMetaData;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

/**
 * A REST controller to serve data about the available locations
 * and allow the user to add new ones.
 */
@RestController
@RequestMapping("/location")
public class LocationMetaDataController {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private Logger logger;

    /**
     * Returns a list of the meta data of all available locations,
     * or, if a locationId is specified, just that one location.
     *
     * @param locationId id of a single station to return
     * @return A list of locations, may be empty.
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<LocationMetaData> locations(@RequestParam(required = false) Integer locationId) {
        if (locationId != null) {
            LocationMetaData location = locationById(locationId);
            return location == null ? Collections.emptyList() : Collections.singletonList(location);
        } else {
            TypedQuery<LocationMetaData> query = entityManager.createQuery("from LocationMetaData", LocationMetaData.class);
            return query.getResultList();
        }
    }

    /**
     * Returns the meta data of a single location.
     *
     * @param locationId id of the station to return
     * @return A location or {@code null} if no location with that id exists.
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{locationId:[\\d]+}")
    public LocationMetaData locationById(@PathVariable int locationId) {
        return entityManager.find(LocationMetaData.class, locationId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{locationDesc:[^\\d].*}")
    public List<LocationMetaData> locationByDescription(@PathVariable String locationDesc) {
        TypedQuery<LocationMetaData> query = entityManager.createQuery(
                "from LocationMetaData where locationDescription like :locationDesc",
                LocationMetaData.class
        );

        query.setParameter("locationDesc", locationDesc + "%");
        return query.getResultList();
    }

    /**
     * Makes this REST controller JSONP capable.
     */
    @ControllerAdvice
    private static class JsonpAdvice extends AbstractJsonpResponseBodyAdvice {
        public JsonpAdvice() {
            super("callback");
        }
    }
}
