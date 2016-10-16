package net.troja.application.rest;

import net.troja.application.Station2RouteRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class DirectController {
    private static final Logger LOGGER = LogManager.getLogger(DirectController.class);

    @Autowired
    private Station2RouteRepository repo;

    @RequestMapping("/direct")
    public DirectResponse direct(@RequestParam(value = "dep_sid") final int source, @RequestParam(value = "arr_sid") final int destination) {
        final long start = System.currentTimeMillis();
        try {
            final boolean direct = repo.onRoute(source, destination);
            return new DirectResponse(source, destination, direct);
        } finally {
            LOGGER.info("Query for dep=" + source + " arr=" + destination + " took " + (System.currentTimeMillis() - start) + "ms");
        }
    }
}
