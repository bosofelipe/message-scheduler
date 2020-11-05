package com.luizalabs.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Requester {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "requester", fetch = FetchType.LAZY)
    private List<Message> messages;
}
