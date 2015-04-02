package com.nanuvem.metagui.server.widgets.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.nanuvem.metagui.server.api.EntityWidget;
import com.nanuvem.metagui.server.api.PropertyWidget;
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
	
	@RequestMapping(value = "entity", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<EntityWidget> createEntityWidget(@RequestBody String input) {
		EntityWidget widget = new Gson().fromJson(input, EntityWidget.class);
		widget = widgetContainer.saveEntityWidget(widget);
		return new ResponseEntity<EntityWidget>(widget, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "entity/{widgetId}",method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<EntityWidget> getEntityWidget(@PathVariable Long widgetId) {
		EntityWidget widget = widgetContainer.getEntityWidget(widgetId);
		if(widget == null)
			return new ResponseEntity<EntityWidget>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<EntityWidget>(widget, HttpStatus.OK);
	}
	
	@RequestMapping(value = "property", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<PropertyWidget> createPropertyWidget(@RequestBody String input) {
		PropertyWidget widget = new Gson().fromJson(input, PropertyWidget.class);
		widget = widgetContainer.savePropertyWidget(widget);
		return new ResponseEntity<PropertyWidget>(widget, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "property/{widgetId}",method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<PropertyWidget> getWidget(@PathVariable Long widgetId) {
		PropertyWidget widget = widgetContainer.getPropertyWidget(widgetId);
		if(widget == null)
			return new ResponseEntity<PropertyWidget>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<PropertyWidget>(widget, HttpStatus.OK);
	}
	
}
