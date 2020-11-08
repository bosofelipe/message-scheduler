package com.luizalabs.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(
        value       = "Message to sent",
        description = "Data to schedule a message"
)
public class MessageDTO {

    private Long id;

    @NotEmpty(message = "The requester of the message cannot be empty!")
    @JsonProperty("requester")
    @ApiModelProperty(notes = "Requester to schedule message sending",  example = "Usuário APT", required = true, position = 0)
    private String requester;

    @JsonProperty("dateTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(notes = "Schedule date and time",  example = "2035-10-05 12:30:00", required = true, position = 1)
    private LocalDateTime dateTime;

    @NotEmpty(message = "Message content cannot be empty!")
    @JsonProperty("content")
    @ApiModelProperty(notes = "Content of message",  example = "Nova nota fiscal para faturamento", required = true, position = 2)
    private String content;

    @NotEmpty(message = "Define the communication type of the message!")
    @JsonProperty("resourceType")
    @ApiModelProperty(notes = "Type of communication that the message will be scheduled to send",  allowableValues = "EMAIL, SMS, PUSH, WHATSAPP", example = "SMS", required = true, position = 2)
    private String communicationType;

    @NotEmpty(message = "Set the message status!")
    @JsonProperty("status")
    @ApiModelProperty(notes = "Message scheduling status",  allowableValues = "CANCELED, SCHEDULED, FINISHED", example = "FINISHED", required = true, position = 3)
    private String status;

}
