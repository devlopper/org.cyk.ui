package org.cyk.ui.web.primefaces.page.security;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.model.security.Role;
import org.cyk.ui.api.model.security.RoleDetails;
import org.cyk.ui.web.primefaces.data.collector.form.FormOneData;
import org.cyk.ui.web.primefaces.page.crud.AbstractConsultPage;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class RoleConsultPage extends AbstractConsultPage<Role> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	private FormOneData<RoleDetails> details;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		
		details = createDetailsForm(RoleDetails.class, identifiable, new DetailsConfigurationListener.Form.Adapter<Role,RoleDetails>(Role.class, RoleDetails.class){
			private static final long serialVersionUID = 1L;
			@Override
			public Boolean getEnabledInDefaultTab() {
				return Boolean.TRUE;
			}
		});
		
	}
	
}
