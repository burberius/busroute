package net.troja.application;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BusRouteApplication implements CommandLineRunner {
    private static final Logger LOGGER = LogManager.getLogger(BusRouteApplication.class);

    @Autowired
    private DataLoader loader;

    @Override
    public void run(final String... args) throws Exception {
        if (args.length != 1) {
            LOGGER.error("You have to give the bus route data file as parameter! We continue without data!");
        } else {
            final Path path = Paths.get(args[0]);
            if (!Files.exists(path)) {
                LOGGER.error("Bus route data file " + path + " does not exist! We continue without data!");
            } else {
                loader.load(path);
                LOGGER.info("Loaded " + loader.getEntryCount() + " entries");
            }
        }
    }

    public static void main(final String[] args) {
        SpringApplication.run(BusRouteApplication.class, args);
    }
}
