package de.fu_berlin.agdb.crepe.rest.controller;

import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import de.fu_berlin.agdb.crepe.data.LocationMetaData;
import de.fu_berlin.agdb.crepe.json.util.SimplePoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

/**
 * A REST controller to serve data about the available locations.
 * Eventually this controller will allow the user to add new locations.
 */
@RestController
@RequestMapping("/location")
public class LocationMetaDataController {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private GeometryFactory geometryFactory;

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
            TypedQuery<LocationMetaData> query = entityManager.createNamedQuery("LocationMetaData.findAll", LocationMetaData.class);
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

    /**
     * Returns a list of locations where the descriptions start with the given string.
     *
     * @param locationDesc string to search for
     * @return A list of locations matching the description
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{locationDesc:[^\\d].*}")
    public List<LocationMetaData> locationByDescription(@PathVariable String locationDesc) {
        TypedQuery<LocationMetaData> query = entityManager.createNamedQuery("LocationMetaData.findByDescription", LocationMetaData.class);
        query.setParameter("locationDescription", locationDesc + "%");
        return query.getResultList();
    }

    /**
     * Returns all known locations, ordered by distance from the supplied position.
     *
     * @param userPosition Position of the user.
     */
    @RequestMapping(method = RequestMethod.POST, value = "/dist")
    public List<LocationMetaData> locationByDistance(@RequestBody SimplePoint userPosition) {
        return locationByDistance(userPosition.asGeometryPoint(geometryFactory), -1);
    }

    /**
     * Returns all known locations, ordered by distance from the supplied position.
     *
     * @param userPosition Position of the user.
     * @param count        Maximum number of results to return, -1 means unlimited.
     */
    @RequestMapping(method = RequestMethod.POST, value = "/dist/{count:[\\d]*}")
    public List<LocationMetaData> locationByDistance(@RequestBody SimplePoint userPosition, @PathVariable int count) {
        return locationByDistance(userPosition.asGeometryPoint(), count);
    }

    /**
     * Returns known locations, ordered by distance from the supplied position.
     *
     * @param point Position of the user.
     * @param count Maximum number of results to return, -1 means unlimited.
     */
    public List<LocationMetaData> locationByDistance(Point point, int count) {
        TypedQuery<LocationMetaData> query = entityManager.createNamedQuery("LocationMetaData.nearby", LocationMetaData.class);
        query.setParameter("Point", point);
        if (count >= 0)
            query.setMaxResults(count);
        return query.getResultList();
    }

    /**
     * Adds a new location to the database.
     *
     * @param location The location to add.
     */
    @RequestMapping(method = RequestMethod.POST, value = "/new")
    public void newLocation(@RequestBody LocationMetaData location) {
        entityManager.getTransaction().begin();
        entityManager.persist(location);
        entityManager.getTransaction().commit();
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
