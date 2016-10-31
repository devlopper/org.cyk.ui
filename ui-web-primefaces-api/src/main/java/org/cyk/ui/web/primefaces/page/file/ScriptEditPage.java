package org.cyk.ui.web.primefaces.page.file;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.Script;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.FileExtensionGroup;
import org.cyk.utility.common.annotation.user.interfaces.FileExtensions;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputFile;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class ScriptEditPage extends AbstractCrudOnePage<Script> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	public static class Form extends AbstractFormModel<Script> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Input @InputFile(extensions=@FileExtensions(groups=FileExtensionGroup.TEXT))
		private File file;
		
		@Override
		public void read() {
			super.read();
			
		}
	}

}
