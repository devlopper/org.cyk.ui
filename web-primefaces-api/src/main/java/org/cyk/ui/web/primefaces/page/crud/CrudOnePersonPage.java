package org.cyk.ui.web.primefaces.page.crud;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.party.person.Person;

@Named @ViewScoped @Getter @Setter
public class CrudOnePersonPage extends AbstractCrudOnePage<Person> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
}
