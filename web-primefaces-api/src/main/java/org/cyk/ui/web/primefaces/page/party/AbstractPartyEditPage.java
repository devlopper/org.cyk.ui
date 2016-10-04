package org.cyk.ui.web.primefaces.page.party;

import java.io.Serializable;

import org.cyk.system.root.business.api.geography.ContactCollectionBusiness;
import org.cyk.system.root.business.impl.geography.ContactCollectionDetails;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.party.Party;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractPartyEditPage<PARTY extends AbstractIdentifiable> extends AbstractCrudOnePage<PARTY> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	protected abstract Party getParty();
	
	@Override
	protected void initialisation() {
		super.initialisation();
		if(isDetailsMenuCommandable(ContactCollection.class,ContactCollectionDetails.class)){
	    	inject(ContactCollectionBusiness.class).load(getParty().getContactCollection());	
		}
	}
}
