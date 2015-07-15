package com.pb.biser.conveyor.controller.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by diver on 5/12/15.
 */
public class BiserResponse {

    private String message = "Success response";
    private String code = "DT_OK";
    private String body;

    private String duration;
    private String type;

    public BiserResponse() {
    }

    public BiserResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public BiserResponse(String body) {
        this.body = body;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof BiserResponse)) return false;

        BiserResponse that = (BiserResponse) o;

        return new EqualsBuilder()
                .append(message, that.message)
                .append(code, that.code)
                .append(body, that.body)
                .append(duration, that.duration)
                .append(type, that.type)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(message)
                .append(code)
                .append(body)
                .append(duration)
                .append(type)
                .toHashCode();
    }


    @Override
    public String toString() {
        return "BiserResponse{" +
                "message='" + message + '\'' +
                ", code='" + code + '\'' +
                ", body='" + body + '\'' +
                ", duration='" + duration + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
