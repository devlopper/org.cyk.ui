package org.cyk.system.test.model.actor;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.test.model.actor.Actor;
import org.cyk.ui.web.primefaces.page.party.AbstractActorConsultPage;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class ActorConsultPage extends AbstractActorConsultPage<Actor> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
}
