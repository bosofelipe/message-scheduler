package com.luizalabs.mapper;

import com.luizalabs.domain.Message;
import com.luizalabs.domain.Requester;
import com.luizalabs.domain.ResourceType;
import com.luizalabs.dto.MessageDTO;
import com.luizalabs.mapper.MessageMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

public class MessageMapperTest {

    public MessageMapperTest() {
    }

    @BeforeEach
    public void init(){
    }

    @Test
    public void convertToMessage(){

        Requester requester = Requester.builder().id(1L).name("Requester 1").build();

        MessageDTO messageDTO = MessageDTO.builder().content("Novo produto importado").requester("Resource-1")
                .dateTime(LocalDateTime.now().plusDays(5))
                .resourceType("SMS").build();

        Message message = MessageMapper.toMessage(messageDTO, requester);

        Assertions.assertEquals(message.getContent(), "Novo produto importado");
        Assertions.assertNotNull(message.getDataTime());
        Assertions.assertEquals(message.getResourceType(), ResourceType.SMS);
    }
}
