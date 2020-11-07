package com.luizalabs.repository;

import com.luizalabs.domain.Message;
import com.luizalabs.domain.MessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {

    Optional<Message> findByIdAndStatus(Long messageId, MessageStatus status);
}
