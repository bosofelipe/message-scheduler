/*
package com.luizalabs.controller;

import com.luizalabs.domain.Message;
import com.luizalabs.domain.MessageStatus;
import com.luizalabs.domain.Requester;
import com.luizalabs.domain.ResourceType;
import com.luizalabs.dto.MessageDTO;
import com.luizalabs.mapper.MessageMapper;
import com.luizalabs.repository.MessageRepository;
import com.luizalabs.repository.RequesterRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableSpringDataWebSupport
public class MessageControllerIT {

    @Autowired
    private TestRestTemplate rest;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private RequesterRepository requesterRepository;

    @BeforeEach
    public void setup() {
        rest.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    @Test
    @Order(1)
    public void scheduleNewMessage() throws Exception {
        ResponseEntity<MessageDTO> response = rest.postForEntity("/api/message/save", createDefaultMessageDTO(), MessageDTO.class);
        Assertions.assertEquals(response.getBody().getContent(), "Aguardar chegada de novo integrante");
        Assertions.assertNotNull(response.getBody().getDateTime());
        Assertions.assertEquals(response.getBody().getStatus(), "SCHEDULED");
        Assertions.assertEquals(response.getBody().getRequester(), "Resource-1");
        Assertions.assertEquals(response.getBody().getResourceType(), "SMS");
    }

    @Test
    @Order(2)
    public void errorOnScheduleMessageNullContent() throws Exception {
        MessageDTO messageDTO = createDefaultMessageDTO();
        messageDTO.setContent(null);
        ResponseEntity<MessageDTO> response = rest.postForEntity("/api/message/save", messageDTO, MessageDTO.class);
        Assertions.assertEquals(response.getStatusCode().value(), 500);
    }

    @Test
    @Order(3)
    public void changeMessageStatus() throws Exception {
        createMessagesAndRequester();
        Message messageToChangeStatus = messageRepository.findAll().get(0);
        Assertions.assertEquals(messageToChangeStatus.getStatus(), MessageStatus.SCHEDULED);

        MessageDTO response = rest.put("/api/message/change/status/"+ messageToChangeStatus.getId(), MessageDTO.builder().status("SENT").build());

        Assertions.assertEquals(response.getContent(), "Aguardar chegada de novo integrante");
        Assertions.assertNotNull(response.getDateTime());
        Assertions.assertEquals(response.getStatus(), "SCHEDULED");
        Assertions.assertEquals(response.getRequester(), "Resource-1");
        Assertions.assertEquals(response.getResourceType(), "SMS");
    }

    @Disabled
    @Test
    @Order(3)
    public void listMessages() throws Exception {
        createMessagesAndRequester();
        ResponseEntity<List<Message>> rateResponse =
                rest.exchange("/api/message/list",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Message>>() {
        });
        Assertions.assertEquals(rateResponse.getBody().size(), 1);
    }

    @Test
    @Order(4)
    public void deleteMessage() throws Exception {
        Message messageToDelete = messageRepository.findAll().get(0);
        rest.delete("/api/message/delete/" + messageToDelete.getId());
        Optional<Message> messageDeleted = messageRepository.findById(messageToDelete.getId());
        Assertions.assertTrue(messageDeleted.isEmpty());
    }

    public void createMessagesAndRequester(){
        Requester requester = requesterRepository.save(Requester.builder().name("Requester-1").build());

        messageRepository.save(MessageMapper.toMessage(
                MessageDTO.builder().content("Aguardar chegada de novo integrante").requester("Resource-1")
                        .dateTime(LocalDateTime.now().plusDays(10)).status("SCHEDULED")
                        .resourceType(ResourceType.EMAIL.name()).build()
                , requester )
        );

        messageRepository.save(MessageMapper.toMessage(
                MessageDTO.builder().content("Verificar equipamentos").requester("Resource-1")
                        .dateTime(LocalDateTime.now().plusDays(10)).status("SCHEDULED")
                        .resourceType(ResourceType.SMS.name()).build()
                , requester )
        );

        messageRepository.save(MessageMapper.toMessage(
                MessageDTO.builder().content("Analisar todas as atividades").requester("Resource-1")
                        .dateTime(LocalDateTime.now().plusDays(10)).status("SCHEDULED")
                        .resourceType(ResourceType.PUSH.name()).build()
                , requester )
        );

        messageRepository.save(MessageMapper.toMessage(
                MessageDTO.builder().content("Analisar todas as atividades").requester("Resource-1")
                        .dateTime(LocalDateTime.now().plusDays(10)).status("SCHEDULED")
                        .resourceType(ResourceType.WHATSAPP.name()).build()
                , requester )
        );
    }

    public MessageDTO createDefaultMessageDTO(){
        return MessageDTO.builder().content("Aguardar chegada de novo integrante").requester("Resource-1")
                .dateTime(LocalDateTime.now().plusDays(10)).status("SCHEDULED")
                .resourceType("SMS").build();
    }
}
*/
