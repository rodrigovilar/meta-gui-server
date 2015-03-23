package com.nanuvem.metagui.server.controller;

import java.util.List;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.nanuvem.metagui.server.container.DomainModelContainer;
import com.nanuvem.metagui.server.controller.util.ListParameterizedType;

@Controller
@EnableAutoConfiguration
@RequestMapping("/operation/{classID}")
public class OperationalController {

	@ResponseBody
	public String getAll(@PathVariable Long classID) {
		Class<?> domain = DomainModelContainer.getDomain(classID);
		List<Object> instances = DomainModelContainer.getInstances(classID);
		return new Gson().toJson(instances, new ListParameterizedType(domain));
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public void create(@PathVariable Long classID, @RequestBody String input) {
		Class<?> domain = DomainModelContainer.getDomain(classID);
		Object instance = new Gson().fromJson(input, domain);
		DomainModelContainer.addInstance(classID, instance);
	}


}
