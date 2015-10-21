package org.cyk.ui.web.primefaces.test.form;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.test.model.Actor;
import org.cyk.ui.web.primefaces.page.AbstractSelectPage;
import org.cyk.ui.web.primefaces.page.AbstractSelectPage.AbstractSelectForm;
import org.cyk.ui.web.primefaces.test.business.ActorBusiness;
import org.cyk.utility.common.annotation.user.interfaces.FieldOverride;

@Named @ViewScoped @Getter @Setter
public class ActorSelectPage extends AbstractSelectPage<Actor,ActorSelectPage.Form> implements Serializable {

	private static final long serialVersionUID = -7392513843271510254L;

	@Inject private ActorBusiness actorBusiness;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		//type = Type.IDENTIFIER;
	}
	@Override
	protected Class<Actor> identifiableClass() {
		return Actor.class;
	}
	@Override
	protected Class<?> __formModelClass__() {
		return Form.class;
	}
	
	@Override
	protected Actor find(String identifier) {
		return actorBusiness.findByRegistrationCode(identifier);
	}
	
	@Getter @Setter @FieldOverride(name="identifiable",type=Actor.class)
	public static class Form extends AbstractSelectForm<Actor> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
	}

	

}
