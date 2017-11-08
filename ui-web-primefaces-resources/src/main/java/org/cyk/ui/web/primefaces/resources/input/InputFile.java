package org.cyk.ui.web.primefaces.resources.input;

import org.cyk.utility.common.helper.FileHelper;
import org.cyk.utility.common.helper.FileHelper.File;
import org.primefaces.model.UploadedFile;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class InputFile extends org.cyk.utility.common.userinterface.input.InputFile {
	private static final long serialVersionUID = 1L;

	private UploadedFile file;
	
	@Override
	public File getPreparedValue() {
		if(file==null)
			value = null;
		else {
			if(value==null)
				value = new File();
			value.setName(FileHelper.getInstance().getName(file.getFileName()));
			value.setExtension(FileHelper.getInstance().getExtension(file.getFileName()));
			value.setMime(file.getContentType());
			value.setBytes(file.getContents());
		}
		return value;
	}
	
}
