package org.cyk.ui.web.api.editor.input;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.editor.EditorInputs;
import org.cyk.ui.api.editor.input.ISelectItem;
import org.cyk.ui.api.editor.input.UIInputComponent;
import org.cyk.ui.api.editor.input.UIInputDate;
import org.cyk.ui.api.editor.input.UIInputMany;
import org.cyk.ui.api.editor.input.UIInputNumber;
import org.cyk.ui.api.editor.input.UIInputSelectOne;
import org.cyk.ui.api.editor.input.UIInputText;
import org.cyk.ui.web.api.AbstractWebInputOutputComponent;
import org.cyk.ui.web.api.editor.WebEditorInputs;
import org.cyk.utility.common.annotation.UIField;

@Getter @Setter
public class AbstractWebInputComponent<VALUE_TYPE> extends AbstractWebInputOutputComponent<VALUE_TYPE> implements Serializable, WebUIInputComponent<VALUE_TYPE> {

	private static final long serialVersionUID = 6386648827377414199L;
	
	static {
		UIManager.componentCreateMethod = new UIManager.ComponentCreateMethod() {
			private static final long serialVersionUID = -6005484639897008871L;
			@SuppressWarnings("unchecked")
			@Override
			protected UIInputComponent<?> component(EditorInputs<?, ?, ?, ?> anEditorInputs,UIInputComponent<?> aComponent) {
				WebUIInputComponent<?> component = null;
				WebEditorInputs<?, ?, ?, ?> editorInputs = (WebEditorInputs<?, ?, ?, ?>) anEditorInputs;
				if(aComponent instanceof UIInputText)
					component = new InputText(editorInputs,(UIInputText) aComponent);
				else if(aComponent instanceof UIInputDate)
					component = new InputDate(editorInputs,(UIInputDate) aComponent);
				else if(aComponent instanceof UIInputNumber)
					component = new InputNumber(editorInputs,(UIInputNumber) aComponent);
				else if(aComponent instanceof UIInputMany){
					component = new InputMany(editorInputs, (UIInputMany) aComponent);
				}else if(aComponent instanceof UIInputSelectOne<?,?>){
					component = new InputSelectOne<Object>(editorInputs,(UIInputSelectOne<Object, ISelectItem>) aComponent);
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
						
						inputSelectOne.getItems().add(new SelectItem(null, /*UIManager.getInstance().text("editor.selectone.noselection")*/"---"));	
						if(datas!=null)
							for(Object object : datas)
								inputSelectOne.getItems().add(new SelectItem(object, UIManager.getInstance().getToStringMethod().execute(object)));
					}
				}
				return component;
			}
		};
	}
	
	protected WebEditorInputs<?, ?, ?, ?> editorInputs;
	protected String label,requiredMessage,validatorId,validationGroupClass,description;
	protected Boolean readOnly,required;
	protected Field field;
	protected Class<?> fieldType;
	protected Converter converter;
	protected Object object; 
	protected UIField annotation;
	protected VALUE_TYPE validatedValue;
	protected UIInputComponent<VALUE_TYPE> __input__;

	public AbstractWebInputComponent(WebEditorInputs<?, ?, ?, ?> editorInputs,UIInputComponent<VALUE_TYPE> input) {
		__input__ = input;
		label = input.getLabel();
		description = input.getDescription();
		requiredMessage = input.getRequiredMessage();
		readOnly = input.getReadOnly();
		required = input.getRequired();
		field = input.getField();
		fieldType = input.getFieldType();
		object = input.getObject();
		value = input.getValue();
		annotation = input.getAnnotation();
		this.editorInputs = editorInputs;
		this.validationGroupClass = input.getValidationGroupClass();
		readOnlyValueCascadeStyleSheet.addClass("cyk-ui-editor-dynamic-input-readonly");
		width = input.getWidth();
		height = input.getHeight();
	}
	
	@SuppressWarnings("unchecked")
	public void validate(FacesContext facesContext,UIComponent uiComponent,Object value) throws ValidatorException{
		//Dynamically find validation logic
		//System.out.println("AbstractWebInputComponent.validate() : "+field.getName()+" - " +value+" - ");
		try {
			getEditorInputs().getEditor().getWindow().getValidationPolicy().validateField(field,value);
		} catch (Exception e) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
		}
		//if success then
		validatedValue = (VALUE_TYPE) value;
		
	}

	@Override
	public void updateValue() throws Exception {
		FieldUtils.writeField(field, object, value, true);
		updateReadOnlyValue();
	}
	
	@Override
	public void updateReadOnlyValue() {
		__input__.setValue(value);
		__input__.updateReadOnlyValue(); 
		//AbstractInputComponent.COMPUTE_READ_ONLY_VALUE_METHOD.execute(new Object[]{field,value});
	}
	
	@Override
	public String getReadOnlyValue() {
		return __input__.getReadOnlyValue();
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
