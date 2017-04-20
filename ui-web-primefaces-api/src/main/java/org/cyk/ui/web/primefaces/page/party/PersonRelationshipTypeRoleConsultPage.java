package org.cyk.ui.web.primefaces.page.party;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.party.person.PersonRelationshipTypeRole;
import org.cyk.ui.web.primefaces.page.crud.AbstractConsultPage;

@Named @ViewScoped @Getter @Setter
public class PersonRelationshipTypeRoleConsultPage extends AbstractConsultPage<PersonRelationshipTypeRole> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	
}
