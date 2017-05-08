package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.data.collector.control.InputChoice;
import org.cyk.ui.api.data.collector.form.FormOneData;
import org.cyk.ui.api.model.AbstractItemCollectionItem;
import org.cyk.ui.web.api.ItemCollectionWebAdapter;
import org.cyk.ui.web.primefaces.page.AbstractCollectionEditPage;

public class ItemCollectionAdapter<ITEM_COLLECTION_ITEM extends AbstractItemCollectionItem<ENTITY>,ENTITY extends AbstractIdentifiable,COLLECTION extends AbstractIdentifiable> extends ItemCollectionWebAdapter<ITEM_COLLECTION_ITEM,ENTITY,COLLECTION>  implements Serializable {
	
	private static final long serialVersionUID = 7806030819027062650L;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ItemCollectionAdapter(COLLECTION collection, Crud crud,FormOneData form) {
		super(collection, crud, form
				, (InputChoice<AbstractIdentifiable, ?, ?, ?, ?, ?>) form.getInputByFieldName(AbstractCollectionEditPage.AbstractForm.FIELD_ONE_ITEM_MASTER_SELECTED));
		
	}
}