package com.luizalabs.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {

    @ApiModelProperty(notes = "Solicitante para agendamento de envio de mensagem",  example = "Usuário APT", required = true, position = 0)
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
