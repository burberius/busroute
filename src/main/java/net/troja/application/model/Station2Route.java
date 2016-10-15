package net.troja.application.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Station2Route {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int station;
    private int route;

    public Station2Route() {
        super();
    }

    public Station2Route(final int station, final int route) {
        super();
        this.station = station;
        this.route = route;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public int getStation() {
        return station;
    }

    public void setStation(final int station) {
        this.station = station;
    }

    public int getRoute() {
        return route;
    }

    public void setRoute(final int route) {
        this.route = route;
    }

    @Override
    public String toString() {
        return "Station2Route [station=" + station + ", route=" + route + "]";
    }
}
