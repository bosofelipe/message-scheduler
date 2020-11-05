package com.luizalabs.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Date data;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "id"), nullable = false)
    private Requester requester;

    @Column(nullable = false)
    private MessageStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ResourceType type;
}
