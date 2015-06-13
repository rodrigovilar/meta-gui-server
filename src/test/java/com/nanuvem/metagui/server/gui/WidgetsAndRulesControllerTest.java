package com.nanuvem.metagui.server.gui;

import static com.nanuvem.metagui.server.util.TestHelper.get;
import static com.nanuvem.metagui.server.util.TestHelper.getObjectFromResult;
import static com.nanuvem.metagui.server.util.TestHelper.instance;
import static com.nanuvem.metagui.server.util.TestHelper.objectToMap;
import static com.nanuvem.metagui.server.util.TestHelper.fixWidgetInstanceMap;
import static com.nanuvem.metagui.server.util.TestHelper.fixRuleInstanceMap;
import static com.nanuvem.metagui.server.util.TestHelper.post;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.nanuvem.metagui.server.MetaGuiEntryPoint;
import com.nanuvem.metagui.server.api.Rule;
import com.nanuvem.metagui.server.api.Widget;
import com.nanuvem.metagui.server.api.WidgetType;
import com.nanuvem.metagui.server.controller.PropertyTypeType;
import com.nanuvem.metagui.server.rules.RuleService;
import com.nanuvem.metagui.server.rules.controller.RulesController;
import com.nanuvem.metagui.server.util.TestCreatorHelper;
import com.nanuvem.metagui.server.widgets.WidgetService;
import com.nanuvem.metagui.server.widgets.controller.WidgetController;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MetaGuiEntryPoint.class)
public class WidgetsAndRulesControllerTest {

	MockMvc mockMvc;

	@Autowired
	RuleService rulesContainer;
	@Autowired
	WidgetService widgetContainer;
	
