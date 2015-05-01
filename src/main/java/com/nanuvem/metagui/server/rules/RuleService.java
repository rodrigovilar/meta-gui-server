package com.nanuvem.metagui.server.rules;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.nanuvem.metagui.server.api.Context;
import com.nanuvem.metagui.server.api.Rule;
import com.nanuvem.metagui.server.api.Widget;
import com.nanuvem.metagui.server.context.ContextService;
import com.nanuvem.metagui.server.widgets.WidgetService;

@Service
public class RuleService {
	
	@Autowired
	private RuleRepository ruleRepository;
	@Autowired
	private ContextService contextContainer;
	@Autowired
	private WidgetService widgetContainer;
	
	private Long version;
	
	private void updateVersion() {
		version = 1l;
		List<Rule> rules = ruleRepository.findAll(new Sort(Sort.Direction.DESC, "version"));
		if(rules.size() > 0) {
			version = rules.get(0).getVersion();
		}
	}
	
	public List<Rule> getAllRules() {
		return ruleRepository.findAll();
	}
	
	public List<Rule> getAllRulesByVersionGreaterThan(Long version) {
		return ruleRepository.findByVersionGreaterThan(version);
	}
	
	@Transactional
	public Rule saveRule(@Valid Rule rule) {
		if(version == null) {
			updateVersion();
		}
		rule.setVersion(++version);
		Context context = contextContainer.getContextByName(rule.getProvidedContext().getName());
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
