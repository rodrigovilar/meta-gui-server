package com.nanuvem.metagui.server.api;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;


@Target(FIELD)
@Retention(RUNTIME)
public @interface RelationshipType {

	boolean composition() default false;
}
