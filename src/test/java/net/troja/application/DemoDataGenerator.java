package net.troja.application;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class DemoDataGenerator {
    public static void main(final String... args) throws IOException {
        final Random rand = new Random(System.currentTimeMillis());
        final int numRoutes = 100000;
        final int numStations = 1000000;

        final List<String> lines = new ArrayList<>();
        lines.add(Integer.toString(numRoutes));

        for (int pos = 0; pos < numRoutes; pos++) {
            final Set<Integer> stations = new HashSet<>();
            for (int num = 0; num < (rand.nextInt(998) + 2); num++) {
                stations.add(rand.nextInt(numStations));
            }
            final StringBuilder result = new StringBuilder();
            result.append(pos);
            for (final Integer station : stations) {
                result.append(" ").append(station);
            }
            lines.add(result.toString());
        }

        final Path path = Paths.get("bigdemo.txt");
        Files.write(path, lines, StandardOpenOption.CREATE);
    }
}
