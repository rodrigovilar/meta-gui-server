package com.nanuvem.metagui.server.rules;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nanuvem.metagui.server.api.Context;
import com.nanuvem.metagui.server.api.Rule;
import com.nanuvem.metagui.server.api.Widget;
import com.nanuvem.metagui.server.context.ContextContainer;
import com.nanuvem.metagui.server.widgets.WidgetContainer;

@Component
public class RulesContainer {
	
	@Autowired
	private RuleRepository ruleRepository;
	@Autowired
	private ContextContainer contextContainer;
	@Autowired
	private WidgetContainer widgetContainer;
	
	private Long version = 0l;
	
	public List<Rule> getAllRules() {
		return ruleRepository.findAll();
	}
	
	public List<Rule> getAllRulesByVersionGreaterThan(Long version) {
		return ruleRepository.findByVersionGreaterThan(version);
	}
	
	public Rule saveRule(@Valid Rule rule) {
		rule.setVersion(++version);
		Context context = contextContainer.getOrCreateContext(rule.getProvidedContext());
		rule.getProvidedContext().setId(context.getId());
		Widget widget = rule.getWidget();
		if(widget.getVersion() != null && widget.getVersion() != 0l)
			widget = widgetContainer.getWidgetByNameAndVersion(widget.getName(), widget.getVersion());
		else
			widget = widgetContainer.getWidgetByName(widget.getName());
		rule.getWidget().setId(widget.getId());
		return ruleRepository.saveAndFlush(rule);
	}
	
	public void clear() {
		ruleRepository.deleteAll();
	}


}
