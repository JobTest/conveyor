package com.pb.biser.conveyor.controller.entity;

/**
 * @author tasman
 */
public class CompanyStats {

    private Long id;
    private boolean active;

    public Long getId() {
        return id;
    }

    public boolean isActive() {
        return active;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CompanyStats that = (CompanyStats) o;

        if (active != that.active) {
            return false;
        }
        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (active ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CompanyStats{" +
                "id=" + id +
                ", active=" + active +
                '}';
    }
}
