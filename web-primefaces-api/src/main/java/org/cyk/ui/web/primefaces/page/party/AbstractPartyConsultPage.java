package org.cyk.ui.web.primefaces.page.party;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.geography.ContactCollectionBusiness;
import org.cyk.system.root.business.impl.geography.ContactCollectionDetails;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.party.Party;
import org.cyk.ui.web.primefaces.data.collector.form.FormOneData;
import org.cyk.ui.web.primefaces.page.crud.AbstractConsultPage;

@Getter @Setter
public abstract class AbstractPartyConsultPage<PARTY extends AbstractIdentifiable> extends AbstractConsultPage<PARTY> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	protected FormOneData<ContactCollectionDetails> contactCollectionDetails;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void consultInitialisation() {
		super.consultInitialisation();
			
		contactCollectionDetails = createDetailsForm(ContactCollectionDetails.class, getParty().getContactCollection()
				, getDetailsConfiguration(ContactCollectionDetails.class).getFormConfigurationAdapter(ContactCollection.class, ContactCollectionDetails.class));
			
	}
	
	@Override
	protected <T extends AbstractIdentifiable> T identifiableFromRequestParameter(Class<T> aClass, String identifierId) {
		T t = super.identifiableFromRequestParameter(aClass, identifierId);
		if(isDetailsMenuCommandable(ContactCollection.class))
			if(t instanceof Party)
				inject(ContactCollectionBusiness.class).load(((Party)t).getContactCollection());
		return t;
	}
	
	protected abstract Party getParty();
	
}
