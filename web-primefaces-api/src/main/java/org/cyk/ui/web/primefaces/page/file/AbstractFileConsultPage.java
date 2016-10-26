package org.cyk.ui.web.primefaces.page.file;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.impl.file.FileContentDetails;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.ui.web.primefaces.Exporter;
import org.cyk.ui.web.primefaces.data.collector.form.FormOneData;
import org.cyk.ui.web.primefaces.page.crud.AbstractConsultPage;

@Getter @Setter
public abstract class AbstractFileConsultPage<FILE extends AbstractIdentifiable> extends AbstractConsultPage<FILE> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	protected Exporter exporter = new Exporter();
	protected FormOneData<FileContentDetails> contentDetails;
	
	protected abstract File getFile();
	
	//@SuppressWarnings("unchecked")
	@Override
	protected void consultInitialisation() {
		super.consultInitialisation();
		
		exporter.setFileUrlFromRequestParameters(new Object[]{UniformResourceLocatorParameter.IDENTIFIABLE,getFile()
				,UniformResourceLocatorParameter.FILE_EXTENSION,getFile().getExtension()});
		exporter.setType(getFile().getMime());
		/*
		@SuppressWarnings("rawtypes")
		DetailsConfigurationListener.Form.Adapter adapter = getDetailsConfiguration(FileContentDetails.class).getFormConfigurationAdapter(File.class, FileContentDetails.class);
		adapter.setTitleId(FileContentDetails.LABEL_IDENTIFIER);
		adapter.setTabId(FileContentDetails.LABEL_IDENTIFIER);
		contentDetails = createDetailsForm(FileContentDetails.class, identifiable, adapter);
		*/
	}
	
	/**/
	
	
}
