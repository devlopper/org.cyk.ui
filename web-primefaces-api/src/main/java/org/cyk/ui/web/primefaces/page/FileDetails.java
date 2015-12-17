package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.file.File;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

public class FileDetails extends AbstractOutputDetails<File> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputText private String uri,content,extension;
	
	public FileDetails(File file) {
		super(file);
		extension = file.getExtension();
	}
}