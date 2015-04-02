package com.nanuvem.metagui.server.widgets;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.nanuvem.metagui.server.api.EntityWidget;
import com.nanuvem.metagui.server.api.PropertyWidget;
import com.nanuvem.metagui.server.api.Widget;

@Service
@Component
public class WidgetContainer {

	@Autowired
	private EntityWidgetRepository entityWidgetRepository;
	@Autowired
	private PropertyWidgetRepository propertyWidgetRepository;
	
	public List<Widget> getAll() {
		List<Widget> widgets = new ArrayList<Widget>();
		widgets.addAll(entityWidgetRepository.findAll());
		widgets.addAll(propertyWidgetRepository.findAll());
		return widgets;
	}
	
	public EntityWidget getEntityWidget(Long id) {
		return entityWidgetRepository.findOne(id);
	}
	
	public PropertyWidget getPropertyWidget(Long id) {
		return propertyWidgetRepository.findOne(id);
	}

	@Transactional
	public EntityWidget saveEntityWidget(EntityWidget widget) {
		widget.setVersion(1l);
		List<Widget> widgetsSameName = entityWidgetRepository.findByName(widget.getName());
		if(widgetsSameName.size() > 0) {
			widget.setVersion(widgetsSameName.get(0).getVersion() + 1);
		}
		return entityWidgetRepository.saveAndFlush(widget);
	}
	
	@Transactional
	public PropertyWidget savePropertyWidget(PropertyWidget widget) {
		widget.setVersion(1l);
		List<Widget> widgetsSameName = propertyWidgetRepository.findByName(widget.getName());
		if(widgetsSameName.size() > 0) {
			widget.setVersion(widgetsSameName.get(0).getVersion() + 1);
		}
		return propertyWidgetRepository.saveAndFlush(widget);
	}

	public void clear() {
		entityWidgetRepository.deleteAll();
		propertyWidgetRepository.deleteAll();
	}
	
}
