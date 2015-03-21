package com.nanuvem.metagui.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages={"com.nanuvem.metagui.server.controller"})
public class MetaGuiEntryPoint {

	public static void run(String[] args) {
		SpringApplication.run(MetaGuiEntryPoint.class, args);
	}
}
