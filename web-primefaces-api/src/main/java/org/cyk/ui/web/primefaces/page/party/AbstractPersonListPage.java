package org.cyk.ui.web.primefaces.page.party;

import java.io.Serializable;

import org.cyk.system.root.model.AbstractIdentifiable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractPersonListPage<PERSON extends AbstractIdentifiable> extends AbstractPartyListPage<PERSON> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	
}
