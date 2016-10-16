package net.troja.application.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DirectResponse {
    @JsonProperty("dep_sid")
    private int source;
    @JsonProperty("arr_sid")
    private int destionation;
    @JsonProperty("direct_bus_route")
    private boolean direct;

    public DirectResponse() {
        super();
    }

    public DirectResponse(final int source, final int destionation, final boolean direct) {
        super();
        this.source = source;
        this.destionation = destionation;
        this.direct = direct;
    }

    public int getSource() {
        return source;
    }

    public void setSource(final int source) {
        this.source = source;
    }

    public int getDestionation() {
        return destionation;
    }

    public void setDestionation(final int destionation) {
        this.destionation = destionation;
    }

    public boolean isDirect() {
        return direct;
    }

    public void setDirect(final boolean direct) {
        this.direct = direct;
    }

}
