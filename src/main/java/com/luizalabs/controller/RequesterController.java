package com.luizalabs.controller;

import com.luizalabs.domain.Requester;
import com.luizalabs.repository.RequesterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/requester")
public class RequesterController {

    @Autowired
    RequesterRepository requesterRepository;

    @Transactional
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> listRequesters(
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        try {
            List<Requester> requesters = new ArrayList<>();
            Pageable paging = PageRequest.of(page, size);

            Page<Requester> pageRequester;
            if (title == null)
                pageRequester = requesterRepository.findAll(paging);
            else
                pageRequester = requesterRepository.findByNameContaining(title, paging);

            requesters = pageRequester.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("requesters", requesters);
            response.put("currentPage", pageRequester.getNumber());
            response.put("totalItems", pageRequester.getTotalElements());
            response.put("totalPages", pageRequester.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
