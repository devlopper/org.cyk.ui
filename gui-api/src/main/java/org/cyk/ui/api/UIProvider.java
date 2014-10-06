package org.cyk.ui.api;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Singleton;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.ui.api.command.CommandListener;
import org.cyk.ui.api.command.DefaultCommand;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UICommandable.EventListener;
import org.cyk.ui.api.command.UICommandable.IconType;
import org.cyk.ui.api.command.UICommandable.ProcessGroup;
import org.cyk.ui.api.data.collector.control.Control;
import org.cyk.ui.api.data.collector.control.Input;
import org.cyk.ui.api.data.collector.control.InputBooleanButton;
import org.cyk.ui.api.data.collector.control.InputBooleanCheck;
import org.cyk.ui.api.data.collector.control.InputCalendar;
import org.cyk.ui.api.data.collector.control.InputChoice;
import org.cyk.ui.api.data.collector.control.InputEditor;
import org.cyk.ui.api.data.collector.control.InputFile;
import org.cyk.ui.api.data.collector.control.InputManyButton;
import org.cyk.ui.api.data.collector.control.InputManyCheck;
import org.cyk.ui.api.data.collector.control.InputManyCheckCombo;
import org.cyk.ui.api.data.collector.control.InputManyCombo;
import org.cyk.ui.api.data.collector.control.InputManyPickList;
import org.cyk.ui.api.data.collector.control.InputNumber;
import org.cyk.ui.api.data.collector.control.InputOneButton;
import org.cyk.ui.api.data.collector.control.InputOneCascadeList;
import org.cyk.ui.api.data.collector.control.InputOneCombo;
import org.cyk.ui.api.data.collector.control.InputOneList;
import org.cyk.ui.api.data.collector.control.InputOneRadio;
import org.cyk.ui.api.data.collector.control.InputPassword;
import org.cyk.ui.api.data.collector.control.InputText;
import org.cyk.ui.api.data.collector.control.InputTextarea;
import org.cyk.ui.api.data.collector.control.OutputLabel;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;
import org.cyk.utility.common.cdi.AbstractBean;

