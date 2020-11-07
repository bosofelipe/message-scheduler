package com.luizalabs.service;

import com.luizalabs.domain.Message;
import com.luizalabs.domain.MessageStatus;
import com.luizalabs.domain.Requester;
import com.luizalabs.dto.MessageDTO;
import com.luizalabs.exception.InvalidScheduleDateException;
import com.luizalabs.exception.MessageNotFoundException;
import com.luizalabs.mapper.MessageMapper;
import com.luizalabs.repository.MessageRepository;
import com.luizalabs.repository.RequesterRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MessageService {

    @Autowired
    private RequesterRepository requesterRepository;
    @Autowired
    private MessageRepository messageRepository;

    public MessageDTO save(MessageDTO messageDTO){
        if(LocalDateTime.now().isAfter(messageDTO.getDateTime())){
            throw new InvalidScheduleDateException("The message's scheduling date must be greater than the current date!");
        }

        Optional<Requester> requesterObj = requesterRepository.findByName(messageDTO.getRequester());
        Requester requester = null;

        if(requesterObj.isEmpty()){
            requester = requesterRepository.saveAndFlush(Requester.builder().name(messageDTO.getRequester()).build());
        }else{
            requester = requesterObj.get();
        }

        Message message = MessageMapper.toMessage(messageDTO, requester);
        return MessageMapper.toMessageDTO(messageRepository.save(message));
    }

    public MessageDTO changeMessageStatus( Long messageId, MessageStatus messageStatus) {
        Message message = messageRepository.findByIdAndStatus(messageId, MessageStatus.SCHEDULED)
                .orElseThrow(() -> new MessageNotFoundException(messageId.toString()));
        message.setStatus(messageStatus);

        return MessageMapper.toMessageDTO(message);
    }

    public Map<String, Boolean> delete(Long messageId) {
        Message message = messageRepository.findByIdAndStatus(messageId, MessageStatus.SCHEDULED)
                .orElseThrow(() -> new MessageNotFoundException(messageId.toString()));
        messageRepository.delete(message);

        Map<String, Boolean> deleted = new HashMap<>();
        deleted.put("deleted", Boolean.TRUE);

        return deleted;
    }

    public MessageStatus checkMessageStatus(Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new MessageNotFoundException(messageId.toString()));
        return message.getStatus();
    }
}
