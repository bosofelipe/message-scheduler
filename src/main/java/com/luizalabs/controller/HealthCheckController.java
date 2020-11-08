package com.luizalabs.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HealthCheckController {

    @GetMapping(path = "/status")
    public ResponseEntity<String> ping()  {
        return new ResponseEntity<String>( "OK", HttpStatus.OK);
    }
}
