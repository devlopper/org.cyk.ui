package org.cyk.ui.api.layout;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.LinkedList;

import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.ui.api.annotation.FormField;
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
	
	@Getter @Setter protected Integer columnsCount = 2;
	@Inject @Getter protected CommonMethodProvider commonMethodProvider;
	@Inject @Getter protected CommonUtils commonUtils;
	protected Collection<ILayoutRow> rows = new ArrayList<>();
	@Getter protected ILayoutRow currentRow;
	
	protected Collection<Object> models = new LinkedList<>();
	protected Collection<Class<?>> groups = new LinkedHashSet<>();
	
	@Override
	public Collection<ILayoutRow> getRows() {
		return rows;
	}
		
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
		for(Object model : models)
			build(model);
	}
	
	/**/
	
	private void build(Object model) {
		Collection<Class<? extends Annotation>> annotationClasses = new ArrayList<>();
		annotationClasses.add(FormField.class);
		Collection<Field> fields = commonUtils.getAllFields(model.getClass(), annotationClasses);
		Integer columnsCount = 0;
		createRow();
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
				OutputLabel label = new OutputLabel(field.getName());
				IComponent<?> input = component(field, model);
				if(input==null){
					log.warning("No component can be found for Type "+field.getType()+". It will be ignored");
					continue;
				}
				add(label);
				add(input);
				columnsCount += label.getWidth()+input.getWidth();
				//System.out.println(columnsCount+" "+layout.getColumnsCount());
				if(columnsCount>=getColumnsCount()){
					createRow();
					columnsCount = 0;
				}
			}
		}
	}
	
	private IComponent<?> component(Field aField,Object anObject){
		IComponent<?> component = null;
		if(String.class.equals(aField.getType()))
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

}
