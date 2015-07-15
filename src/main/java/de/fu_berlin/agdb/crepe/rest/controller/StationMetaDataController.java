package de.fu_berlin.agdb.crepe.rest.controller;

import de.fu_berlin.agdb.crepe.data.StationMetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

/**
 * A REST controller to serve metadata of all available weather stations.
 */
@RestController
@RequestMapping("/stations")
public class StationMetaDataController {
    @Autowired
    private EntityManager entityManager;

    /**
     * /stations returns a list of the metadata of all available stations.
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<StationMetaData> allStations(@RequestParam(required = false) Long stationId) {
        if (stationId != null) {
                StationMetaData station = getStationById(stationId);
                return station == null ? emptyList() : singletonList(station);
            }
            else {
                TypedQuery<StationMetaData> query = entityManager.createQuery("from StationMetaData", StationMetaData.class);
                return query.getResultList();
            }
    }

    /**
     * /stations/{id} returns a single station by its stationId
     * @param stationId A numeric stationId
     * @return The metadata for the corresponding weather station.
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{stationId:[\\d]+}")
    public StationMetaData getStationById(@PathVariable long stationId) {
        return entityManager.find(StationMetaData.class, stationId);
    }

    /**
     * /stations/{name} returns all stations matching that name.
     */
    @RequestMapping(method = RequestMethod.GET, value = "/find/{stationName}")
    public List<StationMetaData> getStationsByName(@PathVariable String stationName) {
        TypedQuery<StationMetaData> q = entityManager.createQuery(
                "from StationMetaData where stationName like :stationName",
                StationMetaData.class
        );

        q.setParameter("stationName", stationName + "%");

        return q.getResultList();
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
