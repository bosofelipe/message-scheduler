package com.luizalabs.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {

    @JsonProperty("requester")
    private String requester;

    @JsonProperty("dateTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTime;

    @JsonProperty("content")
    private String content;

    @JsonProperty("resourceType")
    private String resourceType;

    @JsonProperty("status")
    private String status;

}
