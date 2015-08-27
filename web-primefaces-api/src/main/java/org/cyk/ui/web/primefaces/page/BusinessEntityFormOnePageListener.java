package org.cyk.ui.web.primefaces.page;

import org.cyk.system.root.model.AbstractIdentifiable;

public interface BusinessEntityFormOnePageListener<ENTITY extends AbstractIdentifiable> extends BusinessEntityFormPageListener<ENTITY> {

	void onSucceed(AbstractBusinessEntityFormOnePage<? extends AbstractIdentifiable> page);
	
}
