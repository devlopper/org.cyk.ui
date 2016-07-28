package org.cyk.ui.web.primefaces.page.security;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.impl.security.LicenseDetails;
import org.cyk.system.root.model.security.License;
import org.cyk.ui.web.primefaces.data.collector.form.FormOneData;
import org.cyk.ui.web.primefaces.page.crud.AbstractConsultPage;

@Named @ViewScoped @Getter @Setter
public class LicenseConsultPage extends AbstractConsultPage<License> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	private FormOneData<LicenseDetails> details;
	
	@Override
	protected void consultInitialisation() {
		super.consultInitialisation();
		
		details = createDetailsForm(LicenseDetails.class, identifiable, new DetailsConfigurationListener.Form.Adapter<License,LicenseDetails>(License.class, LicenseDetails.class){
			private static final long serialVersionUID = 1L;
			@Override
			public Boolean getEnabledInDefaultTab() {
				return Boolean.TRUE;
			}
		});
		
	}
	
}
