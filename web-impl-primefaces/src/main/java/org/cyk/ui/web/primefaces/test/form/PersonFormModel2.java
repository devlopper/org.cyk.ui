package org.cyk.ui.web.primefaces.test.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.Sex;
import org.cyk.ui.api.model.AbstractPartyFormModel;
import org.cyk.ui.api.model.SimpleContactCollectionFormModel;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputManyCheck;
import org.cyk.utility.common.annotation.user.interfaces.InputManyChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputManyPickList;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.annotation.user.interfaces.Sequence;
import org.cyk.utility.common.annotation.user.interfaces.Sequence.Direction;

@Getter @Setter @NoArgsConstructor //@SequenceOverride(field="contactCollectionFormModel",value=@Sequence(direction=Direction.AFTER,field="image"))
public class PersonFormModel2 extends AbstractPartyFormModel<Person>  implements Serializable {

	private static final long serialVersionUID = -3897201743383535836L;

	@Input @InputText
	private String lastName;
	
	@Input @InputChoice @InputManyChoice @InputManyCheck
	private List<String> sex1;
	
	@Input @InputChoice @InputManyChoice @InputManyPickList
	private List<Sex> sex2;
	 
	@Input @InputChoice @InputOneChoice @InputOneCombo
	private Locality nationality;
	
	/**/
	
	{	sex1 = new ArrayList<>();
		sex1.add("Sex Value 1");
		sex1.add("Sex Value 2");
		sex1.add("Sex Value 3");
	}
	
	@Override @Sequence(direction=Direction.AFTER,field="nationality")
	public File getImage() {
		return super.getImage();
	}
	
	@Override @Sequence(direction=Direction.AFTER,field="image")
	public SimpleContactCollectionFormModel getContactCollectionFormModel() {
		return super.getContactCollectionFormModel();
	}
		
}
