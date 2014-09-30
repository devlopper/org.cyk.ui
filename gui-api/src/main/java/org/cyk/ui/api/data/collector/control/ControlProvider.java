package org.cyk.ui.api.data.collector.control;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Singleton;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.ui.api.UIManager;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;
import org.cyk.utility.common.cdi.AbstractBean;

@Getter @Setter @Singleton @Deployment(initialisationType=InitialisationType.EAGER)
public class ControlProvider extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 4968509852930057567L;

	private static ControlProvider INSTANCE;
	
	private Package basePackage = getClass().getPackage();
	private Collection<ControlProviderListener<?,?,?,?,?>> controlProviderListeners = new ArrayList<>();
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	private Control<?,?,?,?,?> createControlInstance(Class<? extends Control<?,?,?,?,?>> controlClass){
		for(ControlProviderListener<?,?,?,?,?> listener : controlProviderListeners){
			Class<? extends Control<?,?,?,?,?>> c = listener.controlClassSelected(controlClass);
			if(c!=null)
				controlClass = c; 
		}
		
		Control<?,?,?,?,?> control;
		try {
			control = (Control<?,?,?,?,?>) controlClass.newInstance();
			for(ControlProviderListener<?,?,?,?,?> listener : controlProviderListeners)
				listener.controlInstanceCreated(control);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return control;
	}
	
	public Control<?,?,?,?,?> createFieldControl(Object data,Field field){
		Control<?,?,?,?,?> control = createControlInstance(controlClass(field));
		/*

		*/
		if(control instanceof Input<?,?,?,?,?,?>){
			((Input<?,?,?,?,?,?>)control).setLabel(UIManager.getInstance().fieldLabel(field));
			((Input<?,?,?,?,?,?>)control).setField(field);
			try {
				FieldUtils.writeField(control, "value", FieldUtils.readField(field, data, Boolean.TRUE), Boolean.TRUE);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			((Input<?,?,?,?,?,?>)control).setReadOnlyValue(readOnlyValue(field, data));
			((Input<?,?,?,?,?,?>)control).setRequired(field.getAnnotation(NotNull.class)!=null);
		}
		return control;
	}
	
	public OutputLabel<?,?,?,?,?> createLabel(String value){
		@SuppressWarnings("unchecked")
		OutputLabel<?,?,?,?,?> outputLabel = (OutputLabel<?, ?, ?, ?, ?>) createControlInstance(controlClass((Class<? extends Control<?,?,?,?,?>>)OutputLabel.class));
		outputLabel.setValue(value);
		return outputLabel;
	}
	
	/* */
	
	@SuppressWarnings("unchecked")
	private Class<? extends Control<?,?,?,?,?>> controlClass(Field field){
		Class<?> controlInterface = null;
		if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputText.class)!=null){
			controlInterface = InputText.class;
		}/*else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputTextarea.class)!=null){
			className = InputTextarea.class.getSimpleName();
		}else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputCalendar.class)!=null){
			className = InputCalendar.class.getSimpleName();
		}else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputEditor.class)!=null){
			className = InputEditor.class.getSimpleName();
		}else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputBooleanButton.class)!=null){
			className = InputBooleanButton.class.getSimpleName();
		}else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputPassword.class)!=null){
			className = InputPassword.class.getSimpleName();
		}else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputFile.class)!=null){
			className = InputFile.class.getSimpleName();
		}else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputBooleanCheck.class)!=null){
			className = InputBooleanCheck.class.getSimpleName();
		}else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputNumber.class)!=null){
			className = InputNumber.class.getSimpleName();
		}else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputOneList.class)!=null){
			className = InputOneList.class.getSimpleName();
		}else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputOneCombo.class)!=null){
			className = InputOneCombo.class.getSimpleName();
		}else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputOneButton.class)!=null){
			className = InputOneButton.class.getSimpleName();
		}else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputOneCascadeList.class)!=null){
			className = InputOneCascadeList.class.getSimpleName();
		}else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputOneRadio.class)!=null){
			className = InputOneRadio.class.getSimpleName();
		}else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputManyCombo.class)!=null){
			className = InputManyCombo.class.getSimpleName();
		}else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputManyPickList.class)!=null){
			className = InputManyPickList.class.getSimpleName();
		}else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputManyCheckCombo.class)!=null){
			className = InputManyCheckCombo.class.getSimpleName();
		}else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputManyCheck.class)!=null){
			className = InputManyCheck.class.getSimpleName();
		}else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputManyButton.class)!=null){
			className = InputManyButton.class.getSimpleName();
		}*/else
			return null;
		return controlClass((Class<? extends Control<?,?,?,?,?>>)controlInterface);
	}
	
	@SuppressWarnings("unchecked")
	private Class<? extends Control<?,?,?,?,?>> controlClass(Class<? extends Control<?,?,?,?,?>> anInterface){
		try {
			return (Class<? extends Control<?, ?, ?, ?, ?>>) Class.forName(basePackage.getName()+"."+anInterface.getSimpleName());
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
	
	public static ControlProvider getInstance() {
		return INSTANCE;
	}
	
}
