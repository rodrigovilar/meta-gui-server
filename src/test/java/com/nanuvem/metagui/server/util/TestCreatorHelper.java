package com.nanuvem.metagui.server.util;

import com.nanuvem.metagui.server.api.Context;
import com.nanuvem.metagui.server.api.Rule;
import com.nanuvem.metagui.server.api.WidgetType;
import com.nanuvem.metagui.server.controller.PropertyTypeType;

public class TestCreatorHelper {

	public static Rule createEntityRule(String contextName, String entityTypeLocator) {
		Rule defaultEntityRule = new Rule();
		defaultEntityRule.setEntityTypeLocator(entityTypeLocator);
		Context providedContext = new Context();
		providedContext.setName(contextName);
		providedContext.setType(WidgetType.Entity);
		defaultEntityRule.setProvidedContext(providedContext);
		return defaultEntityRule;
	}
	
	public static Rule createPropertyRule(String contextName, PropertyTypeType propertyTypeTypeLocator, String propertyTypeLocator) {
		Rule propertyTypeRule = new Rule();
		propertyTypeRule.setPropertyTypeTypeLocator(propertyTypeTypeLocator);
		propertyTypeRule.setPropertyTypeLocator(propertyTypeLocator);
		Context providedContext = new Context();
		providedContext.setName(contextName);
		providedContext.setType(WidgetType.Property);
		propertyTypeRule.setProvidedContext(providedContext);
		return propertyTypeRule;
	}
	
	
}
