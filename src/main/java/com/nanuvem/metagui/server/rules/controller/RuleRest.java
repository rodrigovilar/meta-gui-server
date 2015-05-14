package com.nanuvem.metagui.server.rules.controller;

import com.nanuvem.metagui.server.api.Cardinality;
import com.nanuvem.metagui.server.api.Context;
import com.nanuvem.metagui.server.api.Rule;
import com.nanuvem.metagui.server.api.Widget;
import com.nanuvem.metagui.server.controller.ContextRest;
import com.nanuvem.metagui.server.controller.PropertyTypeType;
import com.nanuvem.metagui.server.controller.WidgetRest;

public class RuleRest {

	private long id;
	private long version;
	private WidgetRest widget;
	private ContextRest providedContext;
	private String entityTypeLocator;
	private String propertyTypeLocator;
	private PropertyTypeType propertyTypeTypeLocator;
	private Cardinality relationshipTargetCardinality;
	private String configuration;

	public long getId() {
		return id;
	}

	
	public ContextRest getProvidedContext() {
		return providedContext;
	}


	public void setProvidedContext(ContextRest providedContext) {
		this.providedContext = providedContext;
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

	public WidgetRest getWidget() {
		return widget;
	}


	public void setWidget(WidgetRest widget) {
		this.widget = widget;
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

	public void setPropertyTypeTypeLocator(
			PropertyTypeType propertyTypeTypeLocator) {
		this.propertyTypeTypeLocator = propertyTypeTypeLocator;
	}

	public String getConfiguration() {
		return configuration;
	}

	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}

	public Cardinality getRelationshipTargetCardinality() {
		return relationshipTargetCardinality;
	}


	public void setRelationshipTargetCardinality(
			Cardinality relationshipTargetCardinality) {
		this.relationshipTargetCardinality = relationshipTargetCardinality;
	}

	public static RuleRest toRest(Rule rule) {
		RuleRest ruleRest = new RuleRest();
		ruleRest.setId(rule.getId());
		ruleRest.setVersion(rule.getVersion());
		ruleRest.setWidget(WidgetRest.toRest(rule.getWidget()));
		ruleRest.setProvidedContext(ContextRest.toRest(rule.getProvidedContext()));
		ruleRest.setEntityTypeLocator(rule.getEntityTypeLocator());
		ruleRest.setPropertyTypeLocator(rule.getPropertyTypeLocator());
		ruleRest.setPropertyTypeTypeLocator(rule.getPropertyTypeTypeLocator());
		ruleRest.setRelationshipTargetCardinality(rule.getRelationshipTargetCardinality());
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
		rule.setRelationshipTargetCardinality(ruleRest.getRelationshipTargetCardinality());
		rule.setConfiguration(ruleRest.getConfiguration());
		Widget widget = WidgetRest.toDomain(ruleRest.getWidget());
		rule.setWidget(widget);
		Context context = ContextRest.toDomain(ruleRest.getProvidedContext());
		rule.setProvidedContext(context);
		return rule;
	}

}
