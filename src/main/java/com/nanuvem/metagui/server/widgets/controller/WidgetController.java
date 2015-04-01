package com.nanuvem.metagui.server.widgets.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.nanuvem.metagui.server.api.Widget;
import com.nanuvem.metagui.server.widgets.WidgetContainer;

@Controller
@RequestMapping(value = "/widgets")
public class WidgetController {

	@Autowired
	private WidgetContainer widgetContainer;
	
	public void setWidgetContainer(WidgetContainer widgetContainer) {
		this.widgetContainer = widgetContainer;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Widget>> getAll() {
		return new ResponseEntity<List<Widget>>(widgetContainer.getAll(), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Widget> createWidget(@RequestBody String input) {
		Widget widget = new Gson().fromJson(input, Widget.class);
		widget = widgetContainer.saveWidget(widget);
		return new ResponseEntity<Widget>(widget, HttpStatus.CREATED);
	}
	
}
