package com.luizalabs.service;

import com.luizalabs.domain.Message;
import com.luizalabs.domain.Requester;
import com.luizalabs.dto.MessageDTO;
import com.luizalabs.exception.InvalidScheduleDateException;
import com.luizalabs.mapper.MessageMapper;
import com.luizalabs.repository.MessageRepository;
import com.luizalabs.repository.RequesterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private RequesterRepository requesterRepository;
    @Autowired
    private MessageRepository messageRepository;

    public MessageDTO save(MessageDTO messageDTO){
        if(LocalDateTime.now().isAfter(messageDTO.getDateTime())){
            throw new InvalidScheduleDateException("Data de agendamento maior que data atual");
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
}
