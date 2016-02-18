package org.cyk.ui.web.primefaces.test.business;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.model.AbstractActorQueryOneFormModel;
import org.cyk.ui.api.model.AbstractQueryOneFormModel;
import org.cyk.ui.test.model.Actor;
import org.cyk.utility.common.annotation.user.interfaces.FieldOverride;
import org.cyk.utility.common.annotation.user.interfaces.FieldOverrides;

@Getter @Setter @FieldOverrides(value={@FieldOverride(name=AbstractQueryOneFormModel.FIELD_IDENTIFIABLE,type=Actor.class)})
public class ActorQueryOneFormModel extends AbstractActorQueryOneFormModel.Default<Actor> implements Serializable {
	private static final long serialVersionUID = -3756660150800681378L;
	
}