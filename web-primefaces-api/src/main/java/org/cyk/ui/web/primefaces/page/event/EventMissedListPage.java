package org.cyk.ui.web.primefaces.page.event;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.model.event.EventMissed;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudManyPage;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class EventMissedListPage extends AbstractCrudManyPage<EventMissed> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	/**/
	
}
