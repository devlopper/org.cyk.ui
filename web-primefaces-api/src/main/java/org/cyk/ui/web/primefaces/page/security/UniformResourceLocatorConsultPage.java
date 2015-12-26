package org.cyk.ui.web.primefaces.page.security;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.model.network.UniformResourceLocator;
import org.cyk.ui.api.model.security.UniformResourceLocatorDetails;
import org.cyk.ui.web.primefaces.data.collector.form.FormOneData;
import org.cyk.ui.web.primefaces.page.crud.AbstractConsultPage;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class UniformResourceLocatorConsultPage extends AbstractConsultPage<UniformResourceLocator> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	private FormOneData<UniformResourceLocatorDetails> details;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		
		details = createDetailsForm(UniformResourceLocatorDetails.class, identifiable, new DetailsConfigurationListener.Form.Adapter<UniformResourceLocator,UniformResourceLocatorDetails>(UniformResourceLocator.class, UniformResourceLocatorDetails.class){
			private static final long serialVersionUID = 1L;
			@Override
			public Boolean getEnabledInDefaultTab() {
				return Boolean.TRUE;
			}
		});
		
	}
	
}
