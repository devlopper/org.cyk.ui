package org.cyk.ui.web.primefaces.page.geography;

import java.io.Serializable;
import java.util.Collection;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.business.api.geography.PhoneNumberBusiness;
import org.cyk.system.root.business.impl.geography.ElectronicMailDetails;
import org.cyk.system.root.business.impl.geography.PhoneNumberDetails;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.PhoneNumber;
import org.cyk.ui.api.IdentifierProvider;
import org.cyk.ui.web.primefaces.Commandable;
import org.cyk.ui.web.primefaces.Table;
import org.cyk.ui.web.primefaces.page.crud.AbstractConsultPage;
import org.cyk.utility.common.helper.CollectionHelper;

import lombok.Getter;
import lombok.Setter;

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
				return CollectionHelper.getInstance().cast(PhoneNumber.class, inject(PhoneNumberBusiness.class).findByCollection(identifiable));
			}
			
			@Override
			public Boolean getEnabledInDefaultTab() {
				return Boolean.TRUE;
			}
			
			@Override
			public String getTabId() {
				return IdentifierProvider.Adapter.getTabOf(ContactCollection.class);
			}
			
			@Override
			public String getEditPageOutcome() {
				return "contactCollectionEditPhoneNumbersView";
			}
			
			@Override
			public AbstractIdentifiable getFormIdentifiable() {
				return identifiable;
			}
			
		});
		/*
		phoneNumberTable = (Table<PhoneNumberDetails>) createDetailsTable(PhoneNumberDetails.class, new DetailsConfigurationListener.Table.Adapter<PhoneNumber,PhoneNumberDetails>(PhoneNumber.class, PhoneNumberDetails.class){
			private static final long serialVersionUID = 1L;
			@Override
			public Collection<PhoneNumber> getIdentifiables() {
				return inject(ContactBusiness.class).findByCollectionByClass(identifiable, PhoneNumber.class);
			}
		});
		electronicMailTable = (Table<ElectronicMailDetails>) createDetailsTable(ElectronicMailDetails.class, new DetailsConfigurationListener.Table.Adapter<ElectronicMail,ElectronicMailDetails>(ElectronicMail.class, ElectronicMailDetails.class){
			private static final long serialVersionUID = 1L;
			@Override
			public Collection<ElectronicMail> getIdentifiables() {
				return inject(ContactBusiness.class).findByCollectionByClass(identifiable, ElectronicMail.class);
			}
		});
		*/
		
	}
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		((Commandable)phoneNumberTable.getUpdateCommandable()).setRendered(Boolean.TRUE);
	}
	
}
