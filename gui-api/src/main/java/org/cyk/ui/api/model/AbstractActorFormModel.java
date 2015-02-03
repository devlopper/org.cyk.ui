package org.cyk.ui.api.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs.Layout;
import org.cyk.utility.common.annotation.user.interfaces.OutputSeperator;
import org.cyk.utility.common.annotation.user.interfaces.Text;

@Getter @Setter
public abstract class AbstractActorFormModel<ACTOR extends AbstractActor> extends AbstractFormModel<ACTOR>  implements Serializable {

	private static final long serialVersionUID = -3897201743383535836L;

	@OutputSeperator(label=@Text(value="field.person")) @IncludeInputs(layout=Layout.VERTICAL) 
	private PersonFormModel personFormModel;

	public AbstractActorFormModel() {
		super();
		this.personFormModel = new PersonFormModel();
	}
	
	
	
}
