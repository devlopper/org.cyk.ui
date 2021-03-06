package org.cyk.ui.web.api;

import javax.faces.model.SelectItem;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.data.collector.control.InputChoice;
import org.cyk.ui.api.data.collector.form.FormOneData;
import org.cyk.ui.api.model.AbstractItemCollection;
import org.cyk.ui.api.model.AbstractItemCollectionItem;

public class ItemCollectionWebAdapter<TYPE extends AbstractItemCollectionItem<IDENTIFIABLE>,IDENTIFIABLE extends AbstractIdentifiable,COLLECTION extends AbstractIdentifiable> extends AbstractItemCollection.Listener.Adapter.Default<TYPE,IDENTIFIABLE,COLLECTION,SelectItem> {

	private static final long serialVersionUID = -2744677394853272140L;
	
	public ItemCollectionWebAdapter(COLLECTION collection, Crud crud,FormOneData<AbstractIdentifiable, ?, ?, ?, ?, ?> form
			,InputChoice<AbstractIdentifiable, ?, ?, ?, ?, ?> inputChoice,Class<IDENTIFIABLE> identifiableClass) {
		super(collection, crud,form,inputChoice,identifiableClass);
	}
	
	public ItemCollectionWebAdapter(COLLECTION collection, Crud crud,FormOneData<AbstractIdentifiable, ?, ?, ?, ?, ?> form,Class<IDENTIFIABLE> identifiableClass) {
		super(collection, crud,form,identifiableClass);
	}

	public ItemCollectionWebAdapter(COLLECTION collection, Crud crud) {
		this(collection, crud,null,null,null);
	}
	

}
