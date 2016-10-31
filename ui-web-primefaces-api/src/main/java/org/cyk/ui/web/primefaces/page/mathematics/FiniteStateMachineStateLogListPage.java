package org.cyk.ui.web.primefaces.page.mathematics;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineStateLog;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudManyPage;

@Named @ViewScoped @Getter @Setter
public class FiniteStateMachineStateLogListPage extends AbstractCrudManyPage<FiniteStateMachineStateLog> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		rowAdapter.setOpenable(Boolean.TRUE);
		rowAdapter.setUpdatable(Boolean.TRUE);
		table.setShowHeader(Boolean.FALSE);
		table.setShowToolBar(Boolean.TRUE);
		table.setShowOpenCommand(Boolean.TRUE);
	}
	
}
