package com.pb.biser.conveyor.dao.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pb.biser.conveyor.controller.entity.Type;

import java.util.Map;

/**
 * @author tasman
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchRequestAttributes {
    private String session;
    @JsonProperty("company_id")
    private Long companyId;
    private Long point;
    private Type type;
    private Map<String, String> parameters;
    private Integer status;
    private Diff diff;

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getPoint() {
        return point;
    }

    public void setPoint(Long point) {
        this.point = point;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Diff getDiff() {
        return diff;
    }

    public void setDiff(Diff diff) {
        this.diff = diff;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SearchRequestAttributes that = (SearchRequestAttributes) o;

        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) {
            return false;
        }
        if (diff != null ? !diff.equals(that.diff) : that.diff != null) {
            return false;
        }
        if (parameters != null ? !parameters.equals(that.parameters) : that.parameters != null) {
            return false;
        }
        if (point != null ? !point.equals(that.point) : that.point != null) {
            return false;
        }
        if (session != null ? !session.equals(that.session) : that.session != null) {
            return false;
        }
        if (status != null ? !status.equals(that.status) : that.status != null) {
            return false;
        }
        if (type != null ? !type.equals(that.type) : that.type != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = session != null ? session.hashCode() : 0;
        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        result = 31 * result + (point != null ? point.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (parameters != null ? parameters.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (diff != null ? diff.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SearchRequestAttributes{" +
                "session='" + session + '\'' +
                ", companyId=" + companyId +
                ", point=" + point +
                ", type='" + type + '\'' +
                ", parameters=" + parameters +
                ", status='" + status + '\'' +
                ", diff=" + diff +
                '}';
    }
}
