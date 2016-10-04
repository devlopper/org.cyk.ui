package org.cyk.ui.web.primefaces.page.party;

import java.io.Serializable;

import org.cyk.system.root.business.api.geography.ContactCollectionBusiness;
import org.cyk.system.root.business.impl.party.person.JobDetails;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.person.Person;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractPersonEditPage<PERSON extends AbstractIdentifiable> extends AbstractPartyEditPage<PERSON> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		if(isDetailsMenuCommandable(JobDetails.class))
			inject(ContactCollectionBusiness.class).load(getPerson().getJobInformations().getContactCollection());	
	}
	
	protected abstract Person getPerson();
	
	@Override
	protected Party getParty() {
		return getPerson();
	}
	
}
