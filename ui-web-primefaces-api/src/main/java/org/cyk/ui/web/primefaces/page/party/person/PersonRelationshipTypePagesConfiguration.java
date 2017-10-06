package org.cyk.ui.web.primefaces.page.party.person;

import java.io.Serializable;

import org.cyk.system.root.model.party.person.PersonRelationshipType;
import org.cyk.ui.web.primefaces.page.pattern.tree.AbstractDataTreePagesConfiguration;

public class PersonRelationshipTypePagesConfiguration extends AbstractDataTreePagesConfiguration<PersonRelationshipType> implements Serializable {

	private static final long serialVersionUID = 1L;

	/*@Override
	protected String[] getFieldNames(Action action) {
		return ArrayUtils.addAll(super.getFieldNames(action),PersonRelationshipTypeEditPage.Form.FIELD_GLOBAL_POSITION,GlobalPositionFormModel.FIELD_LONGITUDE
				,GlobalPositionFormModel.FIELD_LATITUDE,GlobalPositionFormModel.FIELD_ALTITUDE);
	}*/
}
