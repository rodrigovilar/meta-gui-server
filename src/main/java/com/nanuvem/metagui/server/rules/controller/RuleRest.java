package com.nanuvem.metagui.server.rules.controller;

import com.nanuvem.metagui.server.api.Context;
import com.nanuvem.metagui.server.api.Rule;
import com.nanuvem.metagui.server.api.Widget;
import com.nanuvem.metagui.server.controller.PropertyTypeType;

public class RuleRest {

	private long id;
	private long version;
	private long widgetID;
	private long widgetVersion;
	private String providedContextName;
	private String entityTypeLocator;
	private String propertyTypeLocator;
	private PropertyTypeType propertyTypeTypeLocator;
	private String configuration;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getVersion() {
		return version;
	}
	public void setVersion(long version) {
		this.version = version;
	}
	public long getWidgetID() {
		return widgetID;
	}
	public void setWidgetID(long widgetID) {
		this.widgetID = widgetID;
	}
	public long getWidgetVersion() {
		return widgetVersion;
	}
	public void setWidgetVersion(long widgetVersion) {
		this.widgetVersion = widgetVersion;
	}
	public String getProvidedContextName() {
		return providedContextName;
	}
	public void setProvidedContextName(String providedContextName) {
		this.providedContextName = providedContextName;
	}
	public String getEntityTypeLocator() {
		return entityTypeLocator;
	}
	public void setEntityTypeLocator(String entityTypeLocator) {
		this.entityTypeLocator = entityTypeLocator;
	}
	public String getPropertyTypeLocator() {
		return propertyTypeLocator;
	}
	public void setPropertyTypeLocator(String propertyTypeLocator) {
		this.propertyTypeLocator = propertyTypeLocator;
	}
	public PropertyTypeType getPropertyTypeTypeLocator() {
		return propertyTypeTypeLocator;
	}
	public void setPropertyTypeTypeLocator(PropertyTypeType propertyTypeTypeLocator) {
		this.propertyTypeTypeLocator = propertyTypeTypeLocator;
	}
	public String getConfiguration() {
		return configuration;
	}
	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}
	
	public static RuleRest toRest(Rule rule) {
		RuleRest ruleRest = new RuleRest();
		ruleRest.setId(rule.getId());
		ruleRest.setVersion(rule.getVersion());
		ruleRest.setWidgetID(rule.getWidget().getId());
		ruleRest.setWidgetVersion(rule.getWidget().getVersion());
		ruleRest.setProvidedContextName(rule.getProvidedContext().getName());
		ruleRest.setEntityTypeLocator(rule.getEntityTypeLocator());
		ruleRest.setPropertyTypeLocator(rule.getPropertyTypeLocator());
		ruleRest.setPropertyTypeTypeLocator(rule.getPropertyTypeTypeLocator());
		ruleRest.setConfiguration(rule.getConfiguration());
		return ruleRest;
	}
	
	public static Rule toDomain(RuleRest ruleRest) {
		Rule rule = new Rule();
		rule.setId(ruleRest.getId());
		rule.setVersion(ruleRest.getVersion());
		rule.setEntityTypeLocator(ruleRest.getEntityTypeLocator());
		rule.setPropertyTypeLocator(ruleRest.getPropertyTypeLocator());
		rule.setPropertyTypeTypeLocator(ruleRest.getPropertyTypeTypeLocator());
		rule.setConfiguration(ruleRest.getConfiguration());
		Widget widget = new Widget();
		widget.setId(ruleRest.getId());
		widget.setVersion(ruleRest.getWidgetVersion());
		rule.setWidget(widget);
		Context context = new Context();
		context.setName(ruleRest.getProvidedContextName());
		rule.setProvidedContext(context);
		return rule;
	}
	
}
