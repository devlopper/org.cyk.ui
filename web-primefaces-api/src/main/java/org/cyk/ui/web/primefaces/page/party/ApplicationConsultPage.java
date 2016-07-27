package org.cyk.ui.web.primefaces.page.party;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.impl.party.ApplicationDetails;
import org.cyk.system.root.model.party.Application;
import org.cyk.ui.web.primefaces.data.collector.form.FormOneData;
import org.cyk.ui.web.primefaces.page.crud.AbstractConsultPage;

@Named @ViewScoped @Getter @Setter
public class ApplicationConsultPage extends AbstractConsultPage<Application> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	private FormOneData<ApplicationDetails> details;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		details = createDetailsForm(ApplicationDetails.class, identifiable, new DetailsConfigurationListener.Form.Adapter<Application,ApplicationDetails>(Application.class, ApplicationDetails.class){
			private static final long serialVersionUID = 1L;
			@Override
			public Boolean getEnabledInDefaultTab() {
				return Boolean.TRUE;
			}
		});	
		
		
	}
	
}
