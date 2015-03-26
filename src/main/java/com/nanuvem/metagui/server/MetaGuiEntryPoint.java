package com.nanuvem.metagui.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages={"com.nanuvem.metagui.server.controller", "com.nanuvem.metagui.server.container"})
@EnableJpaRepositories(basePackages = {"com.nanuvem.metagui.server.container"})
public class MetaGuiEntryPoint {

	public static void run(String[] args) {
		SpringApplication.run(MetaGuiEntryPoint.class, args);
	}
	
	public static void main(String[] args) {
		run(args);
	}
}
