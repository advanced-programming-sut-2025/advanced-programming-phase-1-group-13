package com.project.models;

public record Result(boolean success, String message) {
    @Override
    public String toString() {
        return message;
    }
}
