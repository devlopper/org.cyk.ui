package org.cyk.ui.api.data.collector.form;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.mathematics.NumberBusiness;
import org.cyk.system.root.business.api.time.TimeBusiness;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.command.CommandListener;
import org.cyk.utility.common.CommonUtils;
import org.cyk.utility.common.annotation.user.interfaces.Binding;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.cdi.AbstractBean;

//TODO can be renamed with Edit in the name : AbstractEditFormModel
public abstract class AbstractFormModel<ENTITY extends AbstractIdentifiable> extends AbstractBean implements Serializable,FormModelListener<ENTITY> {

	private static final long serialVersionUID = 9013238823027923151L;
	
	@Getter @Setter protected Crud crud;
	@Getter protected ENTITY identifiable;
	@Getter protected Collection<Field> inputFields , includeFields , identifiableFields;
	
	@Getter @Setter protected CommandListener submitCommandListener;
	
	@Getter protected Collection<FormModelListener<ENTITY>> formModelListeners = new ArrayList<>();
	
	protected NumberBusiness numberBusiness = UIManager.getInstance().getNumberBusiness();
	protected TimeBusiness timeBusiness = UIManager.getInstance().getTimeBusiness();
	protected RootBusinessLayer rootBusinessLayer = RootBusinessLayer.getInstance();
	
	public void setIdentifiable(ENTITY identifiable) {
		this.identifiable = identifiable;
		inputFields = null;
		identifiableFields = null;
		if(this.identifiable==null){
			
		}else{
			inputFields = CommonUtils.getInstance().getAllFields(this.getClass(), Input.class);
			includeFields = CommonUtils.getInstance().getAllFields(this.getClass(), IncludeInputs.class);
			identifiableFields = CommonUtils.getInstance().getAllFields(this.identifiable.getClass());
		}
	}

	/**
	 * Read values from identifiable and write it to form model
	 */
	public void read() {
		if(inputFields!=null)
			for (Field inputField : inputFields)
				for (Field identifiableField : identifiableFields)
					if (bound(inputField, identifiableField) && commonUtils.canWriteSourceToDestination(identifiable, identifiableField, this, inputField))
						read(inputField, identifiableField);
		
		//System.out.println("AbstractFormModel.read() : "+includeFields);
		
		if(includeFields!=null)
			for (Field includeField : includeFields)
				if(AbstractFormModel.class.isAssignableFrom(includeField.getType()))
					((AbstractFormModel<?>)CommonUtils.getInstance().readField(this, includeField, Boolean.FALSE)).read();
		
					
		for(FormModelListener<ENTITY> listener : formModelListeners)
			listener.read(this);
	}
	
	protected void read(Field destination,Field source) {
		try {
			FieldUtils.writeField(destination, this, FieldUtils.readField(source, identifiable, Boolean.TRUE), Boolean.TRUE);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}		
	}

	/**
	 * Read values from form model and write it to identifiable
	 */
	public void write() {
		if(inputFields!=null)
			for (Field inputField : inputFields)
				for (Field identifiableField : identifiableFields)
					if(bound(inputField, identifiableField) && commonUtils.canWriteSourceToDestination(this, inputField, identifiable, identifiableField))
						write(inputField, identifiableField);
			
		if(includeFields!=null)
			for(Field includeField : includeFields)
				if(AbstractFormModel.class.isAssignableFrom(includeField.getType()))
					((AbstractFormModel<?>)CommonUtils.getInstance().readField(this, includeField, Boolean.FALSE)).write();
		
		for(FormModelListener<ENTITY> listener : formModelListeners)
			listener.write(this);
	}
	
	protected void write(Field source,Field destination) {
		try {
			FieldUtils.writeField(destination, identifiable, FieldUtils.readField(source, this, Boolean.TRUE), Boolean.TRUE);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean bound(Field f1, Field f2) {
		if(f1.getName().equals(f2.getName()))
			return Boolean.TRUE;
		Binding binding = f1.getAnnotation(Binding.class);
		return binding != null && f2.getName().equals(binding.field());
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
