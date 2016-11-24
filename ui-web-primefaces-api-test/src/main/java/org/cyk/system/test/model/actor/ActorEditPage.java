package org.cyk.system.test.model.actor;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.ui.api.model.party.AbstractActorEditFormModel;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class ActorEditPage extends AbstractCrudOnePage<Actor> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	public static class Form extends AbstractActorEditFormModel.AbstractDefault.Default<Actor> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
	}

}
