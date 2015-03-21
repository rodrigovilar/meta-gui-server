package com.nanuvem.metagui.server.container;

import java.util.ArrayList;
import java.util.List;

public class DomainModelContainer {

	private static List<Class<?>> entities = new ArrayList<Class<?>>();
	
	public static void deploy(Class<?> entityType) {
		entities.add(entityType);
	}
	
	public static List<Class<?>> getDomains() {
		return new ArrayList<Class<?>>(entities);
	}

}
