package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.data.collector.form.FormConfiguration;

public class BusinessEntityFormManyPageAdapter<ENTITY_TYPE extends AbstractIdentifiable> extends BusinessEntityPrimefacesPageAdapter<ENTITY_TYPE> implements BusinessEntityFormManyPageListener<ENTITY_TYPE>,Serializable {

	private static final long serialVersionUID = -7944074776241690783L;

	@Getter protected Map<FormConfiguration.Type,FormConfiguration> readFormConfigurationMap = new HashMap<>();
	
	public BusinessEntityFormManyPageAdapter(Class<ENTITY_TYPE> entityTypeClass) {
		super(entityTypeClass);
	}
	
}
