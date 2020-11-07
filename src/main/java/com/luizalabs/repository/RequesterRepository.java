package com.luizalabs.repository;

import com.luizalabs.domain.Requester;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RequesterRepository extends JpaRepository<Requester, Long> {

    Optional<Requester> findByName(String name);

    Page<Requester> findById(Long id, Pageable pageable);
    Page<Requester> findByNameContaining(String name, Pageable pageable);
}
