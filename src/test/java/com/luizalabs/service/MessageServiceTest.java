package com.luizalabs.service;

import com.luizalabs.domain.CommunicationType;
import com.luizalabs.domain.Message;
import com.luizalabs.domain.MessageStatus;
import com.luizalabs.domain.Requester;
import com.luizalabs.dto.MessageDTO;
import com.luizalabs.exception.InvalidScheduleDateException;
import com.luizalabs.exception.MessageNotFoundException;
import com.luizalabs.mapper.MessageMapper;
import com.luizalabs.repository.MessageRepository;
import com.luizalabs.repository.RequesterRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Mockito;


import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

public class MessageServiceTest {

    @Spy
    private RequesterRepository requesterRepository;

    @Spy
    private MessageRepository messageRepository;

    @InjectMocks
    private MessageService messageService;

    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveNewMessage(){
        LocalDateTime messageDate = LocalDateTime.now().plusDays(2);
        MessageDTO newMessage = MessageDTO.builder().content("Nova compra feita no app MAGALU")
                .dateTime(messageDate)
                .status("SCHEDULED")
                .requester("UsuarioXYZ").communicationType("SMS")
                .build();

        Requester requester = Requester.builder()
                .id(1L)
                .name("UsuarioXYZ").build();

        Message message = MessageMapper.toMessage(newMessage, requester);

        Mockito.when(requesterRepository.findByName("UsuarioXYZ")).thenReturn(Optional.of(requester));
        Mockito.when(messageRepository.save(Mockito.any())).thenReturn(message);

        MessageDTO savedMessage = messageService.save(newMessage);

        Assertions.assertEquals(MessageStatus.SCHEDULED.name(), savedMessage.getStatus());
        Assertions.assertEquals("Nova compra feita no app MAGALU", savedMessage.getContent());
        Assertions.assertEquals(CommunicationType.SMS.name(), savedMessage.getCommunicationType());
        Assertions.assertEquals("UsuarioXYZ", savedMessage.getRequester());
    }

    @Test
    public void saveNewMessageWithNonexistentRequester(){
        LocalDateTime messageDate = LocalDateTime.now().plusDays(2);
        MessageDTO newMessage = MessageDTO.builder().content("Nova compra feita no app MAGALU")
                .dateTime(messageDate)
                .status("SCHEDULED")
                .requester("UsuarioXYZ").communicationType("SMS")
                .build();

        Requester requester = Requester.builder()
                .id(1L)
                .name("UsuarioXYZ").build();

        Message message = MessageMapper.toMessage(newMessage, requester);
        Mockito.when(requesterRepository.findByName("UsuarioXYZ")).thenReturn(Optional.empty());
        Mockito.when(requesterRepository.saveAndFlush(Mockito.any())).thenReturn(requester);
        Mockito.when(messageRepository.save(Mockito.any())).thenReturn(message);

        MessageDTO savedMessage = messageService.save(newMessage);

        Assertions.assertEquals(MessageStatus.SCHEDULED.name(), savedMessage.getStatus());
        Assertions.assertEquals("Nova compra feita no app MAGALU", savedMessage.getContent());
        Assertions.assertEquals(CommunicationType.SMS.name(), savedMessage.getCommunicationType());
        Assertions.assertEquals("UsuarioXYZ", savedMessage.getRequester());
    }

    @Test
    public void errorOnSaveMessageWithDatePast(){
        LocalDateTime messageDate = LocalDateTime.now().minusDays(10);
        MessageDTO newMessage = MessageDTO.builder().content("Nova compra feita no app MAGALU")
                .dateTime(messageDate)
                .status("SCHEDULED")
                .requester("UsuarioXYZ").communicationType("SMS")
                .build();

        Requester requester = Requester.builder()
                .id(1L)
                .name("UsuarioXYZ").build();

        Message message = MessageMapper.toMessage(newMessage, requester);

        Mockito.when(requesterRepository.findByName("UsuarioXYZ")).thenReturn(Optional.of(requester));
        Mockito.when(messageRepository.save(Mockito.any())).thenReturn(message);

        Throwable exceptionThatWasThrown = Assertions.assertThrows(InvalidScheduleDateException.class, () -> {
            messageService.save(newMessage);
        });
        Assertions.assertEquals("The message's scheduling date must be greater than the current date!", exceptionThatWasThrown.getMessage());
    }

