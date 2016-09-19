package org.cyk.ui.web.primefaces.page.file;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URI;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.impl.file.FileContentDetails;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.File;
import org.cyk.ui.api.model.AbstractBusinessIdentifiedEditFormModel;
import org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputFile;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.annotation.user.interfaces.InputTextarea;

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
				if(inject(FileBusiness.class).isText(getFile()))
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

}
