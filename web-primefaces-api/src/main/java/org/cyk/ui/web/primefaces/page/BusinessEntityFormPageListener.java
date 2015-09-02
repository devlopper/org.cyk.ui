package org.cyk.ui.web.primefaces.page;

import java.util.Map;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.data.collector.form.FormConfiguration;

public interface BusinessEntityFormPageListener<ENTITY extends AbstractIdentifiable> extends BusinessEntityPrimefacesPageListener<ENTITY> {

	Map<Crud,Map<FormConfiguration.Type,FormConfiguration>> getFormConfigurationMap();
	
	Boolean canRedirect(Crud crud,Object data);
	
	void redirect(Crud crud,Object data);
	
	Boolean canRedirectToConsultView(Object data);
	void redirectToConsultView(Object data);
	
	ENTITY getIdentifiable(Object data);
	
}
