package org.cyk.ui.web.api;

import java.io.Serializable;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.model.AbstractItemCollection;
import org.cyk.ui.api.model.AbstractItemCollectionItem;

public abstract class AbstractWebItemCollection<TYPE extends AbstractItemCollectionItem<IDENTIFIABLE>,IDENTIFIABLE extends AbstractIdentifiable> extends AbstractItemCollection<TYPE,IDENTIFIABLE> implements Serializable {

	private static final long serialVersionUID = 3478876936484027644L;
	
	protected String identifier;
	
	public AbstractWebItemCollection(String identifier,Class<TYPE> itemClass,Class<IDENTIFIABLE> identifiableClass) {
		super(itemClass,identifiableClass);
		this.identifier = identifier;
	}

	

}
