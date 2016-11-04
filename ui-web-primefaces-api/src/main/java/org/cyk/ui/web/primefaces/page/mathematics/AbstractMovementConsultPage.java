package org.cyk.ui.web.primefaces.page.mathematics;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.web.primefaces.page.AbstractCollectionItemConsultPage;

@Getter @Setter
public abstract class AbstractMovementConsultPage<ITEM extends AbstractIdentifiable,COLLECTION extends AbstractIdentifiable> extends AbstractCollectionItemConsultPage<ITEM,COLLECTION> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	
}
