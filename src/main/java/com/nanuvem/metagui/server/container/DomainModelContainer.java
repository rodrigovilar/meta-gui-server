package com.nanuvem.metagui.server.container;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DomainModelContainer {

	private static Map<Long, EntityType> entities = new HashMap<Long, EntityType>();
	private static Map<Long, List<Object>> instances = new HashMap<Long, List<Object>>();
	
	@Autowired
	private static EntityTypeRepository entityTypeRepository;
	
	public static long deploy(Class<?> clazz) {
		EntityType entityType = EntityType.entityTypeFromClass(clazz);
		entityType = entityTypeRepository.save(entityType);
		return entityType.getId();
	}
	
	public static Iterable<EntityType> getDomains() {
		return entityTypeRepository.findAll();
	}
	
	public static EntityType getDomain(Long id) {
		return entityTypeRepository.findOne(id);
	}
	
	public static void addInstance(Long id, Object instance) {
		instances.get(id).add(instance);
	}
	
	public static List<Object> getInstances(Long id) {
		return instances.get(id);
	}
	
	public static void clear() {
		entities.clear();
		instances.clear();
	}

}
