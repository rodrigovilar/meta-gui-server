package com.nanuvem.metagui.server;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.nanuvem.metagui.server.api.Cardinality;
import com.nanuvem.metagui.server.api.Context;
import com.nanuvem.metagui.server.api.Rule;
import com.nanuvem.metagui.server.api.Widget;
import com.nanuvem.metagui.server.api.WidgetType;
import com.nanuvem.metagui.server.container.DomainModelContainer;
import com.nanuvem.metagui.server.context.ContextService;
import com.nanuvem.metagui.server.controller.PropertyTypeType;
import com.nanuvem.metagui.server.rules.RuleService;
import com.nanuvem.metagui.server.widgets.WidgetService;

@SpringBootApplication
public class MetaGuiEntryPoint {

	private static WidgetService widgetService;
	private static RuleService ruleService;
	private static ContextService contextService;
	
	public static ConfigurableApplicationContext run(String[] args) {
		ConfigurableApplicationContext application = SpringApplication.run(MetaGuiEntryPoint.class, args);
		DomainModelContainer.setApplicationContext(application);
		initDB(application);
		return application;
	}
	
	public static void main(String[] args) {
		run(args);
	}
	
	public static void initDB(ConfigurableApplicationContext application) {
		widgetService = application.getBean(WidgetService.class);
		ruleService = application.getBean(RuleService.class);
		contextService = application.getBean(ContextService.class);
		
		Context rootContext = createContext("root", WidgetType.EntitySet);
		Context propertyContext = createContext("property", WidgetType.Property);
		Context formContext = createContext("form", WidgetType.Entity);
		Context fieldContext = createContext("field", WidgetType.Property);
		Context fieldRelationContext = createContext("fieldRelation", WidgetType.Relationship);
		Widget listingTableWidget = createWidget("ListingTable", WidgetType.EntitySet, readWidgetFile("ListingTable.js"), propertyContext, formContext);
		Widget toStringPropertyWidget = createWidget("ToStringProperty", WidgetType.Property, readWidgetFile("ToStringProperty.js"));
		Widget DateFormatterWidget = createWidget("DateFormatterWidget", WidgetType.Property, readWidgetFile("DateFormatterWidget.js"));
		Widget simpleFormWidget = createWidget("SimpleFormWidget", WidgetType.Entity, readWidgetFile("SimpleFormWidget.js"), fieldContext);
		Widget simpleTextFieldPropertyWidget = createWidget("SimpleTextFieldPropertyWidget", WidgetType.Property, readWidgetFile("SimpleTextFieldProperty.js"));
		Widget comboBoxWidget = createWidget("ComboBoxWidget", WidgetType.Relationship, readWidgetFile("ComboBoxWidget.js"));
		createRule(rootContext.getName(), "*", null, null, null, listingTableWidget, null);
		createRule(propertyContext.getName(), null, null, "*", null, toStringPropertyWidget, null);
		createRule(propertyContext.getName(), null, PropertyTypeType.date.name(), null, null, DateFormatterWidget, "{\"format\": \"dd-mm-yy\"}");
		createRule(formContext.getName(), null, null, null, null, simpleFormWidget, null);
		createRule(fieldContext.getName(), null, null, null, null, simpleTextFieldPropertyWidget, null);
		createRule(fieldRelationContext.getName(), null, null, null, Cardinality.One, comboBoxWidget, null);
	}
	
	public static Context createContext(String name, WidgetType type) {
		Context context = new Context();
		context.setName(name);
		context.setType(type);
		return contextService.createContext(context);
	}
	
	public static Widget createWidget(String name, WidgetType type, String code, Context ... requiredContexts) {
		Widget widget = new Widget();
		widget.setName(name);
		widget.setType(type);
		widget.setCode(code);
		if(requiredContexts.length > 0)
			widget.setRequiredContexts(Arrays.asList(requiredContexts));
		return widgetService.saveWidget(widget);
	}
	
	public static Rule createRule(String providedContextName, String entityTypeLocator, String propertyTypeTypeLocator, String propertyTypeLocator, Cardinality relationshipTargetCardinality, Widget widget, String configuration) {
		Rule rule = new Rule();
		rule.setProvidedContext(contextService.getContextByName(providedContextName));
		rule.setEntityTypeLocator(entityTypeLocator);
		rule.setPropertyTypeTypeLocator(propertyTypeTypeLocator);
		rule.setPropertyTypeLocator(propertyTypeLocator);
		rule.setRelationshipTargetCardinality(relationshipTargetCardinality);
		rule.setWidget(widget);
		rule.setConfiguration(configuration);
		return ruleService.saveRule(rule);
	}
	
	public static String readWidgetFile(String fileName) {
		String filePath = MetaGuiEntryPoint.class.getResource("/widgets/" + fileName).getPath();
		filePath = filePath.startsWith("file:") ? filePath.substring(5) : filePath;
		filePath = System.getProperty( "os.name" ).contains( "indow" ) && filePath.startsWith("/") ? filePath.substring(1) : filePath;
		try {
			Path widgetPath = Paths.get(filePath);
			return new String(Files.readAllBytes(widgetPath));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
