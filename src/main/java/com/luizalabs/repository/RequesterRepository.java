package com.luizalabs.repository;

import com.luizalabs.domain.Requester;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RequesterRepository extends JpaRepository<Requester, Long> {

    Optional<Requester> findByName(String name);
}
