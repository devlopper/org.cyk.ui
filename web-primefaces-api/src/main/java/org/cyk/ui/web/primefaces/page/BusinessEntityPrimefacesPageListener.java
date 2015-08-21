package org.cyk.ui.web.primefaces.page;

import org.cyk.system.root.model.AbstractIdentifiable;

public interface BusinessEntityPrimefacesPageListener<ENTITY extends AbstractIdentifiable> extends PrimefacesPageListener {

	Class<ENTITY> getEntityTypeClass();
	
}
