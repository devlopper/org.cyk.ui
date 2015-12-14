package org.cyk.ui.web.api;

import javax.faces.model.SelectItem;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.model.AbstractItemCollectionItem;
import org.cyk.ui.api.model.ItemCollectionListener;

public class ItemCollectionWebAdapter<TYPE extends AbstractItemCollectionItem<IDENTIFIABLE>,IDENTIFIABLE extends AbstractIdentifiable> extends ItemCollectionListener.Adapter<TYPE,IDENTIFIABLE,SelectItem> {

	private static final long serialVersionUID = -2744677394853272140L;

}
