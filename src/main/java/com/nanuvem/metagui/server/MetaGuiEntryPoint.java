package com.nanuvem.metagui.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

import com.nanuvem.metagui.server.container.DomainModelContainer;

@SpringBootApplication
@ConfigurationProperties
public class MetaGuiEntryPoint {

	public static void run(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(MetaGuiEntryPoint.class, args);
		DomainModelContainer.setApplicationContext(run);
	}
	
	public static void main(String[] args) {
		run(args);
	}
}
