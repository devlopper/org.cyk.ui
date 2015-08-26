package org.cyk.ui.web.primefaces.page;

import java.util.Map;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.data.collector.form.FormConfiguration;

public interface BusinessEntityFormManyPageListener<ENTITY extends AbstractIdentifiable> extends BusinessEntityPrimefacesPageListener<ENTITY> {

	Map<FormConfiguration.Type,FormConfiguration> getReadFormConfigurationMap();
	
	Boolean canRedirect(Crud crud,Object data);
	
	void redirect(Crud crud,Object data);
	
}
