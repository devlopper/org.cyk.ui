package org.cyk.ui.api.editor.input;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.logging.Level;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.system.root.business.api.validation.ValidationPolicy;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.component.AbstractInputOutputComponent;
import org.cyk.ui.api.editor.EditorInputs;
import org.cyk.utility.common.AbstractMethod;
import org.cyk.utility.common.annotation.UIField;
import org.cyk.utility.common.validation.Client;

@Getter @Setter @Log
public abstract class AbstractInputComponent<VALUE_TYPE> extends AbstractInputOutputComponent<VALUE_TYPE> implements Serializable, UIInputComponent<VALUE_TYPE> {

	private static final long serialVersionUID = 438462134701637492L;

	public static ComputeReadOnlyValueMethod COMPUTE_READ_ONLY_VALUE_METHOD = new ComputeReadOnlyValueMethod() {
		private static final long serialVersionUID = -2440628467044855958L;
		@Override
		protected String __execute__(Object object) {
			Object[] arrays = (Object[]) object;
			Field field = (Field) arrays[0]; 
			Object value = arrays[1];
			if(value instanceof Date){
				Temporal temporal = field.getAnnotation(Temporal.class);
				if(temporal==null || TemporalType.TIMESTAMP.equals(temporal.value()))
					return UIManager.getInstance().formatDate((Date) value,Boolean.TRUE);
				else if(TemporalType.DATE.equals(temporal.value()))
					return UIManager.getInstance().formatDate((Date) value,Boolean.FALSE);
				else if(TemporalType.TIME.equals(temporal.value()))
					return UIManager.getInstance().formatTime((Date) value);
				return UIManager.getInstance().formatDate((Date) value,Boolean.FALSE);
			}
			return value==null?"":value.toString();
		}
	};
	
	public static ComputeLabelValueMethod COMPUTE_LABEL_VALUE_METHOD = new ComputeLabelValueMethod() {
		private static final long serialVersionUID = -2440628467044855958L;
		@Override
		protected String __execute__(Object object) {
			Object[] params = (Object[]) object;
			String fieldName = (String) params[0];
			UIField annotation = (UIField) params[1];
			return UIManager.getInstance().annotationTextValue(annotation.labelValueType(), annotation.label(), fieldName);
		}
	};
	
	protected EditorInputs<?, ?, ?, ?> editorInputs;
	protected ValidationPolicy validationPolicy;
	protected String label,description;
	protected VALUE_TYPE value,validatedValue;
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
		
		this.label = COMPUTE_LABEL_VALUE_METHOD.execute(new Object[]{field.getName(),annotation});
		this.description = annotation.description();
		requiredMessage =label+" : "+UIManager.getInstance().text("editor.field.value.required");
		try {
			value = (VALUE_TYPE) FieldUtils.readField(field, object, Boolean.TRUE);
		} catch (Exception e) {
			log.log(Level.SEVERE, e.toString(), e);
			return;
		}
		updateReadOnlyValue();
	}
	
	@Override
	public void updateReadOnlyValue() {
		readOnlyValue = COMPUTE_READ_ONLY_VALUE_METHOD.execute(new Object[]{field,value});
	}
	
	@Override
	public void updateValue() throws Exception {
		throw new IllegalArgumentException("Must not call this method on this object");
	}
	
	@Override
	public void validate(VALUE_TYPE aValue) {
		//System.out.println("AbstractWebInputComponent.validate() : "+field.getName()+" - " +aValue);
		getValidationPolicy().validateField(field,aValue);
	}
	
	public static UIInputComponent<?> create(EditorInputs<?, ?, ?, ?> editorInputs,UIInputComponent<?> anInputComponent,ValidationPolicy aValidationPolicy){
		anInputComponent.setValidationPolicy(aValidationPolicy);
		return UIManager.getInstance().getComponentCreateMethod().execute(new Object[]{editorInputs,anInputComponent});
	}
	
	/**/
	
	public static abstract class ComputeReadOnlyValueMethod extends AbstractMethod<String, Object>{
		private static final long serialVersionUID = 6676233958270401002L;
	}
	
	public static abstract class ComputeLabelValueMethod extends AbstractMethod<String, Object>{
		private static final long serialVersionUID = 6676233958270401002L;
	}

	
}
