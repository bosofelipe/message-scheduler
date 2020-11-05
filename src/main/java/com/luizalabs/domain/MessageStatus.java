package com.luizalabs.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Arrays;

public enum MessageStatus {
    SCHEDULED("SCHEDULED"),
    SENT("SENT");

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
                .filter(s -> s.getStatus().equals(name.toUpperCase())).findFirst()
                .orElseThrow(() -> new IllegalArgumentException(name));
    }
}
