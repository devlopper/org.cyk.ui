package org.cyk.ui.web.primefaces.page.file;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.file.File;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputTextarea;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class FileEditContentPage extends AbstractFileEditPage<File> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	private Boolean contentEditSupported = Boolean.FALSE;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		if(contentEditSupported = Boolean.TRUE.equals(RootBusinessLayer.getInstance().getFileBusiness().isText(identifiable))){
			//((Form)form.getData()).setContent(new String(identifiable.getBytes()));
			System.out.println("FileEditContentPage.initialisation()");
		}else{
			
		}
		//form.setRendered(contentEditSupported);
		if(Boolean.FALSE.equals(contentEditSupported))
			text = text("file.content.edit.notsupported");
	}
	
	@Override
	protected File getFile() {
		return identifiable;
	}
	
	
	
	@Getter @Setter
	public static class Form extends AbstractFormModel<File> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Input @InputTextarea private String content;
		
	}
	
	/**/
	
	public static class Adapter extends AbstractFileEditPage.AbstractPageAdapter<File> implements Serializable {

		private static final long serialVersionUID = 4370361826462886031L;

		public Adapter() {
			super(File.class);
			
		}
	}

}
