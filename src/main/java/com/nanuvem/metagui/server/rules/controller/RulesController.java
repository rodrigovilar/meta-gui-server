package com.nanuvem.metagui.server.rules.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nanuvem.metagui.server.api.Rule;

@Controller
@RequestMapping(value = "/rules/")
public class RulesController {

	@ResponseBody
	public <T extends Rule> ResponseEntity<T[]> getAll() {
		return new ResponseEntity<T[]>(HttpStatus.OK);
	}
	
}
