package org.cyk.ui.api.component;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.cyk.ui.api.editor.input.InputDate;
import org.cyk.ui.api.editor.input.InputNumber;
import org.cyk.ui.api.editor.input.InputSelectOne;
import org.cyk.ui.api.editor.input.InputText;
import org.cyk.ui.api.editor.input.UIInputComponent;
import org.cyk.ui.api.editor.input.UIInputSelect;
import org.cyk.utility.common.annotation.UIField;
import org.cyk.utility.common.annotation.UIField.OneRelationshipInputType;
import org.cyk.utility.common.cdi.AbstractBean;

@Getter @Setter @Log
public class UIInputFieldDiscoverer extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -1736034319757227724L;
	
	private Object objectModel;
	private Collection<Class<?>> groups = new LinkedHashSet<>();
	private Collection<UIInputComponent<?>> inputComponents;
	
	public UIInputFieldDiscoverer run(){
		inputComponents = new ArrayList<>();
		build(objectModel);
		return this;
	}
	
	private void build(Object objectModel) {
		//Collection<Class<? extends Annotation>> annotationClasses = new ArrayList<>();
		//annotationClasses.add(UIField.class);
		Collection<Field> fields =  commonUtils.getAllFields(objectModel.getClass()); //commonUtils.getAllFields(objectModel.getClass(), annotationClasses);
		
		for(Field field : fields){
			Boolean add = Boolean.TRUE;
			Method getter = MethodUtils.getAccessibleMethod(objectModel.getClass(),"get"+StringUtils.capitalize(field.getName()));
			UIField annotation = null;
			Class<?> fieldType = null;
			if(getter!=null){//Method first
				annotation = getter.getAnnotation(UIField.class);
				fieldType = getter.getReturnType();
			}
			if(annotation==null){// Field second
				annotation = field.getAnnotation(UIField.class);
				fieldType = field.getType();
			}
			if(annotation==null)
				continue;
			if(!groups.isEmpty() /*&& ArrayUtils.isNotEmpty(annotation.groups())*/){
				Boolean found = Boolean.FALSE;
				for(Class<?> clazz : groups)
					if(ArrayUtils.contains(annotation.groups(), clazz)){
						//System.out.println("Found : "+clazz+" "+StringUtils.join(annotation.groups()));
						found = Boolean.TRUE;
						break;
					}
				add = found;
			}
			if(add){
				if(OneRelationshipInputType.FIELDS.equals(annotation.oneRelationshipInputType())){
					build(commonUtils.readField(objectModel,field, true));
				}else{
					
					UIInputComponent<?> input = component(field, fieldType,objectModel,annotation);
					if(input==null){
						log.warning("No component can be found for Type "+field.getType()+". It will be ignored");
					}else{
						inputComponents.add(input);
						
						if(input instanceof UIInputSelect<?, ?>){
							UIInputSelect<?, ?> inputSelect = (UIInputSelect<?, ?>)input;
							if(inputSelect.isBoolean() || inputSelect.isEnum() || inputSelect.isSelectItemForeign() || inputSelect.getAddable())
								;
							else
								((UIInputComponent<?>) input).setReadOnly(Boolean.TRUE);
						}else{
							
						}
					}
				}
				
			}
		}
	}
	
	private UIInputComponent<?> component(Field aField,Class<?> fieldType,Object anObject,UIField annotation){
		UIInputComponent<?> component = null;
		if(OneRelationshipInputType.FORM.equals(annotation.oneRelationshipInputType()))
			component = new InputSelectOne(aField,fieldType,annotation, anObject);
		else if(String.class.equals(aField.getType()))
			component = new InputText(aField,fieldType,annotation, anObject);
		else if(Date.class.equals(aField.getType()))
			component = new InputDate(aField,fieldType,annotation, anObject);
		else if(Boolean.class.equals(ClassUtils.primitiveToWrapper(aField.getType())))
			component = new InputSelectOne(aField,fieldType,annotation, anObject);
		else if(commonUtils.isNumberClass(aField.getType()))
			component = new InputNumber(aField,fieldType,annotation, anObject);
		else if(aField.getType().isEnum())
			component = new InputSelectOne(aField,fieldType,annotation, anObject);
		
		else {
			component = new InputSelectOne(aField,fieldType,annotation, anObject);
		}
		
		return component;
	}

}
