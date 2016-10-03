package org.cyk.ui.api.model.party;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.party.person.PersonRelationshipType;
import org.cyk.system.root.model.party.person.PersonRelationshipTypeGroup;
import org.cyk.ui.api.model.pattern.tree.AbstractDataTreeForm;
import org.cyk.utility.common.annotation.FieldOverride;
import org.cyk.utility.common.annotation.FieldOverrides;

@Getter @Setter 
@FieldOverrides(value = {
		@FieldOverride(name=AbstractDataTreeForm.FIELD_PARENT,type=PersonRelationshipType.class)
		,@FieldOverride(name=AbstractDataTreeForm.FIELD_TYPE,type=PersonRelationshipTypeGroup.class)
		})
public class PersonRelationshipTypeForm extends AbstractDataTreeForm<PersonRelationshipType,PersonRelationshipTypeGroup> {

	private static final long serialVersionUID = -3927257570208213271L;

}
