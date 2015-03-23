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

	public List<EntityTypeRest> getEntities() {
		List<EntityTypeRest> entities = new ArrayList<EntityTypeRest>();
		List<Class<?>> domains = DomainModelContainer.getDomains();
		
		for(Class<?> domain : domains) {
			entities.add(EntityTypeRest.entityTypeRestFromClass(domain));
		}
		
		return entities;
	}

	public EntityTypeRest getEntity(long id) {
		return EntityTypeRest.entityTypeRestFromClass(DomainModelContainer.getDomain(id));
	}

}