    @Test
    public void changeMessageStatus(){
        LocalDateTime messageDate = LocalDateTime.now().plusDays(2);
        MessageDTO newMessage = MessageDTO.builder().content("Aviso de recebimento de produto")
                .dateTime(messageDate)
                .status("SCHEDULED")
                .requester("UsuarioXYZ").communicationType("EMAIL")
                .build();

        Requester requester = Requester.builder()
                .id(1L)
                .name("UsuarioXYZ").build();

        Message message = MessageMapper.toMessage(newMessage, requester);

        Mockito.when(messageRepository.findById(30L)).thenReturn(Optional.of(message));

        MessageDTO savedMessage = messageService.changeMessageStatus(30L, MessageStatus.FINISHED);

        Assertions.assertEquals(MessageStatus.FINISHED.name(), savedMessage.getStatus());
        Assertions.assertEquals("Aviso de recebimento de produto", savedMessage.getContent());
        Assertions.assertEquals(CommunicationType.EMAIL.name(), savedMessage.getCommunicationType());
        Assertions.assertEquals("UsuarioXYZ", savedMessage.getRequester() );
    }

    @Test
    public void messageNotFoundOnChangeStatus(){
        LocalDateTime messageDate = LocalDateTime.now().plusDays(2);
        MessageDTO newMessage = MessageDTO.builder().content("Aviso de recebimento de produto")
                .dateTime(messageDate)
                .status("SCHEDULED")
                .requester("UsuarioXYZ").communicationType("EMAIL")
                .build();

        Requester requester = Requester.builder()
                .id(1L)
                .name("UsuarioXYZ").build();

        Mockito.when(messageRepository.findByIdAndStatus(30L, MessageStatus.SCHEDULED)).thenReturn(Optional.empty());

        Throwable exceptionThatWasThrown = Assertions.assertThrows(MessageNotFoundException.class, () -> {
            messageService.changeMessageStatus(30L, MessageStatus.FINISHED);
        });
        Assertions.assertEquals("Message with id: 30 not found!", exceptionThatWasThrown.getMessage());
    }

    @Test
    public void deleteMessage(){
        LocalDateTime messageDate = LocalDateTime.now().plusDays(2);
        MessageDTO newMessage = MessageDTO.builder().content("Aviso de recebimento de produto")
                .dateTime(messageDate)
                .status("SCHEDULED")
                .requester("UsuarioXYZ").communicationType("EMAIL")
                .build();

        Requester requester = Requester.builder()
                .id(1L)
                .name("UsuarioXYZ").build();

        Message message = MessageMapper.toMessage(newMessage, requester);

        Mockito.when(messageRepository.findById(1000L)).thenReturn(Optional.of(message));

        Map<String, Boolean> messageDeleted = messageService.delete(1000L);

        Assertions.assertTrue(messageDeleted.get("deleted"));
    }

    @Test
    public void messageNotFoundOnDelete(){
        LocalDateTime messageDate = LocalDateTime.now().plusDays(2);
        MessageDTO newMessage = MessageDTO.builder().content("Aviso de recebimento de produto")
                .dateTime(messageDate)
                .status("SCHEDULED")
                .requester("UsuarioXYZ").communicationType("EMAIL")
                .build();

        Requester requester = Requester.builder()
                .id(1L)
                .name("UsuarioXYZ").build();

        Message message = MessageMapper.toMessage(newMessage, requester);

        Mockito.when(messageRepository.findByIdAndStatus(500L, MessageStatus.SCHEDULED)).thenReturn(Optional.empty());

        Throwable exceptionThatWasThrown = Assertions.assertThrows(MessageNotFoundException.class, () -> {
            messageService.delete(1000L);
        });
        Assertions.assertEquals("Message with id: 1000 not found!", exceptionThatWasThrown.getMessage());
    }

    @Test
    public void checkMessageStatus(){
        LocalDateTime messageDate = LocalDateTime.now().plusDays(2);
        MessageDTO newMessage = MessageDTO.builder().content("Aviso de produto extraviado")
                .dateTime(messageDate).status("FINISHED")
                .requester("UsuarioMPQ").communicationType("PUSH")
                .build();

        Requester requester = Requester.builder()
                .id(11L)
                .name("UsuarioMPQ").build();

        Message message = MessageMapper.toMessage(newMessage, requester);

        Mockito.when(messageRepository.findById(1000L)).thenReturn(Optional.of(message));
        Assertions.assertEquals(MessageStatus.FINISHED, messageService.checkMessageStatus(1000L));
    }

    @Test
    public void messageNotFoundOnCheckStatus(){
        LocalDateTime messageDate = LocalDateTime.now().plusDays(2);
        MessageDTO newMessage = MessageDTO.builder().content("Aviso de produto extraviado")
                .dateTime(messageDate)
                .status("FINISHED")
                .requester("UsuarioMPQ").communicationType("EMAIL")
                .build();

        Requester requester = Requester.builder()
                .id(23L)
                .name("UsuarioMPQ").build();

        Message message = MessageMapper.toMessage(newMessage, requester);

        Mockito.when(messageRepository.findById(4400L)).thenReturn(Optional.empty());

        Throwable exceptionThatWasThrown = Assertions.assertThrows(MessageNotFoundException.class, () -> {
            messageService.delete(4400L);
        });

        Assertions.assertEquals("Message with id: 4400 not found!", exceptionThatWasThrown.getMessage());
    }
}
