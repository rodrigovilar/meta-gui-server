package com.nanuvem.metagui.server.api;

import javax.persistence.Entity;

@Entity
public class PropertyRule extends PropertyTypeRule {

	private String propertyLocator;

	public String getPropertyLocator() {
		return propertyLocator;
	}

	public void setPropertyLocator(String propertyLocator) {
		this.propertyLocator = propertyLocator;
	}
	
}
