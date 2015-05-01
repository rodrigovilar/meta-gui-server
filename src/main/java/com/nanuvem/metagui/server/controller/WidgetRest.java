package com.nanuvem.metagui.server.controller;

import com.nanuvem.metagui.server.api.Widget;

public class WidgetRest {

	private String name;
	private String code;
	private Long id;
	private Long version;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

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

	public static WidgetRest toRest(Widget widget) {
		if (widget == null)
			return null;

		WidgetRest rest = new WidgetRest();
		rest.setName(widget.getName());
		rest.setCode(widget.getCode());
		rest.setId(widget.getId());
		rest.setVersion(widget.getVersion());
		return rest;
	}

	public static Widget toDomain(WidgetRest rest) {
		if (rest == null)
			return null;

		Widget widget = new Widget();
		widget.setName(rest.getName());
		widget.setCode(rest.getCode());
		widget.setId(rest.getId());
		widget.setVersion(rest.getVersion());
		return widget;
	}

}
