package org.cyk.ui.api.model;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.cdi.AbstractBean;

public interface ItemCollectionListener<TYPE extends AbstractItemCollectionItem<IDENTIFIABLE>,IDENTIFIABLE extends AbstractIdentifiable,SELECT_ITEM> {
	
	void instanciated(AbstractItemCollection<TYPE,IDENTIFIABLE,SELECT_ITEM> itemCollection,TYPE item);
	
	void add(AbstractItemCollection<TYPE,IDENTIFIABLE,SELECT_ITEM> itemCollection,TYPE item);
	
	void delete(AbstractItemCollection<TYPE,IDENTIFIABLE,SELECT_ITEM> itemCollection,TYPE item);

	void write(TYPE item);
	
	/**/
	
	public static class Adapter<TYPE extends AbstractItemCollectionItem<IDENTIFIABLE>,IDENTIFIABLE extends AbstractIdentifiable,SELECT_ITEM> extends AbstractBean implements ItemCollectionListener<TYPE,IDENTIFIABLE,SELECT_ITEM>{

		private static final long serialVersionUID = 5920340778121618178L;

		@Override
		public void add(AbstractItemCollection<TYPE,IDENTIFIABLE,SELECT_ITEM> itemCollection,TYPE item) {}

		@Override
		public void delete(AbstractItemCollection<TYPE,IDENTIFIABLE,SELECT_ITEM> itemCollection, TYPE item) {}

		@Override
		public void instanciated(AbstractItemCollection<TYPE,IDENTIFIABLE,SELECT_ITEM> itemCollection,TYPE item) {}
	
		@Override
		public void write(TYPE item) {}
	}
	
}
