package org.cyk.ui.api.editor;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.CommonUtils;
import org.cyk.utility.common.annotation.UIField;

import lombok.Setter;
import lombok.Getter;

@Getter @Setter
public abstract class AbstractFormData<ENTITY extends AbstractIdentifiable> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9013238823027923151L;
	
	protected ENTITY identifiable;
	protected Collection<Field> formFields,entityFields;
	
	public void setIdentifiable(ENTITY identifiable) {
		this.identifiable = identifiable;
		formFields = CommonUtils.getInstance().getAllFields(this.getClass(), UIField.class);
		entityFields = CommonUtils.getInstance().getAllFields(identifiable.getClass());
	}
	
	public void read(){
		for(Field field : formFields)
			for(Field f : entityFields)
				if(equals(field, f)){
					try {
						FieldUtils.writeField(field, this, FieldUtils.readField(f, identifiable, Boolean.TRUE), Boolean.TRUE);
					} catch (IllegalAccessException e) {
						throw new RuntimeException(e);
					}
				}
	}
	
	public void write() {
		for(Field field : formFields)
			for(Field f : entityFields)
				if(equals(field, f)){
					try {
						FieldUtils.writeField(f, identifiable, FieldUtils.readField(field, this, Boolean.TRUE), Boolean.TRUE);
					} catch (IllegalAccessException e) {
						throw new RuntimeException(e);
					}
				}
	}
	
	protected Boolean equals(Field f1,Field f2){
		return f1.getName().equals(f2.getName()) && f1.getType().equals(f2.getType());
	}
	
}
