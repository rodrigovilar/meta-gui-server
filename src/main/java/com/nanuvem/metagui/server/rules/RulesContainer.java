package com.nanuvem.metagui.server.rules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nanuvem.metagui.server.api.DefaultEntityRule;
import com.nanuvem.metagui.server.api.Rule;

@Component
public class RulesContainer {
	
	@Autowired
	private DefaultEntityRuleRepository defaultEntityRuleRepository;
	@Autowired
	private EntityRuleRepository entityRuleRepository;
	@Autowired
	private PropertyTypeRuleRepository propertyTypeRuleRepository;
	@Autowired
	private PropertyRuleRepository propertyRuleRepository;
	
	@SuppressWarnings("unchecked")
	public <T extends Rule> List<T> getAllRules() {
		List<T> rules = new ArrayList<T>();
		rules.addAll((Collection<? extends T>) defaultEntityRuleRepository.findAll());
		rules.addAll((Collection<? extends T>) entityRuleRepository.findAll());
		rules.addAll((Collection<? extends T>) propertyTypeRuleRepository.findAll());
		rules.addAll((Collection<? extends T>) propertyRuleRepository.findAll());
		return rules;
	}
	
	public DefaultEntityRule saveDefaultEntityRule(DefaultEntityRule defaultEntityRule) {
		return defaultEntityRuleRepository.save(defaultEntityRule);
	}

}
