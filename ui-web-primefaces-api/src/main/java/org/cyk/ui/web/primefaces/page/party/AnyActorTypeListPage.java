package org.cyk.ui.web.primefaces.page.party;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.model.party.person.AbstractActor;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class AnyActorTypeListPage extends AbstractActorListPage<AbstractActor> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
}
