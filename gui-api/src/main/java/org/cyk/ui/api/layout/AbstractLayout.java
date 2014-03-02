package org.cyk.ui.api.layout;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.logging.Level;

import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.ui.api.annotation.FormField;
import org.cyk.ui.api.annotation.FormField.CompositionRelationshipInputType;
import org.cyk.ui.api.component.IComponent;
import org.cyk.ui.api.component.input.InputBoolean;
import org.cyk.ui.api.component.input.InputDate;
import org.cyk.ui.api.component.input.InputNumber;
import org.cyk.ui.api.component.input.InputSelectOne;
import org.cyk.ui.api.component.input.InputText;
import org.cyk.ui.api.component.output.OutputLabel;
import org.cyk.utility.common.CommonUtils;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.cdi.provider.CommonMethodProvider;

@Log
public abstract class AbstractLayout extends AbstractBean implements ILayout {

	private static final long serialVersionUID = -3972921029089528026L;
	
	@Getter @Setter protected Integer rowsCount=0,columnsCount = 2,_columnsCounter=0;
	@Inject @Getter protected CommonMethodProvider commonMethodProvider;
	@Inject @Getter protected CommonUtils commonUtils;
	//protected Collection<ILayoutRow> rows = new ArrayList<>();
	//@Getter protected ILayoutRow currentRow;
	
	protected Collection<Object> models = new LinkedList<>();
	protected Collection<Class<?>> groups = new LinkedHashSet<>();
	
	/*
	@Override
	public Collection<ILayoutRow> getRows() {
		return rows;
	}*/
		
	@Override
	public void model(Object... theModels) {
		for(Object model : theModels)
			models.add(model);
	}
	
	@Override
	public void group(Class<?>... theGroupsClasses) {
		for(Class<?> clazz : theGroupsClasses)
			groups.add(clazz);
	}
	
	@Override
	public void build() {
		_columnsCounter = 0;
		addRow();
		for(Object model : models)
			build(model);
	}
	
	/**/
	
	private void addRow(){
		createRow();
		rowsCount++;
	}
	
	private void build(Object model) {
		Collection<Class<? extends Annotation>> annotationClasses = new ArrayList<>();
		annotationClasses.add(FormField.class);
		Collection<Field> fields = commonUtils.getAllFields(model.getClass(), annotationClasses);
		
		for(Field field : fields){
			Boolean add = Boolean.TRUE;
			FormField annotation = field.getAnnotation(FormField.class);
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
				if(CompositionRelationshipInputType.FIELDS.equals(annotation.compositionRelationshipInputType()))
					include(model, field);
				else{
					OutputLabel label = new OutputLabel(field.getName());
					IComponent<?> input = component(field, model,annotation);
					if(input==null){
						log.warning("No component can be found for Type "+field.getType()+". It will be ignored");
						continue;
					}
					add(label);
					add(input);
					_columnsCounter += label.getWidth()+input.getWidth();
					//System.out.println(columnsCount+" "+layout.getColumnsCount());
				}
				
				if(_columnsCounter>=getColumnsCount()){
					addRow();
					_columnsCounter = 0;
				}
			}
		}
	}
	
	private IComponent<?> component(Field aField,Object anObject,FormField annotation){
		IComponent<?> component = null;
		if(CompositionRelationshipInputType.FORM.equals(annotation.compositionRelationshipInputType()))
			component = new InputSelectOne(aField.getName(), aField, anObject);
		else if(String.class.equals(aField.getType()))
			component = new InputText(aField.getName(),aField,anObject);
		else if(Date.class.equals(aField.getType()))
			component = new InputDate(aField.getName(), aField, anObject);
		else if(Boolean.class.equals(aField.getType()))
			component = new InputBoolean(aField.getName(), aField, anObject);
		else if(commonUtils.isNumberClass(aField.getType()))
			component = new InputNumber(aField.getName(), aField, anObject);
		else if(aField.getType().isEnum())
			component = new InputSelectOne(aField.getName(), aField, anObject);
		
		return component;
	}
	
	private void include(Object model,Field field){
		try {
			Object object = FieldUtils.readField(field, model, true);
			if(object==null)
				object = field.getType().newInstance();
			build(object);
		} catch (Exception e) {
			log.log(Level.SEVERE,e.toString(),e);
		}
	}

}
