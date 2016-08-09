package org.cyk.ui.web.primefaces.page.geography;

import java.io.Serializable;
import java.util.Collection;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.business.impl.geography.ElectronicMailDetails;
import org.cyk.system.root.business.impl.geography.PhoneNumberDetails;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.ElectronicMail;
import org.cyk.system.root.model.geography.PhoneNumber;
import org.cyk.ui.web.primefaces.Table;
import org.cyk.ui.web.primefaces.page.crud.AbstractConsultPage;

@Named @ViewScoped @Getter @Setter
public class ContactCollectionConsultPage extends AbstractConsultPage<ContactCollection> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	private Table<PhoneNumberDetails> phoneNumberTable;
	private Table<ElectronicMailDetails> electronicMailTable;
	
	@Override
	protected void consultInitialisation() {
		super.consultInitialisation();
		phoneNumberTable = (Table<PhoneNumberDetails>) createDetailsTable(PhoneNumberDetails.class, new DetailsConfigurationListener.Table.Adapter<PhoneNumber,PhoneNumberDetails>(PhoneNumber.class, PhoneNumberDetails.class){
			private static final long serialVersionUID = 1L;
			@Override
			public Collection<PhoneNumber> getIdentifiables() {
				return RootBusinessLayer.getInstance().getContactBusiness().findByCollectionByClass(identifiable, PhoneNumber.class);
			}
		});
		electronicMailTable = (Table<ElectronicMailDetails>) createDetailsTable(ElectronicMailDetails.class, new DetailsConfigurationListener.Table.Adapter<ElectronicMail,ElectronicMailDetails>(ElectronicMail.class, ElectronicMailDetails.class){
			private static final long serialVersionUID = 1L;
			@Override
			public Collection<ElectronicMail> getIdentifiables() {
				return RootBusinessLayer.getInstance().getContactBusiness().findByCollectionByClass(identifiable, ElectronicMail.class);
			}
		});
		
	}
	
}
