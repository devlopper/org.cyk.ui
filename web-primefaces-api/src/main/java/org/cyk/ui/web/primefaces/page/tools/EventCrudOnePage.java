package org.cyk.ui.web.primefaces.page.tools;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.model.event.Event;
import org.cyk.ui.api.command.menu.MenuManager;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.api.model.EventFormModel;
import org.cyk.ui.web.primefaces.EventCalendar;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;

@Named @ViewScoped @Getter @Setter
public class EventCrudOnePage extends AbstractCrudOnePage<Event> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	private EventCalendar eventCalendar;
	
	@Override
	protected void initialisation() { 
		super.initialisation();
		contentTitle=text("calendar");
		eventCalendar = (EventCalendar) eventCalendarInstance(null);
		contextualMenu = MenuManager.getInstance().calendarMenu(userSession);
	}
	/*
	@Override
	protected void create() {
		debug(identifiable);
	}*/
	
	@Override
	protected Class<? extends AbstractFormModel<?>> __formModelClass__() {
		return EventFormModel.class;
	}
	
	@Override
	protected BusinessEntityInfos fetchBusinessEntityInfos() {
		return uiManager.businessEntityInfos(Event.class);
	}
}
