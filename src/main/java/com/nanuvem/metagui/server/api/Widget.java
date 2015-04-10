package com.nanuvem.metagui.server.api;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;


@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"name", "version"}))
public class Widget{

	@GeneratedValue
	@Id
	private Long id;
	@NotNull
	private String name;
	@NotNull
	private Long version;
	@Column(length=1000000)
	private String code;
	@NotNull
	private WidgetType type;
	private String configuration;
	@OneToMany(fetch = FetchType.EAGER)
	private List<Context> requiredContexts;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public WidgetType getType() {
		return type;
	}
	public void setType(WidgetType type) {
		this.type = type;
	}
	public String getConfiguration() {
		return configuration;
	}
	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}
	public List<Context> getRequiredContexts() {
		return requiredContexts;
	}
	public void setRequiredContexts(List<Context> requiredContexts) {
		this.requiredContexts = requiredContexts;
	}
		
}

