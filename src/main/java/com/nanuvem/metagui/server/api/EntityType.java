package com.nanuvem.metagui.server.api;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.data.jpa.repository.JpaRepository;


@Target(TYPE)
@Retention(RUNTIME)
public @interface EntityType {

	String resource();
	Class<? extends JpaRepository<?, ?>> repositoryType();
	
}
