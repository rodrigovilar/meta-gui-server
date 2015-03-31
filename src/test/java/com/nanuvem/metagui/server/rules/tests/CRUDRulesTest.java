package com.nanuvem.metagui.server.rules.tests;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nanuvem.metagui.server.MetaGuiEntryPoint;
import com.nanuvem.metagui.server.api.DefaultEntityRule;
import com.nanuvem.metagui.server.api.Rule;
import com.nanuvem.metagui.server.rules.RulesContainer;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MetaGuiEntryPoint.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CRUDRulesTest {

	@Autowired
	private RulesContainer rulesContainer;
	
	@Test
	public void testCRUD() {
		List<Rule> allRules = rulesContainer.getAllRules();
		rulesContainer.saveDefaultEntityRule(new DefaultEntityRule());
		allRules = rulesContainer.getAllRules();
	}
	
}
