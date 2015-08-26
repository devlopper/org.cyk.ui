package org.cyk.ui.api.model.party;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.ui.api.model.geography.ContactCollectionReadFormModel;
import org.cyk.utility.common.FileExtensionGroup;
import org.cyk.utility.common.annotation.user.interfaces.FileExtensions;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs.Layout;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputFile;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.annotation.user.interfaces.OutputSeperator;
import org.cyk.utility.common.annotation.user.interfaces.Text;
import org.cyk.utility.common.cdi.AbstractBean;

@Getter @Setter
public abstract class AbstractPersonReadFormModel<ENTITY extends AbstractIdentifiable> extends AbstractBean  implements Serializable {

	private static final long serialVersionUID = -3897201743383535836L;

	public static final String FIELD_PHOTO = "photo";
	public static final String FIELD_FIRST_NAME = "firstName";
	public static final String FIELD_LAST_NAME = "lastName";
	
	private ENTITY identifiable;
	@Input @InputFile (extensions=@FileExtensions(groups=FileExtensionGroup.IMAGE)) private File photo;
	
	@Input @InputText protected String firstName,lastName;
	
	@OutputSeperator(label=@Text(value="field.contacts")) 
	@IncludeInputs(layout=Layout.VERTICAL) 
	protected ContactCollectionReadFormModel contactCollectionFormModel = new ContactCollectionReadFormModel();
	
	public AbstractPersonReadFormModel(ENTITY entity){
		identifiable = entity;
		photo = getPerson(entity).getImage();
		firstName = getPerson(entity).getName();
		lastName = getPerson(entity).getLastName();
		contactCollectionFormModel.setIdentifiable(getPerson(entity).getContactCollection());
		contactCollectionFormModel.read();
	}
	
	protected abstract Person getPerson(ENTITY entity);
		
}
