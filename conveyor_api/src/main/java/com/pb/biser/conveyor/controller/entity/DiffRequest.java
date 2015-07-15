package com.pb.biser.conveyor.controller.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by diver on 5/14/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiffRequest {

    private String biserPack;
    private String api2xPack;
    private String session;

    public String getBiserPack() {
        return biserPack;
    }

    public void setBiserPack(String biserPack) {
        this.biserPack = biserPack;
    }

    public String getApi2xPack() {
        return api2xPack;
    }

    public void setApi2xPack(String api2xPack) {
        this.api2xPack = api2xPack;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof DiffRequest)) return false;

        DiffRequest that = (DiffRequest) o;

        return new EqualsBuilder()
                .append(biserPack, that.biserPack)
                .append(api2xPack, that.api2xPack)
                .append(session, that.session)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(biserPack)
                .append(api2xPack)
                .append(session)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "DiffRequest{" +
                "biserPack='" + biserPack + '\'' +
                ", api2xPack='" + api2xPack + '\'' +
                ", session='" + session + '\'' +
                '}';
    }
}
