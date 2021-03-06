package org.cyk.ui.api.model.party;

import java.io.Serializable;
import java.util.Date;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.geography.ContactCollection;
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
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.api.model.geography.ContactCollectionFormModel;
import org.cyk.ui.api.model.geography.LocationFormModel;
import org.cyk.ui.api.model.language.LanguageCollectionFormModel;
import org.cyk.utility.common.FileExtensionGroup;
import org.cyk.utility.common.annotation.user.interfaces.FileExtensions;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs.Layout;
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
import org.cyk.utility.common.annotation.user.interfaces.OutputSeperator;
import org.cyk.utility.common.annotation.user.interfaces.Sequence;
import org.cyk.utility.common.annotation.user.interfaces.Sequence.Direction;
import org.cyk.utility.common.annotation.user.interfaces.Text;
import org.cyk.utility.common.cdi.AbstractBean;
import org.hibernate.validator.constraints.Email;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public abstract class AbstractPersonEditFormModel<PERSON extends AbstractIdentifiable> extends AbstractPartyEditFormModel<PERSON>  implements Serializable {

	private static final long serialVersionUID = -3897201743383535836L;

	@Input @InputText protected String lastnames;
	@Input @InputText protected String surname;
	@Input @InputChoice @InputOneChoice @InputOneRadio protected Sex sex;
	@Input @InputCalendar protected Date birthDate;
	
	@IncludeInputs(layout=Layout.VERTICAL) protected LocationFormModel birthLocation = new LocationFormModel();
	
	@Input @InputChoice @InputOneChoice @InputOneRadio protected MaritalStatus maritalStatus;
	@Input @InputChoice @InputOneChoice @InputOneRadio protected PersonTitle title;
	@Input @InputChoice @InputChoiceAutoComplete @InputOneChoice @InputOneAutoComplete protected Country nationality;
	
	@Input @InputChoice @InputOneChoice @InputOneRadio protected BloodGroup bloodGroup;
	@Input @InputTextarea protected String otherMedicalInformations;
	
	/* Job details */
	
	@Input @InputText protected String jobCompany;
	@Input @InputChoice @InputChoiceAutoComplete @InputOneChoice @InputOneAutoComplete protected JobTitle jobTitle;
	@Input @InputChoice @InputChoiceAutoComplete @InputOneChoice @InputOneAutoComplete protected JobFunction jobFunction;
	
	@IncludeInputs(layout=Layout.VERTICAL)
	protected ContactCollectionFormModel jobContactCollection = new ContactCollectionFormModel(RootConstant.Code.LocationType.OFFICE);
	
	@Input @InputFile(extensions=@FileExtensions(groups=FileExtensionGroup.IMAGE)) protected File signatureSpecimen;
	
	@IncludeInputs(layout=Layout.VERTICAL)
	protected LanguageCollectionFormModel languageCollection = new LanguageCollectionFormModel();
	/*
	@OutputSeperator(label=@Text(value="field.father"))
	@IncludeInputs(layout=Layout.VERTICAL)
	protected ContactCollectionFormModel fatherContactCollection = new ContactCollectionFormModel(LocationType.HOME);
	
	@OutputSeperator(label=@Text(value="field.mother"))
	@IncludeInputs(layout=Layout.VERTICAL)
	protected ContactCollectionFormModel motherContactCollection = new ContactCollectionFormModel(LocationType.HOME);
	*/
	
	@Input @InputText @Email private String fatherElectronicMail,motherElectronicMail;
	
	protected abstract Person getPerson();
	
	protected Party getParty(){
		return getPerson();
	}
	
	@Override
	public void setIdentifiable(PERSON identifiable) {
		super.setIdentifiable(identifiable);
		setJobContactCollection();
	}
	
	protected void setJobContactCollection(){
		if(jobContactCollection.getIdentifiable()==null){
			if(getPerson().getJobInformations()!=null && getPerson().getJobInformations().getContactCollection()==null)
				getPerson().getJobInformations().setContactCollection(new ContactCollection());
			if(getPerson().getJobInformations()!=null)
				jobContactCollection.setIdentifiable(getPerson().getJobInformations().getContactCollection());
			//if(getPerson().getJobInformations().getContactCollection()!=null)
	    	//	inject(ContactCollectionBusiness.class).load(getPerson().getJobInformations().getContactCollection());	
		}
	}
	
	@Override
	public void read() {
		super.read();
		setJobContactCollection();
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
	
	@Override @Sequence(direction=Direction.AFTER,field=FIELD_IMAGE)
	public ContactCollectionFormModel getContactCollection() {
		return super.getContactCollection();
	}
	
	/**/
	
	@Getter @Setter
	public static abstract class Extends<PERSON extends Person> extends AbstractPersonEditFormModel<PERSON>  implements Serializable {

		private static final long serialVersionUID = -3897201743383535836L;
	
		@Override
		protected Person getPerson() {
			return identifiable;
		}
		
		/**/
		
		@Getter @Setter
		public static class Default<PERSON extends Person> extends Extends<PERSON>  implements Serializable {

			private static final long serialVersionUID = -3897201743383535836L;
				
		}
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
	
	/**/
	
	@Getter @Setter
	public static class Contacts extends AbstractFormModel<Person> implements Serializable {
		private static final long serialVersionUID = -751917271358280700L;
		
		@OutputSeperator(label=@Text(value="father")) 
		@IncludeInputs private Parent father = new Parent();
		
		@OutputSeperator(label=@Text(value="mother")) 
		@IncludeInputs private Parent mother = new Parent();
		
		/**/
	
		@Override
		public void read() {
			super.read();
		}
		
		@Override
		public void write() {
			super.write();
			
		}
		
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
			@IncludeInputs private ContactCollectionFormModel homeContactCollection = new ContactCollectionFormModel(RootConstant.Code.LocationType.HOME);
			/*
			@Input @InputText private String homeAddress;
			@Input @InputText private String homePostCode;
			@Input @InputText private String homePhoneNumber;
			*/
			
			@OutputSeperator(label=@Text(value="work")) 
			@Input @InputText private String company;
			@Input @InputChoice @InputChoiceAutoComplete @InputOneChoice @InputOneAutoComplete private JobFunction jobFunction;
			@IncludeInputs private ContactCollectionFormModel workContactCollection = new ContactCollectionFormModel(RootConstant.Code.LocationType.OFFICE);
			/*
			@Input @InputText private String workAddress;
			@Input @InputText private String workPhoneNumber;
			@Input @InputText private String workPostCode;
			*/
			
			public Parent() {
				contactCollection.setFieldRenderedValue(Boolean.FALSE, ContactCollectionFormModel.FIELD_LAND_PHONE_NUMBER,ContactCollectionFormModel.FIELD_LOCATION
						,ContactCollectionFormModel.FIELD_POSTALBOX);
				
				homeContactCollection.setFieldRenderedValue(Boolean.FALSE, ContactCollectionFormModel.FIELD_ELECTRONICMAIL1
						,ContactCollectionFormModel.FIELD_MOBILE_PHONE_NUMBER);
				
				workContactCollection.setFieldRenderedValue(Boolean.FALSE, ContactCollectionFormModel.FIELD_ELECTRONICMAIL1
						,ContactCollectionFormModel.FIELD_MOBILE_PHONE_NUMBER);
				
			}
			
			public void read(Person person){
				title = person.getExtendedInformations().getTitle();
				names = person.getName();
			}
			
			public void write(Person person){
				person.getExtendedInformations().setTitle(title);
				person.setName(names);
			}
		}	
	
	}
	
}
