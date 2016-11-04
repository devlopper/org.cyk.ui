package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudManyPage;

@Getter @Setter
public abstract class AbstractCollectionItemListPage<ITEM extends AbstractIdentifiable,COLLECTION extends AbstractIdentifiable> extends AbstractCrudManyPage<ITEM> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	
}
