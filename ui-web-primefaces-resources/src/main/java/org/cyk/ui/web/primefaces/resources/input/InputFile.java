package org.cyk.ui.web.primefaces.resources.input;

import org.primefaces.model.UploadedFile;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class InputFile extends org.cyk.utility.common.userinterface.input.InputFile {
	private static final long serialVersionUID = 1L;

	private UploadedFile file;
	
}
