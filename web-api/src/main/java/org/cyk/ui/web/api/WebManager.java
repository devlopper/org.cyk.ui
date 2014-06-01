package org.cyk.ui.web.api;

import java.io.Serializable;

import javax.inject.Named;
import javax.inject.Singleton;

import lombok.Getter;

import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.validation.Client;

@Singleton @Named @Getter
public class WebManager extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -1690449792881915040L;
	
	private static WebManager INSTANCE;
	public static WebManager getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		super.initialisation();
		INSTANCE = this;
	}
	
	private final String clientValidationGroupClass = Client.class.getName();
	
	private final String blockUIDialogWidgetId = "blockUIDialogWidget";
	private final String messageDialogWidgetId = "messageDialogWidget";
	
	private final String requestParameterClass = "clazz";
	

}
