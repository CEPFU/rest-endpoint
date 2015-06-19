package de.fu_berlin.agdb.crepe.rest.controller;

import de.fu_berlin.agdb.crepe.data.StationMetaData;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

import java.util.Collections;
import java.util.List;

/**
 * A REST controller to serve metadata of all available weather stations.
 */
@RestController
@RequestMapping("/stations")
public class StationMetaDataController {
    private static final ServiceRegistry serviceRegistry;
    private static final SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();

            serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static Session getSession() throws HibernateException {
        return sessionFactory.openSession();
    }


    /**
     * /stations returns a list of the metadata of all available stations.
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<StationMetaData> allStations(@RequestParam(required = false) Long stationId) {
        Query q;
        List<StationMetaData> result;
            if (stationId != null) {
                StationMetaData station = getStationById(stationId);
                result = station == null ? Collections.emptyList() : Collections.singletonList(station);
            }
            else {
                final Session session = getSession();
                q = session.createQuery("from StationMetaData");
                result = (List<StationMetaData>) q.list();
                session.close();
            }

        return result;
    }

    /**
     * /stations/{id} returns a single station by its stationId
     * @param stationId A numeric stationId
     * @return The metadata for the corresponding weather station.
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{stationId:[\\d]+}")
    public StationMetaData getStationById(@PathVariable long stationId) {
        final Session session = getSession();
        StationMetaData station = (StationMetaData) session.get(StationMetaData.class, stationId);
        session.close();
        return station;
    }

    /**
     * /stations/{name} returns all stations matching that name.
     */
    @RequestMapping(method = RequestMethod.GET, value = "/find/{stationName}")
    public List<StationMetaData> getStationsByName(@PathVariable String stationName) {
        Query q;
        List<StationMetaData> result;
        final Session session = getSession();

        q = session.createQuery("from StationMetaData where stationName like :stationName");
        q.setString("stationName", stationName + "%");

        result = (List<StationMetaData>) q.list();
        session.close();

        return result;
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
