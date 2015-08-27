package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;

import org.cyk.system.root.model.AbstractIdentifiable;

public class BusinessEntityFormManyPageAdapter<ENTITY_TYPE extends AbstractIdentifiable> extends BusinessEntityFormPageAdapter<ENTITY_TYPE> implements BusinessEntityFormManyPageListener<ENTITY_TYPE>,Serializable {

	private static final long serialVersionUID = -7944074776241690783L;

	public BusinessEntityFormManyPageAdapter(Class<ENTITY_TYPE> entityTypeClass) {
		super(entityTypeClass);
	}
	
}
