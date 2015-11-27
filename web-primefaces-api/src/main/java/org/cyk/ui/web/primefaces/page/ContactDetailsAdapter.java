package org.cyk.ui.web.primefaces.page;

import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.ui.api.model.geography.ContactDetails;
import org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage.DetailsConfigurationListener;

public class ContactDetailsAdapter extends DetailsConfigurationListener.Form.Adapter<ContactCollection,ContactDetails>{

	private static final long serialVersionUID = -9101575271431241099L;

	public ContactDetailsAdapter() {
		super(ContactCollection.class, ContactDetails.class);
	}
	
}