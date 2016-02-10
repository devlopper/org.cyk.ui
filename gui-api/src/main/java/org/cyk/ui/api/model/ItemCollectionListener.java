package org.cyk.ui.api.model;

import java.util.ArrayList;
import java.util.Collection;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.cdi.AbstractBean;

public interface ItemCollectionListener<TYPE extends AbstractItemCollectionItem<IDENTIFIABLE>,IDENTIFIABLE extends AbstractIdentifiable,SELECT_ITEM> {
	
	Collection<IDENTIFIABLE> create();
	
	Collection<IDENTIFIABLE> load();
	
	IDENTIFIABLE instanciate(AbstractItemCollection<TYPE,IDENTIFIABLE,SELECT_ITEM> itemCollection);
	
	void instanciated(AbstractItemCollection<TYPE,IDENTIFIABLE,SELECT_ITEM> itemCollection,TYPE item);
	
	void add(AbstractItemCollection<TYPE,IDENTIFIABLE,SELECT_ITEM> itemCollection,TYPE item);
	
	void delete(AbstractItemCollection<TYPE,IDENTIFIABLE,SELECT_ITEM> itemCollection,TYPE item);

	/**
	 * Take value from item fields to identifiable fields
	 */
	void write(TYPE item);
	
	/**
	 * Take value from identifiable fields to item fields
	 */
	void read(TYPE item);
	
	/**/
	
	public static class Adapter<TYPE extends AbstractItemCollectionItem<IDENTIFIABLE>,IDENTIFIABLE extends AbstractIdentifiable,SELECT_ITEM> extends AbstractBean implements ItemCollectionListener<TYPE,IDENTIFIABLE,SELECT_ITEM>{
		private static final long serialVersionUID = 5920340778121618178L;

		@Override
		public Collection<IDENTIFIABLE> create() {
			return new ArrayList<>();
		}
		
		@Override
		public Collection<IDENTIFIABLE> load() {
			return null;
		}
		
		@Override
		public IDENTIFIABLE instanciate(AbstractItemCollection<TYPE, IDENTIFIABLE, SELECT_ITEM> itemCollection) {
			return null;
		}
		
		@Override public void add(AbstractItemCollection<TYPE,IDENTIFIABLE,SELECT_ITEM> itemCollection,TYPE item) {}

		@Override public void delete(AbstractItemCollection<TYPE,IDENTIFIABLE,SELECT_ITEM> itemCollection, TYPE item) {}

		@Override public void instanciated(AbstractItemCollection<TYPE,IDENTIFIABLE,SELECT_ITEM> itemCollection,TYPE item) {}
	
		@Override public void write(TYPE item) {}
		
		@Override public void read(TYPE item) {}
	}
	
}
