package com.nanuvem.metagui.server.api;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.nanuvem.metagui.server.controller.PropertyTypeType;

@Entity
public class Rule {

	@GeneratedValue
	@Id
	private Long id;
	private Long version;
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	private Context providedContext;
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	private Widget widget;
	private String entityTypeLocator;
	private String propertyTypeLocator;
	private PropertyTypeType propertyTypeTypeLocator;
	private String configuration;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	public Context getProvidedContext() {
		return providedContext;
	}
	public void setProvidedContext(Context context) {
		this.providedContext = context;
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
	public Widget getWidget() {
		return widget;
	}
	public void setWidget(Widget widget) {
		this.widget = widget;
	}
	public String getConfiguration() {
		return configuration;
	}
	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}
}
