package org.cyk.ui.web.primefaces.test.form;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.party.person.MaritalStatus;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.Sex;
import org.cyk.ui.api.model.AbstractPartyFormModel;
import org.cyk.ui.api.model.SimpleContactCollectionFormModel;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputEditor;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.annotation.user.interfaces.Sequence;
import org.cyk.utility.common.annotation.user.interfaces.Sequence.Direction;

@Getter @Setter @NoArgsConstructor //@SequenceOverride(field="contactCollectionFormModel",value=@Sequence(direction=Direction.AFTER,field="image"))
public class PersonFormModel3 extends AbstractPartyFormModel<Person>  implements Serializable {

	private static final long serialVersionUID = -3897201743383535836L;

	@Input @InputText
	private String names;
	 
	@Input @InputEditor 
    private String birth;
	
	@Input @InputChoice @InputOneChoice @InputOneCombo
	private Sex sex;
	
	@Input @InputChoice @InputOneChoice @InputOneCombo
	private MaritalStatus maritalStatus;
	
	@Override
	public void read() {
		super.read();
		names = identifiable.getName();
		birth = identifiable.getBirthDate()+" | "+identifiable.getBirthLocation();
	}
	
	/**/
	
	@Override @Sequence(direction=Direction.AFTER,field="maritalStatus")
	public File getImage() {
		return super.getImage();
	}
	
	@Override @Sequence(direction=Direction.AFTER,field="image")
	public SimpleContactCollectionFormModel getContactCollectionFormModel() {
		return super.getContactCollectionFormModel();
	}
		
}
