package com.luizalabs.controller;

import java.util.Map;


import com.luizalabs.ApiPageable;

import com.luizalabs.domain.MessageStatus;
import com.luizalabs.dto.MessageDTO;
import com.luizalabs.service.MessageService;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("api/message")
public class MessageController {

	@Autowired
	private MessageService messageService;

	@ApiOperation(value="Schedule a new message", notes="")
	@PostMapping(path = "/schedule", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<MessageDTO> schedule(@RequestBody MessageDTO messageDTO) {
		return new ResponseEntity<MessageDTO>( messageService.save(messageDTO), HttpStatus.OK);
	}

	@ApiOperation(value="Change a message status", notes="")
	@PatchMapping(path = "/change/status/{messageId}/{status}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE })
	public MessageDTO changeMessageStatus(@PathVariable("messageId") Long messageId, @PathVariable MessageStatus messageStatus) {
		return messageService.changeMessageStatus(messageId, messageStatus);
	}

	@ApiOperation(value="Delete a message", notes="")
	@DeleteMapping(path = "/delete/{messageId}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE })
	public Map<String, Boolean> delete(@PathVariable Long messageId) {
		return messageService.delete(messageId);
	}

	@ApiPageable
	@ApiOperation(value="Check message status", notes="")
	@GetMapping(path = "/checkMessageStatus", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<MessageStatus> status(@PathVariable("messageId") Long messageId) {
		return new ResponseEntity<MessageStatus>(messageService.checkMessageStatus(messageId), HttpStatus.OK);
	}
}
