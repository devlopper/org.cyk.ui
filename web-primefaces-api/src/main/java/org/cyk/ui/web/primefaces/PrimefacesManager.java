package org.cyk.ui.web.primefaces;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.inject.Named;
import javax.inject.Singleton;

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
		/*
		UIManager.componentCreateMethod = new UIManager.ComponentCreateMethod() {
			private static final long serialVersionUID = -6005484639897008871L;
			@SuppressWarnings("unchecked")
			@Override
			protected UIInputComponent<?> __execute__(UIInputComponent<?> aComponent) {
				WebUIInputComponent<?> component = null;
				if(aComponent instanceof UIInputText)
					component = new InputText(null,(UIInputText) aComponent);
				else if(aComponent instanceof UIInputDate)
					component = new InputDate(null,(UIInputDate) aComponent);
				else if(aComponent instanceof UIInputNumber)
					component = new InputNumber(null,(UIInputNumber) aComponent);
				else if(aComponent instanceof UIInputMany){
					component = new InputMany(null, (UIInputMany) aComponent);
				}else if(aComponent instanceof UIInputSelectOne<?,?>){
					component = new InputSelectOne<Object>(null,(UIInputSelectOne<Object, ISelectItem>) aComponent);
					WebUIInputSelectOne<Object,Object> inputSelectOne = (WebUIInputSelectOne<Object, Object>) component;
					if(inputSelectOne.isSelectItemForeign() && (inputSelectOne.getItems()==null || inputSelectOne.getItems().isEmpty())){
						Collection<Object> datas = (Collection<Object>) UIManager.getInstance().getCollectionLoadMethod().execute((Class<Object>) inputSelectOne.getFieldType());
						
						//if(inputSelectOne.getValue()!=null){
							if(datas==null)
								if(inputSelectOne.getValue()==null)
									;
								else
									datas = Arrays.asList(inputSelectOne.getValue());
							else if(inputSelectOne.getValue()!=null && !datas.contains(inputSelectOne.getValue()))
								datas.add(inputSelectOne.getValue());
						//}
						
						inputSelectOne.getItems().add(new SelectItem(null, UIManager.getInstance().text("editor.selectone.noselection")"---"));	
						if(datas!=null)
							for(Object object : datas)
								inputSelectOne.getItems().add(new SelectItem(object, UIManager.getInstance().getToStringMethod().execute(object)));
					}
				}
				return component;
			}
		};
		*/
		
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
