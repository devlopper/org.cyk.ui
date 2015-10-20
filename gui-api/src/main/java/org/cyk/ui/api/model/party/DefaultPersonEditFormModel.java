package org.cyk.ui.api.model.party;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.geography.Country;
import org.cyk.system.root.model.geography.Location;
import org.cyk.system.root.model.party.person.BloodGroup;
import org.cyk.system.root.model.party.person.JobFunction;
import org.cyk.system.root.model.party.person.JobTitle;
import org.cyk.system.root.model.party.person.MaritalStatus;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonExtendedInformations;
import org.cyk.system.root.model.party.person.PersonTitle;
import org.cyk.system.root.model.party.person.Sex;
import org.cyk.ui.api.model.geography.ContactCollectionEditFormModel;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputCalendar;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputEditor;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.annotation.user.interfaces.Sequence;
import org.cyk.utility.common.annotation.user.interfaces.Sequence.Direction;

@Getter @Setter @NoArgsConstructor
public class DefaultPersonEditFormModel extends AbstractPartyEditFormModel<Person>  implements Serializable {

	private static final long serialVersionUID = -3897201743383535836L;

	@Input @InputChoice @InputOneChoice @InputOneCombo private PersonTitle title;
	@Input @InputText private String lastName;
	@Input @InputText private String surname;
	@Input @InputCalendar private Date birthDate;
	@Input @InputText private String birthLocation;
	@Input @InputChoice @InputOneChoice @InputOneCombo private Sex sex;
	@Input @InputChoice @InputOneChoice @InputOneCombo private MaritalStatus maritalStatus;
	@Input @InputChoice @InputOneChoice @InputOneCombo private Country nationality;
	
	@Input @InputChoice @InputOneChoice @InputOneCombo private BloodGroup bloodGroup;
	//@Input @InputText private String allergicReactionResponse,allergicReactionType;
	@Input @InputEditor private String otherMedicalInformations;
	
	@Input @InputText private String company;
	@Input @InputChoice @InputOneChoice @InputOneCombo private JobTitle jobTitle;
	@Input @InputChoice @InputOneChoice @InputOneCombo private JobFunction jobFunction;
	
	@Override
	public void write() {
		super.write();
		//identifiable.setSurname(surname);
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
				location.setComment(null);
		}else{
			Location location = getExtendedInformations(Boolean.TRUE).getBirthLocation();
			if(location==null)
				getExtendedInformations(Boolean.TRUE).setBirthLocation(new Location(null,null,birthLocation));
			else
				location.setComment(birthLocation);
		}
		/*if(identifiable.getExtendedInformations()!=null){
			identifiable.getExtendedInformations().setMaritalStatus(maritalStatus);
			if(identifiable.getExtendedInformations().getBirthLocation()==null) 
				identifiable.getExtendedInformations().setBirthLocation(new Location());
			identifiable.getExtendedInformations().getBirthLocation().setComment(birthLocation);
		}*/
	}
	
	private PersonExtendedInformations getExtendedInformations(Boolean createIfNull){
		PersonExtendedInformations personExtendedInformations = identifiable.getExtendedInformations();
		if(personExtendedInformations==null)
			if(Boolean.TRUE.equals(createIfNull))
				personExtendedInformations = new PersonExtendedInformations(identifiable);
		identifiable.setExtendedInformations(personExtendedInformations);
		return personExtendedInformations;
	}
	
	@Override
	public void read() {
		super.read();
		if(identifiable.getExtendedInformations()!=null){
			if(identifiable.getExtendedInformations().getBirthLocation()!=null)
				birthLocation = identifiable.getExtendedInformations().getBirthLocation().getComment();
			title = identifiable.getExtendedInformations().getTitle();
			
		}
	}
	
	/**/
	
	@Override @Sequence(direction=Direction.AFTER,field=FIELD_NATIONALITY)
	public File getImage() {
		return super.getImage();
	}
	
	@Override @Sequence(direction=Direction.AFTER,field=FIELD_IMAGE)
	public ContactCollectionEditFormModel getContactCollectionFormModel() {
		return super.getContactCollectionFormModel();
	}
	
	/**/
	
	public static final String FIELD_LAST_NAME = "lastName";
	public static final String FIELD_BIRTH_DATE = "birthDate";
	public static final String FIELD_BIRTH_LOCATION = "birthLocation";
	public static final String FIELD_SURNAME = "surname";
	public static final String FIELD_SEX = "sex";
	public static final String FIELD_MARITAL_STATUS = "maritalStatus";
	public static final String FIELD_NATIONALITY = "nationality";
	public static final String FIELD_TITLE = "title";
	
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
}
