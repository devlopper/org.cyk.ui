package org.cyk.ui.api.model.party;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.party.Party;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.utility.common.FileExtensionGroup;
import org.cyk.utility.common.annotation.user.interfaces.FileExtensions;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputFile;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.annotation.user.interfaces.OutputSeperator;
import org.cyk.utility.common.annotation.user.interfaces.Text;

@Getter @Setter @NoArgsConstructor
public abstract class AbstractPartyFormModel<PARTY extends Party> extends AbstractFormModel<PARTY> implements Serializable {

	private static final long serialVersionUID = -3897201743383535836L;
	
	public static final String FIELD_NAME = "name";
	public static final String FIELD_IMAGE = "image";
	public static final String FIELD_CONTACT_COLLECTION_FORM_MODEL = "contactCollectionFormModel";
	
	@OutputSeperator(label=@Text(value="field.identity")) 
	@Input @InputText
	@NotNull(groups=org.cyk.utility.common.validation.Client.class)
	protected String name;
	
	@Input @InputFile(extensions=@FileExtensions(groups=FileExtensionGroup.IMAGE))
	protected File image;
	
	@Override
	public void read() {
		super.read();
		image = identifiable.getImage();
	}
	
	@Override
	public void write() {
		super.write();
		GlobalIdentifier globalIdentifier = identifiable.getGlobalIdentifierCreateIfNull();
		globalIdentifier.setImage(image);
	}
	
}
