package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;

import org.cyk.system.root.model.AbstractIdentifiable;

public class DefaultBusinessEntityFormOnePageAdapter<ENTITY extends AbstractIdentifiable> extends BusinessEntityFormOnePageAdapter<ENTITY> implements Serializable {

	private static final long serialVersionUID = -4255109770974601234L;

	public DefaultBusinessEntityFormOnePageAdapter(Class<ENTITY> entityTypeClass,CreateMode createMode) {
		super(entityTypeClass, createMode);
	}
	
	public DefaultBusinessEntityFormOnePageAdapter(Class<ENTITY> entityTypeClass) {
		this(entityTypeClass,CreateMode.FAST);
	}
	
	@Override
	public Class<ENTITY> getEntityTypeClass() {
		return entityTypeClass;
	}
	
	/*
	@Override
	public void initialised(AbstractBusinessEntityFormOnePage<? extends AbstractIdentifiable> page) {
		super.initialised(page);
		if(StringUtils.isEmpty(page.getPreviousUrl())){
			page.setPreviousUrl(page.);
		}
	}*/
	
}
