package com.nanuvem.metagui.server.controller;

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
import com.nanuvem.metagui.server.container.EntityTypeDomain;

@Controller
@EnableAutoConfiguration
@RequestMapping(value = "/api/{resource}")
public class OperationalController {

	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public <T> ResponseEntity<T[]> getAll(@PathVariable String resource) {
		T[] instances = (T[]) DomainModelContainer.getInstances(resource).toArray();
		return new ResponseEntity<T[]>(instances, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> create(@PathVariable String resource, @RequestBody String input) {
		EntityTypeDomain entityType = DomainModelContainer.getDomain(resource);
		Object instance = new Gson().fromJson(input, entityType.getClazz());
		instance = DomainModelContainer.addInstance(resource, instance);
        return new ResponseEntity<Object>(instance, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "{instanceId}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> update(@PathVariable String resource, @PathVariable int instanceId, @RequestBody String input) {
		EntityTypeDomain entityType = DomainModelContainer.getDomain(resource);
		Object instance = new Gson().fromJson(input, entityType.getClazz());
		instance = DomainModelContainer.updateInstance(resource, instance, instanceId);
        return new ResponseEntity<Object>(instance, HttpStatus.CREATED);
	}


}
