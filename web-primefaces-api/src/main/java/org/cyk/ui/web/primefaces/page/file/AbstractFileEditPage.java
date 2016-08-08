package org.cyk.ui.web.primefaces.page.file;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URI;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.business.impl.file.FileContentDetails;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.File;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.data.collector.form.FormConfiguration;
import org.cyk.ui.api.model.AbstractBusinessIdentifiedEditFormModel;
import org.cyk.ui.web.primefaces.page.AbstractBusinessEntityFormOnePage;
import org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputFile;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.annotation.user.interfaces.InputTextarea;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractFileEditPage<FILE extends AbstractIdentifiable> extends AbstractCrudOnePage<FILE> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	protected abstract File getFile();
	
	public static abstract class AbstractForm<FILE extends AbstractIdentifiable> extends AbstractBusinessIdentifiedEditFormModel<FILE> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Input @InputFile protected File file;
		
		@Input @InputText protected String extension;
		
		@Input @InputTextarea protected String content;
		
		/*@Input @InputText*/ protected String uniformResourceLocator;
		
		@Input @InputText protected String mime;
		
		protected abstract File getFile();
		
		@Override
		public void read() {
			super.read();
			extension = getFile().getExtension();
			uniformResourceLocator = getFile().getUri() == null ? Constant.EMPTY_STRING : getFile().getUri().toString();
			mime = getFile().getMime();
			if( FileContentDetails.LABEL_IDENTIFIER.equals( ((AbstractPrimefacesPage)window).getSelectedTabId() )){
				if(RootBusinessLayer.getInstance().getFileBusiness().isText(getFile()))
					try {
						content = new String(getFile().getBytes(),Constant.ENCODING_UTF8);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
			}
			
			
		}
		
		@Override
		public void write() {
			super.write();
			if(StringUtils.isNotBlank(extension) || file == null)
				getFile().setExtension(extension);
			else
				getFile().setExtension(file.getExtension());
			
			if(StringUtils.isNotBlank(uniformResourceLocator) || file == null)
				getFile().setUri(URI.create(uniformResourceLocator));
			else
				getFile().setUri(file.getUri());
			
			if(StringUtils.isNotBlank(mime) || file == null)
				getFile().setMime(mime);
			else
				getFile().setMime(file.getMime());
			
			
			if(StringUtils.isNotBlank(content) || file == null){
				if( FileContentDetails.LABEL_IDENTIFIER.equals( ((AbstractPrimefacesPage)window).getSelectedTabId() ))
					try {
						getFile().setBytes(content.getBytes(Constant.ENCODING_UTF8));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
			}else
				getFile().setBytes(file.getBytes());
			
		}
		
		/**/
		
		public static final String FIELD_FILE = "file";
		public static final String FIELD_EXTENSION = "extension";
		public static final String FIELD_CONTENT = "content";
		public static final String FIELD_UNIFORM_RESOURCE_LOCATOR = "uniformResourceLocator";
		public static final String FIELD_MIME = "mime";
	}
	
	/**/
	
	public static class AbstractPageAdapter<FILE extends AbstractIdentifiable> extends AbstractBusinessEntityFormOnePage.BusinessEntityFormOnePageListener.Adapter.Default<FILE> implements Serializable {

		private static final long serialVersionUID = 4370361826462886031L;

		public AbstractPageAdapter(Class<FILE> entityTypeClass) {
			super(entityTypeClass);
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