	@InjectMocks
	RulesController rulesController;
	@InjectMocks
	WidgetController widgetController;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = standaloneSetup(rulesController, widgetController).build();
		rulesController.setRulesContainer(rulesContainer);
		widgetController.setWidgetContainer(widgetContainer);
	}
	
	public void tearDown() {
		rulesContainer.clear();
		widgetContainer.clear();
	}
	
	@DirtiesContext
	@Test
	public void testCreateWidgets() throws Exception {
		Widget widget = TestCreatorHelper.createSimpleWidget("fooName", "fooCode", WidgetType.Property);
		
		Map<String, Object> widgetInstanceMap = objectToMap(widget);
		fixWidgetInstanceMap(widgetInstanceMap, 1, widget.getType().name());
		
		post(mockMvc, "/widgets", widget).andExpect(status().isCreated()).andExpect(instance(widgetInstanceMap));
		
		Widget widget2 = TestCreatorHelper.createSimpleWidget("fooName", "otherFooCode", WidgetType.Property);
		
		Map<String, Object> widgetInstanceMap2 = objectToMap(widget2);
		fixWidgetInstanceMap(widgetInstanceMap2, 2, widget2.getType().name());
		
		post(mockMvc, "/widgets", widget2).andExpect(status().isCreated()).andExpect(instance(widgetInstanceMap2));
		
		Widget widget3 = TestCreatorHelper.createSimpleWidget("otherFooName", "fooCode2", WidgetType.Property);
		
		Map<String, Object> widgetInstanceMap3 = objectToMap(widget3);
		fixWidgetInstanceMap(widgetInstanceMap3, 1, widget3.getType().name());
		
		post(mockMvc, "/widgets", widget3).andExpect(status().isCreated()).andExpect(instance(widgetInstanceMap3));
		
		Widget entityWidget = TestCreatorHelper.createSimpleWidget("entityWidget", "entityWidgetCode", WidgetType.Entity);
		
		Map<String, Object> widgetInstanceMap4 = objectToMap(entityWidget);
		fixWidgetInstanceMap(widgetInstanceMap4, 1, entityWidget.getType().name());
		
		post(mockMvc, "/widgets", entityWidget).andExpect(status().isCreated()).andExpect(instance(widgetInstanceMap4));
		
		Widget entityWidget2 = TestCreatorHelper.createSimpleWidget("entityWidget", "any Code", WidgetType.Entity);
		
		Map<String, Object> widgetInstanceMap5 = objectToMap(entityWidget2);
		fixWidgetInstanceMap(widgetInstanceMap5, 2, entityWidget2.getType().name());
		
		post(mockMvc, "/widgets", entityWidget2).andExpect(status().isCreated()).andExpect(instance(widgetInstanceMap5));
		
		Widget widgetWithContexts = TestCreatorHelper.createSimpleWidget("entityWidget", "any Code", WidgetType.Entity, "form", "report");
		
		Map<String, Object> widgetInstanceMap6 = objectToMap(widgetWithContexts);
		fixWidgetInstanceMap(widgetInstanceMap6, 3, widgetWithContexts.getType().name());
		
		post(mockMvc, "/widgets", widgetWithContexts).andExpect(status().isCreated()).andExpect(instance(widgetInstanceMap6));
		
	}

	@DirtiesContext
	@Test
	public void testGetWidgets() throws Exception {
		get(mockMvc, "/widgets").andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(0)));
		
		Widget widget = TestCreatorHelper.createSimpleWidget("entityWidget", "entityWidgetCode", WidgetType.Entity);
		post(mockMvc, "/widgets", widget).andExpect(status().isCreated());
		get(mockMvc, "/widgets").andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(1)));
		
		Widget widget2 = TestCreatorHelper.createSimpleWidget("fooName", "fooCode", WidgetType.Property);
		post(mockMvc, "/widgets", widget2).andExpect(status().isCreated());
		get(mockMvc, "/widgets").andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(2)));
	}

	@DirtiesContext
	@Test
	public void testCreateRules() throws Exception {
		
		String contextName = "form";
		Widget entitySetWidget = TestCreatorHelper.createSimpleWidget("CRUDComponent", "entityWidgetCode", WidgetType.EntitySet, contextName);
		post(mockMvc, "/widgets", entitySetWidget).andExpect(status().isCreated());
		String contextName2 = "row";
		Widget entityWidget = TestCreatorHelper.createSimpleWidget("TableFormWidget", "entityWidgetCode", WidgetType.Entity, contextName2);
		post(mockMvc, "/widgets", entityWidget).andExpect(status().isCreated());
		Rule defaultEntityRule = TestCreatorHelper.createEntityRule(contextName, "*", entityWidget.getName());
		Map<String, Object> instanceMap = objectToMap(defaultEntityRule);
		fixRuleInstanceMap(instanceMap);
		
		post(mockMvc, "/rules", defaultEntityRule).andExpect(status().isCreated()).andExpect(instance(instanceMap));
		
		Widget propertyWidget = TestCreatorHelper.createSimpleWidget("StringTextField", "fooCode", WidgetType.Property);
		post(mockMvc, "/widgets", propertyWidget).andExpect(status().isCreated());
		Rule propertyTypeRule = TestCreatorHelper.createPropertyRule(contextName2, PropertyTypeType.string, "*", propertyWidget.getName());
		instanceMap = objectToMap(propertyTypeRule);
		fixRuleInstanceMap(instanceMap);
		instanceMap.put("propertyTypeTypeLocator", propertyTypeRule.getPropertyTypeTypeLocator());
		
		post(mockMvc, "/rules", propertyTypeRule).andExpect(status().isCreated()).andExpect(instance(instanceMap));
		
		Rule propertyRule = TestCreatorHelper.createPropertyRule(contextName2, PropertyTypeType.string, "*.name", propertyWidget.getName());
		instanceMap = objectToMap(propertyRule);
		fixRuleInstanceMap(instanceMap);
		instanceMap.put("propertyTypeTypeLocator", propertyTypeRule.getPropertyTypeTypeLocator());
		
		post(mockMvc, "/rules", propertyRule).andExpect(status().isCreated()).andExpect(instance(instanceMap));
		
		Rule entityRule = TestCreatorHelper.createEntityRule(contextName, "*Item", entityWidget.getName());
		instanceMap = objectToMap(entityRule);
		fixRuleInstanceMap(instanceMap);
		
		post(mockMvc, "/rules", entityRule).andExpect(status().isCreated()).andExpect(instance(instanceMap));
		
	}

	
	@DirtiesContext
	@Test
	public void testGetAllRules() throws Exception {
		get(mockMvc, "/rules").andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(0)));
		
		String contextName = "form";
		Widget entitySetWidget = TestCreatorHelper.createSimpleWidget("CRUDComponent", "entityWidgetCode", WidgetType.EntitySet, contextName);
		post(mockMvc, "/widgets", entitySetWidget).andExpect(status().isCreated());
		String contextName2 = "row";
		Widget entityWidget = TestCreatorHelper.createSimpleWidget("TableFormWidget", "entityWidgetCode", WidgetType.Entity, contextName2);
		post(mockMvc, "/widgets", entityWidget).andExpect(status().isCreated());
		Rule defaultEntityRule = TestCreatorHelper.createEntityRule(contextName, "*", entityWidget.getName());
		
		MvcResult mvcResult = post(mockMvc, "/rules", defaultEntityRule).andExpect(status().isCreated()).andReturn();
		defaultEntityRule = getObjectFromResult(mvcResult, Rule.class);
		
		get(mockMvc, "/rules").andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(1)));
		
		Widget propertyWidget = TestCreatorHelper.createSimpleWidget("fooName", "fooCode", WidgetType.Property);
		post(mockMvc, "/widgets", propertyWidget).andExpect(status().isCreated());
		Rule propertyTypeRule = TestCreatorHelper.createPropertyRule(contextName2, PropertyTypeType.string, "*", propertyWidget.getName());
		
		MvcResult mvcResult2 = post(mockMvc, "/rules", propertyTypeRule).andExpect(status().isCreated()).andReturn();
		propertyTypeRule = getObjectFromResult(mvcResult2, Rule.class);
		
		get(mockMvc, "/rules").andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(2)));
		
		get(mockMvc, "/rules?version=" + defaultEntityRule.getVersion()).andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(1)));
		
		Rule defaultEntityRule2 = TestCreatorHelper.createEntityRule(contextName, "*", entityWidget.getName());
		
		MvcResult mvcResult3 = post(mockMvc, "/rules", defaultEntityRule2).andExpect(status().isCreated()).andReturn();
		defaultEntityRule2 = getObjectFromResult(mvcResult3, Rule.class);
		
		get(mockMvc, "/rules").andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(3)));
		
		get(mockMvc, "/rules?version=" + defaultEntityRule.getVersion()).andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(2)));
		
		get(mockMvc, "/rules?version=" + propertyTypeRule.getVersion()).andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(1)));
		
	}

}
