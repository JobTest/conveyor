package com.pb.biser.conveyor.controller.entity;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Map;

/**
 * Created by diver on 5/12/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BiserRequest {

    public Map<String, String> parameters;
    public String session;
    @JsonProperty("company_id")
    public Long companyId;
    public Long point;
    public String type;

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public Long getPoint() {
        return point;
    }

    public void setPoint(Long point) {
        this.point = point;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BiserRequest)) return false;

        BiserRequest that = (BiserRequest) o;

        if (parameters != null ? !parameters.equals(that.parameters) : that.parameters != null) return false;
        if (session != null ? !session.equals(that.session) : that.session != null) return false;
        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        if (point != null ? !point.equals(that.point) : that.point != null) return false;
        return !(type != null ? !type.equals(that.type) : that.type != null);

    }

    @Override
    public int hashCode() {
        int result = parameters != null ? parameters.hashCode() : 0;
        result = 31 * result + (session != null ? session.hashCode() : 0);
        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        result = 31 * result + (point != null ? point.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BiserRequest{" +
                "parameters=" + parameters +
                ", session='" + session + '\'' +
                ", companyId=" + companyId +
                ", point=" + point +
                ", type='" + type + '\'' +
                '}';
    }
}
