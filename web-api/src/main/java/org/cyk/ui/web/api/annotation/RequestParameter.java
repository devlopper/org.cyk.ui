package org.cyk.ui.web.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.cyk.system.root.model.AbstractIdentifiable;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface RequestParameter {
	
	String name() default "";
	
	Class<? extends AbstractIdentifiable> type() default AbstractIdentifiable.class;
	
}