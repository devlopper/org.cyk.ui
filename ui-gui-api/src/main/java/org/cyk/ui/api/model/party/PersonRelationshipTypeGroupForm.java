package org.cyk.ui.api.model.party;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.party.person.PersonRelationshipTypeGroup;
import org.cyk.ui.api.model.pattern.tree.AbstractDataTreeForm;
import org.cyk.ui.api.model.pattern.tree.AbstractDataTreeTypeForm;
import org.cyk.utility.common.annotation.FieldOverride;
import org.cyk.utility.common.annotation.FieldOverrides;

@Getter @Setter 
@FieldOverrides(value = {
		@FieldOverride(name=AbstractDataTreeForm.FIELD_NEW_PARENT,type=PersonRelationshipTypeGroup.class)
		})
public class PersonRelationshipTypeGroupForm extends AbstractDataTreeTypeForm<PersonRelationshipTypeGroup> {

	private static final long serialVersionUID = -3927257570208213271L;

}
