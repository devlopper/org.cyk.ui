package org.cyk.ui.web.primefaces.page.mathematics;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.web.primefaces.page.AbstractCollectionListPage;

@Getter @Setter
public abstract class AbstractMovementCollectionListPage<COLLECTION extends AbstractIdentifiable,ITEM extends AbstractIdentifiable> extends AbstractCollectionListPage<COLLECTION,ITEM> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	/**/
	
	public static abstract class Extends<COLLECTION extends AbstractCollection<ITEM>,ITEM extends AbstractCollectionItem<COLLECTION>> extends AbstractMovementCollectionListPage<COLLECTION,ITEM> implements Serializable {

		private static final long serialVersionUID = 3274187086682750183L;
		
		/**/
		
	}
	
}
