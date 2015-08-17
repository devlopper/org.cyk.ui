package org.cyk.ui.web.primefaces.page;

import java.util.Set;

import org.cyk.system.root.model.AbstractIdentifiable;

public interface BusinessEntityFormOnePageListener<ENTITY_TYPE extends AbstractIdentifiable> {

	Class<ENTITY_TYPE> getEntityTypeClass();
	
	void initialised(AbstractBusinessEntityFormOnePage<? extends AbstractIdentifiable> page);
	
	void afterInitialised(AbstractBusinessEntityFormOnePage<? extends AbstractIdentifiable> page);
	
	void onSucceed(AbstractBusinessEntityFormOnePage<? extends AbstractIdentifiable> page);
	
	CreateMode getCreateMode();
	
	Set<String> getRequiredFields();
	
	Set<String> getOnCreateFields();
	
	/**/
	
	public static enum CreateMode{
		FAST,
		COMPLETE,
		;
		
	}
}
