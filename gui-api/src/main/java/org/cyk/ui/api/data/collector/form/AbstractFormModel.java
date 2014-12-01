package org.cyk.ui.api.data.collector.form;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.command.CommandListener;
import org.cyk.utility.common.CommonUtils;
import org.cyk.utility.common.annotation.user.interfaces.Input;

public abstract class AbstractFormModel<ENTITY extends AbstractIdentifiable> implements Serializable,FormModelListener<ENTITY> {

	private static final long serialVersionUID = 9013238823027923151L;
	
	@Getter protected ENTITY identifiable;
	@Getter protected Collection<Field> formFields, identifiableFields;
	
	@Getter @Setter protected CommandListener submitCommandListener;
	
	@Getter protected Collection<FormModelListener<ENTITY>> formModelListeners = new ArrayList<>();
	
	public void setIdentifiable(ENTITY identifiable) {
		this.identifiable = identifiable;
		formFields = null;
		identifiableFields = null;
		if(this.identifiable==null){
			
		}else{
			formFields = CommonUtils.getInstance().getAllFields(this.getClass(), Input.class);
			identifiableFields = CommonUtils.getInstance().getAllFields(this.identifiable.getClass());
		}
	}

	public void read() {
		if(formFields!=null)
			for (Field field : formFields)
				for (Field f : identifiableFields)
					if (equals(field, f))
						read(field, f);
		for(FormModelListener<ENTITY> listener : formModelListeners)
			listener.read(this);
	}
	
	protected void read(Field field,Field f) {
		try {
			FieldUtils.writeField(field, this, FieldUtils.readField(f, identifiable, Boolean.TRUE), Boolean.TRUE);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}		
	}

	public void write() {
		if(formFields!=null)
			for (Field field : formFields)
				for (Field f : identifiableFields)
					if (equals(field, f))
						write(field, f);
		for(FormModelListener<ENTITY> listener : formModelListeners)
			listener.write(this);
	}
	
	protected void write(Field field,Field f) {
		try {
			FieldUtils.writeField(f, identifiable, FieldUtils.readField(field, this, Boolean.TRUE), Boolean.TRUE);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean equals(Field f1, Field f2) {
		return f1.getName().equals(f2.getName()) && f1.getType().equals(f2.getType());
	}

	@Override
	public void read(AbstractFormModel<ENTITY> form) {}
	
	@Override
	public void write(AbstractFormModel<ENTITY> form) {}

	@SuppressWarnings("unchecked")
	public static AbstractFormModel<AbstractIdentifiable> instance(Class<?> aClass,AbstractIdentifiable identifiable){
		AbstractFormModel<AbstractIdentifiable> data = null;
		try {
			data = (AbstractFormModel<AbstractIdentifiable>) aClass.newInstance();
			data.setIdentifiable(identifiable);
			data.read();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	
}
