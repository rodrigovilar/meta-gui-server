package com.nanuvem.metagui.server.controller;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.nanuvem.metagui.server.container.EntityTypeDomain;

public class EntityTypeRest {

	private long id;
	private String name;
	private String resource;
	private List<PropertyTypeRest> propertyTypes = new ArrayList<PropertyTypeRest>();
	private List<RelationshipTypeRest> relationshipTypes = new ArrayList<RelationshipTypeRest>();

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

	public List<PropertyTypeRest> getPropertyTypes() {
		return propertyTypes;
	}

	public void setPropertyTypes(List<PropertyTypeRest> propertyTypes) {
		this.propertyTypes = propertyTypes;
	}

	public List<RelationshipTypeRest> getRelationshipTypes() {
		return relationshipTypes;
	}

	public void setRelationshipTypes(List<RelationshipTypeRest> relationshipTypes) {
		this.relationshipTypes = relationshipTypes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((propertyTypes == null) ? 0 : propertyTypes.hashCode());
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
		if (propertyTypes == null) {
			if (other.propertyTypes != null)
				return false;
		} else if (!propertyTypes.equals(other.propertyTypes))
			return false;
		return true;
	}

	public static EntityTypeRest toRest(EntityTypeDomain domain,
			boolean withDetails) {
		if (domain == null)
			return null;

		EntityTypeRest entityTypeRest = new EntityTypeRest();
		entityTypeRest.setName(domain.getName());
		entityTypeRest.setId(domain.getId());
		entityTypeRest.setResource(domain.getResource());

		if (withDetails) {
			// Get EntityType Properties
			for (Field field : domain.getClazz().getSuperclass()
					.getDeclaredFields()) {
				processField(entityTypeRest, field);
			}

			for (Field field : domain.getClazz().getDeclaredFields()) {
				processField(entityTypeRest, field);
			}
		}

		return entityTypeRest;
	}

	private static void processField(EntityTypeRest entityTypeRest, Field field) {
		if (isProperty(field)) {
			PropertyTypeRest propertyTypeRest = 
					PropertyTypeRest.propertyTypeRestFromField(field);
			entityTypeRest.getPropertyTypes().add(propertyTypeRest);
		} else {
			RelationshipTypeRest relationshipTypeRest =
					RelationshipTypeRest.relationshipTypeRestFromField(field);
			entityTypeRest.getRelationshipTypes().add(relationshipTypeRest);
		}
	}

	private static boolean isProperty(Field field) {
		return !hasAnnotation(field, OneToMany.class) && !hasAnnotation(field, OneToOne.class) 
				&& !hasAnnotation(field, ManyToOne.class);
	}

	private static boolean hasAnnotation(Field field, Class<? extends Annotation> annotation) {
		return field.getAnnotation(annotation) != null;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

}
