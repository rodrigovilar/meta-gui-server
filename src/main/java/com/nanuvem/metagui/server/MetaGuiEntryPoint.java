package com.nanuvem.metagui.server;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

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
		WidgetService widgetService = application.getBean(WidgetService.class);
		RuleService ruleService = application.getBean(RuleService.class);
		ContextService contextService = application.getBean(ContextService.class);
		
		Context rootContext = createContext("root", WidgetType.EntitySet, contextService);
		Context propertyContext = createContext("property", WidgetType.Entity, contextService);
		Widget listingTableWidget = createWidget("ListingTable", WidgetType.EntitySet, readWidgetFile("ListingTable.js"), widgetService, propertyContext);
		Widget toStringPropertyWidget = createWidget("ToStringProperty", WidgetType.Property, readWidgetFile("ToStringProperty.js"), widgetService);
		Widget DateFormatterWidget = createWidget("DateFormatterWidget", WidgetType.Property, readWidgetFile("DateFormatterWidget.js"), widgetService);
		createRule(rootContext, "*", null, null, listingTableWidget, ruleService);
		createRule(propertyContext, null, null, "*", toStringPropertyWidget, ruleService);
		createRule(propertyContext, null, PropertyTypeType.date, null, DateFormatterWidget, ruleService);
	}
	
	private static Context createContext(String name, WidgetType type, ContextService contextService) {
		Context context = new Context();
		context.setName(name);
		context.setType(type);
		return contextService.createContext(context);
	}
	
	private static Widget createWidget(String name, WidgetType type, String code, WidgetService widgetService, Context ... requiredContexts) {
		Widget widget = new Widget();
		widget.setName(name);
		widget.setType(type);
		widget.setCode(code);
		if(requiredContexts.length > 0)
			widget.setRequiredContexts(Arrays.asList(requiredContexts));
		return widgetService.saveWidget(widget);
	}
	
	private static Rule createRule(Context providedContext, String entityTypeLocator, PropertyTypeType propertyTypeTypeLocator, String propertyTypeLocator, Widget widget, RuleService ruleService) {
		Rule rule = new Rule();
		rule.setProvidedContext(providedContext);
		rule.setEntityTypeLocator(entityTypeLocator);
		rule.setPropertyTypeTypeLocator(propertyTypeTypeLocator);
		rule.setPropertyTypeLocator(propertyTypeLocator);
		rule.setWidget(widget);
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
