package com.nanuvem.metagui.server.controller;

import java.lang.reflect.Field;

public class PropertyTypeRest {

	private String name;
	private PropertyTypeType type;

	
	public PropertyTypeType getType() {
		return type;
	}

	public void setType(PropertyTypeType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		PropertyTypeRest other = (PropertyTypeRest) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	public static PropertyTypeRest propertyTypeRestFromField(Field field) {
		PropertyTypeRest propertyTypeRest = new PropertyTypeRest();
		propertyTypeRest.setName(field.getName());
		String fieldName = field.getType().getSimpleName();
		propertyTypeRest.setType(propertyTypeTypeFromString(fieldName));
		return propertyTypeRest;
	}

	public static PropertyTypeType propertyTypeTypeFromString(String typeName) {
		if(typeName.equals("String")){
			return PropertyTypeType.string;
		}
		if(typeName.equals("boolean")){
			return PropertyTypeType.bool;
		}
		if(typeName.equals("Date")){
			return PropertyTypeType.date;
		}
		if(typeName.equals("double")){
			return PropertyTypeType.real;
		}
		if(typeName.equals("int")){
			return PropertyTypeType.integer;
		}
		return null;
	}
	
	
}
