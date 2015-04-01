package com.nanuvem.metagui.server.rules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nanuvem.metagui.server.api.DefaultEntityRule;
import com.nanuvem.metagui.server.api.EntityRule;
import com.nanuvem.metagui.server.api.PropertyRule;
import com.nanuvem.metagui.server.api.PropertyTypeRule;
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
	
	private Long version = 0l;
	
	@SuppressWarnings("unchecked")
	public <T extends Rule> List<T> getAllRules() {
		List<T> rules = new ArrayList<T>();
		rules.addAll((Collection<? extends T>) defaultEntityRuleRepository.findAll());
		rules.addAll((Collection<? extends T>) entityRuleRepository.findAll());
		rules.addAll((Collection<? extends T>) propertyTypeRuleRepository.findAll());
		rules.addAll((Collection<? extends T>) propertyRuleRepository.findAll());
		return rules;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Rule> List<T> getAllRulesByVersionGreaterThan(Long version) {
		List<T> rules = new ArrayList<T>();
		rules.addAll((Collection<? extends T>) defaultEntityRuleRepository.findByVersionGreaterThan(version));
		rules.addAll((Collection<? extends T>) entityRuleRepository.findByVersionGreaterThan(version));
		rules.addAll((Collection<? extends T>) propertyTypeRuleRepository.findByVersionGreaterThan(version));
		rules.addAll((Collection<? extends T>) propertyRuleRepository.findByVersionGreaterThan(version));
		return rules;
	}
	
	public DefaultEntityRule saveDefaultEntityRule(DefaultEntityRule defaultEntityRule) {
		defaultEntityRule.setVersion(++version);
		return defaultEntityRuleRepository.saveAndFlush(defaultEntityRule);
	}
	
	public PropertyTypeRule savePropertyTypeRule(PropertyTypeRule propertyTypeRule) {
		propertyTypeRule.setVersion(++version);
		return propertyTypeRuleRepository.saveAndFlush(propertyTypeRule);
	}

	public PropertyRule savePropertyRule(PropertyRule propertyRule) {
		propertyRule.setVersion(++version);
		return propertyRuleRepository.saveAndFlush(propertyRule);
	}
	
	public EntityRule saveEntityRule(EntityRule entityRule) {
		entityRule.setVersion(++version);
		return entityRuleRepository.saveAndFlush(entityRule);
	}
	
	public void clear() {
		defaultEntityRuleRepository.deleteAll();
		entityRuleRepository.deleteAll();
		propertyTypeRuleRepository.deleteAll();
		propertyRuleRepository.deleteAll();
	}


}
