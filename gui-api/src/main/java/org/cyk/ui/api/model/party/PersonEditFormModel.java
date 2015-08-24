package org.cyk.ui.api.model.party;

import java.io.Serializable;
import java.util.Date;

import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.geography.Location;
import org.cyk.system.root.model.party.person.MaritalStatus;
import org.cyk.system.root.model.party.person.Person;
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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class PersonEditFormModel extends AbstractPartyEditFormModel<Person>  implements Serializable {

	private static final long serialVersionUID = -3897201743383535836L;

	public static final String FIELD_LAST_NAME = "lastName";
	public static final String FIELD_BIRTH_DATE = "birthDate";
	public static final String FIELD_BIRTH_LOCATION = "birthLocation";
	public static final String FIELD_SEX = "sex";
	public static final String FIELD_MARITAL_STATUS = "maritalStatus";
	public static final String FIELD_NATIONALITY = "nationality";
	
	@Input @InputText private String lastName;
	@Input @InputCalendar private Date birthDate;
	@Input @InputText private String birthLocation;
	@Input @InputChoice @InputOneChoice @InputOneCombo private Sex sex;
	@Input @InputChoice @InputOneChoice @InputOneCombo private MaritalStatus maritalStatus;
	@Input @InputChoice @InputOneChoice @InputOneCombo private Locality nationality;
	
	@Override
	public void write() {
		super.write();
		if(identifiable.getBirthLocation()==null) 
			identifiable.setBirthLocation(new Location());
		identifiable.getBirthLocation().setComment(birthLocation);
	}
	
	@Override
	public void read() {
		super.read();
		if(identifiable.getBirthLocation()!=null)
			birthLocation = identifiable.getBirthLocation().toString();
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
	
	
		
}
