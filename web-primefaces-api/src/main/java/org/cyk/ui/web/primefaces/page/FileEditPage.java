package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.file.File;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.FileExtensionGroup;
import org.cyk.utility.common.annotation.user.interfaces.FileExtensions;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputFile;

@Named @ViewScoped @Getter @Setter
public class FileEditPage extends AbstractCrudOnePage<File> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		contentTitle = identifiable.getExtension();
	}
	
	@Override
	protected void update() {
		//File file = ((Form)form.getData()).file;
		//System.out.println("FileEditPage.update()");
		//debug(form.findInputByFieldName("file").getValue());
		//RootBusinessLayer.getInstance().getFileBusiness().process(identifiable, arg1, arg2);
		//RootBusinessLayer.getInstance().getFileBusiness().update(identifiable, arg1, arg2);
	}
	
	public static class Form extends AbstractFormModel<File> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Input @InputFile(extensions=@FileExtensions(groups=FileExtensionGroup.IMAGE))
		protected File file;
		
		@Override
		public void read() {
			super.read();
			
		}
		
	}

}
