package org.cyk.ui.api.form;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.cyk.ui.api.AbstractView;
import org.cyk.ui.api.annotation.FormField;
import org.cyk.ui.api.annotation.FormField.CompositionRelationshipInputType;
import org.cyk.ui.api.component.UIComponent;
import org.cyk.ui.api.component.output.UIOutputComponent;
import org.cyk.ui.api.form.input.UIInputComponent;
import org.cyk.ui.api.form.input.UIInputSelect;
import org.cyk.ui.api.form.input.InputDate;
import org.cyk.ui.api.form.input.InputNumber;
import org.cyk.ui.api.form.input.InputSelectOne;
import org.cyk.ui.api.form.input.InputText;
import org.cyk.ui.api.form.output.IOutputLabel;
import org.cyk.ui.api.form.output.IOutputMessage;
import org.cyk.ui.api.form.output.OutputLabel;
import org.cyk.ui.api.form.output.OutputMessage;

@Log
public abstract class AbstractFormData<FORM,OUTPUTLABEL,INPUT,SELECTITEM> extends AbstractView implements UIFormData<FORM,OUTPUTLABEL,INPUT,SELECTITEM> , Serializable {

	private static final long serialVersionUID = -3666969590470758214L;
	
	@Getter protected FORM dataModel;
	protected OUTPUTLABEL currentLabel;
	protected UIInputComponent<?> currentInput;
	@Getter protected Collection<UIInputComponent<?>> inputFields = new ArrayList<>();
	@Getter @Setter protected UIInputComponent<?> parentField;
	
	@Override
	public void build() {
		dataModel = createDataModel();
		_columnsCounter = 0;
		addRow();
		build(objectModel);
	}
		
	private void addRow() {
		createRow();
		rowsCount++;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void add(UIComponent<?> component) {
		if(component instanceof IOutputLabel)
			currentLabel = (OUTPUTLABEL) createComponent((IOutputLabel) component);
		else if(component instanceof UIInputComponent<?>){
			if(currentLabel!=null){
				UIInputComponent<?> iinput = input((UIInputComponent<?>) component);
				if(iinput==null){
					log.warning("No input component implementation can be found for Type "+component.getFamily()+". It will be ignored");
					return;
				}
				getInputFields().add(iinput);
				INPUT input = (INPUT) createComponent(iinput);
				link(currentLabel, input);
				currentLabel = null;
				currentInput = iinput;
			}
		}else if(component instanceof UIOutputComponent<?>){
			UIOutputComponent<?> ioutput = output((UIOutputComponent<?>) component);
			if(ioutput==null){
				log.warning("No output component implementation can be found for Type "+component.getFamily()+". It will be ignored");
				return;
			}
			createComponent(ioutput);
		}else if(component instanceof IOutputMessage){
			if(currentInput!=null){
				IOutputMessage message = new OutputMessage(currentInput.getId());
				createComponent(message);
			}
		}
	}
	
	private void build(Object objectModel) {
		Collection<Class<? extends Annotation>> annotationClasses = new ArrayList<>();
		annotationClasses.add(FormField.class);
		Collection<Field> fields = commonUtils.getAllFields(objectModel.getClass(), annotationClasses);
		
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
				if(CompositionRelationshipInputType.FIELDS.equals(annotation.compositionRelationshipInputType())){
					build(commonUtils.readField(objectModel,field, true));
				}else{
					OutputLabel label = new OutputLabel(field.getName());
					UIComponent<?> input = component(field, objectModel,annotation);
					if(input==null){
						log.warning("No component can be found for Type "+field.getType()+". It will be ignored");
						continue;
					}
					add(label);
					
					if(input instanceof UIInputSelect<?, ?>){
						UIInputSelect<?, ?> inputSelect = (UIInputSelect<?, ?>)input;
						if(inputSelect.isBoolean() || inputSelect.isEnum() || inputSelect.getAddable())
							;//add(input);
						else
							((UIInputComponent<?>) input).setReadOnly(Boolean.TRUE);
							//add(new OutputText((String) getCommonUtils().readField(objectModel, field, false)));
					}else{
						
					}
					
					add(input);
					
					
					//OutputMessage message = new OutputMessage(input.getId());
					//add(message);
					
					_columnsCounter += label.getWidth()+input.getWidth();//+message.getWidth();
					//System.out.println(columnsCount+" "+layout.getColumnsCount());
				}
				
				if(_columnsCounter>=getColumnsCount()){
					addRow();
					_columnsCounter = 0;
				}
			}
		}
	}
	
	private UIComponent<?> component(Field aField,Object anObject,FormField annotation){
		UIComponent<?> component = null;
		if(CompositionRelationshipInputType.FORM.equals(annotation.compositionRelationshipInputType()))
			component = new InputSelectOne(aField.getName(), aField, anObject);
		else if(String.class.equals(aField.getType()))
			component = new InputText(aField.getName(),aField,anObject);
		else if(Date.class.equals(aField.getType()))
			component = new InputDate(aField.getName(), aField, anObject);
		else if(Boolean.class.equals(ClassUtils.primitiveToWrapper(aField.getType())))
			component = new InputSelectOne(aField.getName(), aField, anObject);
		else if(commonUtils.isNumberClass(aField.getType()))
			component = new InputNumber(aField.getName(), aField, anObject);
		else if(aField.getType().isEnum())
			component = new InputSelectOne(aField.getName(), aField, anObject);
		
		return component;
	}
			
	@Override
	public void updateFieldsValue() throws Exception {
		for(UIInputComponent<?> input : getInputFields()){
			input.updateValue();
			//System.out.println(input.getField().getName()+" - "+container.getCommonUtils().readField(input.getObject(), input.getField(), false));
		}
	}
	
}
