package com.pb.biser.conveyor.controller.entity;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by diver on 5/14/15.
 */
public class Response {

    @JsonProperty("biser")
    public BiserResponse biserResponse;

    public Response() {
    }

    public Response(BiserResponse biserResponse) {
        this.biserResponse = biserResponse;
    }

    public BiserResponse getBiserResponse() {
        return biserResponse;
    }

    public void setBiserResponse(BiserResponse biserResponse) {
        this.biserResponse = biserResponse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Response response = (Response) o;

        return !(biserResponse != null ? !biserResponse.equals(response.biserResponse) : response.biserResponse != null);

    }

    @Override
    public int hashCode() {
        return biserResponse != null ? biserResponse.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Response{" +
                "biserResponse=" + biserResponse +
                '}';
    }
}
