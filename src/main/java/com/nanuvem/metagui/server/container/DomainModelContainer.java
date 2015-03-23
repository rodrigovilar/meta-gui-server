package com.nanuvem.metagui.server.container;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DomainModelContainer {

	private static Long counter = 0l;
	private static Map<Long, Class<?>> entities = new HashMap<Long, Class<?>>();
	
	public static long deploy(Class<?> entityType) {
		entities.put(++counter, entityType);
		return counter;
	}
	
	public static List<Class<?>> getDomains() {
		return new ArrayList<Class<?>>(entities.values());
	}
	
	public static Class<?> getDomain(Long id) {
		return entities.get(id);
	}
	
	public static void clear() {
		counter = 0l;
		entities.clear();
	}

}
