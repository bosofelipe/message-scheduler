package com.luizalabs.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum MessageStatus {
    CANCELED("CANCELED"),
    SCHEDULED("SCHEDULED"),
    FINISHED("FINISHED");

    private String name;

    private MessageStatus(String name) {
        this.name = name;
    }

    private String getStatus() {
        return name;
    }

    @JsonCreator
    public static MessageStatus find(String name) {
        return Arrays.asList(MessageStatus.values()).stream()
                .filter(s -> s.name.equalsIgnoreCase(name)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException(name));
    }
}
