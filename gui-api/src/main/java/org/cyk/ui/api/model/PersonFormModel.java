package org.cyk.ui.api.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.geography.Location;
import org.cyk.system.root.model.party.person.MaritalStatus;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.Sex;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputCalendar;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.annotation.user.interfaces.Sequence;
import org.cyk.utility.common.annotation.user.interfaces.Sequence.Direction;

@Getter @Setter @NoArgsConstructor //@SequenceOverride(field="contactCollectionFormModel",value=@Sequence(direction=Direction.AFTER,field="image"))
public class PersonFormModel extends AbstractPartyFormModel<Person>  implements Serializable {

	private static final long serialVersionUID = -3897201743383535836L;

	@Input @InputText
	private String lastName;
	 
	@Input @InputCalendar 
	private Date birthDate;
	
	@Input @InputText 
    private String birthLocation;
	
	@Input @InputChoice @InputOneChoice @InputOneCombo
	private Sex sex;
	
	@Input @InputChoice @InputOneChoice @InputOneCombo
	private MaritalStatus maritalStatus;
	
	@Input @InputChoice @InputOneChoice @InputOneCombo
	private Locality nationality;
	
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
	
	@Override @Sequence(direction=Direction.AFTER,field="nationality")
	public File getImage() {
		return super.getImage();
	}
	
	@Override @Sequence(direction=Direction.AFTER,field="image")
	public SimpleContactCollectionFormModel getContactCollectionFormModel() {
		return super.getContactCollectionFormModel();
	}
		
}
