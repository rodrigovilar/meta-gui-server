package com.nanuvem.metagui.server.api;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Context {

	@Id
	@GeneratedValue
	private Long id;
	@Column(nullable = false, unique = true)
	private String name;
	@NotNull
	private WidgetType type;
	@JsonIgnore
	@ManyToMany(mappedBy = "requiredContexts")
	private List<Widget> widgets;
	@JsonIgnore
	@OneToMany(mappedBy = "providedContext")
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
	public WidgetType getType() {
		return type;
	}
	public void setType(WidgetType type) {
		this.type = type;
	}
	public List<Widget> getWidgets() {
		return widgets;
	}
	public void setWidgets(List<Widget> widgets) {
		this.widgets = widgets;
	}
	public List<Rule> getRules() {
		return rules;
	}
	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}
	
}
