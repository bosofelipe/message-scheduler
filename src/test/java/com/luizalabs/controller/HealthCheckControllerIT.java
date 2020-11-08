package com.luizalabs.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableSpringDataWebSupport
public class HealthCheckControllerIT {

    @Autowired
    private TestRestTemplate rest;

    @Test
    public void status() throws Exception {
        ResponseEntity<String> response = rest.getForEntity("/api/status", String.class);
        Assertions.assertEquals(response.getBody(), "OK");
    }
}
