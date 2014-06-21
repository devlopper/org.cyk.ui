package org.cyk.ui.web.api;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
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
	private final String requestParameterIdentifiable = "identifiable";
	private final String requestParameterWindowMode = "windowmode";
	private final String requestParameterWindowModeDialog = "windowmodedialog";
	private final String requestParameterWindowModeNormal = "windowmodenormal";
	
	public String facesMessageSeverity(FacesMessage facesMessage){
		switch(facesMessage.getSeverity().getOrdinal()){
		case 0:return "info";
		case 1:return "warn";
		case 2:return "error";
		case 3:return "fatal";
		default: return "info";
		}
	}
	
	/* Java Script stuff */
	
	public String javaScriptWindowHref(String url){
		return "window.location='"+url+"'";
	}

}
