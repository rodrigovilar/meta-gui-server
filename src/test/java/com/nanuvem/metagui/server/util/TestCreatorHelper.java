package com.nanuvem.metagui.server.util;

import java.util.ArrayList;
import java.util.List;

import com.nanuvem.metagui.server.api.Context;
import com.nanuvem.metagui.server.api.Rule;
import com.nanuvem.metagui.server.api.Widget;
import com.nanuvem.metagui.server.api.WidgetType;
import com.nanuvem.metagui.server.controller.PropertyTypeType;

public class TestCreatorHelper {

	public static Rule createEntityRule(String contextName, String entityTypeLocator, String widgetName) {
		Rule defaultEntityRule = new Rule();
		defaultEntityRule.setEntityTypeLocator(entityTypeLocator);
		Context providedContext = new Context();
		providedContext.setName(contextName);
		providedContext.setType(WidgetType.Entity);
		defaultEntityRule.setProvidedContext(providedContext);
		Widget widget = new Widget();
		widget.setName(widgetName);
		defaultEntityRule.setWidget(widget);
		return defaultEntityRule;
	}
	
	public static Rule createPropertyRule(String contextName, PropertyTypeType propertyTypeTypeLocator, String propertyTypeLocator, String widgetName) {
		Rule propertyTypeRule = new Rule();
		propertyTypeRule.setPropertyTypeTypeLocator(propertyTypeTypeLocator.name());
		propertyTypeRule.setPropertyTypeLocator(propertyTypeLocator);
		Context providedContext = new Context();
		providedContext.setName(contextName);
		providedContext.setType(WidgetType.Property);
		propertyTypeRule.setProvidedContext(providedContext);
		Widget widget = new Widget();
		widget.setName(widgetName);
		propertyTypeRule.setWidget(widget);
		return propertyTypeRule;
	}
	
	public static Widget createWidget(String name, String code, WidgetType type, List<Context> requiredContexts) {
		Widget widget = new Widget();
		widget.setName(name);
		widget.setCode(code);
		widget.setType(type);
		widget.setRequiredContexts(requiredContexts);
		return widget;
	}

	public static Widget createSimpleWidget(String name, String code, WidgetType type, String ... requiredContexts) {
		List<Context> contexts = null;
		if(requiredContexts.length > 0) {
			contexts = new ArrayList<Context>();
			for(String contextName : requiredContexts) {
				Context context = new Context();
				context.setName(contextName);
				context.setType(type);
				contexts.add(context);
			}
		}
		return createWidget(name, code, type, contexts);
	}
	
	
	
	
}
