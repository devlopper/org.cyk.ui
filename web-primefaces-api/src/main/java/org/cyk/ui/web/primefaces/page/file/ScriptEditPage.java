package org.cyk.ui.web.primefaces.page.file;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.impl.file.FileContentDetails;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.Script;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.api.data.collector.form.FormConfiguration;
import org.cyk.ui.web.primefaces.page.AbstractBusinessEntityFormOnePage;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.ui.web.primefaces.page.file.AbstractFileEditPage.AbstractForm;
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
	
	public static class Adapter extends AbstractBusinessEntityFormOnePage.BusinessEntityFormOnePageListener.Adapter.Default<Script> implements Serializable {

		private static final long serialVersionUID = 4370361826462886031L;

		public Adapter() {
			super(Script.class);
			FormConfiguration configuration = createFormConfiguration(Crud.CREATE, FormConfiguration.TYPE_INPUT_SET_SMALLEST);
			configuration.addFieldNames(AbstractForm.FIELD_CODE,AbstractForm.FIELD_NAME);
			configuration.addRequiredFieldNames(AbstractForm.FIELD_FILE);
			
			configuration = createFormConfiguration(Crud.UPDATE, UIManager.getInstance().businessEntityInfos(entityTypeClass).getUserInterface().getLabelId());
			configuration.addFieldNames(AbstractForm.FIELD_CODE,AbstractForm.FIELD_NAME,AbstractForm.FIELD_DESCRIPTION,AbstractForm.FIELD_EXTENSION,AbstractForm.FIELD_UNIFORM_RESOURCE_LOCATOR,AbstractForm.FIELD_MIME);
			configuration.addRequiredFieldNames(AbstractForm.FIELD_FILE);
			
			configuration = createFormConfiguration(Crud.UPDATE, FileContentDetails.LABEL_IDENTIFIER);
			configuration.addRequiredFieldNames(AbstractForm.FIELD_CONTENT);
			
			configuration = createFormConfiguration(Crud.DELETE);
			configuration.addFieldNames(AbstractForm.FIELD_CODE,AbstractForm.FIELD_NAME);
		}
		
	}

}
