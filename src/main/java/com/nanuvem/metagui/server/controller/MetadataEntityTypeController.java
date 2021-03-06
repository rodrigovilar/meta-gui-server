package com.nanuvem.metagui.server.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nanuvem.metagui.server.container.DomainModelContainer;
import com.nanuvem.metagui.server.container.EntityTypeDomain;

@Controller
public class MetadataEntityTypeController {

	@RequestMapping("/entities")
	@ResponseBody
	public List<EntityTypeRest> getEntities() {
		List<EntityTypeRest> entities = new ArrayList<EntityTypeRest>();
		Iterable<EntityTypeDomain> domains = DomainModelContainer.getDomains();
		
		for(EntityTypeDomain domain : domains) {
			entities.add(EntityTypeRest.toRest(domain, false));
		}
		
		return entities;
	}

	@RequestMapping("/entities/{entityId}")
	@ResponseBody
	public EntityTypeRest getEntity(@PathVariable Integer entityId) {
		return EntityTypeRest.toRest(DomainModelContainer.getDomain(entityId), true);
	}

}
