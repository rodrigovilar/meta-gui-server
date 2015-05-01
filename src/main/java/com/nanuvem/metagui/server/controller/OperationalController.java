package com.nanuvem.metagui.server.controller;

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
@RequestMapping(value = "/api/{resource}")
public class OperationalController {

	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public <T> ResponseEntity<T[]> getAll(@PathVariable String resource) {
		T[] instances = (T[]) DomainModelContainer.getInstances(resource).toArray();
		return new ResponseEntity<T[]>(instances, HttpStatus.OK);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public <T> ResponseEntity<T> create(@PathVariable String resource, @RequestBody String input) {
		EntityTypeDomain entityType = DomainModelContainer.getDomain(resource);
		System.out.println(input);
		T instance = (T) new Gson().fromJson(input, entityType.getClazz());
		instance = DomainModelContainer.saveInstance(resource, instance);
        return new ResponseEntity<T>(instance, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "{instanceId}", method = RequestMethod.GET)
	@ResponseBody
	public <T> ResponseEntity<T> get(@PathVariable String resource, @PathVariable Long instanceId) {
		T instance = DomainModelContainer.getInstance(resource, instanceId);
		if(instance == null)
			return new ResponseEntity<T>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<T>(instance, HttpStatus.OK);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "{instanceId}", method = RequestMethod.PUT)
	@ResponseBody
	public <T> ResponseEntity<T> update(@PathVariable String resource, @PathVariable Long instanceId, @RequestBody String input) {
		EntityTypeDomain entityType = DomainModelContainer.getDomain(resource);
		T instance = DomainModelContainer.getInstance(resource, instanceId);
		
		if(instance == null) {
			return new ResponseEntity<T>(HttpStatus.NOT_FOUND);
		}
		
		T newInstance = (T) new Gson().fromJson(input, entityType.getClazz());
		instance = DomainModelContainer.saveInstance(instanceId, resource, newInstance);
        return new ResponseEntity<T>(instance, HttpStatus.CREATED);
	}

	@RequestMapping(value = "{instanceId}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<?> delete(@PathVariable String resource, @PathVariable Long instanceId) {
		if(DomainModelContainer.deleteInstance(resource, instanceId))
			return new ResponseEntity<Object>(HttpStatus.OK);
        return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
	}	

}
