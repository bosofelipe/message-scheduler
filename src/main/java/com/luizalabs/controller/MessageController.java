package com.luizalabs.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.google.common.collect.Lists;
import com.luizalabs.ApiPageable;
import com.luizalabs.domain.Message;

import com.luizalabs.dto.MessageDTO;
import com.luizalabs.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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

	@ApiOperation(value="Save schedule message", notes="")
	@PostMapping(path = "/save", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<MessageDTO> save(@RequestBody MessageDTO messageDTO) {
		return new ResponseEntity<MessageDTO>( messageService.save(messageDTO), HttpStatus.OK);
	}

	@ApiOperation(value="Delete a message scheduled", notes="")
	@DeleteMapping(path = "/delete/{messageId}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE })
	public Map<String, Boolean> delete(@PathVariable Long messageId) {
		messageService.delete(messageId);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	@ApiPageable
	@ApiOperation(value="List messages paginated", notes="")
	@GetMapping(path = "/list", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE })
	public List<MessageDTO> list(Pageable pageable) {
		return messageService.list(pageable);
	}
}
