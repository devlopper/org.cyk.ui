package org.cyk.ui.api.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.geography.Location;
import org.cyk.system.root.model.party.person.MaritalStatus;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.Sex;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.utility.common.annotation.user.interfaces.Binding;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs.Layout;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputCalendar;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.annotation.user.interfaces.OutputSeperator;
import org.cyk.utility.common.annotation.user.interfaces.Sequence;
import org.cyk.utility.common.annotation.user.interfaces.Sequence.Direction;
import org.cyk.utility.common.annotation.user.interfaces.Text;

@Getter @Setter @NoArgsConstructor
public class PersonFormModel extends AbstractFormModel<Person>  implements Serializable {

	private static final long serialVersionUID = -3897201743383535836L;

	@Input @InputText @Binding(field="name")
	@NotNull(groups=org.cyk.utility.common.validation.Client.class)
	private String firstName;
	
	@Input @InputText
	
	private String lastName;
	 
	@Input @InputCalendar 
	@Valid
	@Sequence(direction=Direction.AFTER,field="sex")
	private Date birthDate;
	
	@Input @InputText 
	@Valid
    private String birthLocation;
	
	@Input @InputChoice @InputOneChoice @InputOneCombo
	private Sex sex;
	
	@Input @InputChoice @InputOneChoice @InputOneCombo
	private MaritalStatus maritalStatus;
	
	@Input @InputChoice @InputOneChoice @InputOneCombo
	private Locality nationality;
	
	@OutputSeperator(label=@Text(value="field.contacts")) @IncludeInputs(layout=Layout.VERTICAL) 
	private AbstractFormModel<ContactCollection> contactCollectionFormModel = new SimpleContactCollectionFormModel();
	
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
		//birthLocation = identifiable.getBirthLocation().toString();
	}

}
