package com.luizalabs.controller;

import com.luizalabs.domain.CommunicationType;
import com.luizalabs.domain.Message;
import com.luizalabs.domain.MessageStatus;
import com.luizalabs.domain.Requester;
import com.luizalabs.dto.MessageDTO;
import com.luizalabs.mapper.MessageMapper;
import com.luizalabs.repository.MessageRepository;
import com.luizalabs.repository.RequesterRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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

    @Test
    @Order(1)
    public void scheduleNewMessage() throws Exception {
        ResponseEntity<MessageDTO> response = rest.postForEntity("/api/message/schedule", createDefaultMessageDTO(), MessageDTO.class);
        Assertions.assertEquals(response.getBody().getContent(), "Aguardar chegada de novo integrante");
        Assertions.assertNotNull(response.getBody().getDateTime());
        Assertions.assertEquals(response.getBody().getStatus(), "SCHEDULED");
        Assertions.assertEquals(response.getBody().getRequester(), "Resource-1");
        Assertions.assertEquals(response.getBody().getCommunicationType(), "SMS");
    }

    @Test
    @Order(2)
    public void errorOnScheduleMessageNullContent() throws Exception {
        MessageDTO messageDTO = createDefaultMessageDTO();
        messageDTO.setContent(null);
        ResponseEntity<MessageDTO> response = rest.postForEntity("/api/message/schedule", messageDTO, MessageDTO.class);
        Assertions.assertEquals(response.getStatusCode().value(), 400);
        Assertions.assertEquals("Message content cannot be empty!", response.getBody().getContent());
    }

    @Test
    @Order(3)
    public void errorOnScheduleInvalidCommunicationType() throws Exception {
        MessageDTO messageDTO = createDefaultMessageDTO();
        messageDTO.setCommunicationType("MOTOBOY");
        ResponseEntity<MessageDTO> response = rest.postForEntity("/api/message/schedule", messageDTO, MessageDTO.class);
        Assertions.assertEquals(response.getStatusCode().value(), 500);
    }

    @Test
    @Order(4)
    public void errorOnScheduleInvalidDate() throws Exception {
        MessageDTO messageDTO = createDefaultMessageDTO();
        messageDTO.setDateTime(LocalDateTime.now().minusDays(20));
        ResponseEntity<MessageDTO> response = rest.postForEntity("/api/message/schedule", messageDTO, MessageDTO.class);
        Assertions.assertEquals(response.getStatusCode().value(), 500);
    }

    @Test
    @Order(4)
    public void errorOnScheduleInvalidStatus() throws Exception {
        MessageDTO messageDTO = createDefaultMessageDTO();
        messageDTO.setStatus("ILLEGAL");
        ResponseEntity<MessageDTO> response = rest.postForEntity("/api/message/schedule", messageDTO, MessageDTO.class);
        Assertions.assertEquals(response.getStatusCode().value(), 500);
    }

    @Test
    @Order(5)
    public void changeMessageStatus() throws Exception {
        createMessagesAndRequester();
        Message messageToChangeStatus = messageRepository.findAll().get(0);
        Assertions.assertEquals(messageToChangeStatus.getStatus(), MessageStatus.SCHEDULED);

        ResponseEntity<MessageDTO> response = rest.exchange("/api/message/"+messageToChangeStatus.getId()+"/change/status/FINISHED", HttpMethod.PUT, null, MessageDTO.class);
        Assertions.assertEquals(response.getBody().getStatus(), "FINISHED");
    }

    @Test
    @Order(6)
    public void changeMessage() throws Exception {
        createMessagesAndRequester();
        Message messageToChangeStatus = messageRepository.findAll().get(0);
        Assertions.assertEquals(messageToChangeStatus.getStatus(), MessageStatus.SCHEDULED);

        MessageDTO updatedMessageDTO = MessageDTO.builder().content("Alteração de cadastro")
                .status("FINISHED").communicationType("EMAIL").dateTime(LocalDateTime.now().plusHours(40))
                .requester("Novo")
                .build();

        rest.put("/api/message/"+messageToChangeStatus.getId()+"/change", updatedMessageDTO, MessageDTO.class);

        Optional<Message> updatedMessage = messageRepository.findById(messageToChangeStatus.getId());

        Assertions.assertEquals(updatedMessage.get().getContent(), "Alteração de cadastro");
        Assertions.assertEquals(updatedMessage.get().getStatus(), MessageStatus.FINISHED);
        Assertions.assertEquals(updatedMessage.get().getCommunicationType(), CommunicationType.EMAIL);
        Assertions.assertEquals(updatedMessage.get().getRequester().getName(), "Novo");
        Assertions.assertNotNull(updatedMessage.get().getDataTime());
    }

    @Test
    @Order(7)
    public void checkMessageStatus() throws Exception {
        createMessagesAndRequester();
        List<Message> messages = messageRepository.findAll();

        ResponseEntity<MessageStatus> response = rest.getForEntity("/api/message/"+messages.get(0).getId()+"/check/status", MessageStatus.class);
        Assertions.assertEquals(response.getBody().name(), "SCHEDULED");

        response = rest.getForEntity("/api/message/"+messages.get(1).getId()+"/check/status", MessageStatus.class);
        Assertions.assertEquals(response.getBody().name(), "FINISHED");

        response = rest.getForEntity("/api/message/"+messages.get(2).getId()+"/check/status", MessageStatus.class);
        Assertions.assertEquals(response.getBody().name(), "CANCELED");

        response = rest.getForEntity("/api/message/"+messages.get(3).getId()+"/check/status", MessageStatus.class);
        Assertions.assertEquals(response.getBody().name(), "SCHEDULED");
    }

    @Test
    @Order(8)
    public void deleteMessage() throws Exception {
        createMessagesAndRequester();
        Message messageToDelete = messageRepository.findAll().get(0);
        rest.delete("/api/message/"+messageToDelete.getId()+ "/delete");
        Optional<Message> messageDeleted = messageRepository.findById(messageToDelete.getId());
        Assertions.assertTrue(messageDeleted.isEmpty());
    }

    private void createMessagesAndRequester(){
        Requester requester = requesterRepository.save(Requester.builder().name("Requester-1").build());

        messageRepository.save(MessageMapper.toMessage(
                MessageDTO.builder().content("Aguardar chegada de novo integrante").requester("Resource-30")
                        .dateTime(LocalDateTime.now().plusDays(10)).status("SCHEDULED")
                        .communicationType(CommunicationType.EMAIL.name()).build()
                , requester )
        );

        messageRepository.save(MessageMapper.toMessage(
                MessageDTO.builder().content("Dados incorretos do contato").requester("Resource-10")
                        .dateTime(LocalDateTime.now().plusDays(10)).status("FINISHED")
                        .communicationType(CommunicationType.SMS.name()).build()
                , requester )
        );

        messageRepository.save(MessageMapper.toMessage(
                MessageDTO.builder().content("Novo produto para entrega").requester("Resource-23")
                        .dateTime(LocalDateTime.now().plusDays(10)).status("CANCELED")
                        .communicationType(CommunicationType.PUSH.name()).build()
                , requester )
        );

        messageRepository.save(MessageMapper.toMessage(
                MessageDTO.builder().content("Teste de envio").requester("Resource-42")
                        .dateTime(LocalDateTime.now().plusDays(10)).status("SCHEDULED")
                        .communicationType(CommunicationType.WHATSAPP.name()).build()
                , requester )
        );
    }

    @AfterEach
    public void tearDown(){
        messageRepository.deleteAll();
        requesterRepository.deleteAll();
    }

    private MessageDTO createDefaultMessageDTO(){
        return MessageDTO.builder().content("Aguardar chegada de novo integrante").requester("Resource-1")
                .dateTime(LocalDateTime.now().plusDays(10)).status("SCHEDULED")
                .communicationType("SMS").build();
    }
}
