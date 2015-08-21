package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;

import org.cyk.system.root.model.AbstractIdentifiable;

public class DefaultBusinessEntityFormManyPageAdapter<ENTITY extends AbstractIdentifiable> extends BusinessEntityFormManyPageAdapter<ENTITY> implements Serializable {

	private static final long serialVersionUID = -4255109770974601234L;

	
	public DefaultBusinessEntityFormManyPageAdapter(Class<ENTITY> entityTypeClass) {
		super(entityTypeClass);
	}
		
}
