package com.nanuvem.metagui.server.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
@RequestMapping("/operation")
public class OperationalController {

	@ResponseBody
	public String getAll() {
		return null;
	}

	public void create(String json) {
		// TODO Auto-generated method stub
		
	}


}
