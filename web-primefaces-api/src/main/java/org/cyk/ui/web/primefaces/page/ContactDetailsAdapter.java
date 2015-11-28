package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;

import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.ui.api.model.geography.ContactDetails;
import org.cyk.ui.api.model.party.DefaultPersonEditFormModel;
import org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage.DetailsConfigurationListener;

public class ContactDetailsAdapter extends DetailsConfigurationListener.Form.Adapter<ContactCollection,ContactDetails>{

	private static final long serialVersionUID = -9101575271431241099L;

	public ContactDetailsAdapter() {
		super(ContactCollection.class, ContactDetails.class);
	}
	
	/**/
	
	public static class Default extends ContactDetailsAdapter implements Serializable {

		private static final long serialVersionUID = -5670510395431924380L;
		
		@Override
		public ContactDetails createData(ContactCollection identifiable) {
			return new ContactDetails(identifiable);
		}
		@Override
		public String getTitleId() {
			return "contacts";
		}
		@Override
		public String getTabId() {
			return DefaultPersonEditFormModel.TAB_CONTACT_ID;
		}
		
	}
}