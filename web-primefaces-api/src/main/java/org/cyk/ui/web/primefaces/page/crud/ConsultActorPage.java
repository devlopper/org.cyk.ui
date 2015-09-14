package org.cyk.ui.web.primefaces.page.crud;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.party.person.AbstractActor;

@Named @ViewScoped @Getter @Setter
public class ConsultActorPage extends AbstractConsultPage<AbstractActor> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
}
