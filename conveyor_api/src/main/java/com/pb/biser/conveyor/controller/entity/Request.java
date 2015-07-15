package com.pb.biser.conveyor.controller.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by diver on 5/14/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)//TODO diver: Enable this annotation
public class Request {

    public BiserRequest biserRequest;
    public DiffRequest diffRequest;
    public Sys sys;

    public BiserRequest getBiserRequest() {
        return biserRequest;
    }

    public void setBiserRequest(BiserRequest biserRequest) {
        this.biserRequest = biserRequest;
    }

    public DiffRequest getDiffRequest() {
        return diffRequest;
    }

    public void setDiffRequest(DiffRequest diffRequest) {
        this.diffRequest = diffRequest;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Request)) return false;

        Request request = (Request) o;

        return new EqualsBuilder()
                .append(biserRequest, request.biserRequest)
                .append(diffRequest, request.diffRequest)
                .append(sys, request.sys)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(biserRequest)
                .append(diffRequest)
                .append(sys)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "Request{" +
                "biserRequest=" + biserRequest +
                ", diffRequest=" + diffRequest +
                ", sys=" + sys +
                '}';
    }
}
