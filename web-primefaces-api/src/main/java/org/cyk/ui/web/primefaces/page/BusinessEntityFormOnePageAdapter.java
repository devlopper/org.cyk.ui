package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.cdi.AbstractBean;

import lombok.Getter;

public class BusinessEntityFormOnePageAdapter<ENTITY_TYPE extends AbstractIdentifiable> extends AbstractBean implements BusinessEntityFormOnePageListener<ENTITY_TYPE>,Serializable {

	private static final long serialVersionUID = -7944074776241690783L;

	protected Class<ENTITY_TYPE> entityTypeClass;
	@Getter protected CreateMode createMode = CreateMode.FAST;
	@Getter protected Set<String> requiredFields = new LinkedHashSet<>(),onCreateFields = new LinkedHashSet<>();
	
	public BusinessEntityFormOnePageAdapter(Class<ENTITY_TYPE> entityTypeClass,CreateMode createMode) {
		super();
		this.entityTypeClass = entityTypeClass;
		this.createMode = createMode;
		if(createMode==null)
			createMode = CreateMode.FAST;
	}
	
	public BusinessEntityFormOnePageAdapter(Class<ENTITY_TYPE> entityTypeClass) {
		this(entityTypeClass,CreateMode.FAST);
	}
	
	@Override
	public Class<ENTITY_TYPE> getEntityTypeClass() {
		return null;
	}

	@Override
	public void initialised(AbstractBusinessEntityFormOnePage<? extends AbstractIdentifiable> page) {}

	@Override
	public void afterInitialised(AbstractBusinessEntityFormOnePage<? extends AbstractIdentifiable> page) {}

	@Override
	public void onSucceed(AbstractBusinessEntityFormOnePage<? extends AbstractIdentifiable> page) {
		
	}

	

	
	
}
