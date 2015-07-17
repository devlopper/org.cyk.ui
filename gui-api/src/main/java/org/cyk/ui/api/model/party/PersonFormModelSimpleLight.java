package org.cyk.ui.api.model.party;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.Sex;
import org.cyk.ui.api.model.geography.ContactCollectionFormModelSimpleLight;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputCalendar;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.annotation.user.interfaces.Sequence;
import org.cyk.utility.common.annotation.user.interfaces.Sequence.Direction;

@Getter @Setter @NoArgsConstructor
public class PersonFormModelSimpleLight extends AbstractPartyFormModel<Person>  implements Serializable {

	private static final long serialVersionUID = -3897201743383535836L;

	@Input @InputText
	private String lastName;
	 
	@Input @InputCalendar 
	private Date birthDate;
		
	@Input @InputChoice @InputOneChoice @InputOneCombo
	private Sex sex;
			
	/**/
	
	/*
	@Override @Sequence(direction=Direction.AFTER,field="sex")
	public File getImage() { 
		return super.getImage();
	}
	*/
	
	@Override @Sequence(direction=Direction.AFTER,field="sex")
	public ContactCollectionFormModelSimpleLight getContactCollectionFormModel() {
		return super.getContactCollectionFormModel();
	}
	
	
		
}
