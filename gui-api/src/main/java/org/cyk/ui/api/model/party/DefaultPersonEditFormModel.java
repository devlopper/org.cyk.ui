package org.cyk.ui.api.model.party;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.geography.Country;
import org.cyk.system.root.model.geography.Location;
import org.cyk.system.root.model.party.person.BloodGroup;
import org.cyk.system.root.model.party.person.JobFunction;
import org.cyk.system.root.model.party.person.JobTitle;
import org.cyk.system.root.model.party.person.MaritalStatus;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonTitle;
import org.cyk.system.root.model.party.person.Sex;
import org.cyk.ui.api.model.geography.ContactCollectionEditFormModel;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputCalendar;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
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
	@Input @InputCalendar private Date birthDate;
	@Input @InputText private String birthLocation;
	@Input @InputChoice @InputOneChoice @InputOneCombo private Sex sex;
	@Input @InputChoice @InputOneChoice @InputOneCombo private MaritalStatus maritalStatus;
	@Input @InputChoice @InputOneChoice @InputOneCombo private Country nationality;
	
	@Input @InputText private String company;
	@Input @InputChoice @InputOneChoice @InputOneCombo private JobTitle jobTitle;
	@Input @InputChoice @InputOneChoice @InputOneCombo private JobFunction jobFunction;
	
	@Input @InputChoice @InputOneChoice @InputOneCombo private BloodGroup bloodGroup;
	
	@Override
	public void write() {
		super.write();
		
		if(identifiable.getExtendedInformations()!=null){
			identifiable.getExtendedInformations().setMaritalStatus(maritalStatus);
			if(identifiable.getExtendedInformations().getBirthLocation()==null) 
				identifiable.getExtendedInformations().setBirthLocation(new Location());
			identifiable.getExtendedInformations().getBirthLocation().setComment(birthLocation);
		}
	}
	
	@Override
	public void read() {
		super.read();
		if(identifiable.getExtendedInformations()!=null){
			if(identifiable.getExtendedInformations().getBirthLocation()!=null)
				birthLocation = identifiable.getExtendedInformations().getBirthLocation().toString();
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
	public static final String FIELD_SEX = "sex";
	public static final String FIELD_MARITAL_STATUS = "maritalStatus";
	public static final String FIELD_NATIONALITY = "nationality";
	public static final String FIELD_BLOOD_GROUP = "bloodGroup";
	public static final String FIELD_TITLE = "title";
	
	public static final String FIELD_JOB_TITLE = "jobTitle";
	public static final String FIELD_JOB_FUNCTION = "jobFunction";
	public static final String FIELD_COMPANY = "company";
}
