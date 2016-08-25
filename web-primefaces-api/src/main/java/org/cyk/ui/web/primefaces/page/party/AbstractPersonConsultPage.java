package org.cyk.ui.web.primefaces.page.party;

import java.io.Serializable;

import org.cyk.system.root.business.api.language.LanguageCollectionItemBusiness;
import org.cyk.system.root.business.impl.party.person.JobDetails;
import org.cyk.system.root.business.impl.party.person.MedicalDetails;
import org.cyk.system.root.business.impl.party.person.RelationshipDetails;
import org.cyk.system.root.business.impl.party.person.SignatureDetails;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.ui.web.primefaces.data.collector.form.FormOneData;
import org.cyk.ui.web.primefaces.page.DetailsConfiguration;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractPersonConsultPage<PERSON extends AbstractIdentifiable> extends AbstractPartyConsultPage<PERSON> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	public static DetailsConfiguration JOB_FORM_CONFIGURATION = new DetailsConfiguration();
	public static DetailsConfiguration SIGNATURE_COLLECTION_FORM_CONFIGURATION = new DetailsConfiguration();
	
	protected FormOneData<MedicalDetails> medicalDetails;
	protected FormOneData<JobDetails> jobDetails;
	protected FormOneData<RelationshipDetails> relationshipDetails;
	protected FormOneData<SignatureDetails> signatureDetails;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void consultInitialisation() {
		if(getPerson().getExtendedInformations()!=null && getPerson().getExtendedInformations().getLanguageCollection()!=null)
			getPerson().getExtendedInformations().getLanguageCollection().setCollection(inject(LanguageCollectionItemBusiness.class)
				.findByCollection(getPerson().getExtendedInformations().getLanguageCollection()));
		super.consultInitialisation();
		@SuppressWarnings("rawtypes")
		DetailsConfigurationListener.Form.Adapter adapter = getDetailsConfiguration(JobDetails.class).getFormConfigurationAdapter(Person.class, JobDetails.class);
		adapter.setTitleId(JobDetails.LABEL_IDENTIFIER);
		adapter.setTabId(JobDetails.LABEL_IDENTIFIER);
		jobDetails = createDetailsForm(JobDetails.class, getPerson(), adapter);
		jobDetails.addControlSetListener(getDetailsConfiguration(JobDetails.class).getFormControlSetAdapter(Person.class));
		
		adapter = getDetailsConfiguration(SignatureDetails.class).getFormConfigurationAdapter(Person.class, SignatureDetails.class);
		adapter.setTitleId(SignatureDetails.LABEL_IDENTIFIER);
		adapter.setTabId(SignatureDetails.LABEL_IDENTIFIER);
		signatureDetails = createDetailsForm(SignatureDetails.class, getPerson(),adapter);
		signatureDetails.addControlSetListener(getDetailsConfiguration(SignatureDetails.class).getFormControlSetAdapter(Person.class));
		
	} 
	
	@Override
	protected Party getParty() {
		return getPerson();
	}
	
	protected abstract Person getPerson();
	
}
