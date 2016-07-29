package org.cyk.ui.web.primefaces.page.party;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.party.Party;
import org.cyk.ui.api.model.geography.ContactDetails;
import org.cyk.ui.web.primefaces.data.collector.form.FormOneData;
import org.cyk.ui.web.primefaces.page.ContactDetailsAdapter;
import org.cyk.ui.web.primefaces.page.crud.AbstractConsultPage;

@Getter @Setter
public abstract class AbstractPartyConsultPage<PARTY extends AbstractIdentifiable> extends AbstractConsultPage<PARTY> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	protected FormOneData<ContactDetails> contactDetails;
	
	@Override
	protected void consultInitialisation() {
		super.consultInitialisation();
		contactDetails = createDetailsForm(ContactDetails.class, getParty().getContactCollection(), new ContactDetailsAdapter.Default());	
	} 
	
	protected abstract Party getParty();
	
}
