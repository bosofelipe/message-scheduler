package com.luizalabs.controller;


import java.util.List;


import com.google.common.collect.Lists;
import com.luizalabs.ApiPageable;
import com.luizalabs.domain.Message;

import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/message")
public class MessageController {

	@ApiPageable
	@ApiOperation(value="List messages paginated", notes="")
	@GetMapping(path = "/list", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE })
	public List<String> list(Pageable pageable) {
		return Lists.newArrayList();
	}
}
