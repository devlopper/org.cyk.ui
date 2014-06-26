package org.cyk.ui.web.api.editor.input;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;

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
import org.cyk.ui.api.editor.input.AbstractInputComponent;
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
	protected String label,requiredMessage,validatorId,validationGroupClass,readOnlyValue,description;
	protected Boolean readOnly,required;
	protected Field field;
	protected Class<?> fieldType;
	protected Converter converter;
	protected Object object; 
	protected UIField annotation;
	protected VALUE_TYPE validatedValue;

	public AbstractWebInputComponent(WebEditorInputs<?, ?, ?, ?> editorInputs,UIInputComponent<VALUE_TYPE> input) {
		label = input.getLabel();
		description = input.getDescription();
		requiredMessage = input.getRequiredMessage();
		readOnly = input.getReadOnly();
		//System.out.println(editorInputs.getEditor().getCrud());
		/*
		if(editorInputs==null || editorInputs.getEditor().getCrud()==null || Crud.CREATE.equals(editorInputs.getEditor().getCrud()) || Crud.UPDATE.equals(editorInputs.getEditor().getCrud()))
			required = input.getRequired();
		else
			required = Boolean.FALSE;
			*/
		required = input.getRequired();
		field = input.getField();
		fieldType = input.getFieldType();
		object = input.getObject();
		value = input.getValue();
		annotation = input.getAnnotation();
		this.editorInputs = editorInputs;
		readOnlyValue = input.getReadOnlyValue();
		this.validationGroupClass = input.getValidationGroupClass();
		readOnlyValueCascadeStyleSheet.addClass("cyk-ui-editor-dynamic-input-readonly");
	}
	
	@SuppressWarnings("unchecked")
	public void validate(FacesContext facesContext,UIComponent uiComponent,Object value) throws ValidatorException{
		//Dynamically find validation logic
		//System.out.println("AbstractWebInputComponent.validate() : "+field.getName()+" - " +value+" - ");
		//if success then
		validatedValue = (VALUE_TYPE) value;
		
	}

	@Override
	public void updateValue() throws Exception {
		FieldUtils.writeField(field, object, value, true);
		readOnlyValue=AbstractInputComponent.COMPUTE_READ_ONLY_VALUE_METHOD.execute(new Object[]{field,value});
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
