package com.luizalabs.controller;


import java.util.List;


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

	@ApiPageable
	@ApiOperation(value="List messages paginated", notes="")
	@GetMapping(path = "/list", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE })
	public List<String> list(Pageable pageable) {
		return Lists.newArrayList();
	}

	@ApiPageable
	@ApiOperation(value="Save schedule message", notes="")
	@PostMapping(path = "/save", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<MessageDTO> save(@RequestBody MessageDTO messageDTO) {
		return new ResponseEntity<MessageDTO>( messageService.save(messageDTO), HttpStatus.OK);
	}
}
