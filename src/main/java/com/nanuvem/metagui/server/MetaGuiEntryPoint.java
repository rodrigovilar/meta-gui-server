package com.nanuvem.metagui.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.nanuvem.metagui.server.container.DomainModelContainer;

@SpringBootApplication
public class MetaGuiEntryPoint {

	public static void run(String[] args) {
		ConfigurableApplicationContext application = SpringApplication.run(MetaGuiEntryPoint.class, args);
		DomainModelContainer.setApplicationContext(application);
	}
	
	public static void main(String[] args) {
		run(args);
	}
}
