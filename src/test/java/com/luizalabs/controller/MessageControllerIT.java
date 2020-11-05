package com.luizalabs.service.controller;

import com.luizalabs.domain.Message;
import com.luizalabs.dto.MessageDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MessageControllerIT {

    @Autowired
    private TestRestTemplate rest;

    @Test
    public void saveNewMessage() throws Exception {
        MessageDTO messageDTO = createDefaultMessageDTO();
        ResponseEntity<MessageDTO> response = rest.postForEntity("/api/message/save", messageDTO, MessageDTO.class);
        Assertions.assertEquals(response.getStatusCode(), 200);
    }

    public MessageDTO createDefaultMessageDTO(){
        return MessageDTO.builder().content("Novo mensagem para agendar").requester("Resource-1")
                .dateTime(LocalDateTime.now().plusDays(10)).status("SCHEDULED")
                .resourceType("SMS").build();
    }
}
