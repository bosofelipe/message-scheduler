package com.luizalabs.controller;


import java.util.HashMap;
import java.util.Map;


import com.luizalabs.ApiPageable;
import com.luizalabs.domain.Message;

import com.luizalabs.dto.MessageDTO;
import com.luizalabs.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
	public ResponseEntity<Page<Message>> list(
			@PageableDefault(size = 10, direction = Sort.Direction.DESC, sort = "id") Pageable pageable) {
		return new ResponseEntity<Page<Message>>(messageService.list(pageable), HttpStatus.OK);
	}
}
