package net.troja.application.rest;

import net.troja.application.BusRouteApplication;
import net.troja.application.ConnectionStorage;
import net.troja.application.Station2RouteRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class DirectController {
    private static final Logger LOGGER = LogManager.getLogger(DirectController.class);

    @Value("${storage}")
    private String storageType;

    @Autowired
    private Station2RouteRepository repo;
    @Autowired
    private ConnectionStorage storage;

    @RequestMapping("/direct")
    public DirectResponse direct(@RequestParam(value = "dep_sid") final int source, @RequestParam(value = "arr_sid") final int destination) {
        final long start = System.currentTimeMillis();
        try {
            boolean direct = false;
            if (BusRouteApplication.STORAGE_TYPE_DB.equals(storageType)) {
                direct = repo.onRoute(source, destination);
            } else {
                direct = storage.onRoute(source, destination);
            }
            return new DirectResponse(source, destination, direct);
        } finally {
            LOGGER.info("Query for dep=" + source + " arr=" + destination + " took " + (System.currentTimeMillis() - start) + "ms");
        }
    }
}
