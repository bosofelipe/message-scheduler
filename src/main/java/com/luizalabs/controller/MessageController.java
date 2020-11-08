package com.luizalabs.controller;

import com.luizalabs.ApiPageable;
import com.luizalabs.domain.MessageStatus;
import com.luizalabs.dto.MessageDTO;
import com.luizalabs.service.MessageService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("api/message")
public class MessageController {

	@Autowired
	private MessageService messageService;

	@ApiOperation(value="Schedule a new message", notes="")
	@PostMapping(path = "/schedule", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<MessageDTO> schedule(@RequestBody @Valid MessageDTO messageDTO) {
		return new ResponseEntity<MessageDTO>( messageService.save(messageDTO), HttpStatus.OK);
	}

	@ApiOperation(value="Change a message status", notes="")
	@PutMapping(path = "/{messageId}/change/status/{messageStatus}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE })
	public MessageDTO changeMessageStatus(@PathVariable("messageId") Long messageId, @PathVariable MessageStatus messageStatus) {
		return messageService.changeMessageStatus(messageId, messageStatus);
	}

	@ApiPageable
	@ApiOperation(value="Change message properties", notes="")
	@PutMapping(path = "/{messageId}/change", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<MessageDTO> change(@PathVariable("messageId") Long messageId, @RequestBody @Valid MessageDTO messageDTO) {
		return new ResponseEntity<MessageDTO>(messageService.change(messageId, messageDTO), HttpStatus.OK);
	}

	@ApiOperation(value="Delete a message", notes="")
	@DeleteMapping(path = "/{messageId}/delete", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE })
	public Map<String, Boolean> delete(@PathVariable Long messageId) {
		return messageService.delete(messageId);
	}

	@ApiPageable
	@ApiOperation(value="Check message status", notes="")
	@GetMapping(path = "/{messageId}/check/status", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<MessageStatus> checkStatus(@PathVariable("messageId") Long messageId) {
		return new ResponseEntity<MessageStatus>(messageService.checkMessageStatus(messageId), HttpStatus.OK);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();

		ex.getBindingResult().getFieldErrors().forEach(error ->
				errors.put(error.getField(), error.getDefaultMessage()));

		return errors;
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(ConstraintViolationException.class)
	public Map<String, String> handleConstraintViolation(ConstraintViolationException ex) {
		Map<String, String> errors = new HashMap<>();

		ex.getConstraintViolations().forEach(cv -> {
			errors.put("message", cv.getMessage());
			errors.put("path", (cv.getPropertyPath()).toString());
		});

		return errors;
	}
}
