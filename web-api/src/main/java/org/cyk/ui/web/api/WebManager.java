package org.cyk.ui.web.api;

import java.io.Serializable;

import javax.inject.Named;
import javax.inject.Singleton;

import lombok.Getter;

import org.cyk.utility.common.validation.Client;

@Singleton @Named @Getter
public class WebManager implements Serializable {

	private static final long serialVersionUID = -1690449792881915040L;
	
	private final String clientValidationGroupClass = Client.class.getName();
	
	private final String requestParameterClass = "clazz";
	

}
