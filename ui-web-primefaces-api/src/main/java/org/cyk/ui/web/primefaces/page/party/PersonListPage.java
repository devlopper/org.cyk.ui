package org.cyk.ui.web.primefaces.page.party;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.party.person.Person;
import org.cyk.ui.api.model.table.AbstractTable.RenderType;

//@Named @ViewScoped 
@Getter @Setter
public class PersonListPage extends AbstractPersonListPage<Person> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		table.setRenderType(RenderType.TABLE);
	}
	
}
