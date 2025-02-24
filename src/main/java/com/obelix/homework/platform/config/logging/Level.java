package com.obelix.homework.platform.config.logging;

public enum Level {
    INFO("INFO"),
    WARN("WARN"),
    ERROR("ERROR");

    public String value;

    Level(String value) {
        this.value = value;
    }
}
