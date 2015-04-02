package com.nanuvem.metagui.server.api;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
	private String script;
	private ArrayList<String> contexts;
	
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
	public String getScript() {
		return script;
	}
	public void setScript(String script) {
		this.script = script;
	}
	public ArrayList<String> getContexts() {
		return contexts;
	}
	public void setContexts(ArrayList<String> contexts) {
		this.contexts = contexts;
	}
	
}

