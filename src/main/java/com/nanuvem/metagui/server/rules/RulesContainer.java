package com.nanuvem.metagui.server.rules;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nanuvem.metagui.server.api.Rule;

@Component
public class RulesContainer {
	
	@Autowired
	private RuleRepository ruleRepository;
	
	private Long version = 0l;
	
	public List<Rule> getAllRules() {
		return ruleRepository.findAll();
	}
	
	public List<Rule> getAllRulesByVersionGreaterThan(Long version) {
		return ruleRepository.findByVersionGreaterThan(version);
	}
	
	public Rule saveRule(Rule rule) {
		rule.setVersion(++version);
		return ruleRepository.saveAndFlush(rule);
	}
	
	public void clear() {
		ruleRepository.deleteAll();
	}


}
