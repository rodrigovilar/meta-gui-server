package com.nanuvem.metagui.server.api;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class Widget {

	@GeneratedValue
	@Id
	private Long id;
	private String script;
	private ArrayList<String> contexts;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
