package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudManyPage;

@Getter @Setter
public abstract class AbstractCollectionListPage<COLLECTION extends AbstractIdentifiable,ITEM extends AbstractIdentifiable> extends AbstractCrudManyPage<COLLECTION> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	/**/
	
	public static abstract class Extends<COLLECTION extends AbstractCollection<ITEM>,ITEM extends AbstractCollectionItem<COLLECTION>> extends AbstractCollectionListPage<COLLECTION,ITEM> implements Serializable {

		private static final long serialVersionUID = 3274187086682750183L;
		
		/**/
		
	}

	
}
