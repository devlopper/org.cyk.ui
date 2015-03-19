package org.cyk.ui.web.primefaces;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.inject.Singleton;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.ContentType;
import org.cyk.ui.api.AbstractUITargetManager;
import org.cyk.ui.api.data.collector.control.InputChoice;
import org.cyk.ui.web.api.WebManager;
import org.cyk.ui.web.primefaces.data.collector.control.InputManyPickList;
import org.cyk.ui.web.primefaces.data.collector.control.InputText;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;
import org.primefaces.context.RequestContext;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;

@Singleton @Named @Deployment(initialisationType=InitialisationType.EAGER) @Getter @Setter
public class PrimefacesManager extends AbstractUITargetManager<DynaFormModel,DynaFormRow,DynaFormLabel,DynaFormControl,SelectItem> implements Serializable {

	private static final long serialVersionUID = -3546850417728323300L;

	private static PrimefacesManager INSTANCE;
	public static final String PUSH_CHANNEL_GLOBAL = "/pushChannelGlobal";
	public static final String PUSH_CHANNEL_USER = "/pushChannelUser";
	
	public static PrimefacesManager getInstance() {
		return INSTANCE;
	}
	
	private String templateControlSetDefault = "/org.cyk.ui.web.primefaces/template/controlset/default.xhtml";
	private String formId = ":form:contentPanel";
	
	private String notificationChannelSocketWidgetVar = "notificationChannelWidgetVar";
	private String notificationChannelGrowlWidgetVar = "notificationChannelGrowlWidgetVar";
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();	
		uiProvider.setControlBasePackage(InputText.class.getPackage());
		uiProvider.setCommandableClass(Commandable.class);
		uiProvider.getUiProviderListeners().add(this);
	}
	
	@Override
	public ContentType contentType() {
		return ContentType.HTML;
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
	
	@SuppressWarnings("unchecked")
	@Override
	public void choices(InputChoice<?, ?, ?, ?, ?, ?> inputChoice, Object data,Field field, List<Object> list) {
		super.choices(inputChoice, data, field, list);
		if(inputChoice instanceof InputManyPickList){
			InputManyPickList<Object> pickList = (InputManyPickList<Object>) inputChoice;
			pickList.getDualListModel().setSource(list);
			List<Object> targetList = (List<Object>) commonUtils.readField(data, field, Boolean.TRUE);
			try {
				FieldUtils.writeField(field, data, targetList);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			pickList.getDualListModel().setTarget(targetList);
		}
	}
	
	
	
	@Override
	protected Boolean itemWrapper(InputChoice<?, ?, ?, ?, ?, ?> inputChoice) {
		return !(inputChoice instanceof InputManyPickList<?>);
	}

	@Override
	protected SelectItem item(AbstractIdentifiable identifiable) {
		return new SelectItem(identifiable,identifiable instanceof AbstractEnumeration?((AbstractEnumeration)identifiable).getName():identifiable.getUiString());
	}
	
	@Override
	protected SelectItem item(Enum<?> anEnum) {
		return new SelectItem(anEnum,anEnum.toString());
	}
	
	public void connectSocket(String socket,String channel){
		RequestContext.getCurrentInstance().execute("PF('"+socket+"').connect('"+channel+ "');");
	}
	
	public void disconnectSocket(String socket){
		RequestContext.getCurrentInstance().execute("PF('"+socket+"').disconnect();");
	}

}
