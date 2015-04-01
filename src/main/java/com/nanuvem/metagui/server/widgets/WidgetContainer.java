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
		widget.setVersion(1l);
		List<Widget> widgetsSameName = widgetRepository.findByName(widget.getName());
		if(widgetsSameName.size() > 0) {
			widget.setVersion(widgetsSameName.get(0).getVersion() + 1);
		}
		return widgetRepository.saveAndFlush(widget);
	}

	public void clear() {
		widgetRepository.deleteAll();
	}
	
}
