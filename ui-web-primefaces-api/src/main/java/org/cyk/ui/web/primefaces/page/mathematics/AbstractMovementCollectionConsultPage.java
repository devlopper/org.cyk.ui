package org.cyk.ui.web.primefaces.page.mathematics;

import java.io.Serializable;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.AbstractCollectionItemBusiness;
import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.business.impl.BusinessInterfaceLocator;
import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.web.primefaces.page.AbstractCollectionConsultPage;

@Getter @Setter
public abstract class AbstractMovementCollectionConsultPage<COLLECTION extends AbstractIdentifiable,ITEM extends AbstractIdentifiable,ITEM_DETAILS extends AbstractOutputDetails<?>> extends AbstractCollectionConsultPage<COLLECTION,ITEM,ITEM_DETAILS> implements Serializable {
	private static final long serialVersionUID = 3274187086682750183L;

	/**/
	
	@Getter @Setter
	public static abstract class Extends<COLLECTION extends AbstractCollection<ITEM>,ITEM extends AbstractCollectionItem<COLLECTION>,ITEM_DETAILS extends AbstractOutputDetails<ITEM>> extends AbstractMovementCollectionConsultPage<COLLECTION,ITEM,ITEM_DETAILS> implements Serializable {
		private static final long serialVersionUID = 3274187086682750183L;
		
		@Override @SuppressWarnings("unchecked")
		protected Collection<ITEM> findByCollection(COLLECTION collection) {
			return ((AbstractCollectionItemBusiness<ITEM, COLLECTION>)inject(BusinessInterfaceLocator.class).injectTyped(getItemClass())).findByCollection(identifiable);
		}
		
	}
	
}
