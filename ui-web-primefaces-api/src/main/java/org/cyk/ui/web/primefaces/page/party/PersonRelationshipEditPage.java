package org.cyk.ui.web.primefaces.page.party;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonRelationship;
import org.cyk.system.root.model.party.person.PersonRelationshipTypeRole;
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
		personRelationship.getExtremity1().setPerson(webManager.getIdentifiableFromRequestParameter(Person.class,Boolean.TRUE));
		return personRelationship;
	}
	
	@Getter @Setter
	public static class Form extends AbstractFormModel<PersonRelationship> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Input @InputChoice @InputChoiceAutoComplete @InputOneChoice @InputOneAutoComplete private Person person1;
		@Input @InputChoice @InputOneChoice @InputOneCombo private PersonRelationshipTypeRole role1;
		@Input @InputChoice @InputChoiceAutoComplete @InputOneChoice @InputOneAutoComplete private Person person2;
		@Input @InputChoice @InputOneChoice @InputOneCombo private PersonRelationshipTypeRole role2;
		
		@Override
		public void read() {
			super.read();
			person1 = identifiable.getExtremity1().getPerson();
			role1 = identifiable.getExtremity1().getRole();
			person2 = identifiable.getExtremity2().getPerson();
			role2 = identifiable.getExtremity2().getRole();
		}
		
		@Override
		public void write() {
			super.write();
			identifiable.getExtremity1().setPerson(person1);
			identifiable.getExtremity1().setRole(role1);
			identifiable.getExtremity2().setPerson(person2);
			identifiable.getExtremity2().setRole(role2);
		}
	}

}
