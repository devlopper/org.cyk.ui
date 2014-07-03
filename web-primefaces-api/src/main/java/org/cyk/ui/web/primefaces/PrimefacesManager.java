package org.cyk.ui.web.primefaces;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.inject.Named;
import javax.inject.Singleton;

import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.editor.input.UIInputComponent;
import org.cyk.ui.web.api.WebManager;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;
import org.cyk.utility.common.cdi.AbstractBean;
import org.primefaces.context.RequestContext;

@Singleton @Named @Deployment(initialisationType=InitialisationType.EAGER)
public class PrimefacesManager extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -3546850417728323300L;

	private static PrimefacesManager INSTANCE;
	
	public static PrimefacesManager getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();		
	}
	
	public String includeFile(UIInputComponent<?> input){
		if(UIManager.getInstance().isInputText(input))
			return "include/inputTextOneLine.xhtml";
		else if(UIManager.getInstance().isInputSelectOne(input))
			return "include/inputOneMenu.xhtml";
		else if(UIManager.getInstance().isInputNumber(input))
			return "include/inputNumber.xhtml";
		return null;
	}
	
	public void openDialog(String outcome,Map<String, Object> dialogParams,Map<String,List<String>> urlParams){
		dialogParams.put("modal", true);  
		dialogParams.put("draggable", false);
		dialogParams.put("resizable", false);  
		dialogParams.put("contentWidth", 800); 
		dialogParams.put("contentHeight", 500); 
		
		urlParams.put(WebManager.getInstance().getRequestParameterWindowMode(),Arrays.asList(WebManager.getInstance().getRequestParameterWindowModeDialog()));
		
		RequestContext.getCurrentInstance().openDialog(outcome, dialogParams, urlParams); 
	}
	
}
