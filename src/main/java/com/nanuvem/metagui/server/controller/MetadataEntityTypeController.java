package com.nanuvem.metagui.server.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nanuvem.metagui.server.container.DomainModelContainer;

@Controller
@EnableAutoConfiguration
public class MetadataEntityTypeController {

	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "Hello World";
	}

	public List<EntityType> getEntities() {
		List<EntityType> entities = new ArrayList<EntityType>();
		List<Class<?>> domains = DomainModelContainer.getDomains();
		
		for(Class<?> domain : domains) {
			EntityType entityType = new EntityType();
			entityType.setName(domain.getSimpleName());
			entities.add(entityType);
		}
		
		return entities;
	}

}
