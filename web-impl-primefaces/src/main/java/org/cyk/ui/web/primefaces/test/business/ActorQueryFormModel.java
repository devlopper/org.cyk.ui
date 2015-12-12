package org.cyk.ui.web.primefaces.test.business;

import java.io.Serializable;

import org.cyk.ui.api.model.AbstractActorQueryFormModel;
import org.cyk.ui.api.model.AbstractQueryFormModel;
import org.cyk.ui.test.model.Actor;
import org.cyk.utility.common.annotation.user.interfaces.FieldOverride;
import org.cyk.utility.common.annotation.user.interfaces.FieldOverrides;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter @FieldOverrides(value={@FieldOverride(name=AbstractQueryFormModel.FIELD_IDENTIFIABLE,type=Actor.class)})
public class ActorQueryFormModel extends AbstractActorQueryFormModel.Default<Actor> implements Serializable {
	private static final long serialVersionUID = -3756660150800681378L;
	
}