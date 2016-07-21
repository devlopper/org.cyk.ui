package org.cyk.ui.web.primefaces.page.nestedset;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.impl.pattern.tree.DataTreeIdentifiableGlobalIdentifierDetails;
import org.cyk.system.root.model.pattern.tree.DataTreeIdentifiableGlobalIdentifier;
import org.cyk.ui.web.primefaces.data.collector.form.FormOneData;
import org.cyk.ui.web.primefaces.page.crud.AbstractConsultPage;

@Named @ViewScoped @Getter @Setter
public class DataTreeIdentifiableGlobalIdentifierConsultPage extends AbstractConsultPage<DataTreeIdentifiableGlobalIdentifier> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	private FormOneData<DataTreeIdentifiableGlobalIdentifierDetails> details;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		
		details = createDetailsForm(DataTreeIdentifiableGlobalIdentifierDetails.class, identifiable, new DetailsConfigurationListener.Form.Adapter<DataTreeIdentifiableGlobalIdentifier,DataTreeIdentifiableGlobalIdentifierDetails>(DataTreeIdentifiableGlobalIdentifier.class, DataTreeIdentifiableGlobalIdentifierDetails.class){
			private static final long serialVersionUID = 1L;
			@Override
			public Boolean getEnabledInDefaultTab() {
				return Boolean.TRUE;
			}
		});
		
	}
	
}
