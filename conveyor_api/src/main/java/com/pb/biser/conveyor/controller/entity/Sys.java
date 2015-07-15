package com.pb.biser.conveyor.controller.entity;

/**
 * Created by diver on 5/12/15.
 */
public class Sys {

    public String ref;
    public String obj_id;
    public String conv_id;
    public String node_id;

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getObj_id() {
        return obj_id;
    }

    public void setObj_id(String obj_id) {
        this.obj_id = obj_id;
    }

    public String getConv_id() {
        return conv_id;
    }

    public void setConv_id(String conv_id) {
        this.conv_id = conv_id;
    }

    public String getNode_id() {
        return node_id;
    }

    public void setNode_id(String node_id) {
        this.node_id = node_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sys sys = (Sys) o;

        if (ref != null ? !ref.equals(sys.ref) : sys.ref != null) return false;
        if (obj_id != null ? !obj_id.equals(sys.obj_id) : sys.obj_id != null) return false;
        if (conv_id != null ? !conv_id.equals(sys.conv_id) : sys.conv_id != null) return false;
        return !(node_id != null ? !node_id.equals(sys.node_id) : sys.node_id != null);

    }

    @Override
    public int hashCode() {
        int result = ref != null ? ref.hashCode() : 0;
        result = 31 * result + (obj_id != null ? obj_id.hashCode() : 0);
        result = 31 * result + (conv_id != null ? conv_id.hashCode() : 0);
        result = 31 * result + (node_id != null ? node_id.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Sys{" +
                "ref='" + ref + '\'' +
                ", obj_id='" + obj_id + '\'' +
                ", conv_id='" + conv_id + '\'' +
                ", node_id='" + node_id + '\'' +
                '}';
    }
}
