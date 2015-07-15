package com.pb.biser.conveyor.dao.entity;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * @author tasman
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Diff {
    private String biserPack;
    private String api2xPack;
    private String biserCode;
    private String api2xCode;

    public Diff(String biserPack, String api2xPack) {
        this.biserPack = biserPack;
        this.api2xPack = api2xPack;
    }

    public Diff() {
    }

    public String getBiserPack() {
        return biserPack;
    }

    public String getApi2xPack() {
        return api2xPack;
    }

    public String getBiserCode() {
        return biserCode;
    }

    public void setBiserCode(String biserCode) {
        this.biserCode = biserCode;
    }

    public String getApi2xCode() {
        return api2xCode;
    }

    public void setApi2xCode(String api2xCode) {
        this.api2xCode = api2xCode;
    }

    public void setApi2xPack(String api2xPack) {
        this.api2xPack = api2xPack;
    }

    public void setBiserPack(String biserPack) {
        this.biserPack = biserPack;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Diff diff = (Diff) o;

        if (api2xCode != null ? !api2xCode.equals(diff.api2xCode) : diff.api2xCode != null) {
            return false;
        }
        if (api2xPack != null ? !api2xPack.equals(diff.api2xPack) : diff.api2xPack != null) {
            return false;
        }
        if (biserCode != null ? !biserCode.equals(diff.biserCode) : diff.biserCode != null) {
            return false;
        }
        if (biserPack != null ? !biserPack.equals(diff.biserPack) : diff.biserPack != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = biserPack != null ? biserPack.hashCode() : 0;
        result = 31 * result + (api2xPack != null ? api2xPack.hashCode() : 0);
        result = 31 * result + (biserCode != null ? biserCode.hashCode() : 0);
        result = 31 * result + (api2xCode != null ? api2xCode.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Diff{" +
                "biserPack='" + biserPack + '\'' +
                ", api2xPack='" + api2xPack + '\'' +
                ", biserCode='" + biserCode + '\'' +
                ", api2xCode='" + api2xCode + '\'' +
                '}';
    }
}
