package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;

import org.cyk.system.root.model.AbstractIdentifiable;

public class BusinessEntityFormOnePageAdapter<ENTITY_TYPE extends AbstractIdentifiable> extends BusinessEntityFormPageAdapter<ENTITY_TYPE> implements BusinessEntityFormOnePageListener<ENTITY_TYPE>,Serializable {

	private static final long serialVersionUID = -7944074776241690783L;

	public BusinessEntityFormOnePageAdapter(Class<ENTITY_TYPE> entityTypeClass) {
		super(entityTypeClass);
	}
	
	@Override
	public void onSucceed(AbstractBusinessEntityFormOnePage<? extends AbstractIdentifiable> page) {
		
	}

	
	
	
	
}
