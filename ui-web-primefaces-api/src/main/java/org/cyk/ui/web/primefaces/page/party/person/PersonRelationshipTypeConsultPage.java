package org.cyk.ui.web.primefaces.page.party.person;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.business.impl.party.person.PersonRelationshipTypeBusinessImpl;
import org.cyk.system.root.model.party.person.PersonRelationshipType;
import org.cyk.system.root.model.party.person.PersonRelationshipTypeGroup;
import org.cyk.ui.web.primefaces.page.nestedset.AbstractDataTreeNodeConsultPage;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class PersonRelationshipTypeConsultPage extends AbstractDataTreeNodeConsultPage<PersonRelationshipType,PersonRelationshipTypeBusinessImpl.Details<PersonRelationshipType,PersonRelationshipTypeGroup>> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	
}
