package org.cyk.ui.web.primefaces.page.party;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.geography.ContactCollectionBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.party.Party;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;

@Getter @Setter
public abstract class AbstractPartyEditPage<PARTY extends AbstractIdentifiable> extends AbstractCrudOnePage<PARTY> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	protected abstract Party getParty();
	
	@Override
	protected void initialisation() {
		super.initialisation();
		//createFormOneData(new ContactCollectionFormModel(LocationType.HOME));
	}
	
	@Override
	protected void processOnIdentifiableFound(PARTY identifiable) {
		super.processOnIdentifiableFound(identifiable);
		//if(isDetailsMenuCommandable(ContactCollection.class))
		//	inject(ContactCollectionBusiness.class).load(getParty().getContactCollection());
	}
	
	/**/
	

	
}
