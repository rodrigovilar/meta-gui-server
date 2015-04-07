package com.nanuvem.metagui.server.api;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;


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
	private String code;
	@NotNull
	private WidgetType type;
	private String configuration;
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Context> requiredContexts;
	@JsonIgnore
	@OneToMany(mappedBy = "widget")
	private List<Rule> rules;
	
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
	public List<Rule> getRules() {
		return rules;
	}
	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}
		
}

