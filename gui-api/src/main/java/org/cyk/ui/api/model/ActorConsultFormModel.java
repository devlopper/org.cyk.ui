package org.cyk.ui.api.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.utility.common.FileExtensionGroup;
import org.cyk.utility.common.annotation.user.interfaces.FileExtensions;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputFile;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Getter @Setter @NoArgsConstructor
public class ActorConsultFormModel extends AbstractFormModel<AbstractActor>  implements Serializable {

	private static final long serialVersionUID = -3897201743383535836L;

	@Input @InputFile (extensions=@FileExtensions(groups=FileExtensionGroup.IMAGE))
	private File photo;
	@Input @InputText private String firstName,lastName,registrationCode;
		
	@Override
	public void read() {
		registrationCode = identifiable.getRegistration().getCode();
		photo = identifiable.getPerson().getImage();
		firstName = identifiable.getPerson().getName();
		lastName = identifiable.getPerson().getLastName();
	}
		
}
