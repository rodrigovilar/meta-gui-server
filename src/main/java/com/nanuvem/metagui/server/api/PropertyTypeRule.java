package com.nanuvem.metagui.server.api;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.nanuvem.metagui.server.controller.PropertyTypeType;

@Entity
public class PropertyTypeRule extends Rule {

	private PropertyTypeType propertyTypeType;
	@ManyToOne
	private PropertyTypeWidget widget;

	public PropertyTypeType getPropertyTypeType() {
		return propertyTypeType;
	}

	public void setPropertyTypeType(PropertyTypeType propertyTypeType) {
		this.propertyTypeType = propertyTypeType;
	}

	public PropertyTypeWidget getWidget() {
		return widget;
	}

	public void setWidget(PropertyTypeWidget widget) {
		this.widget = widget;
	}
	
}
