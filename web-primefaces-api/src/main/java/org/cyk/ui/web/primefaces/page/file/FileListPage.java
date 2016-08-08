package org.cyk.ui.web.primefaces.page.file;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.impl.file.FileDetails;
import org.cyk.system.root.model.file.File;
import org.cyk.ui.api.data.collector.form.FormConfiguration;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class FileListPage extends AbstractFileListPage<File> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	public static class Adapter extends AbstractPageAdapter<File> implements Serializable {

		private static final long serialVersionUID = 4370361826462886031L;

		public Adapter() {
			super(File.class);
			FormConfiguration configuration = getFormConfiguration(Crud.READ);
			configuration.addFieldNames(FileDetails.FIELD_EXTENSION);
		}
		
	}
	
}
