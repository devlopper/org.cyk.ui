package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;

import org.cyk.system.root.business.impl.geography.ContactCollectionBusinessImpl;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage.DetailsConfigurationListener;
//TODO what is the use ?
public class ContactDetailsAdapter extends DetailsConfigurationListener.Form.Adapter<ContactCollection,ContactCollectionBusinessImpl.Details>{

	private static final long serialVersionUID = -9101575271431241099L;

	public ContactDetailsAdapter() {
		super(ContactCollection.class, ContactCollectionBusinessImpl.Details.class);
	}
	
	/**/
	
	public static class Default extends ContactDetailsAdapter implements Serializable {

		private static final long serialVersionUID = -5670510395431924380L;
		
	}
}