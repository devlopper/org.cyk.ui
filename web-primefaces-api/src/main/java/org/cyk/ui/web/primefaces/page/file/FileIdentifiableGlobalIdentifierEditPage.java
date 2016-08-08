package org.cyk.ui.web.primefaces.page.file;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.FileIdentifiableGlobalIdentifier;
import org.cyk.ui.web.primefaces.globalidentification.AbstractJoinGlobalIdentifierEditPage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class FileIdentifiableGlobalIdentifierEditPage extends AbstractJoinGlobalIdentifierEditPage<FileIdentifiableGlobalIdentifier> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	@Getter @Setter
	public static class Form extends AbstractForm<FileIdentifiableGlobalIdentifier> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		/*@Input @InputFile @NotNull */protected File externalFile;
		
		@Input @InputChoice(load=true) @InputOneChoice @InputOneCombo @NotNull private File internalFile;
		
		@Override
		public void read() {
			super.read();
			internalFile = identifiable.getFile();
		}
		
		@Override
		public void write() {
			super.write();
			if(internalFile!=null)
				identifiable.setFile(internalFile);
			else if(externalFile!=null)
				identifiable.setFile(externalFile);
			
		}
		
		public static final String FIELD_EXTERNAL_FILE = "externalFile";
		public static final String FIELD_INTERNAL_FILE = "internalFile";
		
	}
	
}
