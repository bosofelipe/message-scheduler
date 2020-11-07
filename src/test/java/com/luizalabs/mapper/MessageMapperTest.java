package com.luizalabs.mapper;

import com.luizalabs.domain.Message;
import com.luizalabs.domain.MessageStatus;
import com.luizalabs.domain.Requester;
import com.luizalabs.domain.ResourceType;
import com.luizalabs.dto.MessageDTO;
import com.luizalabs.mapper.MessageMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

public class MessageMapperTest {

    @Test
    public void toMessage(){
        Requester requester = Requester.builder().id(1L).name("Requester 1").build();
        MessageDTO messageDTO = MessageDTO.builder().content("Novo produto importado").requester("Resource-1")
                .dateTime(LocalDateTime.now().plusDays(5)).status("CANCELED")
                .resourceType("SMS").build();
        Message message = MessageMapper.toMessage(messageDTO, requester);
        Assertions.assertEquals("Novo produto importado", message.getContent());
        Assertions.assertNotNull(message.getDataTime());
        Assertions.assertEquals(MessageStatus.CANCELED, message.getStatus());
        Assertions.assertEquals(ResourceType.SMS, message.getResourceType());
    }

    @Test
    public void toMessageDTO(){
        Requester requester = Requester.builder().id(1L).name("Requester 1").build();
        Message message = Message.builder().content("Carregamento para nova filial")
                .requester(Requester.builder().id(1L).name("Gerente Loja 1").build())
                .dataTime(LocalDateTime.now().plusDays(1))
                .status(MessageStatus.FINISHED)
                .resourceType(ResourceType.PUSH).build();
        MessageDTO messageDTO = MessageMapper.toMessageDTO(message);
        Assertions.assertEquals("Gerente Loja 1", messageDTO.getRequester());
        Assertions.assertEquals("Carregamento para nova filial", message.getContent());
        Assertions.assertNotNull(message.getDataTime());
        Assertions.assertEquals(MessageStatus.FINISHED, message.getStatus());
        Assertions.assertEquals(ResourceType.PUSH, message.getResourceType());
    }

}
