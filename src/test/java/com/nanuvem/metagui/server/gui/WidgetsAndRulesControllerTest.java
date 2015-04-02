package com.nanuvem.metagui.server.gui;

import static com.nanuvem.metagui.server.util.TestHelper.get;
import static com.nanuvem.metagui.server.util.TestHelper.getObjectFromResult;
import static com.nanuvem.metagui.server.util.TestHelper.instance;
import static com.nanuvem.metagui.server.util.TestHelper.objectToMap;
import static com.nanuvem.metagui.server.util.TestHelper.post;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.nanuvem.metagui.server.MetaGuiEntryPoint;
import com.nanuvem.metagui.server.api.DefaultEntityRule;
import com.nanuvem.metagui.server.api.EntityRule;
import com.nanuvem.metagui.server.api.EntityWidget;
import com.nanuvem.metagui.server.api.PropertyRule;
import com.nanuvem.metagui.server.api.PropertyTypeRule;
import com.nanuvem.metagui.server.api.PropertyWidget;
import com.nanuvem.metagui.server.api.Widget;
import com.nanuvem.metagui.server.controller.PropertyTypeType;
import com.nanuvem.metagui.server.rules.RulesContainer;
import com.nanuvem.metagui.server.rules.controller.RulesController;
import com.nanuvem.metagui.server.widgets.WidgetContainer;
import com.nanuvem.metagui.server.widgets.controller.WidgetController;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MetaGuiEntryPoint.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WidgetsAndRulesControllerTest {

	MockMvc mockMvc;

	@Autowired
	RulesContainer rulesContainer;
	@Autowired
	WidgetContainer widgetContainer;
	
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
	public void testCreateRules() throws Exception {
		DefaultEntityRule defaultEntityRule = new DefaultEntityRule();
		defaultEntityRule.setContext("fooContext");
		Map<String, Object> instanceMap = objectToMap(defaultEntityRule);
		instanceMap.remove("id");
		instanceMap.remove("version");
		
		post(mockMvc, "/rules/defaultentityrule", defaultEntityRule).andExpect(status().isCreated()).andExpect(instance(instanceMap));
		
		PropertyTypeRule propertyTypeRule = new PropertyTypeRule();
		propertyTypeRule.setContext("fooContext2");
		propertyTypeRule.setPropertyTypeType(PropertyTypeType.string);
		instanceMap = objectToMap(propertyTypeRule);
		instanceMap.remove("id");
		instanceMap.remove("version");
		instanceMap.put("propertyTypeType", propertyTypeRule.getPropertyTypeType().name());
		
		post(mockMvc, "/rules/propertytyperule", propertyTypeRule).andExpect(status().isCreated()).andExpect(instance(instanceMap));
		
		PropertyRule propertyRule = new PropertyRule();
		propertyRule.setContext("fooContext3");
		propertyRule.setPropertyTypeType(PropertyTypeType.string);
		propertyRule.setPropertyLocator("*.name");
		instanceMap = objectToMap(propertyRule);
		instanceMap.remove("id");
		instanceMap.remove("version");
		instanceMap.put("propertyTypeType", propertyTypeRule.getPropertyTypeType().name());
		
		post(mockMvc, "/rules/propertyrule", propertyRule).andExpect(status().isCreated()).andExpect(instance(instanceMap));
		
		EntityRule entityRule = new EntityRule();
		entityRule.setContext("fooContext4");
		entityRule.setEntityLocator("*Item");
		instanceMap = objectToMap(entityRule);
		instanceMap.remove("id");
		instanceMap.remove("version");
		
		post(mockMvc, "/rules/entityrule", entityRule).andExpect(status().isCreated()).andExpect(instance(instanceMap));
		
	}
	
	@DirtiesContext
	@Test
	public void testGetAllRules() throws Exception {
		get(mockMvc, "/rules").andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(0)));
		
		DefaultEntityRule defaultEntityRule = new DefaultEntityRule();
		defaultEntityRule.setContext("fooContext");
		
		MvcResult mvcResult = post(mockMvc, "/rules/defaultentityrule", defaultEntityRule).andExpect(status().isCreated()).andReturn();
		defaultEntityRule = getObjectFromResult(mvcResult, DefaultEntityRule.class);
		
		get(mockMvc, "/rules").andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(1)));
		
		PropertyTypeRule propertyTypeRule = new PropertyTypeRule();
		propertyTypeRule.setContext("fooContext2");
		propertyTypeRule.setPropertyTypeType(PropertyTypeType.string);
		
		MvcResult mvcResult2 = post(mockMvc, "/rules/propertytyperule", propertyTypeRule).andExpect(status().isCreated()).andReturn();
		propertyTypeRule = getObjectFromResult(mvcResult2, PropertyTypeRule.class);
		
		get(mockMvc, "/rules").andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(2)));
		
		get(mockMvc, "/rules?version=" + defaultEntityRule.getVersion()).andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(1)));
		
		DefaultEntityRule defaultEntityRule2 = new DefaultEntityRule();
		defaultEntityRule2.setContext("fooContext");
		
		MvcResult mvcResult3 = post(mockMvc, "/rules/defaultentityrule", defaultEntityRule2).andExpect(status().isCreated()).andReturn();
		defaultEntityRule2 = getObjectFromResult(mvcResult3, DefaultEntityRule.class);
		
		get(mockMvc, "/rules").andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(3)));
		
		get(mockMvc, "/rules?version=" + defaultEntityRule.getVersion()).andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(2)));
		
		get(mockMvc, "/rules?version=" + propertyTypeRule.getVersion()).andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(1)));
		
	}
	
	@DirtiesContext
	@Test
	public void testCreateWidgets() throws Exception {
		Widget widget = new Widget();
		widget.setName("fooName");
		widget.setScript("fooScript");
		widget.setContexts(new ArrayList<String>(Arrays.asList("form")));
		
		Map<String, Object> widgetInstanceMap = objectToMap(widget);
		widgetInstanceMap.remove("id");
		widgetInstanceMap.put("version", 1);
		
		post(mockMvc, "/widgets", widget).andExpect(status().isCreated()).andExpect(instance(widgetInstanceMap));
		
		Widget widget2 = new Widget();
		widget2.setName("fooName");
		widget2.setScript("otherFooScript");
		widget2.setContexts(new ArrayList<String>(Arrays.asList("form")));
		
		Map<String, Object> widgetInstanceMap2 = objectToMap(widget2);
		widgetInstanceMap2.remove("id");
		widgetInstanceMap2.put("version", 2);
		
		post(mockMvc, "/widgets", widget2).andExpect(status().isCreated()).andExpect(instance(widgetInstanceMap2));
		
		Widget widget3 = new Widget();
		widget3.setName("otherFooName");
		widget3.setScript("fooScript2");
		widget3.setContexts(new ArrayList<String>(Arrays.asList("view")));
		
		Map<String, Object> widgetInstanceMap3 = objectToMap(widget3);
		widgetInstanceMap3.remove("id");
		widgetInstanceMap3.put("version", 1);
		
		post(mockMvc, "/widgets", widget3).andExpect(status().isCreated()).andExpect(instance(widgetInstanceMap3));
		
		EntityWidget entityWidget = new EntityWidget();
		entityWidget.setName("entityWidget");
		entityWidget.setScript("entityWidgetScript");
		entityWidget.setContexts(new ArrayList<String>(Arrays.asList("listing")));
		
		Map<String, Object> widgetInstanceMap4 = objectToMap(entityWidget);
		widgetInstanceMap4.remove("id");
		widgetInstanceMap4.put("version", 1);
		
		post(mockMvc, "/widgets", entityWidget).andExpect(status().isCreated()).andExpect(instance(widgetInstanceMap4));
		
		PropertyWidget propertyWidget = new PropertyWidget();
		propertyWidget.setName("propertyWidget");
		propertyWidget.setScript("propertyWidgetScript");
		propertyWidget.setContexts(new ArrayList<String>(Arrays.asList("listing")));
		
		Map<String, Object> widgetInstanceMap5 = objectToMap(propertyWidget);
		widgetInstanceMap5.remove("id");
		widgetInstanceMap5.put("version", 1);
		
		post(mockMvc, "/widgets", propertyWidget).andExpect(status().isCreated()).andExpect(instance(widgetInstanceMap5));
		
	}
	
	@DirtiesContext
	@Test
	public void testGetWidgets() throws Exception {
		get(mockMvc, "/widgets").andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(0)));
		
		Widget widget = new Widget();
		widget.setName("fooName");
		widget.setScript("fooScript");
		widget.setContexts(new ArrayList<String>(Arrays.asList("form")));
		
		Map<String, Object> widgetInstanceMap = objectToMap(widget);
		widgetInstanceMap.remove("id");
		widgetInstanceMap.put("version", 1);
		
		MvcResult mvcResult = post(mockMvc, "/widgets", widget).andExpect(status().isCreated()).andExpect(instance(widgetInstanceMap)).andReturn();
		widget = getObjectFromResult(mvcResult, Widget.class);
		widgetInstanceMap = objectToMap(widget);
		widgetInstanceMap.put("id", widget.getId().intValue());
		widgetInstanceMap.put("version", widget.getVersion().intValue());
		
		get(mockMvc, "/widgets/" + widget.getId()).andExpect(status().isOk())
		.andExpect(instance(widgetInstanceMap));
		
		get(mockMvc, "/widgets").andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(1)));
		
		Widget widget2 = new Widget();
		widget2.setName("fooName");
		widget2.setScript("otherFooScript");
		widget2.setContexts(new ArrayList<String>(Arrays.asList("form")));
		
		Map<String, Object> widgetInstanceMap2 = objectToMap(widget2);
		widgetInstanceMap2.remove("id");
		widgetInstanceMap2.put("version", 2);
		
		mvcResult = post(mockMvc, "/widgets", widget2).andExpect(status().isCreated()).andExpect(instance(widgetInstanceMap2)).andReturn();
		widget2 = getObjectFromResult(mvcResult, Widget.class);
		widgetInstanceMap2 = objectToMap(widget2);
		widgetInstanceMap2.put("id", widget2.getId().intValue());
		widgetInstanceMap2.put("version", widget2.getVersion().intValue());
		
		get(mockMvc, "/widgets/" + widget2.getId()).andExpect(status().isOk())
		.andExpect(instance(widgetInstanceMap2));
		
		get(mockMvc, "/widgets").andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(2)));
	}
	
	@DirtiesContext
	@Test
	public void testCreateRulesWithWidgets() throws Exception {
		
		EntityWidget entityWidget = new EntityWidget();
		entityWidget.setName("entityWidget");
		entityWidget.setScript("entityWidgetScript");
		entityWidget.setContexts(new ArrayList<String>(Arrays.asList("listing")));
		
		Map<String, Object> widgetInstanceMap4 = objectToMap(entityWidget);
		widgetInstanceMap4.remove("id");
		widgetInstanceMap4.put("version", 1);
		
		MvcResult mvcResult = post(mockMvc, "/widgets", entityWidget).andExpect(status().isCreated()).andExpect(instance(widgetInstanceMap4)).andReturn();
		entityWidget = getObjectFromResult(mvcResult, EntityWidget.class);
		
		DefaultEntityRule defaultEntityRule = new DefaultEntityRule();
		defaultEntityRule.setContext("fooContext");
		EntityWidget widget = new EntityWidget();
		widget.setId(entityWidget.getId());
		defaultEntityRule.setWidget(widget);
		Map<String, Object> instanceMap = objectToMap(defaultEntityRule);
		instanceMap.remove("id");
		instanceMap.remove("version");
		instanceMap.remove("widget");
		
		mvcResult = post(mockMvc, "/rules/defaultentityrule", defaultEntityRule).andExpect(status().isCreated()).andExpect(instance(instanceMap)).andReturn();
		defaultEntityRule = getObjectFromResult(mvcResult, DefaultEntityRule.class);
		
		mvcResult = get(mockMvc, "/rules").andExpect(status().isOk()).andReturn();
	}
	
}
