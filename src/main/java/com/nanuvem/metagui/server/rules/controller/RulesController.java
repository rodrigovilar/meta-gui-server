package com.nanuvem.metagui.server.rules.controller;

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
import com.nanuvem.metagui.server.rules.RulesContainer;

@Controller
@RequestMapping(value = "/rules")
public class RulesController {

	@Autowired
	private RulesContainer rulesContainer;
	
	public void setRulesContainer(RulesContainer rulesContainer) {
		this.rulesContainer = rulesContainer;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Rule>> getAll(@RequestParam(value="version", defaultValue="0") Long version) {
		List<Rule> rules = null;
		if(version != 0) {
			rules = rulesContainer.getAllRulesByVersionGreaterThan(version);
		}
		else {
			rules = rulesContainer.getAllRules();
		}
		return new ResponseEntity<List<Rule>>(rules, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Rule> createRule(@RequestBody String input) {
		Rule rule = new Gson().fromJson(input, Rule.class);
		rule = rulesContainer.saveRule(rule);
		return new ResponseEntity<Rule>(rule, HttpStatus.CREATED);
	}
	
}
