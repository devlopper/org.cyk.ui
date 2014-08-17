package org.cyk.ui.web.primefaces.dynamic;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.AbstractIdentifiable;

@Getter
@Setter
public abstract class AbstractEditPrimefacesPage<ENTITY extends AbstractIdentifiable> extends AbstractBusinessEntityPrimefacesPage<ENTITY> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	protected Crud crud;
	
	@Override
	protected void initialisation() { 
		super.initialisation();
		crud = crudFromRequestParameter();
	}
	
	public abstract void edit();
}
