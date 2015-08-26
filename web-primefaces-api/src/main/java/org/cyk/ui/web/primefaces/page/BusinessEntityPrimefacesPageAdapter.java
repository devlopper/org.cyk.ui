package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.data.collector.form.FormConfiguration;
import org.cyk.utility.common.cdi.DefaultBeanAdapter;

public class BusinessEntityPrimefacesPageAdapter<ENTITY_TYPE extends AbstractIdentifiable> extends DefaultBeanAdapter implements BusinessEntityPrimefacesPageListener<ENTITY_TYPE>,Serializable {

	private static final long serialVersionUID = -7944074776241690783L;

	@Getter protected Class<ENTITY_TYPE> entityTypeClass;
	
	public BusinessEntityPrimefacesPageAdapter(Class<ENTITY_TYPE> entityTypeClass) {
		super();
		this.entityTypeClass = entityTypeClass;
	}
	
	protected FormConfiguration getFormConfiguration(Crud crud,Map<Crud,Map<FormConfiguration.Type,FormConfiguration>> map){
		if(map.isEmpty())
			return null;
		Map<FormConfiguration.Type,FormConfiguration> formMap = map.get(crud);
		if(formMap==null || formMap.isEmpty())
			return null;
		return formMap.entrySet().iterator().next().getValue();
	}
	
	protected FormConfiguration createFormConfiguration(Crud crud,FormConfiguration.Type type,Map<Crud,Map<FormConfiguration.Type,FormConfiguration>> map){
		FormConfiguration formConfiguration = new FormConfiguration(type);
		Map<FormConfiguration.Type,FormConfiguration> formMap = new HashMap<>();
		formMap.put(type==null?FormConfiguration.Type.DEFAULT:type, formConfiguration);
		map.put(crud, formMap);
		return formConfiguration;
	}
	
	protected FormConfiguration createFormConfiguration(Crud crud,Map<Crud,Map<FormConfiguration.Type,FormConfiguration>> map){
		return createFormConfiguration(crud, null, map);
	}
	
}
