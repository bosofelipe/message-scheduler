package com.luizalabs.mapper;

import com.luizalabs.domain.Message;
import com.luizalabs.domain.MessageStatus;
import com.luizalabs.domain.Requester;
import com.luizalabs.domain.ResourceType;
import com.luizalabs.dto.MessageDTO;

public class MessageMapper {

    public static Message toMessage(MessageDTO messageDTO, Requester requester){
        return Message.builder()
                .requester(requester)
                .content(messageDTO.getContent())
                .dataTime(messageDTO.getDateTime())
                .resourceType(ResourceType.valueOf(messageDTO.getResourceType()))
                .status(MessageStatus.valueOf(messageDTO.getStatus())).build();
    }

    public static MessageDTO toMessageDTO(Message message){
        return MessageDTO.builder()
                .requester(message.getRequester().getName())
                .content(message.getContent())
                .dateTime(message.getDataTime())
                .resourceType(message.getResourceType().name())
                .status(message.getStatus().name()).build();
    }
}
