package net.troja.application;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.stream.Stream;

import net.troja.application.model.Station2Route;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DataLoader {
    private static final Logger LOGGER = LogManager.getLogger(DataLoader.class);
    public static final String LOG_INVALID_CONTENT = "Invalid content, skipping!";
    public static final String LOG_INVALID_LINE = "Invalid line (less then 3 entries), skipping!";
    public static final String LOG_INVALID_FIRST_LINE = "Invalid first line! Trying to continue without this information";

    @Autowired
    private Station2RouteRepository repository;
    @Autowired
    private ConnectionStorage storage;
    private int lines;
    private long entryCount;

    @Value("${storage}")
    private String storageType;

    public DataLoader() {
        super();
    }

    public void load(final Path file) {
        if (BusRouteApplication.STORAGE_TYPE_DB.equals(storageType)) {
            repository.deleteAll();
        } else {
            storage.clear();
        }
        lines = 0;

        try (Stream<String> stream = Files.lines(file)) {

            stream.forEach(new Consumer<String>() {
                @Override
                public void accept(final String line) {
                    if (lines == 0) {
                        try {
                            lines = Integer.parseInt(line);
                        } catch (final NumberFormatException e) {
                            // error handling in the following if
                        }
                        if (lines == 0) {
                            LOGGER.warn(LOG_INVALID_FIRST_LINE);
                            lines = -1;
                        }
                    } else if (!StringUtils.isBlank(line)) {
                        parseLine(line);
                    }
                }
            });
        } catch (final IOException e) {
            LOGGER.error("Could not load data file", e);
        }
        if (BusRouteApplication.STORAGE_TYPE_DB.equals(storageType)) {
            entryCount = repository.count();
        } else {
            entryCount = storage.size();
        }
    }

    private void parseLine(final String line) {
        final String[] split = line.split(" ");
        try {
            if (split.length >= 3) {
                if (BusRouteApplication.STORAGE_TYPE_DB.equals(storageType)) {
                    parseLineToDb(split);
                } else {
                    parseLineToStorage(split);
                }
            } else {
                LOGGER.warn(LOG_INVALID_LINE);
            }
        } catch (final NumberFormatException e) {
            LOGGER.warn(LOG_INVALID_CONTENT);
        }
    }

    private void parseLineToStorage(final String[] split) {
        for (int pos = 1; pos < split.length; pos++) {
            final Integer source = Integer.parseInt(split[pos]);
            for (int run = pos; run < split.length; run++) {
                final Integer destination = Integer.parseInt(split[run]);
                storage.put(source, destination);
            }
        }
    }

    private void parseLineToDb(final String[] split) {
        final Integer route = Integer.parseInt(split[0]);
        for (int pos = 1; pos < split.length; pos++) {
            final Integer station = Integer.parseInt(split[pos]);
            repository.save(new Station2Route(station, route));
        }
    }

    public long getEntryCount() {
        return entryCount;
    }
}
