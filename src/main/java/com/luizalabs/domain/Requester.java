package com.luizalabs.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@Entity
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Requester {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "requester", fetch = FetchType.LAZY)
    private List<Message> messages;
}
