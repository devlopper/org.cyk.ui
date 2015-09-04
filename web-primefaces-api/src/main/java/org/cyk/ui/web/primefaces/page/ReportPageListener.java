package org.cyk.ui.web.primefaces.page;

import org.cyk.system.root.model.AbstractIdentifiable;

public interface ReportPageListener<ENTITY extends AbstractIdentifiable> extends BusinessEntityPrimefacesPageListener<ENTITY> {

	String getUrl(String outcome,Object[] parameters);
	
}
