package org.cyk.ui.web.primefaces.page.event;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.model.event.EventMissed;
import org.cyk.ui.web.primefaces.page.crud.AbstractConsultPage;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class EventMissedConsultPage extends AbstractConsultPage<EventMissed> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;


}
