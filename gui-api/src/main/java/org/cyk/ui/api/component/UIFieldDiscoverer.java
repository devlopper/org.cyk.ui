package org.cyk.ui.api.component;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;

import javax.persistence.Embedded;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.cyk.system.root.business.api.Crud;
import org.cyk.ui.api.Debug;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.editor.input.InputDate;
import org.cyk.ui.api.editor.input.InputMany;
import org.cyk.ui.api.editor.input.InputNumber;
import org.cyk.ui.api.editor.input.InputSelectOne;
import org.cyk.ui.api.editor.input.InputText;
import org.cyk.ui.api.editor.input.UIInputComponent;
import org.cyk.ui.api.editor.input.UIInputSelect;
import org.cyk.ui.api.editor.output.OutputSeparator;
import org.cyk.utility.common.annotation.UIField;
import org.cyk.utility.common.annotation.UIField.BreakLineAfter;
import org.cyk.utility.common.annotation.UIField.OneRelationshipInputType;
import org.cyk.utility.common.annotation.UIField.SeparatorAfter;
import org.cyk.utility.common.cdi.AbstractBean;

@Getter @Setter @Log
public class UIFieldDiscoverer extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -1736034319757227724L;
	
	private Object objectModel;
	private Collection<Class<?>> groups = new LinkedHashSet<>();
	private Collection<UIInputOutputComponent<?>> components;
	
	public UIFieldDiscoverer run(Crud crud){
		components = new ArrayList<>();
		build(objectModel);
		for(UIInputOutputComponent<?> component : components){
			if(component instanceof UIInputComponent<?>){
				UIInputComponent<?> input = (UIInputComponent<?>) component;
				if(!Boolean.TRUE.equals(input.getReadOnly()))
					input.setReadOnly(crud==null || Crud.READ.equals(crud) || Crud.DELETE.equals(crud) );
				if(Boolean.TRUE.equals(input.getReadOnly()))
					input.setRequired(Boolean.FALSE);
				else
					input.setRequired(!Boolean.TRUE.equals(Debug.getInstance().getInputIgnoreRequired()) && input.getField().isAnnotationPresent(NotNull.class));
			}
		}
		return this;
	}
	
	private void build(Object objectModel) {
		//Collection<Class<? extends Annotation>> annotationClasses = new ArrayList<>();
		//annotationClasses.add(UIField.class);
		Collection<Field> fields =  commonUtils.getAllFields(objectModel.getClass()); //commonUtils.getAllFields(objectModel.getClass(), annotationClasses);
		
		for(Field field : fields){
			Boolean add = Boolean.TRUE;
			UIFieldInfos uiFieldInfos = uiFieldOf(field,objectModel.getClass());
			if(uiFieldInfos==null)
				continue;
			if(!groups.isEmpty() /*&& ArrayUtils.isNotEmpty(annotation.groups())*/){
				Boolean found = Boolean.FALSE;
				for(Class<?> clazz : groups)
					if(ArrayUtils.contains(uiFieldInfos.getAnnotation().groups(), clazz)){
						//System.out.println("Found : "+clazz+" "+StringUtils.join(annotation.groups()));
						found = Boolean.TRUE;
						break;
					}
				add = found;
			}
			if(add){
				
				OneRelationshipInputType oneRelationshipInputType = uiFieldInfos.getAnnotation().oneRelationshipInputType();
				if(OneRelationshipInputType.AUTO.equals(oneRelationshipInputType))
					if(field.isAnnotationPresent(OneToOne.class))
						oneRelationshipInputType = OneRelationshipInputType.FIELDS;
				
				if(OneRelationshipInputType.FIELDS.equals(oneRelationshipInputType)){
					if(SeparatorAfter.AUTO.equals(uiFieldInfos.getAnnotation().separatorAfter())){
						if(!field.isAnnotationPresent(Embedded.class))
							components.add(new OutputSeparator(UIManager.getInstance().annotationTextValue(uiFieldInfos.getAnnotation().separatorLabelValueType(), 
									uiFieldInfos.getAnnotation().separatorLabel(), UIManager.getInstance().uiLabelIdOfClass(field.getType()))));
					}
					
					build(commonUtils.readField(objectModel,field, true));
				}else{
					
					UIInputComponent<?> input = component(field, uiFieldInfos.getFieldType(),objectModel,uiFieldInfos.getAnnotation());
					if(input==null){
						log.warning("No component can be found for Type "+field.getType()+". It will be ignored");
					}else{
						components.add(input);
						input.setWidth(uiFieldInfos.getAnnotation().columnSpan());
						input.setHeight(uiFieldInfos.getAnnotation().rowSpan());
						if(BreakLineAfter.TRUE.equals(uiFieldInfos.getAnnotation().breakLineAfter()))
							input.setWidth(1000);
						if(SeparatorAfter.TRUE.equals(uiFieldInfos.getAnnotation().separatorAfter())){
							components.add(new OutputSeparator(UIManager.getInstance().annotationTextValue(uiFieldInfos.getAnnotation().separatorLabelValueType(), 
									uiFieldInfos.getAnnotation().separatorLabel(), "SEP")));
						}
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
		else if(Collection.class.isAssignableFrom(aField.getType())){
			component = new InputMany(aField, fieldType, annotation, anObject);
		}else {
			component = new InputSelectOne(aField,fieldType,annotation, anObject);
		}
		
		return component;
	}
	
	public static UIFieldInfos uiFieldOf(Field field,Class<?> clazz){
		Method getter = MethodUtils.getAccessibleMethod(clazz,"get"+StringUtils.capitalize(field.getName()));
		UIField annotation = null;
		Class<?> fieldType = null;
		if(getter!=null){//Method first
			annotation = getter.getAnnotation(UIField.class);
			fieldType = getter.getReturnType();
			//if(annotation!=null)
			//	System.out.println("UIInputFieldDiscoverer.build() : "+fieldType);
		}
		if(annotation==null){// Field second
			annotation = field.getAnnotation(UIField.class);
			fieldType = field.getType();
		}
		if(annotation==null)
			return null;
		return new UIFieldInfos(annotation, fieldType);
	}

}
