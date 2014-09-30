package org.cyk.ui.web.primefaces;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.data.collector.control.Control;
import org.cyk.ui.api.data.collector.control.ControlProvider;
import org.cyk.ui.api.data.collector.control.ControlProviderListener;
import org.cyk.ui.api.editor.input.UIInputComponent;
import org.cyk.ui.web.api.WebManager;
import org.cyk.ui.web.primefaces.data.collector.control.InputText;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;
import org.cyk.utility.common.cdi.AbstractBean;
import org.primefaces.context.RequestContext;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;

@Singleton @Named @Deployment(initialisationType=InitialisationType.EAGER) @Getter @Setter
public class PrimefacesManager extends AbstractBean implements ControlProviderListener<DynaFormModel,DynaFormRow,DynaFormLabel,DynaFormControl,SelectItem>, Serializable {

	private static final long serialVersionUID = -3546850417728323300L;

	private static PrimefacesManager INSTANCE;
	
	public static PrimefacesManager getInstance() {
		return INSTANCE;
	}
	
	@Inject private ControlProvider controlProvider;
	private String templateControlSetDefault = "/org.cyk.ui.web.primefaces/template/controlset/default.xhtml";
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();	
		controlProvider.setBasePackage(InputText.class.getPackage());
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

	@Override
	public Class<? extends Control<?, ?, ?, ?, ?>> controlClassSelected(Class<? extends Control<?, ?, ?, ?, ?>> aClass) {
		return null;
	}

	@Override
	public void controlInstanceCreated(Control<?, ?, ?, ?, ?> control) {
		
		
	}
	
}
