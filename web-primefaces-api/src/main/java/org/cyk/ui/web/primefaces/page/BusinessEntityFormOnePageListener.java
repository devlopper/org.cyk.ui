package org.cyk.ui.web.primefaces.page;

import java.util.Map;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.data.collector.form.FormConfiguration;

public interface BusinessEntityFormOnePageListener<ENTITY extends AbstractIdentifiable> extends BusinessEntityPrimefacesPageListener<ENTITY> {

	void onSucceed(AbstractBusinessEntityFormOnePage<? extends AbstractIdentifiable> page);
	
	Map<Crud,Map<FormConfiguration.Type,FormConfiguration>> getFormConfigurationMap();
	
}
