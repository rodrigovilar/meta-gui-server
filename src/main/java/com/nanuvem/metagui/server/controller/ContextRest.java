package com.nanuvem.metagui.server.controller;

import com.nanuvem.metagui.server.api.Context;
import com.nanuvem.metagui.server.api.WidgetType;

public class ContextRest {

	private String name;
	private String type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public static ContextRest toRest(Context context) {
		if (context == null)
			return null;

		ContextRest contextRest = new ContextRest();
		contextRest.setName(context.getName());
		contextRest.setType(context.getType().name());

		return contextRest;
	}

	public static Context toDomain(ContextRest rest) {
		if (rest == null)
			return null;

		Context context = new Context();
		context.setName(rest.getName());
		context.setType(WidgetType.valueOf(rest.getType()));
		return context;
	}

}
