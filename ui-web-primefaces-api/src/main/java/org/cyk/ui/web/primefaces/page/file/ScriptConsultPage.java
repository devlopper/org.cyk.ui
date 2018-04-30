package org.cyk.ui.web.primefaces.page.file;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.business.api.language.programming.ScriptBusiness;
import org.cyk.system.root.business.impl.language.programming.ScriptDetails;
import org.cyk.system.root.model.language.programming.Script;
import org.cyk.ui.web.primefaces.page.crud.AbstractConsultPage;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class ScriptConsultPage extends AbstractConsultPage<Script> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	@Override
	protected void consultInitialisation() {
		super.consultInitialisation();
		inject(ScriptBusiness.class).evaluate(identifiable);
		((ScriptDetails)details.getData()).setReturned(identifiable.getReturned().toString());
	}
	
}
