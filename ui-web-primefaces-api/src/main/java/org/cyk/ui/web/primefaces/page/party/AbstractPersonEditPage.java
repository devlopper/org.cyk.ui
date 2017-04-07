package org.cyk.ui.web.primefaces.page.party;

import java.io.Serializable;

import org.cyk.system.root.business.api.party.person.JobInformationsBusiness;
import org.cyk.system.root.business.api.party.person.MedicalInformationsBusiness;
import org.cyk.system.root.business.api.party.person.PersonExtendedInformationsBusiness;
import org.cyk.system.root.business.impl.party.person.JobDetails;
import org.cyk.system.root.business.impl.party.person.MedicalDetails;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.person.JobInformations;
import org.cyk.system.root.model.party.person.Person;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractPersonEditPage<PERSON extends AbstractIdentifiable> extends AbstractPartyEditPage<PERSON> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	//protected FormOneData<AbstractPersonEditFormModel.Extends.Default<Person>> fatherForm;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		/*AbstractPersonEditFormModel.Extends.Default<Person> father = new AbstractPersonEditFormModel.Extends.Default<Person>();
		if(Crud.CREATE.equals(crud))
			inject(PersonBusiness.class).ad ;
		else{
			father.setIdentifiable(inject(PersonRelationshipBusiness.class).findByPerson(getPerson()));
		}
		fatherForm = (FormOneData<AbstractPersonEditFormModel.Extends.Default<Person>>) createFormOneData(father, crud);
		
		//fatherForm = (FormOneData<Object>) createFormOneData(data,crud,getSubmitCommandableLabelId());
		
		fatherForm.setTabTitle("Father");
		*/
	}
	
	@Override
	protected void update() {
		debug(getPerson().getJobInformations());
		super.update();
	}
	
	@Override
	protected void processOnIdentifiableFound(PERSON identifiable) {
		super.processOnIdentifiableFound(identifiable);
		if(isDetailsMenuCommandable(JobDetails.class)){
			getPerson().setJobInformations(inject(JobInformationsBusiness.class).findByParty(getPerson()));
			if(getPerson().getJobInformations()==null)
				getPerson().setJobInformations(new JobInformations(getPerson()));
			else
				inject(JobInformationsBusiness.class).load(getPerson().getJobInformations());
			
			//inject(JobInformationsBusiness.class).load(getPerson().getJobInformations());
			
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
	
	
}
