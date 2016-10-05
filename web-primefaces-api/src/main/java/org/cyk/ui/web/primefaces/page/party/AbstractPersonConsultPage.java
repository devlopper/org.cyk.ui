package org.cyk.ui.web.primefaces.page.party;

import java.io.Serializable;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.party.person.JobInformationsBusiness;
import org.cyk.system.root.business.api.party.person.MedicalInformationsBusiness;
import org.cyk.system.root.business.api.party.person.PersonExtendedInformationsBusiness;
import org.cyk.system.root.business.api.party.person.PersonRelationshipBusiness;
import org.cyk.system.root.business.impl.party.person.JobDetails;
import org.cyk.system.root.business.impl.party.person.MedicalDetails;
import org.cyk.system.root.business.impl.party.person.PersonRelationshipDetails;
import org.cyk.system.root.business.impl.party.person.SignatureDetails;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonRelationship;
import org.cyk.ui.web.primefaces.Table;
import org.cyk.ui.web.primefaces.data.collector.form.FormOneData;
import org.cyk.ui.web.primefaces.page.DetailsConfiguration;

@Getter @Setter
public abstract class AbstractPersonConsultPage<PERSON extends AbstractIdentifiable> extends AbstractPartyConsultPage<PERSON> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	public static DetailsConfiguration JOB_FORM_CONFIGURATION = new DetailsConfiguration();
	public static DetailsConfiguration SIGNATURE_COLLECTION_FORM_CONFIGURATION = new DetailsConfiguration();
	
	protected FormOneData<MedicalDetails> medicalDetails;
	protected FormOneData<JobDetails> jobDetails;
	protected Table<PersonRelationshipDetails> relationshipTable;
	protected FormOneData<SignatureDetails> signatureDetails;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void consultInitialisation() {
		/*if(getPerson().getExtendedInformations()!=null && getPerson().getExtendedInformations().getLanguageCollection()!=null)
			getPerson().getExtendedInformations().getLanguageCollection().setCollection(inject(LanguageCollectionItemBusiness.class)
				.findByCollection(getPerson().getExtendedInformations().getLanguageCollection()));
		*/
		super.consultInitialisation();
		
		@SuppressWarnings("rawtypes")
		DetailsConfigurationListener.Form.Adapter adapter = getDetailsConfiguration(JobDetails.class).getFormConfigurationAdapter(Person.class, JobDetails.class);
		adapter.setTitleId(JobDetails.LABEL_IDENTIFIER);
		adapter.setTabId(JobDetails.LABEL_IDENTIFIER);
		jobDetails = createDetailsForm(JobDetails.class, getPerson(), adapter);
		
		adapter = getDetailsConfiguration(SignatureDetails.class).getFormConfigurationAdapter(Person.class, SignatureDetails.class);
		adapter.setTitleId(SignatureDetails.LABEL_IDENTIFIER);
		adapter.setTabId(SignatureDetails.LABEL_IDENTIFIER);
		signatureDetails = createDetailsForm(SignatureDetails.class, getPerson(),adapter);
		
		adapter = getDetailsConfiguration(MedicalDetails.class).getFormConfigurationAdapter(Person.class, MedicalDetails.class);
		adapter.setTitleId(MedicalDetails.LABEL_IDENTIFIER);
		adapter.setTabId(MedicalDetails.LABEL_IDENTIFIER);
		medicalDetails = createDetailsForm(MedicalDetails.class, getPerson(),adapter);
		
		relationshipTable = (Table<PersonRelationshipDetails>) createDetailsTable(PersonRelationshipDetails.class, new DetailsConfigurationListener.Table.Adapter<PersonRelationship,PersonRelationshipDetails>(PersonRelationship.class, PersonRelationshipDetails.class){
			private static final long serialVersionUID = 1L;
			@Override
			public Collection<PersonRelationship> getIdentifiables() {
				return inject(PersonRelationshipBusiness.class).findByPerson(getPerson());
			}
		});
	} 
	
	@Override
	protected <T extends AbstractIdentifiable> T identifiableFromRequestParameter(Class<T> aClass, String identifierId) {
		T t = super.identifiableFromRequestParameter(aClass, identifierId);
		if(isDetailsMenuCommandable(JobDetails.class))
			inject(JobInformationsBusiness.class).load(((Person)t).getJobInformations());
		else if(isDetailsMenuCommandable(MedicalDetails.class))
			inject(MedicalInformationsBusiness.class).load(((Person)t).getMedicalInformations());
		else{
			((Person)t).setExtendedInformations(inject(PersonExtendedInformationsBusiness.class).findByParty((Person)t));
			inject(PersonExtendedInformationsBusiness.class).load(((Person)t).getExtendedInformations());
		}
		return t;
	}
	
	@Override
	protected Party getParty() {
		return getPerson();
	}
	
	protected abstract Person getPerson();
	
}
