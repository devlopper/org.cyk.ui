package org.cyk.ui.web.primefaces.page.party;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.ui.api.model.party.AbstractActorEditFormModel;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class AnyActorTypeEditPage extends AbstractActorEditPage<AbstractActor> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	@Override
	protected AbstractActor getActor() {
		return identifiable;
	}
	
	@Getter @Setter
	public static class Form extends AbstractActorEditFormModel.AbstractDefault.Default<AbstractActor> implements Serializable {

		private static final long serialVersionUID = 2646571878912106597L;
		
	}

	
	
}
