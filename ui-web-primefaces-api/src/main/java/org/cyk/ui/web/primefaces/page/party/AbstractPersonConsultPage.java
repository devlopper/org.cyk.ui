package org.cyk.ui.web.primefaces.page.party;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.party.person.JobInformationsBusiness;
import org.cyk.system.root.business.api.party.person.MedicalInformationsAllergyBusiness;
import org.cyk.system.root.business.api.party.person.MedicalInformationsBusiness;
import org.cyk.system.root.business.api.party.person.MedicalInformationsMedicationBusiness;
import org.cyk.system.root.business.api.party.person.PersonExtendedInformationsBusiness;
import org.cyk.system.root.business.api.party.person.PersonRelationshipBusiness;
import org.cyk.system.root.business.impl.party.person.JobDetails;
import org.cyk.system.root.business.impl.party.person.MedicalDetails;
import org.cyk.system.root.business.impl.party.person.MedicalInformationsAllergyDetails;
import org.cyk.system.root.business.impl.party.person.MedicalInformationsMedicationDetails;
import org.cyk.system.root.business.impl.party.person.PersonRelationshipDetails;
import org.cyk.system.root.business.impl.party.person.SignatureDetails;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.person.MedicalInformationsAllergy;
import org.cyk.system.root.model.party.person.MedicalInformationsMedication;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonRelationship;
import org.cyk.ui.api.model.table.Row;
import org.cyk.ui.api.model.table.RowAdapter;
import org.cyk.ui.web.primefaces.Table;
import org.cyk.ui.web.primefaces.data.collector.form.FormOneData;
import org.cyk.ui.web.primefaces.page.DetailsConfiguration;

