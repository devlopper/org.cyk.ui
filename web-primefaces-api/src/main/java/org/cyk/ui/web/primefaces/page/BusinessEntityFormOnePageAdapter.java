package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.data.collector.form.FormConfiguration;

public class BusinessEntityFormOnePageAdapter<ENTITY_TYPE extends AbstractIdentifiable> extends BusinessEntityPrimefacesPageAdapter<ENTITY_TYPE> implements BusinessEntityFormOnePageListener<ENTITY_TYPE>,Serializable {

	private static final long serialVersionUID = -7944074776241690783L;

	@Getter protected Map<Crud,Map<FormConfiguration.Type,FormConfiguration>> formConfigurationMap = new HashMap<>();
	
	public BusinessEntityFormOnePageAdapter(Class<ENTITY_TYPE> entityTypeClass) {
		super(entityTypeClass);
	}
	
	@Override
	public void onSucceed(AbstractBusinessEntityFormOnePage<? extends AbstractIdentifiable> page) {
		
	}

	
	
	
	
}
