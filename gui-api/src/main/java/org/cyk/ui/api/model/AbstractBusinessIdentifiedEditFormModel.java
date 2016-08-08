package org.cyk.ui.api.model;

import java.io.Serializable;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.File;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.utility.common.FileExtensionGroup;
import org.cyk.utility.common.annotation.user.interfaces.FileExtensions;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputFile;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public abstract class AbstractBusinessIdentifiedEditFormModel<IDENTIFIABLE extends AbstractIdentifiable> extends AbstractFormModel<IDENTIFIABLE> implements Serializable {

	private static final long serialVersionUID = -3897201743383535836L;
	
	//@OutputSeperator(label=@Text(value="field.identity")) 
	
	@Input @InputFile(extensions=@FileExtensions(groups=FileExtensionGroup.IMAGE)) protected File image;
	
	@Input @InputText protected String code;
	
	@Input @InputText protected String name;
	
	@Input @InputText protected String abbreviation;
	
	@Input @InputText protected String description;
	
	@Override
	public void read() {
		super.read();
		code = identifiable.getCode();
		image = identifiable.getImage();
		name = identifiable.getName();
		abbreviation = identifiable.getAbbreviation();
		description = identifiable.getDescription();
	}
	
	protected void setBusinessValues(AbstractIdentifiable identifiable){
		identifiable.getGlobalIdentifierCreateIfNull().setCode(code);
		identifiable.setName(name);
		identifiable.setImage(image);
		identifiable.setAbbreviation(abbreviation);
		identifiable.setDescription(description);
	}
	
	@Override
	public void write() {
		super.write();
		setBusinessValues(identifiable);
	}
	
	/**/
	public static final String FIELD_IMAGE = "image";
	public static final String FIELD_CODE = "code";
	public static final String FIELD_NAME = "name";
	public static final String FIELD_ABBREVIATION = "abbreviation";
	public static final String FIELD_DESCRIPTION = "description";
}
