package com.nanuvem.metagui.server.container;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DomainModelContainer {

	private static Long counter = 0l;
	private static Map<Long, Class<?>> entities = new HashMap<Long, Class<?>>();
	private static Map<Long, List<Object>> instances = new HashMap<Long, List<Object>>();
	
	public static long deploy(Class<?> entityType) {
		entities.put(++counter, entityType);
		instances.put(counter, new ArrayList<Object>());
		return counter;
	}
	
	public static List<Class<?>> getDomains() {
		return new ArrayList<Class<?>>(entities.values());
	}
	
	public static Class<?> getDomain(Long id) {
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
