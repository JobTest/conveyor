package com.pb.biser.conveyor.controller.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author Alexander Bondarenko
 *         Date: 5/21/15
 *         Time: 5:33 PM
 */
public class DiffResponse {

    private String diff = "OK";

    public DiffResponse() {
    }

    public DiffResponse(String diff) {
        this.diff = diff;
    }

    public String getDiff() {
        return diff;
    }

    public void setDiff(String diff) {
        this.diff = diff;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof DiffResponse)) return false;

        DiffResponse that = (DiffResponse) o;

        return new EqualsBuilder()
                .append(diff, that.diff)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(diff)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "DiffResponse{" +
                "diff='" + diff + '\'' +
                '}';
    }
}
