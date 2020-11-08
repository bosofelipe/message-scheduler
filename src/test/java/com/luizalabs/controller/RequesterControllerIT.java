package com.luizalabs.controller;

import com.luizalabs.domain.CommunicationType;
import com.luizalabs.domain.Requester;
import com.luizalabs.dto.MessageDTO;
import com.luizalabs.mapper.MessageMapper;
import com.luizalabs.repository.MessageRepository;
import com.luizalabs.repository.RequesterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableSpringDataWebSupport
public class RequesterControllerIT {

    @Autowired
    private TestRestTemplate rest;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private RequesterRepository requesterRepository;

    @BeforeEach
    private void initData(){
        createMessagesAndRequester();
    }

    @Test
    @Order(1)
    public void listRequesters() throws Exception {
        Map<String, Object> requesters = Objects.requireNonNull(
                rest.exchange("/api/requester/list", HttpMethod.GET, null, new ParameterizedTypeReference<Map<String, Object>>() {
                }).getBody());

        List<Map<String, Object>> requestersResult = (List)requesters.get("requesters");
        Assertions.assertEquals(requestersResult.get(0).get("name"), "Requester-101");
        Assertions.assertEquals(requestersResult.size(), 10);
        Assertions.assertEquals(requesters.get("totalPages"), 3);
        Assertions.assertEquals(requesters.get("totalItems"), 30);
        Assertions.assertEquals(requesters.get("currentPage"), 0);
    }

    @Test
    @Order(2)
    public void listRequestersPaging() throws Exception {
        Map<String, Object> requesters = Objects.requireNonNull(
                rest.exchange("/api/requester/list?page=1", HttpMethod.GET, null, new ParameterizedTypeReference<Map<String, Object>>() {
                }).getBody());

        List<Map<String, Object>> requestersResult = (List)requesters.get("requesters");
        Assertions.assertEquals(requestersResult.get(0).get("name"), "Requester-106");
        Assertions.assertEquals(requestersResult.size(), 10);
        Assertions.assertEquals(requesters.get("totalPages"), 3);
        Assertions.assertEquals(requesters.get("totalItems"), 30);
        Assertions.assertEquals(requesters.get("currentPage"), 1);

    }

    private void createMessagesAndRequester(){
        for(int i= 101; i< 116; i++){
            messageRepository.save(MessageMapper.toMessage(
                    MessageDTO.builder().content("Mensagem de chegada " + i)
                            .dateTime(LocalDateTime.now().plusDays(10)).status("SCHEDULED")
                            .communicationType(CommunicationType.EMAIL.name()).build()
                    , requesterRepository.save(Requester.builder().name("Requester-" + i).build()) )
            );

            messageRepository.save(MessageMapper.toMessage(
                    MessageDTO.builder().content("Mensagem de chegada " + i)
                            .dateTime(LocalDateTime.now().plusDays(10)).status("SCHEDULED")
                            .communicationType(CommunicationType.PUSH.name()).build()
                    , requesterRepository.save(Requester.builder().name("Requester-" + i).build()) )
            );
        }
    }

    @AfterEach
    private void tearDown(){
        messageRepository.deleteAll();
        requesterRepository.deleteAll();
    }
}
