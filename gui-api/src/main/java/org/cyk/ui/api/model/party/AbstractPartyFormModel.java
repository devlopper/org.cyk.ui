package org.cyk.ui.api.model.party;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.party.Party;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.api.model.geography.ContactCollectionFormModel;
import org.cyk.utility.common.FileExtensionGroup;
import org.cyk.utility.common.annotation.user.interfaces.FileExtensions;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs.Layout;
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
	
	@OutputSeperator(label=@Text(value="field.contacts")) 
	@IncludeInputs(layout=Layout.VERTICAL) 
	protected ContactCollectionFormModel contactCollectionFormModel = new ContactCollectionFormModel();
	
	@Override
	public void setIdentifiable(PARTY identifiable) {
		super.setIdentifiable(identifiable);
		contactCollectionFormModel.setIdentifiable(identifiable.getContactCollection());
	}
}
