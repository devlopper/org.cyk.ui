package org.cyk.ui.api.model.party;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.geography.Country;
import org.cyk.system.root.model.geography.Location;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.person.BloodGroup;
import org.cyk.system.root.model.party.person.JobFunction;
import org.cyk.system.root.model.party.person.JobInformations;
import org.cyk.system.root.model.party.person.JobTitle;
import org.cyk.system.root.model.party.person.MaritalStatus;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonExtendedInformations;
import org.cyk.system.root.model.party.person.PersonTitle;
import org.cyk.system.root.model.party.person.Sex;
import org.cyk.ui.api.model.geography.ContactCollectionFormModel;
import org.cyk.utility.common.FileExtensionGroup;
import org.cyk.utility.common.annotation.user.interfaces.FileExtensions;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputCalendar;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputEditor;
import org.cyk.utility.common.annotation.user.interfaces.InputFile;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.annotation.user.interfaces.Sequence;
import org.cyk.utility.common.annotation.user.interfaces.Sequence.Direction;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public abstract class AbstractPersonEditFormModel<PERSON extends AbstractIdentifiable> extends AbstractPartyEditFormModel<PERSON>  implements Serializable {

	private static final long serialVersionUID = -3897201743383535836L;

	@Input @InputText protected String lastnames;
	@Input @InputText protected String surname;
	@Input @InputChoice @InputOneChoice @InputOneCombo protected Sex sex;
	@Input @InputCalendar protected Date birthDate;
	@Input @InputText protected String birthLocation;
	@Input @InputChoice @InputOneChoice @InputOneCombo protected MaritalStatus maritalStatus;
	@Input @InputChoice @InputOneChoice @InputOneCombo protected PersonTitle title;
	@Input @InputChoice @InputOneChoice @InputOneCombo protected Country nationality;
	
	@Input @InputChoice @InputOneChoice @InputOneCombo protected BloodGroup bloodGroup;
	//@Input @InputText protected String allergicReactionResponse,allergicReactionType;
	@Input @InputEditor protected String otherMedicalInformations;
	
	@Input @InputText protected String company;
	@Input @InputChoice @InputOneChoice @InputOneCombo protected JobTitle jobTitle;
	@Input @InputChoice @InputOneChoice @InputOneCombo protected JobFunction jobFunction;
	
	@Input @InputFile(extensions=@FileExtensions(groups=FileExtensionGroup.IMAGE)) protected File signatureSpecimen;
	
	protected abstract Person getPerson();
	
	protected Party getParty(){
		return getPerson();
	}
	
	@Override
	public void write() {
		super.write();
		getPerson().setLastnames(lastnames);
		getPerson().setSurname(surname);
		getPerson().setSex(sex);
		getPerson().setNationality(nationality);
		//identifiable.setBirthDate(birthDate);
		if(title!=null)
			getExtendedInformations(Boolean.TRUE).setTitle(title);
		
		if(maritalStatus!=null)
			;//getExtendedInformations(Boolean.TRUE).setMaritalStatus(maritalStatus);
		if(StringUtils.isBlank(birthLocation)){
			Location location = getExtendedInformations(Boolean.TRUE).getBirthLocation();
			if(location==null)
				;
			else
				location.setOtherDetails(null);
		}else{
			Location location = getExtendedInformations(Boolean.TRUE).getBirthLocation();
			if(location==null){
				getExtendedInformations(Boolean.TRUE).setBirthLocation(new Location(null,null));
				getExtendedInformations(Boolean.TRUE).getBirthLocation().setOtherDetails(birthLocation);
			}else
				location.setOtherDetails(birthLocation);
		}
		
		if(signatureSpecimen!=null)
			getExtendedInformations(Boolean.TRUE).setSignatureSpecimen(signatureSpecimen);
		
		if(jobFunction!=null)
			getJobInformations(Boolean.TRUE).setFunction(jobFunction);
		if(jobTitle!=null)
			getJobInformations(Boolean.TRUE).setTitle(jobTitle);
		
		/*if(identifiable.getExtendedInformations()!=null){
			identifiable.getExtendedInformations().setMaritalStatus(maritalStatus);
			if(identifiable.getExtendedInformations().getBirthLocation()==null) 
				identifiable.getExtendedInformations().setBirthLocation(new Location());
			identifiable.getExtendedInformations().getBirthLocation().setComment(birthLocation);
		}*/

	}
	
	private PersonExtendedInformations getExtendedInformations(Boolean createIfNull){
		PersonExtendedInformations personExtendedInformations = getPerson().getExtendedInformations();
		if(personExtendedInformations==null)
			if(Boolean.TRUE.equals(createIfNull))
				personExtendedInformations = new PersonExtendedInformations(getPerson());
		getPerson().setExtendedInformations(personExtendedInformations);
		return personExtendedInformations;
	}
	
	private JobInformations getJobInformations(Boolean createIfNull){
		JobInformations jobInformations = getPerson().getJobInformations();
		if(jobInformations==null)
			if(Boolean.TRUE.equals(createIfNull))
				jobInformations = new JobInformations(getPerson());
		getPerson().setJobInformations(jobInformations);
		return jobInformations;
	}
	
	@Override
	public void read() {
		super.read();
		if(getPerson().getExtendedInformations()!=null){
			if(getPerson().getExtendedInformations().getBirthLocation()!=null)
				birthLocation = getPerson().getExtendedInformations().getBirthLocation().getOtherDetails();
			title = getPerson().getExtendedInformations().getTitle();
			signatureSpecimen = getPerson().getExtendedInformations().getSignatureSpecimen();
		}
		if(getPerson().getJobInformations()!=null){
			jobFunction = getPerson().getJobInformations().getFunction();
			jobTitle = getPerson().getJobInformations().getTitle();
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
	public static abstract class AbstractDefault<PERSON extends Person> extends AbstractPersonEditFormModel<PERSON>  implements Serializable {

		private static final long serialVersionUID = -3897201743383535836L;
	
		@Override
		protected Person getPerson() {
			return identifiable;
		}
		
		/**/
		
		@Getter @Setter
		public static class Default<PERSON extends Person> extends AbstractDefault<PERSON>  implements Serializable {

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
	
	public static final String FIELD_BLOOD_GROUP = "bloodGroup";
	public static final String FIELD_ALLERGIC_REACTION_RESPONSE = "allergicReactionResponse";
	public static final String FIELD_ALLERGIC_REACTION_TYPE = "allergicReactionType";
	public static final String FIELD_ALLERGIES = "allergies";
	public static final String FIELD_MEDICATIONS = "medications";
	public static final String FIELD_OTHER_MEDICAL_INFORMATIONS = "otherMedicalInformations";
	
	public static final String FIELD_JOB_TITLE = "jobTitle";
	public static final String FIELD_JOB_FUNCTION = "jobFunction";
	public static final String FIELD_COMPANY = "jobCompany";
	public static final String FIELD_JOB_CONTACTS = "jobContacts";
	
	/**/
	
}