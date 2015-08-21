package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import lombok.Getter;

import org.cyk.system.root.model.AbstractIdentifiable;

public class BusinessEntityFormManyPageAdapter<ENTITY_TYPE extends AbstractIdentifiable> extends BusinessEntityPrimefacesPageAdapter<ENTITY_TYPE> implements BusinessEntityFormManyPageListener<ENTITY_TYPE>,Serializable {

	private static final long serialVersionUID = -7944074776241690783L;

	@Getter protected Set<String> onReadFields = new LinkedHashSet<>();
	
	public BusinessEntityFormManyPageAdapter(Class<ENTITY_TYPE> entityTypeClass) {
		super(entityTypeClass);
	}
	
}
