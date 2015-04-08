package com.nanuvem.metagui.server.widgets;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nanuvem.metagui.server.api.Context;
import com.nanuvem.metagui.server.api.Widget;
import com.nanuvem.metagui.server.context.ContextService;

@Service
public class WidgetService {

	@Autowired
	private WidgetRepository widgetRepository;
	@Autowired
	private ContextService contextContainer;
	
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
				context.setId(contextContainer.createContext(context).getId());
			}
		}
		return widgetRepository.saveAndFlush(widget);
	}
	
	public void clear() {
		widgetRepository.deleteAll();
	}
	
}
