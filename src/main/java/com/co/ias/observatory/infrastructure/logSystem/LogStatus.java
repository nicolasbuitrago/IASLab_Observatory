package com.co.ias.observatory.infrastructure.logSystem;

public enum LogStatus {

    ERROR("ERROR"),
    INFO("INFO"),
    DEBUG("DEBUG");

    private final String level;

    LogStatus(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

}
