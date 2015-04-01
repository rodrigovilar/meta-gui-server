package com.nanuvem.metagui.server.widgets;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nanuvem.metagui.server.api.Widget;

@Component
public class WidgetContainer {

	@Autowired
	private WidgetRepository widgetRepository;
	
	public List<Widget> getAll() {
		return widgetRepository.findAll();
	}

	public Widget saveWidget(Widget widget) {
		return widgetRepository.saveAndFlush(widget);
	}

	public void clear() {
		widgetRepository.deleteAll();
	}
	
}
