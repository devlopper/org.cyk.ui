package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.data.collector.form.FormConfiguration;
import org.cyk.ui.web.api.WebNavigationManager;

public class BusinessEntityFormPageAdapter<ENTITY_TYPE extends AbstractIdentifiable> extends BusinessEntityPrimefacesPageAdapter<ENTITY_TYPE> implements BusinessEntityFormPageListener<ENTITY_TYPE>,Serializable {

	private static final long serialVersionUID = -7944074776241690783L;

	@Getter protected Map<Crud,Map<FormConfiguration.Type,FormConfiguration>> formConfigurationMap = new HashMap<>();
	
	public BusinessEntityFormPageAdapter(Class<ENTITY_TYPE> entityTypeClass) {
		super(entityTypeClass);
	}

	@Override
	public Boolean canRedirect(Crud crud, Object data) {
		return null;
	}

	@Override
	public void redirect(Crud crud, Object data) {
		ENTITY_TYPE identifiable = getIdentifiable(data);
		if(identifiable!=null)
			WebNavigationManager.getInstance().redirectToDynamicCrudOne(identifiable,crud);
	}

	@Override
	public ENTITY_TYPE getIdentifiable(Object data) {
		return null;
	}
	
	/**/
	
	protected FormConfiguration getFormConfiguration(Crud crud){
		if(formConfigurationMap.isEmpty())
			return null;
		Map<FormConfiguration.Type,FormConfiguration> formMap = formConfigurationMap.get(crud);
		if(formMap==null || formMap.isEmpty())
			return null;
		return formMap.entrySet().iterator().next().getValue();
	}
	
	protected FormConfiguration createFormConfiguration(Crud crud,FormConfiguration.Type type){
		FormConfiguration formConfiguration = new FormConfiguration(type);
		Map<FormConfiguration.Type,FormConfiguration> formMap = new HashMap<>();
		formMap.put(type==null?FormConfiguration.Type.DEFAULT:type, formConfiguration);
		formConfigurationMap.put(crud, formMap);
		return formConfiguration;
	}
	
	protected FormConfiguration createFormConfiguration(Crud crud){
		return createFormConfiguration(crud, null);
	}
	
}
