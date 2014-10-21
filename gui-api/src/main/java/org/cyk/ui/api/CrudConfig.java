package org.cyk.ui.api;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;

@Getter @Setter
public class CrudConfig implements Serializable {

	private static final long serialVersionUID = 524804677149804204L;

	private Class<? extends AbstractIdentifiable> identifiableClass;
	private Class<?> formClass;
	private Boolean oneEdit = Boolean.TRUE;
	
	public CrudConfig(Class<? extends AbstractIdentifiable> identifiableClass, Class<?> formClass) {
		super();
		this.identifiableClass = identifiableClass;
		this.formClass = formClass;
	}
	
	
	
}
