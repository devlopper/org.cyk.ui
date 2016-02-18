package org.cyk.ui.web.primefaces.test.business;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.model.AbstractActorQueryManyFormModel;
import org.cyk.ui.api.model.AbstractQueryManyFormModel;
import org.cyk.ui.test.model.Actor;
import org.cyk.utility.common.annotation.user.interfaces.FieldOverride;
import org.cyk.utility.common.annotation.user.interfaces.FieldOverrides;

@Getter @Setter @FieldOverrides(value={@FieldOverride(name=AbstractQueryManyFormModel.FIELD_IDENTIFIABLES,type=Actor.class)})
public class ActorQueryManyFormModel extends AbstractActorQueryManyFormModel.Default<Actor> implements Serializable {
	private static final long serialVersionUID = -3756660150800681378L;
	
}