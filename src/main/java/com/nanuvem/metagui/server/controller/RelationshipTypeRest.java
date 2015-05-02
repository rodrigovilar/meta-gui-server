package com.nanuvem.metagui.server.controller;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.nanuvem.metagui.server.api.Cardinality;
import com.nanuvem.metagui.server.container.DomainModelContainer;
import com.nanuvem.metagui.server.container.EntityTypeDomain;

public class RelationshipTypeRest {

	private String name;
	private EntityTypeRest targetType;
	private Cardinality targetCardinality;
	private Cardinality sourceCardinality;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public EntityTypeRest getTargetType() {
		return targetType;
	}

	public void setTargetType(EntityTypeRest targetType) {
		this.targetType = targetType;
	}

	public Cardinality getTargetCardinality() {
		return targetCardinality;
	}

	public void setTargetCardinality(Cardinality targetCardinality) {
		this.targetCardinality = targetCardinality;
	}

	public Cardinality getSourceCardinality() {
		return sourceCardinality;
	}

	public void setSourceCardinality(Cardinality sourceCardinality) {
		this.sourceCardinality = sourceCardinality;
	}

	public static RelationshipTypeRest relationshipTypeRestFromField(Field field) {
		RelationshipTypeRest relationshipTypeRest = new RelationshipTypeRest();
		relationshipTypeRest.setName(field.getName());

		String typeName = null;
		
		if (field.getAnnotation(OneToOne.class) != null) {
			Class<?> type = field.getType();
			typeName = type.getSimpleName();
			relationshipTypeRest.setSourceCardinality(Cardinality.One);
			relationshipTypeRest.setTargetCardinality(Cardinality.One);
		} else if (field.getAnnotation(ManyToOne.class) != null) {
			Class<?> type = field.getType();
			typeName = type.getSimpleName();
			relationshipTypeRest.setSourceCardinality(Cardinality.Many);
			relationshipTypeRest.setTargetCardinality(Cardinality.One);
		} else if (field.getAnnotation(OneToMany.class) != null) {
			ParameterizedType genericType = (ParameterizedType) field.getGenericType();
			Class<?> type = (Class<?>) genericType.getActualTypeArguments()[0];
			typeName = type.getSimpleName();
			relationshipTypeRest.setSourceCardinality(Cardinality.One);
			relationshipTypeRest.setTargetCardinality(Cardinality.Many);
		} else if (field.getAnnotation(ManyToMany.class) != null) {
			ParameterizedType genericType = (ParameterizedType) field.getGenericType();
			Class<?> type = (Class<?>) genericType.getActualTypeArguments()[0];
			typeName = type.getSimpleName();
			relationshipTypeRest.setSourceCardinality(Cardinality.Many);
			relationshipTypeRest.setTargetCardinality(Cardinality.Many);
		}
		
		EntityTypeDomain entityTypeDomain = 
				DomainModelContainer.getEntityTypeRepository().findByName(typeName);
		EntityTypeRest targetType = EntityTypeRest.toRest(entityTypeDomain, false);
		relationshipTypeRest.setTargetType(targetType);
				
		return relationshipTypeRest;
	}

}
