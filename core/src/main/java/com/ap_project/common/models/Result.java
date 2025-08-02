package com.ap_project.models;

public class Result {
    public final boolean success;
    public final String message;

    public Result(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
