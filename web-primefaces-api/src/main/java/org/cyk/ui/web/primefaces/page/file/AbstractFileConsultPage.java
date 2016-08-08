package org.cyk.ui.web.primefaces.page.file;

import java.io.Serializable;

import org.cyk.system.root.business.impl.file.FileContentDetails;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.File;
import org.cyk.ui.web.primefaces.data.collector.form.FormOneData;
import org.cyk.ui.web.primefaces.page.crud.AbstractConsultPage;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractFileConsultPage<FILE extends AbstractIdentifiable> extends AbstractConsultPage<FILE> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	protected FormOneData<FileContentDetails> contentDetails;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void consultInitialisation() {
		super.consultInitialisation();
		
		@SuppressWarnings("rawtypes")
		DetailsConfigurationListener.Form.Adapter adapter = getDetailsConfiguration(FileContentDetails.class).getFormConfigurationAdapter(File.class, FileContentDetails.class);
		adapter.setTitleId(FileContentDetails.LABEL_IDENTIFIER);
		adapter.setTabId(FileContentDetails.LABEL_IDENTIFIER);
		contentDetails = createDetailsForm(FileContentDetails.class, identifiable, adapter);
		contentDetails.addControlSetListener(getDetailsConfiguration(FileContentDetails.class).getFormControlSetAdapter(File.class));
		
	} 
	
}
