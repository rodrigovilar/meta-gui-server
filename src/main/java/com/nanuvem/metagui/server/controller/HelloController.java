package com.nanuvem.metagui.server.controller;

import java.util.List;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
public class HelloController {

	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "Hello World";
	}

	public List<EntityType> getEntities() {
		// TODO Auto-generated method stub
		return null;
	}

}
