package org.cyk.ui.web.api.form.input;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.validator.ValidatorException;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.editor.input.ISelectItem;
import org.cyk.ui.api.editor.input.UIInputComponent;
import org.cyk.ui.api.editor.input.UIInputDate;
import org.cyk.ui.api.editor.input.UIInputNumber;
import org.cyk.ui.api.editor.input.UIInputSelectOne;
import org.cyk.ui.api.editor.input.UIInputText;
import org.cyk.ui.web.api.AbstractWebInputOutputComponent;
import org.cyk.ui.web.api.form.WebEditorInputs;
import org.cyk.utility.common.annotation.UIField;

@Getter @Setter
public class AbstractWebInputComponent<VALUE_TYPE> extends AbstractWebInputOutputComponent<VALUE_TYPE> implements Serializable, WebUIInputComponent<VALUE_TYPE> {

	private static final long serialVersionUID = 6386648827377414199L;
	
	static {
		UIManager.COMPONENT_CREATE_METHOD = new UIManager.ComponentCreateMethod() {
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
				else if(aComponent instanceof UIInputSelectOne<?,?>){
					component = new InputSelectOne<Object>(null,(UIInputSelectOne<Object, ISelectItem>) aComponent);
					WebUIInputSelectOne<Object,Object> inputSelectOne = (WebUIInputSelectOne<Object, Object>) component;
					if(inputSelectOne.getItems()==null || inputSelectOne.getItems().isEmpty()){
						Collection<Object> datas = (Collection<Object>) UIManager.collection(inputSelectOne.getField().getType());
						if(inputSelectOne.getValue()!=null){
							if(datas==null)
								datas = Arrays.asList(inputSelectOne.getValue());
							else
								datas.add(inputSelectOne.getValue());
						}
					}
				}
				return component;
			}
		};
	}
	
	protected WebEditorInputs<?, ?, ?, ?> editorInputs;
	protected String label,requiredMessage,validatorId,validationGroupClass,readOnlyValue,description;
	protected Boolean readOnly,required;
	protected Field field;
	protected Converter converter;
	protected Object object; 
	protected UIField annotation;

	public AbstractWebInputComponent(WebEditorInputs<?, ?, ?, ?> editorInputs,UIInputComponent<VALUE_TYPE> input) {
		label = input.getLabel();
		description = input.getDescription();
		requiredMessage = input.getRequiredMessage();
		readOnly = input.getReadOnly();
		required = input.getRequired();
		field = input.getField();
		object = input.getObject();
		value = input.getValue();
		annotation = field.getAnnotation(UIField.class);
		this.editorInputs = editorInputs;
		readOnlyValue = input.getReadOnlyValue();
		this.validationGroupClass = input.getValidationGroupClass();
	}
	
	public String getReadOnlyValue(){
		Object value = commonUtils.readField(object, field, false);
		return uiManager.toString(value);
	}
	
	public void validate(FacesContext facesContext,UIComponent uiComponent,Object value) throws ValidatorException{
		//Dynamically find validation logic
		//System.out.println("AbstractWebInputComponent.validate() : "+value);
		
	}

	@Override
	public void updateValue() throws Exception {
		FieldUtils.writeField(field, object, value, true);
	}

	@Override
	public String getTemplateFile() {
		return editorInputs.getInputTemplateFile();
	}

	@Override
	public String getTemplateFileAtRight() {
		return editorInputs.getInputTemplateFileAtRight();
	}

	@Override
	public String getTemplateFileAtTop() {
		return editorInputs.getInputTemplateFileAtTop();
	}
		
}
