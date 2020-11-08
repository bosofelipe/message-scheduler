package com.luizalabs.mapper;

import com.luizalabs.domain.Message;
import com.luizalabs.domain.MessageStatus;
import com.luizalabs.domain.Requester;
import com.luizalabs.domain.CommunicationType;
import com.luizalabs.dto.MessageDTO;

public class MessageMapper {

    public static Message toMessage(MessageDTO messageDTO, Requester requester){
        return Message.builder()
                .requester(requester)
                .content(messageDTO.getContent())
                .dataTime(messageDTO.getDateTime())
                .comunicationType(CommunicationType.valueOf(messageDTO.getCommunicationType()))
                .status(MessageStatus.valueOf(messageDTO.getStatus())).build();
    }

    public static MessageDTO toMessageDTO(Message message){
        return MessageDTO.builder()
                .requester(message.getRequester().getName())
                .content(message.getContent())
                .dateTime(message.getDataTime())
                .id(message.getId())
                .communicationType(message.getComunicationType().name())
                .status(message.getStatus().name()).build();
    }
}
