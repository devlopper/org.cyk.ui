package org.cyk.ui.api.model;

import org.cyk.utility.common.cdi.AbstractBean;

public interface ItemCollectionListener<TYPE extends AbstractItemCollectionItem> {
	
	void instanciated(AbstractItemCollection<TYPE> itemCollection,TYPE item);
	
	void add(AbstractItemCollection<TYPE> itemCollection,TYPE item);
	
	void delete(AbstractItemCollection<TYPE> itemCollection,TYPE item);

	/**/
	
	public static class ItemCollectionAdapter<TYPE extends AbstractItemCollectionItem> extends AbstractBean implements ItemCollectionListener<TYPE>{

		private static final long serialVersionUID = 5920340778121618178L;

		@Override
		public void add(AbstractItemCollection<TYPE> itemCollection,TYPE item) {}

		@Override
		public void delete(AbstractItemCollection<TYPE> itemCollection, TYPE item) {}

		@Override
		public void instanciated(AbstractItemCollection<TYPE> itemCollection,TYPE item) {}
	
	}
	
}
