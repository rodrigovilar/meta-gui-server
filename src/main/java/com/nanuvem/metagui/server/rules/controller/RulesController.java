package com.nanuvem.metagui.server.rules.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.nanuvem.metagui.server.api.Rule;
import com.nanuvem.metagui.server.rules.RuleService;

@Controller
@RequestMapping(value = "/rules")
public class RulesController {

	@Autowired
	private RuleService rulesContainer;
	
	public void setRulesContainer(RuleService rulesContainer) {
		this.rulesContainer = rulesContainer;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<RuleRest>> getAll(@RequestParam(value="version", defaultValue="0") Long version) {
		List<Rule> rules = null;
		if(version != 0) {
			rules = rulesContainer.getAllRulesByVersionGreaterThan(version);
		}
		else {
			rules = rulesContainer.getAllRules();
		}
		
		List<RuleRest> rulesRest = new ArrayList<RuleRest>();
		for(Rule rule : rules) {
			rulesRest.add(RuleRest.toRest(rule));
		}
		
		return new ResponseEntity<List<RuleRest>>(rulesRest, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<RuleRest> createRule(@RequestBody String input) {
		RuleRest ruleRest = new Gson().fromJson(input, RuleRest.class);
		Rule rule = rulesContainer.saveRule(RuleRest.toDomain(ruleRest));
		return new ResponseEntity<RuleRest>(RuleRest.toRest(rule), HttpStatus.CREATED);
	}
	
}
