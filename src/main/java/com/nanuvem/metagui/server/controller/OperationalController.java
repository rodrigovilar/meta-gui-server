package com.nanuvem.metagui.server.controller;

import java.util.List;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.nanuvem.metagui.server.container.DomainModelContainer;
import com.nanuvem.metagui.server.container.EntityType;
import com.nanuvem.metagui.server.controller.util.ListParameterizedType;

@Controller
@EnableAutoConfiguration
public class OperationalController {

	@RequestMapping(value = "/api/{classID}", method = RequestMethod.GET)
	@ResponseBody
	public String getAll(@PathVariable Long classID) {
		EntityType domain = DomainModelContainer.getDomain(classID);
		List<Object> instances = DomainModelContainer.getInstances(classID);
		return new Gson().toJson(instances, new ListParameterizedType(domain.getClazz()));
	}
	
	@RequestMapping(value = "/api/{classID}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> create(@PathVariable Long classID, @RequestBody String input) {
		EntityType domain = DomainModelContainer.getDomain(classID);
		Object instance = new Gson().fromJson(input, domain.getClazz());
		DomainModelContainer.addInstance(classID, instance);
        return new ResponseEntity<Object>(instance, HttpStatus.CREATED);
	}


}
