package org.cyk.ui.api.model.party;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.party.Party;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.api.model.geography.ContactCollectionEditFormModel;
import org.cyk.utility.common.FileExtensionGroup;
import org.cyk.utility.common.annotation.user.interfaces.FileExtensions;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputFile;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs.Layout;
import org.cyk.utility.common.annotation.user.interfaces.OutputSeperator;
import org.cyk.utility.common.annotation.user.interfaces.Text;

@Getter @Setter @NoArgsConstructor
public abstract class AbstractPartyEditFormModel<PARTY extends AbstractIdentifiable> extends AbstractFormModel<PARTY> implements Serializable {

	private static final long serialVersionUID = -3897201743383535836L;
	
	//@OutputSeperator(label=@Text(value="field.identity")) 
	
	@Input @InputText
	@NotNull(groups=org.cyk.utility.common.validation.Client.class)
	protected String code;
	
	@Input @InputText
	@NotNull(groups=org.cyk.utility.common.validation.Client.class)
	protected String name;
	
	@Input @InputFile(extensions=@FileExtensions(groups=FileExtensionGroup.IMAGE))
	protected File image;
	
	@OutputSeperator(label=@Text(value="field.contacts")) 
	@IncludeInputs(layout=Layout.VERTICAL) 
	protected ContactCollectionEditFormModel contactCollection = new ContactCollectionEditFormModel();
	
	@Override
	public void setIdentifiable(PARTY identifiable) {
		super.setIdentifiable(identifiable);
		if(getParty().getContactCollection()==null)
			getParty().setContactCollection(new ContactCollection());
		contactCollection.setIdentifiable(getParty().getContactCollection());
	}
	
	protected abstract Party getParty();
	
	@Override
	public void read() {
		super.read();
		code = identifiable.getCode();
		image = identifiable.getImage();
		name = identifiable.getName();
	}
	
	@Override
	public void write() {
		super.write();
		GlobalIdentifier globalIdentifier = identifiable.getGlobalIdentifierCreateIfNull();
		globalIdentifier.setCode(code);
		globalIdentifier.setName(name);
		globalIdentifier.setImage(image);
		
	}
	
	/**/
	public static final String FIELD_CODE = "code";
	public static final String FIELD_NAME = "name";
	public static final String FIELD_IMAGE = "image";
	public static final String FIELD_CONTACT_COLLECTION = "contactCollection";

}
