package org.cyk.ui.web.primefaces.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.utility.common.FileExtensionGroup;
import org.cyk.utility.common.annotation.user.interfaces.FileExtensions;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputFile;
import org.primefaces.model.UploadedFile;

@Getter @Setter
public class PersonFormModel extends org.cyk.ui.api.model.PersonFormModel implements Serializable {

	private static final long serialVersionUID = -3328823824725030136L;

	@Input @InputFile(extensions=@FileExtensions(groups=FileExtensionGroup.IMAGE))
	private UploadedFile photo;
	
}
