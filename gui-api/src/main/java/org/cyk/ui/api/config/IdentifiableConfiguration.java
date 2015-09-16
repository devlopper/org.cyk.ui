package org.cyk.ui.api.config;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.api.data.collector.form.FormMap;

@Getter @Setter @NoArgsConstructor
public class IdentifiableConfiguration extends ClassConfiguration<AbstractIdentifiable> implements Serializable {

	private static final long serialVersionUID = 524804677149804204L;

	private FormMap formMap;
	
	private Boolean oneEdit = Boolean.TRUE,globalFiltering=Boolean.TRUE,usableByChild=Boolean.TRUE;

	public IdentifiableConfiguration(Class<? extends AbstractIdentifiable> identifiableClass,
			Class<? extends AbstractFormModel<? extends AbstractIdentifiable>> editOneFormModelClass,
			Class<?> readOneFormModelClass) {
		super(identifiableClass);
		formMap = new FormMap(identifiableClass,editOneFormModelClass,readOneFormModelClass);
	}
	/*
	public Class<? extends AbstractIdentifiable> getIdentifiableClass(){
		return clazz;
	}*/
	
}
