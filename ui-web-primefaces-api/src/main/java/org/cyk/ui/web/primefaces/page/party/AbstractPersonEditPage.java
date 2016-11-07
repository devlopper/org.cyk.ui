package org.cyk.ui.web.primefaces.page.party;

import java.io.Serializable;

import org.cyk.system.root.business.api.party.person.JobInformationsBusiness;
import org.cyk.system.root.business.api.party.person.MedicalInformationsBusiness;
import org.cyk.system.root.business.api.party.person.PersonExtendedInformationsBusiness;
import org.cyk.system.root.business.impl.party.person.JobDetails;
import org.cyk.system.root.business.impl.party.person.MedicalDetails;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.geography.LocationType;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.person.JobFunction;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonTitle;
import org.cyk.ui.api.model.geography.ContactCollectionFormModel;
import org.cyk.ui.web.primefaces.data.collector.form.FormOneData;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputChoiceAutoComplete;
import org.cyk.utility.common.annotation.user.interfaces.InputOneAutoComplete;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneRadio;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.annotation.user.interfaces.OutputSeperator;
import org.cyk.utility.common.annotation.user.interfaces.Text;
import org.cyk.utility.common.cdi.AbstractBean;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractPersonEditPage<PERSON extends AbstractIdentifiable> extends AbstractPartyEditPage<PERSON> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	protected FormOneData<ContactsForm> contactsForm;
	/*
	@Override
	protected void initialisation() {
		super.initialisation();
		contactsForm = (FormOneData<ContactsForm>) createFormOneData(new ContactsForm(), crud);
		contactsForm.setTabTitle("Contacts");
	}*/
	
	@Override
	protected void processOnIdentifiableFound(PERSON identifiable) {
		super.processOnIdentifiableFound(identifiable);
		if(isDetailsMenuCommandable(JobDetails.class)){
			getPerson().setJobInformations(inject(JobInformationsBusiness.class).findByParty(getPerson()));
			inject(JobInformationsBusiness.class).load(getPerson().getJobInformations());
		}else if(isDetailsMenuCommandable(MedicalDetails.class)){
			getPerson().setMedicalInformations(inject(MedicalInformationsBusiness.class).findByParty(getPerson()));
			inject(MedicalInformationsBusiness.class).load(getPerson().getMedicalInformations());		
		}else {
			getPerson().setExtendedInformations(inject(PersonExtendedInformationsBusiness.class).findByParty(getPerson()));
			inject(PersonExtendedInformationsBusiness.class).load(getPerson().getExtendedInformations());		
		}
	}
	
	protected abstract Person getPerson();
	
	@Override
	protected Party getParty() {
		return getPerson();
	}
	
	/**/
	
	@Getter @Setter
	public static class ContactsForm extends AbstractBean implements Serializable {
		private static final long serialVersionUID = -751917271358280700L;
		
		@OutputSeperator(label=@Text(value="father")) 
		@IncludeInputs private Parent father = new Parent();
		
		@OutputSeperator(label=@Text(value="mother")) 
		@IncludeInputs private Parent mother = new Parent();
		
		/**/
		
		@Getter @Setter
		public static class Parent extends AbstractBean implements Serializable {
			private static final long serialVersionUID = 1L;
			
			@Input @InputChoice @InputOneChoice @InputOneRadio private PersonTitle title;
			@Input @InputText private String names;
			
			@OutputSeperator(label=@Text(value="contacts")) 
			@IncludeInputs private ContactCollectionFormModel contactCollection = new ContactCollectionFormModel(null);
			/*
			@Input @InputText private String mobilePhoneNumber;
			@Input @InputText private String email;
			*/
			
			@OutputSeperator(label=@Text(value="home")) 
			@IncludeInputs private ContactCollectionFormModel homeContactCollection = new ContactCollectionFormModel(LocationType.HOME);
			/*
			@Input @InputText private String homeAddress;
			@Input @InputText private String homePostCode;
			@Input @InputText private String homePhoneNumber;
			*/
			
			@OutputSeperator(label=@Text(value="work")) 
			@Input @InputText private String company;
			@Input @InputChoice @InputChoiceAutoComplete @InputOneChoice @InputOneAutoComplete private JobFunction jobFunction;
			@IncludeInputs private ContactCollectionFormModel workContactCollection = new ContactCollectionFormModel(LocationType.OFFICE);
			/*
			@Input @InputText private String workAddress;
			@Input @InputText private String workPhoneNumber;
			@Input @InputText private String workPostCode;
			*/
			
			public Parent() {
				contactCollection.setFieldRenderedValue(Boolean.FALSE, ContactCollectionFormModel.FIELD_LAND_PHONE_NUMBER,ContactCollectionFormModel.FIELD_LOCATION
						,ContactCollectionFormModel.FIELD_POSTALBOX);
				
				homeContactCollection.setFieldRenderedValue(Boolean.FALSE, ContactCollectionFormModel.FIELD_ELECTRONICMAIL
						,ContactCollectionFormModel.FIELD_MOBILE_PHONE_NUMBER);
				
				workContactCollection.setFieldRenderedValue(Boolean.FALSE, ContactCollectionFormModel.FIELD_ELECTRONICMAIL
						,ContactCollectionFormModel.FIELD_MOBILE_PHONE_NUMBER);
				
			}
		}
		
	}
}
