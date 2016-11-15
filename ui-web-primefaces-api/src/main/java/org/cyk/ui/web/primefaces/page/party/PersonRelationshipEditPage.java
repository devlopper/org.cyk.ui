package org.cyk.ui.web.primefaces.page.party;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonRelationship;
import org.cyk.system.root.model.party.person.PersonRelationshipType;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputChoiceAutoComplete;
import org.cyk.utility.common.annotation.user.interfaces.InputOneAutoComplete;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;

@Getter @Setter @Named @ViewScoped
public class PersonRelationshipEditPage extends AbstractCrudOnePage<PersonRelationship> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	@Override
	protected PersonRelationship instanciateIdentifiable() {
		PersonRelationship personRelationship = super.instanciateIdentifiable();
		personRelationship.setPerson2(webManager.getIdentifiableFromRequestParameter(Person.class,Boolean.TRUE));
		return personRelationship;
	}
	
	@Getter @Setter
	public static class Form extends AbstractFormModel<PersonRelationship> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Input @InputChoice @InputChoiceAutoComplete @InputOneChoice @InputOneAutoComplete private Person person1;
		@Input @InputChoice @InputOneChoice @InputOneCombo private PersonRelationshipType type;
		@Input @InputChoice @InputChoiceAutoComplete @InputOneChoice @InputOneAutoComplete private Person person2;
			
	}

}
