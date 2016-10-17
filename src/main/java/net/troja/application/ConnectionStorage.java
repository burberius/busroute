package net.troja.application;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Repository;

@Repository
public class ConnectionStorage {
    private final Set<String> connections = new HashSet<>();

    public void put(final int source, final int destination) {
        if (source < destination) {
            connections.add(source + " " + destination);
        } else if (destination < source) {
            connections.add(destination + " " + source);
        }
    }

    public boolean onRoute(final int source, final int destination) {
        boolean result = false;
        if (source < destination) {
            result = connections.contains(source + " " + destination);
        } else if (destination < source) {
            result = connections.contains(destination + " " + source);
        }
        return result;
    }

    public void clear() {
        connections.clear();
    }

    public long size() {
        return connections.size();
    }
}
