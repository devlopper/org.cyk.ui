package org.cyk.ui.web.primefaces.page.party;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.ui.web.primefaces.data.collector.control.ControlSetAdapter;
import org.cyk.ui.web.primefaces.data.collector.form.FormOneData;
import org.cyk.ui.web.primefaces.page.party.AbstractActorConsultPage.JobDetails;
import org.cyk.ui.web.primefaces.page.party.AbstractActorConsultPage.MedicalDetails;
import org.cyk.ui.web.primefaces.page.party.AbstractActorConsultPage.RelationshipDetails;
import org.cyk.ui.web.primefaces.page.party.AbstractActorConsultPage.SignatureDetails;

@Getter @Setter
public abstract class AbstractPersonConsultPage<PERSON extends AbstractIdentifiable> extends AbstractPartyConsultPage<PERSON> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	protected FormOneData<MedicalDetails> medicalDetails;
	protected FormOneData<JobDetails> jobDetails;
	protected FormOneData<RelationshipDetails> relationshipDetails;
	protected FormOneData<SignatureDetails> signatureDetails;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void consultInitialisation() {
		super.consultInitialisation();
		jobDetails = createDetailsForm(JobDetails.class, identifiable
				, new DetailsConfigurationListener.Form.Adapter<PERSON,JobDetails>((Class<PERSON>) identifiable.getClass(),JobDetails.class));
		
		signatureDetails = createDetailsForm(SignatureDetails.class, identifiable
				, new DetailsConfigurationListener.Form.Adapter<PERSON,SignatureDetails>((Class<PERSON>) identifiable.getClass(),SignatureDetails.class));
	} 
	
	protected abstract Person getPerson();
	
	protected JobDetailsControlSetAdapter<PERSON> getJobDetailsControlSetAdapter(){
		return new JobDetailsControlSetAdapter<PERSON>();
	}
	
	/**/
	
	public static class MedicalDetailsControlSetAdapter<IDENTIFIABLE extends AbstractIdentifiable> extends ControlSetAdapter<AbstractOutputDetails<IDENTIFIABLE>> {
				
	}
	
	public static class JobDetailsControlSetAdapter<IDENTIFIABLE extends AbstractIdentifiable> extends ControlSetAdapter<AbstractOutputDetails<IDENTIFIABLE>> {
		
	}
	
	public static class RelationshipDetailsControlSetAdapter<IDENTIFIABLE extends AbstractIdentifiable> extends ControlSetAdapter<AbstractOutputDetails<IDENTIFIABLE>> {
		
	}
	
	public static class SignatureDetailsControlSetAdapter<IDENTIFIABLE extends AbstractIdentifiable> extends ControlSetAdapter<AbstractOutputDetails<IDENTIFIABLE>> {
		
	}
}
