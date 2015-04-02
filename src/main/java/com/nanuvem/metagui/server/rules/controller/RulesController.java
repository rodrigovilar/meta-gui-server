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
import com.nanuvem.metagui.server.api.DefaultEntityRule;
import com.nanuvem.metagui.server.api.EntityRule;
import com.nanuvem.metagui.server.api.PropertyRule;
import com.nanuvem.metagui.server.api.PropertyTypeRule;
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
	public <T extends Rule> ResponseEntity<List<T>> getAll(@RequestParam(value="version", defaultValue="0") Long version) {
		List<T> rules = null;
		if(version != 0) {
			rules = rulesContainer.getAllRulesByVersionGreaterThan(version);
		}
		else {
			rules = rulesContainer.getAllRules();
		}
		return new ResponseEntity<List<T>>(rules, HttpStatus.OK);
	}
	
	@RequestMapping(value = "defaultentityrule", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<DefaultEntityRule> createDefaultEntityRule(@RequestBody String input) {
		DefaultEntityRule defaultEntityRule = new Gson().fromJson(input, DefaultEntityRule.class);
		defaultEntityRule = rulesContainer.saveDefaultEntityRule(defaultEntityRule);
		return new ResponseEntity<DefaultEntityRule>(defaultEntityRule, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "propertytyperule", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<PropertyTypeRule> createPropertyTypeRule(@RequestBody String input) {
		PropertyTypeRule propertyTypeRule = new Gson().fromJson(input, PropertyTypeRule.class);
		propertyTypeRule = rulesContainer.savePropertyTypeRule(propertyTypeRule);
		return new ResponseEntity<PropertyTypeRule>(propertyTypeRule, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "propertyrule", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<PropertyRule> createPropertyRule(@RequestBody String input) {
		PropertyRule propertyRule = new Gson().fromJson(input, PropertyRule.class);
		propertyRule = rulesContainer.savePropertyRule(propertyRule);
		return new ResponseEntity<PropertyRule>(propertyRule, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "entityrule", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<EntityRule> createEntityRule(@RequestBody String input) {
		EntityRule entityRule = new Gson().fromJson(input, EntityRule.class);
		entityRule = rulesContainer.saveEntityRule(entityRule);
		return new ResponseEntity<EntityRule>(entityRule, HttpStatus.CREATED);
	}
	
}
