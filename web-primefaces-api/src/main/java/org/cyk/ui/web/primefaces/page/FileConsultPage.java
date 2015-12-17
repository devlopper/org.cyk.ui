package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.file.File;
import org.cyk.ui.web.primefaces.data.collector.form.FormOneData;
import org.cyk.ui.web.primefaces.page.crud.AbstractConsultPage;

@Named @ViewScoped @Getter @Setter
public class FileConsultPage extends AbstractConsultPage<File> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	private FormOneData<FileDetails> details;
	
	@Override
	protected void initialisation() {
		super.initialisation();

		details = createDetailsForm(FileDetails.class, identifiable, new DetailsConfigurationListener.Form.Adapter<File,FileDetails>(File.class, FileDetails.class){
			private static final long serialVersionUID = 1L;
			@Override
			public Boolean getEnabledInDefaultTab() {
				return Boolean.TRUE;
			}
		});
		
	}

}
