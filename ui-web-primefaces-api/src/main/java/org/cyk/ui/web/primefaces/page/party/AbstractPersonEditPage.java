package org.cyk.ui.web.primefaces.page.party;

import java.io.Serializable;
import java.util.Date;

import org.cyk.system.root.business.api.party.person.JobInformationsBusiness;
import org.cyk.system.root.business.api.party.person.MedicalInformationsBusiness;
import org.cyk.system.root.business.api.party.person.PersonExtendedInformationsBusiness;
import org.cyk.system.root.business.impl.party.person.JobDetails;
import org.cyk.system.root.business.impl.party.person.MedicalDetails;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.geography.Country;
import org.cyk.system.root.model.geography.Location;
import org.cyk.system.root.model.language.LanguageCollection;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.person.BloodGroup;
import org.cyk.system.root.model.party.person.JobFunction;
import org.cyk.system.root.model.party.person.JobInformations;
import org.cyk.system.root.model.party.person.JobTitle;
import org.cyk.system.root.model.party.person.MaritalStatus;
import org.cyk.system.root.model.party.person.MedicalInformations;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonExtendedInformations;
import org.cyk.system.root.model.party.person.PersonTitle;
import org.cyk.system.root.model.party.person.Sex;
import org.cyk.ui.api.model.geography.LocationFormModel;
import org.cyk.ui.api.model.language.LanguageCollectionFormModel;
import org.cyk.utility.common.FileExtensionGroup;
import org.cyk.utility.common.annotation.user.interfaces.FileExtensions;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputCalendar;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputChoiceAutoComplete;
import org.cyk.utility.common.annotation.user.interfaces.InputFile;
import org.cyk.utility.common.annotation.user.interfaces.InputOneAutoComplete;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneRadio;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.annotation.user.interfaces.InputTextarea;
import org.cyk.utility.common.annotation.user.interfaces.Sequence;
import org.cyk.utility.common.annotation.user.interfaces.Sequence.Direction;

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
	protected void processOnIdentifiableFound(PERSON identifiable) {
		super.processOnIdentifiableFound(identifiable);
		if(isDetailsMenuCommandable(JobDetails.class)){
			getPerson().setJobInformations(inject(JobInformationsBusiness.class).findByParty(getPerson()));
			if(getPerson().getJobInformations()==null)
				getPerson().setJobInformations(new JobInformations(getPerson()));
			else
				;//inject(JobInformationsBusiness.class).load(getPerson().getJobInformations());
			
			//inject(JobInformationsBusiness.class).load(getPerson().getJobInformations());
			
		}else if(isDetailsMenuCommandable(MedicalDetails.class)){
			getPerson().setMedicalInformations(inject(MedicalInformationsBusiness.class).findByParty(getPerson()));
			//inject(MedicalInformationsBusiness.class).load(getPerson().getMedicalInformations());		
		}else {
			getPerson().setExtendedInformations(inject(PersonExtendedInformationsBusiness.class).findByParty(getPerson()));
			//inject(PersonExtendedInformationsBusiness.class).load(getPerson().getExtendedInformations());		
		}
	}
	
	protected abstract Person getPerson();
	
	@Override
	protected Party getParty() {
		return getPerson();
	}
	
	/**/
	
	@Getter @Setter
	public abstract static class AbstractForm<PERSON extends AbstractIdentifiable> extends AbstractPartyEditPage.Form<PERSON>  implements Serializable {
		private static final long serialVersionUID = -3897201743383535836L;
		
		@Input @InputText protected String lastnames;
		@Input @InputText protected String surname;
		@Input @InputChoice @InputOneChoice @InputOneRadio protected Sex sex;
		@Input @InputCalendar protected Date birthDate;
		
		@IncludeInputs(layout=IncludeInputs.Layout.VERTICAL) protected LocationFormModel birthLocation = new LocationFormModel();
		
		@Input @InputChoice @InputOneChoice @InputOneRadio protected MaritalStatus maritalStatus;
		@Input @InputChoice @InputOneChoice @InputOneRadio protected PersonTitle title;
		@Input @InputChoice @InputChoiceAutoComplete @InputOneChoice @InputOneAutoComplete protected Country nationality;
		
		@Input @InputChoice @InputOneChoice @InputOneRadio protected BloodGroup bloodGroup;
		@Input @InputTextarea protected String otherMedicalInformations;
		
		/* Job details */
		
		@Input @InputText protected String jobCompany;
		@Input @InputChoice @InputChoiceAutoComplete @InputOneChoice @InputOneAutoComplete protected JobTitle jobTitle;
		@Input @InputChoice @InputChoiceAutoComplete @InputOneChoice @InputOneAutoComplete protected JobFunction jobFunction;
		
		@Input @InputFile(extensions=@FileExtensions(groups=FileExtensionGroup.IMAGE)) protected File signatureSpecimen;
		
		@IncludeInputs(layout=IncludeInputs.Layout.VERTICAL)
		protected LanguageCollectionFormModel languageCollection = new LanguageCollectionFormModel();
		
		protected abstract Person getPerson();
		
		protected Party getParty(){
			return getPerson();
		}
				
		@Override
		public void read() {
			super.read();
			name = getPerson().getName();
			birthDate = getPerson().getBirthDate();
			lastnames = getPerson().getLastnames();
			sex = getPerson().getSex();
			nationality = getPerson().getNationality();
			if(getPerson().getExtendedInformations()!=null){
				languageCollection.setIdentifiable(getPerson().getExtendedInformations().getLanguageCollection());
				languageCollection.read();
				if(getPerson().getExtendedInformations().getBirthLocation()!=null){
					birthLocation.setIdentifiable(getPerson().getExtendedInformations().getBirthLocation());
					birthLocation.read();
				}
				title = getPerson().getExtendedInformations().getTitle();
				signatureSpecimen = getPerson().getExtendedInformations().getSignatureSpecimen();
			}
			if(getPerson().getJobInformations()!=null){
				jobCompany = getPerson().getJobInformations().getCompany();
				jobFunction = getPerson().getJobInformations().getFunction();
				jobTitle = getPerson().getJobInformations().getTitle();
			}
			
			if(getPerson().getMedicalInformations()!=null){
				bloodGroup = getPerson().getMedicalInformations().getBloodGroup();
				otherMedicalInformations = getPerson().getMedicalInformations().getOtherDetails();
			}
		}
		
		@Override
		public void write() {
			super.write();
			getPerson().setName(name);
			getPerson().setLastnames(lastnames);
			getPerson().setSurname(surname);
			getPerson().setSex(sex);
			getPerson().setNationality(nationality);
			getPerson().setBirthDate(birthDate);
			
			PersonExtendedInformations personExtendedInformations = getPerson().getExtendedInformations();
			if(personExtendedInformations!=null){
				if(title!=null)
					personExtendedInformations.setTitle(title);
				if(languageCollection!=null){
					if(languageCollection.getLanguage1()!=null && languageCollection.getIdentifiable()==null){
						personExtendedInformations.setLanguageCollection(new LanguageCollection());
						languageCollection.setIdentifiable(personExtendedInformations.getLanguageCollection());
					}
					languageCollection.write();
				}
				
				personExtendedInformations.setMaritalStatus(maritalStatus);
				
				if(personExtendedInformations.getBirthLocation()==null && (birthLocation.getLocality()!=null /*|| StringUtils.isNotBlank(birthLocation.getOtherDetails())*/ )){
					getPerson().getExtendedInformations().setBirthLocation(new Location());
					birthLocation.setIdentifiable(getPerson().getExtendedInformations().getBirthLocation());
				}
				birthLocation.write();
				
				if(signatureSpecimen!=null)
					personExtendedInformations.setSignatureSpecimen(signatureSpecimen);	
			}
			
			JobInformations jobInformations = getPerson().getJobInformations();
			if(jobInformations!=null){
				jobInformations.setFunction(jobFunction);
				jobInformations.setTitle(jobTitle);
				jobInformations.setCompany(jobCompany);	
			}
			
			MedicalInformations medicalInformations = getPerson().getMedicalInformations();
			if(medicalInformations!=null){
				medicalInformations.setBloodGroup(bloodGroup);	
				medicalInformations.setOtherDetails(otherMedicalInformations);
			}
		
		}
		
		/**/
		
		@Override @Sequence(direction=Direction.AFTER,field=FIELD_NATIONALITY)
		public File getImage() {
			return super.getImage();
		}
		
		/**/
		
		public static final String FIELD_LAST_NAMES = "lastnames";
		public static final String FIELD_BIRTH_DATE = "birthDate";
		public static final String FIELD_BIRTH_LOCATION = "birthLocation";
		public static final String FIELD_SURNAME = "surname";
		public static final String FIELD_SEX = "sex";
		public static final String FIELD_MARITAL_STATUS = "maritalStatus";
		public static final String FIELD_NATIONALITY = "nationality";
		public static final String FIELD_TITLE = "title";
		public static final String FIELD_SIGNATURE_SPECIMEN = "signatureSpecimen";
		public static final String FIELD_LANGUAGE_COLLECTION = "languageCollection";
		//public static final String FIELD_FATHER_ELECTRONIC_MAIL = "fatherElectronicMail";
		//public static final String FIELD_MOTHER_ELECTRONIC_MAIL = "motherElectronicMail";
		
		public static final String FIELD_BLOOD_GROUP = "bloodGroup";
		public static final String FIELD_ALLERGIC_REACTION_RESPONSE = "allergicReactionResponse";
		public static final String FIELD_ALLERGIC_REACTION_TYPE = "allergicReactionType";
		public static final String FIELD_ALLERGIES = "allergies";
		public static final String FIELD_MEDICATIONS = "medications";
		public static final String FIELD_OTHER_MEDICAL_INFORMATIONS = "otherMedicalInformations";
		
		public static final String FIELD_JOB_TITLE = "jobTitle";
		public static final String FIELD_JOB_FUNCTION = "jobFunction";
		public static final String FIELD_JOB_COMPANY = "jobCompany";
		public static final String FIELD_JOB_CONTACT_COLLECTION = "jobContactCollection";
		
	}
}
