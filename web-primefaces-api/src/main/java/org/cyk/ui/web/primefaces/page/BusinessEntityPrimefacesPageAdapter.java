package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;

import org.cyk.system.root.model.AbstractIdentifiable;

import lombok.Getter;

public class BusinessEntityPrimefacesPageAdapter<ENTITY_TYPE extends AbstractIdentifiable> extends PrimefacesPageAdapter implements BusinessEntityPrimefacesPageListener<ENTITY_TYPE>,Serializable {

	private static final long serialVersionUID = -7944074776241690783L;

	@Getter protected Class<ENTITY_TYPE> entityTypeClass;
	
	public BusinessEntityPrimefacesPageAdapter(Class<ENTITY_TYPE> entityTypeClass) {
		super();
		this.entityTypeClass = entityTypeClass;
	}
	
	
	
}
