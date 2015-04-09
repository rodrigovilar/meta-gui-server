package com.nanuvem.metagui.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.nanuvem.metagui.server.api.Widget;
import com.nanuvem.metagui.server.api.WidgetType;
import com.nanuvem.metagui.server.container.DomainModelContainer;
import com.nanuvem.metagui.server.rules.RuleService;
import com.nanuvem.metagui.server.widgets.WidgetService;

@SpringBootApplication
public class MetaGuiEntryPoint {

	
	public static ConfigurableApplicationContext run(String[] args) {
		ConfigurableApplicationContext application = SpringApplication.run(MetaGuiEntryPoint.class, args);
		DomainModelContainer.setApplicationContext(application);
		return application;
	}
	
	public static void initDB(ConfigurableApplicationContext application) {
		WidgetService widgetService = application.getBean(WidgetService.class);
		RuleService ruleService = application.getBean(RuleService.class);
		Widget widget = new Widget();
		widget.setName("widget");
		widget.setType(WidgetType.Entity);
		widget.setCode("");
		widgetService.saveWidget(widget);
	}
	
	public static void main(String[] args) {
		ConfigurableApplicationContext configurableApplicationContext = run(args);
		initDB(configurableApplicationContext);
	}
}
