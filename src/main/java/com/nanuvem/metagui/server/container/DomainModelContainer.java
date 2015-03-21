package com.nanuvem.metagui.server.container;

import java.util.ArrayList;
import java.util.List;

public class DomainModelContainer {

	private static List<Class<?>> entities = new ArrayList<Class<?>>();
	
	public static long deploy(Class<?> entityType) {
		entities.add(entityType);
		return 0;
	}
	
	public static List<Class<?>> getDomains() {
		return new ArrayList<Class<?>>(entities);
	}

}
