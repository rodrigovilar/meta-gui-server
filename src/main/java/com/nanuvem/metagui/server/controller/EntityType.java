package com.nanuvem.metagui.server.controller;

public class EntityType {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof EntityType) {
			EntityType other = (EntityType)obj;
			return this.getName().equals(other.getName());
		}
		return false;
	}
	
}
