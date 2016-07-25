package org.cyk.ui.api.model.party;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs.Layout;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Getter @Setter
public abstract class AbstractActorEditFormModel<ACTOR extends AbstractActor> extends AbstractFormModel<ACTOR>  implements Serializable {

	private static final long serialVersionUID = -3897201743383535836L;

	@Input @InputText protected String registrationCode;
	
	@IncludeInputs(layout=Layout.VERTICAL) 
	private DefaultPersonEditFormModel personFormModel = new DefaultPersonEditFormModel();

	@Override
	public void setIdentifiable(ACTOR actor) {
		if(actor.getPerson()==null)
			actor.setPerson(new Person());
		personFormModel.setIdentifiable(actor.getPerson());
		super.setIdentifiable(actor);
	}
	
	@Override
	public void write() {
		super.write();
		identifiable.setCode(registrationCode);
	}
	
	@Override
	public void read() {
		super.read();
		registrationCode = identifiable.getCode();
	}
	
	/**/
	
	@Getter @Setter
	public static class Default extends AbstractActorEditFormModel<AbstractActor>  implements Serializable {

		private static final long serialVersionUID = -3897201743383535836L;
			
	}

	
	/**/
	
	public static final String FIELD_REGISTRATION_CODE = "registrationCode";
	
}
