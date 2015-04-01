package com.nanuvem.metagui.server.api;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class Widget{

	@GeneratedValue
	@Id
	private Long id;
	private String name;
	private Long version;
	private String script;
	private ArrayList<String> contexts;
	
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

