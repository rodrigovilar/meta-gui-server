package com.nanuvem.metagui.server.widgets;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.nanuvem.metagui.server.api.Context;
import com.nanuvem.metagui.server.api.Widget;
import com.nanuvem.metagui.server.context.ContextContainer;

@Service
@Component
public class WidgetContainer {

	@Autowired
	private WidgetRepository widgetRepository;
	@Autowired
	private ContextContainer contextContainer;
	
	public List<Widget> getAll() {
		return widgetRepository.findAll();
	}
	
	public Widget getWidget(Long id) {
		return widgetRepository.findOne(id);
	}
	
	public Widget getWidgetByName(String name) {
		List<Widget> widgets = widgetRepository.findByNameOrderByVersionDesc(name);
		Widget widget = widgets.size() > 0 ? widgets.get(0) : null;
		return widget;
	}
	
	public Widget getWidgetByNameAndVersion(String name, Long version) {
		List<Widget> widgets = widgetRepository.findByNameAndVersion(name, version);
		Widget widget = widgets.size() > 0 ? widgets.get(0) : null;
		return widget;
	}
	
	public Widget saveWidget(Widget widget) {
		widget.setVersion(1l);
		Widget widgetSameName = getWidgetByName(widget.getName());
		if(widgetSameName != null) {
			widget.setVersion(widgetSameName.getVersion() + 1);
		}
		if(widget.getRequiredContexts() != null) {
			for(Context context : widget.getRequiredContexts()) {
				context.setId(contextContainer.getOrCreateContext(context).getId());
			}
		}
		return widgetRepository.saveAndFlush(widget);
	}
	
	public void clear() {
		widgetRepository.deleteAll();
	}
	
}
