package org.cyk.ui.api.model.party;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.party.person.Person;
import org.cyk.ui.api.model.AbstractQueryManyFormModel;
import org.cyk.utility.common.annotation.user.interfaces.FieldOverride;
import org.cyk.utility.common.annotation.user.interfaces.FieldOverrides;

@Getter @Setter @FieldOverrides(value={@FieldOverride(name=AbstractQueryManyFormModel.FIELD_IDENTIFIABLES,type=Person.class)})
public class PersonQueryManyFormModel extends AbstractQueryManyFormModel<Person,String> implements Serializable {
	private static final long serialVersionUID = -3756660150800681378L;
	
}