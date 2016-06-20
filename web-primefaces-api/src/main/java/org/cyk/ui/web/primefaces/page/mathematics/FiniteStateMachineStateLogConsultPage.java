package org.cyk.ui.web.primefaces.page.mathematics;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.impl.mathematics.FiniteStateMachineStateLogDetails;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineStateLog;
import org.cyk.ui.web.primefaces.data.collector.form.FormOneData;
import org.cyk.ui.web.primefaces.page.crud.AbstractConsultPage;

@Named @ViewScoped @Getter @Setter
public class FiniteStateMachineStateLogConsultPage extends AbstractConsultPage<FiniteStateMachineStateLog> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	private FormOneData<FiniteStateMachineStateLogDetails> details;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		details = createDetailsForm(FiniteStateMachineStateLogDetails.class, identifiable, new DetailsConfigurationListener.Form.Adapter<FiniteStateMachineStateLog,FiniteStateMachineStateLogDetails>(FiniteStateMachineStateLog.class, FiniteStateMachineStateLogDetails.class){
			private static final long serialVersionUID = 1L;
			@Override
			public Boolean getEnabledInDefaultTab() {
				return Boolean.TRUE;
			}
		});	
		
		
	}
	
}
