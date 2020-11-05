package com.luizalabs.exception;

public class MessageNotFoundException extends RuntimeException {

    public MessageNotFoundException(String id) {
        super(String.format("Message with id: %s not found!", id));
    }
}
