package com.nanuvem.metagui.server.controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.nanuvem.metagui.server.container.EntityTypeDomain;

public class EntityTypeRest {

	private long id;
	private String name;
	private List<PropertyTypeRest> properties = new ArrayList<PropertyTypeRest>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<PropertyTypeRest> getProperties() {
		return properties;
	}

	public void setProperties(List<PropertyTypeRest> properties) {
		this.properties = properties;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((properties == null) ? 0 : properties.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EntityTypeRest other = (EntityTypeRest) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (properties == null) {
			if (other.properties != null)
				return false;
		} else if (!properties.equals(other.properties))
			return false;
		return true;
	}

	public static EntityTypeRest toRest(EntityTypeDomain domain,
			boolean withProperties) {
		if (domain == null)
			return null;

		EntityTypeRest entityTypeRest = new EntityTypeRest();
		entityTypeRest.setName(domain.getName());
		entityTypeRest.setId(domain.getId());

		if (withProperties) {
			//Get EntityType Properties
			for (Field field : domain.getClazz().getSuperclass().getDeclaredFields()) {
				PropertyTypeRest propertyTypeRest = PropertyTypeRest
						.propertyTypeRestFromField(field);
				entityTypeRest.getProperties().add(propertyTypeRest);
			}
			for (Field field : domain.getClazz().getDeclaredFields()) {
				PropertyTypeRest propertyTypeRest = PropertyTypeRest
						.propertyTypeRestFromField(field);
				entityTypeRest.getProperties().add(propertyTypeRest);
			}
		}

		return entityTypeRest;
	}

}
