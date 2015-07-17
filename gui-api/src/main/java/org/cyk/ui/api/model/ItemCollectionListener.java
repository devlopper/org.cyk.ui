package org.cyk.ui.api.model;

public interface ItemCollectionListener<TYPE> {
	
	void add(AbstractItemCollection<TYPE> itemCollection);
	
	void delete(AbstractItemCollection<TYPE> itemCollection,TYPE item);

}