@Getter @Setter @Singleton @Deployment(initialisationType=InitialisationType.EAGER) 
//TODO to be merge with UIManager -> UIManagerListener
public class UIProvider extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 4968509852930057567L;

	private static UIProvider INSTANCE;
	
	private Package controlBasePackage = getClass().getPackage();
	private Class<? extends UICommandable> commandableClass;
	private Collection<UIProviderListener<?,?,?,?,?>> uiProviderListeners = new ArrayList<>();
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
		
	public Control<?,?,?,?,?> createFieldControl(Object data,Field field){
		Control<?,?,?,?,?> control = createControlInstance(controlClass(field));
		/*

		*/
		if(control instanceof Input<?,?,?,?,?,?>){
			Input<?,?,?,?,?,?> input = (Input<?,?,?,?,?,?>)control;
			input.setLabel(UIManager.getInstance().fieldLabel(field));
			input.setField(field);
			try {
				FieldUtils.writeField(control, "value", FieldUtils.readField(field, data, Boolean.TRUE), Boolean.TRUE);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			input.setReadOnlyValue(readOnlyValue(field, data));
			input.setRequired(field.getAnnotation(NotNull.class)!=null);
		}
		
		if(control instanceof InputChoice<?,?,?,?,?,?>){
			@SuppressWarnings("unchecked")
			InputChoice<?,?,?,?,?,Object> inputChoice = (InputChoice<?,?,?,?,?,Object>)control;
			for(UIProviderListener<?,?,?,?,?> listener : uiProviderListeners)
				listener.choices(data,field, (List<Object>) inputChoice.getList());
		}
		
		return control;
	}
	
	public OutputLabel<?,?,?,?,?> createLabel(String value){
		@SuppressWarnings("unchecked")
		OutputLabel<?,?,?,?,?> outputLabel = (OutputLabel<?, ?, ?, ?, ?>) createControlInstance(controlClass((Class<? extends Control<?,?,?,?,?>>)OutputLabel.class));
		outputLabel.setValue(value);
		return outputLabel;
	}
	
	public UICommandable createCommandable(CommandListener commandListener,String labelId,IconType iconType,EventListener anExecutionPhase,ProcessGroup aProcessGroup){
		Class<? extends UICommandable> commandableClass = this.commandableClass;
		for(UIProviderListener<?,?,?,?,?> listener : uiProviderListeners){
			Class<? extends UICommandable> c = listener.commandableClassSelected(commandableClass);
			if(c!=null)
				commandableClass = c; 
		}
		UICommandable commandable;
		try {
			commandable = commandableClass.newInstance();
			commandable.setCommand(new DefaultCommand());
			commandable.getCommand().setMessageManager(MessageManager.INSTANCE);
			commandable.setLabel(UIManager.getInstance().text(labelId));
			commandable.setIconType(iconType);
			commandable.setEventListener(anExecutionPhase);
			commandable.setProcessGroup(aProcessGroup);
			if(commandListener!=null)
				commandable.getCommand().getCommandListeners().add(commandListener);
			for(UIProviderListener<?,?,?,?,?> listener : uiProviderListeners)
				listener.commandableInstanceCreated(commandable);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return commandable;
	}
	
	/* */
	
	private Control<?,?,?,?,?> createControlInstance(Class<? extends Control<?,?,?,?,?>> controlClass){
		for(UIProviderListener<?,?,?,?,?> listener : uiProviderListeners){
			Class<? extends Control<?,?,?,?,?>> c = listener.controlClassSelected(controlClass);
			if(c!=null)
				controlClass = c; 
		}
		
		Control<?,?,?,?,?> control;
		try {
			control = (Control<?,?,?,?,?>) controlClass.newInstance();
			for(UIProviderListener<?,?,?,?,?> listener : uiProviderListeners)
				listener.controlInstanceCreated(control);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return control;
	}
	
	@SuppressWarnings("unchecked")
	private Class<? extends Control<?,?,?,?,?>> controlClass(Field field){
		Class<?> controlInterface = null;
		if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputText.class)!=null){
			controlInterface = InputText.class;
		}else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputTextarea.class)!=null){
			controlInterface = InputTextarea.class;
		}else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputCalendar.class)!=null){
			controlInterface = InputCalendar.class;
		}else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputEditor.class)!=null){
			controlInterface = InputEditor.class;
		}else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputBooleanButton.class)!=null){
			controlInterface = InputBooleanButton.class;
		}else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputPassword.class)!=null){
			controlInterface = InputPassword.class;
		}else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputFile.class)!=null){
			controlInterface = InputFile.class;
		}else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputBooleanCheck.class)!=null){
			controlInterface = InputBooleanCheck.class;
		}else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputNumber.class)!=null){
			controlInterface = InputNumber.class;
		}else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputOneList.class)!=null){
			controlInterface = InputOneList.class;
		}else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputOneCombo.class)!=null){
			controlInterface = InputOneCombo.class;
		}else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputOneButton.class)!=null){
			controlInterface = InputOneButton.class;
		}else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputOneCascadeList.class)!=null){
			controlInterface = InputOneCascadeList.class;
		}else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputOneRadio.class)!=null){
			controlInterface = InputOneRadio.class;
		}else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputManyCombo.class)!=null){
			controlInterface = InputManyCombo.class;
		}else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputManyPickList.class)!=null){
			controlInterface = InputManyPickList.class;
		}else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputManyCheckCombo.class)!=null){
			controlInterface = InputManyCheckCombo.class;
		}else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputManyCheck.class)!=null){
			controlInterface = InputManyCheck.class;
		}else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputManyButton.class)!=null){
			controlInterface = InputManyButton.class;
		}else
			return null;
		return controlClass((Class<? extends Control<?,?,?,?,?>>)controlInterface);
	}
	
	@SuppressWarnings("unchecked")
	private Class<? extends Control<?,?,?,?,?>> controlClass(Class<? extends Control<?,?,?,?,?>> anInterface){
		try {
			return (Class<? extends Control<?, ?, ?, ?, ?>>) Class.forName(controlBasePackage.getName()+"."+anInterface.getSimpleName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String readOnlyValue(Field field,Object object){
		Object value = null;
		try {
			value = FieldUtils.readField(field, object, Boolean.TRUE);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		if(value==null)
			return "";
		return value.toString();
	}
	
	public static UIProvider getInstance() {
		return INSTANCE;
	}
	
}
