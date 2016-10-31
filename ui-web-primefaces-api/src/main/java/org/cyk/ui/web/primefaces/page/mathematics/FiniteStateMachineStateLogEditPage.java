package org.cyk.ui.web.primefaces.page.mathematics;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineStateLog;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;

@Getter @Setter
public class FiniteStateMachineStateLogEditPage extends AbstractCrudOnePage<FiniteStateMachineStateLog> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	@Getter @Setter
	public static class Form extends AbstractFormModel<FiniteStateMachineStateLog> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		
		
	}

}
