package org.cyk.ui.web.primefaces.page.party;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.party.person.PersonRelationshipType;
import org.cyk.system.root.model.party.person.PersonRelationshipTypeRole;
import org.cyk.system.root.model.party.person.PersonRelationshipTypeRoleName;
import org.cyk.ui.api.model.AbstractBusinessIdentifiedEditFormModel;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;

@Getter @Setter 
//@Named @ViewScoped
public class PersonRelationshipTypeRoleEditPage extends AbstractCrudOnePage<PersonRelationshipTypeRole> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
		
	@Getter @Setter
	public static class Form extends AbstractBusinessIdentifiedEditFormModel<PersonRelationshipTypeRole> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Input @InputChoice @InputOneChoice @InputOneCombo private PersonRelationshipType personRelationshipType;
		@Input @InputChoice @InputOneChoice @InputOneCombo private PersonRelationshipTypeRoleName role;
		
		/**/
		
		public static final String FIELD_PERSON_RELATIONSHIP_TYPE = "personRelationshipType";
		public static final String FIELD_ROLE = "role";
	}

}
