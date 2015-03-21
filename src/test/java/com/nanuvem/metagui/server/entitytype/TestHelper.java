package com.nanuvem.metagui.server.entitytype;

import com.nanuvem.metagui.server.controller.EntityTypeRest;
import com.nanuvem.metagui.server.controller.PropertyTypeRest;
import com.nanuvem.metagui.server.controller.PropertyTypeType;

public class TestHelper {

	public static EntityTypeRest createEntityTypeRest(String name) {
		EntityTypeRest expected = new EntityTypeRest();
		expected.setName(name);
		return expected;
	}

	public static void addPropertyTypeRest(EntityTypeRest expected, String name,
			PropertyTypeType type) {
		PropertyTypeRest prop = new PropertyTypeRest();
		prop.setName(name);
		prop.setType(type);
		expected.getProperties().add(prop);
	}

}
