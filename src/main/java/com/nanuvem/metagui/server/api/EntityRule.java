package com.nanuvem.metagui.server.api;

import javax.persistence.Entity;

@Entity
public class EntityRule extends DefaultEntityRule {

	private String entityLocator;

	public String getEntityLocator() {
		return entityLocator;
	}

	public void setEntityLocator(String entityLocator) {
		this.entityLocator = entityLocator;
	}
	
}
