package com.nanuvem.metagui.server.container;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DomainModelContainer {

	private static Long counter = 0l;
	private static Map<Long, EntityType> entities = new HashMap<Long, EntityType>();
	private static Map<Long, List<Object>> instances = new HashMap<Long, List<Object>>();
	
	public static long deploy(Class<?> entityType) {
		entities.put(++counter, EntityType.entityTypeFromClass(entityType, counter));
		instances.put(counter, new ArrayList<Object>());
		return counter;
	}
	
	public static List<EntityType> getDomains() {
		return new ArrayList<EntityType>(entities.values());
	}
	
	public static EntityType getDomain(Long id) {
		return entities.get(id);
	}
	
	public static void addInstance(Long id, Object instance) {
		instances.get(id).add(instance);
	}
	
	public static List<Object> getInstances(Long id) {
		return instances.get(id);
	}
	
	public static void clear() {
		counter = 0l;
		entities.clear();
		instances.clear();
	}

}
