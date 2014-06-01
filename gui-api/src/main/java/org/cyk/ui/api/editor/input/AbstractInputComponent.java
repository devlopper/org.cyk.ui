package org.cyk.ui.api.editor.input;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.logging.Level;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.component.AbstractInputOutputComponent;
import org.cyk.ui.api.editor.EditorInputs;
import org.cyk.utility.common.annotation.UIField;
import org.cyk.utility.common.validation.Client;

@Getter @Setter @Log
public abstract class AbstractInputComponent<VALUE_TYPE> extends AbstractInputOutputComponent<VALUE_TYPE> implements Serializable, UIInputComponent<VALUE_TYPE> {

	private static final long serialVersionUID = 438462134701637492L;

	protected EditorInputs<?, ?, ?, ?> editorInputs;
	protected String label,description;
	protected VALUE_TYPE value;
	protected Object object;
	protected Field field;
	protected Class<?> fieldType;
	protected Boolean required,readOnly;
	protected String requiredMessage,validatorId,validationGroupClass=Client.class.getName(),readOnlyValue;
	
	protected UIField annotation;
	
	@SuppressWarnings("unchecked")
	public AbstractInputComponent(Field aField,Class<?> fieldType,UIField annotation,Object anObject) {
		this.field=aField;
		this.annotation = annotation;
		this.object=anObject;
		this.fieldType=fieldType;
		
		String _label = StringUtils.isEmpty(annotation.label())?field.getName():annotation.label();
		switch(annotation.labelValueType()){
		case I18N_ID:this.label=UIManager.getInstance().text(_label); break;
		case I18N_VALUE:this.label=_label; break;
		case VALUE:this.label=_label; break;
		}
		
		this.description = annotation.description();
		required = field.isAnnotationPresent(NotNull.class);
		requiredMessage =label+" : "+UIManager.getInstance().text("editor.field.value.required");
		
		try {
			value = (VALUE_TYPE) FieldUtils.readField(field, object, Boolean.TRUE);
		} catch (Exception e) {
			log.log(Level.SEVERE, e.toString(), e);
		}
		readOnlyValue = value==null?"":value.toString();
	}
	
	@Override
	public void updateValue() throws Exception {
		throw new IllegalArgumentException("Must not call this method on this object");
	}
	
	public static UIInputComponent<?> create(UIInputComponent<?> anInputComponent){
		return UIManager.COMPONENT_CREATE_METHOD.execute(anInputComponent);
	}
	
	

	
}
