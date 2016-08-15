package org.cyk.ui.web.primefaces.page.file;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.model.file.File;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class FileEditPage extends AbstractFileEditPage<File> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	@Override
	protected File getFile() {
		return identifiable;
	}
	
	public static class Form extends AbstractFileEditPage.AbstractForm<File> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
				
		@Override
		protected File getFile() {
			return identifiable;
		}
	}

}
