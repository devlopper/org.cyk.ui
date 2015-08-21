package org.cyk.ui.web.primefaces.page;

import java.util.Set;

import org.cyk.system.root.model.AbstractIdentifiable;

public interface BusinessEntityFormOnePageListener<ENTITY extends AbstractIdentifiable> extends BusinessEntityPrimefacesPageListener<ENTITY> {

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