@Getter @Setter
public abstract class AbstractPersonConsultPage<PERSON extends AbstractIdentifiable> extends AbstractPartyConsultPage<PERSON> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	public static DetailsConfiguration JOB_FORM_CONFIGURATION = new DetailsConfiguration();
	public static DetailsConfiguration SIGNATURE_COLLECTION_FORM_CONFIGURATION = new DetailsConfiguration();
	
	protected FormOneData<MedicalDetails> medicalDetails;
	protected Table<MedicalInformationsMedicationDetails> medicationTable;
	protected Table<MedicalInformationsAllergyDetails> allergyTable;
	
	protected FormOneData<JobDetails> jobDetails;
	protected Table<PersonRelationshipDetails> relationshipTable;
	protected FormOneData<SignatureDetails> signatureDetails;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void consultInitialisation() {
		super.consultInitialisation();
		
		@SuppressWarnings("rawtypes")
		DetailsConfigurationListener.Form.Adapter adapter = getDetailsConfiguration(JobDetails.class).getFormConfigurationAdapter(Person.class, JobDetails.class);
		adapter.setTitleId(JobDetails.LABEL_IDENTIFIER);
		adapter.setTabId(JobDetails.LABEL_IDENTIFIER);
		jobDetails = createDetailsForm(JobDetails.class, getPerson(), adapter);
		
		adapter = getDetailsConfiguration(SignatureDetails.class).getFormConfigurationAdapter(Person.class, SignatureDetails.class);
		adapter.setTitleId(SignatureDetails.LABEL_IDENTIFIER);
		adapter.setTabId(businessEntityInfos.getUserInterface().getLabelId());
		adapter.setEnabledInDefaultTab(Boolean.TRUE);
		adapter.setFormConfigurationIdentifier(SignatureDetails.LABEL_IDENTIFIER);
		signatureDetails = createDetailsForm(SignatureDetails.class, getPerson(),adapter);
		
		@SuppressWarnings("rawtypes")
		final DetailsConfigurationListener.Form.Adapter medicalAdapter = getDetailsConfiguration(MedicalDetails.class).getFormConfigurationAdapter(Person.class, MedicalDetails.class);
		medicalAdapter.setTitleId(MedicalDetails.LABEL_IDENTIFIER);
		medicalAdapter.setTabId(MedicalDetails.LABEL_IDENTIFIER);
		medicalAdapter.setIdentifiableClass(Person.class);//FIXME when not set , it gives null pointer , WHY ?
		
		medicalDetails = createDetailsForm(MedicalDetails.class, getPerson(),medicalAdapter);
		
		medicationTable = (Table<MedicalInformationsMedicationDetails>) createDetailsTable(MedicalInformationsMedicationDetails.class, new DetailsConfigurationListener.Table.Adapter<MedicalInformationsMedication,MedicalInformationsMedicationDetails>(MedicalInformationsMedication.class, MedicalInformationsMedicationDetails.class){
			private static final long serialVersionUID = 1L;
			@Override
			public Collection<MedicalInformationsMedication> getIdentifiables() {
				return inject(MedicalInformationsMedicationBusiness.class).findByMedicalInformations(getPerson().getMedicalInformations());
			}
			@Override
			public String getTabId() {
				return medicalAdapter.getTabId();
			}
			@Override
			public Collection<? extends AbstractIdentifiable> getMasters() {
				return getPerson().getMedicalInformations() == null ? super.getMasters() : Arrays.asList(getPerson().getMedicalInformations());
			}
			
		});
		
		allergyTable = (Table<MedicalInformationsAllergyDetails>) createDetailsTable(MedicalInformationsAllergyDetails.class, new DetailsConfigurationListener.Table.Adapter<MedicalInformationsAllergy,MedicalInformationsAllergyDetails>(MedicalInformationsAllergy.class, MedicalInformationsAllergyDetails.class){
			private static final long serialVersionUID = 1L;
			@Override
			public Collection<MedicalInformationsAllergy> getIdentifiables() {
				return inject(MedicalInformationsAllergyBusiness.class).findByMedicalInformations(getPerson().getMedicalInformations());
			}
			@Override
			public String getTabId() {
				return medicalAdapter.getTabId();
			}
			@Override
			public Collection<? extends AbstractIdentifiable> getMasters() {
				return getPerson().getMedicalInformations() == null ? super.getMasters() : Arrays.asList(getPerson().getMedicalInformations());
			}
			
		});
		
		relationshipTable = (Table<PersonRelationshipDetails>) createDetailsTable(PersonRelationshipDetails.class, new DetailsConfigurationListener.Table.Adapter<PersonRelationship,PersonRelationshipDetails>(PersonRelationship.class, PersonRelationshipDetails.class){
			private static final long serialVersionUID = 1L;
			@Override
			public Collection<PersonRelationship> getIdentifiables() {
				return inject(PersonRelationshipBusiness.class).findByPerson(getPerson());
			}
			
			@Override
			public String getTabId() {
				return businessEntityInfos.getUserInterface().getLabelId();
			}
			
			@Override
			public Boolean getEnabledInDefaultTab() {
				return Boolean.TRUE;
			}
			
			@Override
			public Boolean getIsIdentifiableMaster() {
				return Boolean.FALSE;
			}
			
			@Override
			public Collection<? extends AbstractIdentifiable> getMasters() {
				return Arrays.asList(getPerson());
			}
			
			@Override
			public RowAdapter<PersonRelationshipDetails> getRowAdapter() {
				return new RowAdapter<PersonRelationshipDetails>(getUserSession()){
					private static final long serialVersionUID = 1L;
					@Override
					public void added(Row<PersonRelationshipDetails> row) {
						super.added(row);
						/*String format = "%s : <a href='consultlink'>%s</a>";
						String type = getPerson().equals(row.getData().getMaster().getPerson1()) ? "INVERSE of "+row.getData().getType() : row.getData().getType().getValue();
						String person = getPerson().equals(row.getData().getMaster().getPerson1()) ? row.getData().getPerson2().getValue() : row.getData().getPerson1().getValue();
						row.setHtml(String.format(format, type,person));
						*/
					}
				};
			}
			
		});
		
		//relationshipTable.setRenderType(RenderType.LIST);
		
	} 
	
	/*@Override
	protected void createContactCollectionDetails() {
		//super.createContactCollectionDetails();
		contactCollectionDetails = createDetailsForm(ContactCollectionDetails.class, getParty(), getContactCollectionDetailsAdapter());
	}*/
	
	@SuppressWarnings("rawtypes")
	@Override
	protected DetailsConfigurationListener.Form.Adapter getContactCollectionDetailsAdapter() {
		DetailsConfigurationListener.Form.Adapter adapter = super.getContactCollectionDetailsAdapter();
		adapter.setTabId(businessEntityInfos.getUserInterface().getLabelId());
		adapter.setEnabledInDefaultTab(Boolean.TRUE);
		return adapter;
	}
	
	@Override
	protected void processOnIdentifiableFound(PERSON identifiable) {
		super.processOnIdentifiableFound(identifiable);
		if(isDetailsMenuCommandable(JobDetails.class)){
			getPerson().setJobInformations(inject(JobInformationsBusiness.class).findByParty(getPerson()));
			//inject(JobInformationsBusiness.class).load(getPerson().getJobInformations());
		}else if(isDetailsMenuCommandable(MedicalDetails.class)){
			getPerson().setMedicalInformations(inject(MedicalInformationsBusiness.class).findByParty(getPerson()));
			//inject(MedicalInformationsBusiness.class).load(getPerson().getMedicalInformations());
		}else{
			getPerson().setExtendedInformations(inject(PersonExtendedInformationsBusiness.class).findByParty(getPerson()));
			//inject(PersonExtendedInformationsBusiness.class).load(getPerson().getExtendedInformations());		
		}
	}
	
	@Override
	protected Party getParty() {
		return getPerson();
	}
	
	protected abstract Person getPerson();
	
}
