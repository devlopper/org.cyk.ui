package org.cyk.ui.web.primefaces.page.file;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.impl.file.FileIdentifiableGlobalIdentifierDetails;
import org.cyk.system.root.model.file.FileIdentifiableGlobalIdentifier;
import org.cyk.ui.web.primefaces.data.collector.form.FormOneData;
import org.cyk.ui.web.primefaces.page.crud.AbstractConsultPage;

@Named @ViewScoped @Getter @Setter
public class FileIdentifiableGlobalIdentifierConsultPage extends AbstractConsultPage<FileIdentifiableGlobalIdentifier> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	private FormOneData<FileIdentifiableGlobalIdentifierDetails> details;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		
		details = createDetailsForm(FileIdentifiableGlobalIdentifierDetails.class, identifiable, new DetailsConfigurationListener.Form.Adapter<FileIdentifiableGlobalIdentifier,FileIdentifiableGlobalIdentifierDetails>(FileIdentifiableGlobalIdentifier.class, FileIdentifiableGlobalIdentifierDetails.class){
			private static final long serialVersionUID = 1L;
			@Override
			public Boolean getEnabledInDefaultTab() {
				return Boolean.TRUE;
			}
		});
		
	}
	
}
