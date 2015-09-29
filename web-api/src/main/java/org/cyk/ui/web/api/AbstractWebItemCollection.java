package org.cyk.ui.web.api;

import java.io.Serializable;

import org.cyk.ui.api.model.AbstractItemCollection;
import org.cyk.ui.api.model.AbstractItemCollectionItem;

public abstract class AbstractWebItemCollection<TYPE extends AbstractItemCollectionItem> extends AbstractItemCollection<TYPE> implements Serializable {

	private static final long serialVersionUID = 3478876936484027644L;
	
	protected String identifier;
	
	public AbstractWebItemCollection(String identifier,Class<TYPE> itemClass) {
		super(itemClass);
		this.identifier = identifier;
	}

	

}
