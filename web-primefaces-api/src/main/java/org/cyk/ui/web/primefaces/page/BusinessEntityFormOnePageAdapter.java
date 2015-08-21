package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import lombok.Getter;

import org.cyk.system.root.model.AbstractIdentifiable;

public class BusinessEntityFormOnePageAdapter<ENTITY_TYPE extends AbstractIdentifiable> extends BusinessEntityPrimefacesPageAdapter<ENTITY_TYPE> implements BusinessEntityFormOnePageListener<ENTITY_TYPE>,Serializable {

	private static final long serialVersionUID = -7944074776241690783L;

	@Getter protected CreateMode createMode = CreateMode.FAST;
	@Getter protected Set<String> requiredFields = new LinkedHashSet<>(),onCreateFields = new LinkedHashSet<>();
	
	public BusinessEntityFormOnePageAdapter(Class<ENTITY_TYPE> entityTypeClass,CreateMode createMode) {
		super(entityTypeClass);
		this.createMode = createMode;
		if(createMode==null)
			createMode = CreateMode.FAST;
	}
	
	public BusinessEntityFormOnePageAdapter(Class<ENTITY_TYPE> entityTypeClass) {
		this(entityTypeClass,CreateMode.FAST);
	}
	
	@Override
	public void onSucceed(AbstractBusinessEntityFormOnePage<? extends AbstractIdentifiable> page) {
		
	}

	

	
	
}
