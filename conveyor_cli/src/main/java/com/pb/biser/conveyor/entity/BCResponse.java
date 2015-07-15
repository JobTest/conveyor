package com.pb.biser.conveyor.entity;

/**
 * Created by diver on 5/19/15.
 */
public class BCResponse {

    private String request_proc;
    private BCResponseOps[] ops;

    public String getRequest_proc() {
        return request_proc;
    }

    public void setRequest_proc(String request_proc) {
        this.request_proc = request_proc;
    }

    public BCResponseOps[] getOps() {
        return ops;
    }

    public void setOps(BCResponseOps[] ops) {
        this.ops = ops;
    }
}
