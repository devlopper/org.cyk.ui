package org.cyk.ui.web.primefaces.page.party;

import java.io.Serializable;

import org.cyk.system.root.business.impl.geography.ContactCollectionBusinessImpl;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.party.Party;
import org.cyk.ui.api.IdentifierProvider;
import org.cyk.ui.web.primefaces.data.collector.form.FormOneData;
import org.cyk.ui.web.primefaces.page.crud.AbstractConsultPage;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractPartyConsultPage<PARTY extends AbstractIdentifiable> extends AbstractConsultPage<PARTY> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	protected FormOneData<ContactCollectionBusinessImpl.Details> contactCollectionDetails;
	
	@Override
	protected void consultInitialisation() {
		super.consultInitialisation();	
		createContactCollectionDetails();
	}
	
	@SuppressWarnings("unchecked")
	protected void createContactCollectionDetails(){
		contactCollectionDetails = createDetailsForm(ContactCollectionBusinessImpl.Details.class, getParty().getContactCollection(), getContactCollectionDetailsAdapter());
	}
	
	@Override
	protected void processOnIdentifiableFound(PARTY identifiable) {
		super.processOnIdentifiableFound(identifiable);
		//if(StringUtils.isBlank(selectedTabId) || isDetailsMenuCommandable(identifiable.getClass()))
		//	inject(ContactCollectionBusiness.class).load(getParty().getContactCollection());
	}
	
	@SuppressWarnings("rawtypes")
	protected DetailsConfigurationListener.Form.Adapter getContactCollectionDetailsAdapter(){
		DetailsConfigurationListener.Form.Adapter adapter = getDetailsConfiguration(ContactCollectionBusinessImpl.Details.class)
				.getFormConfigurationAdapter(ContactCollection.class, ContactCollectionBusinessImpl.Details.class);
		adapter.setFormIdentifiable(identifiable);
		adapter.setFormConfigurationIdentifier(IdentifierProvider.Adapter.getTabOf(ContactCollection.class));
		return adapter;
	}
		
	protected abstract Party getParty();

	/**/
	
	
}
