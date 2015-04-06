package org.cyk.ui.web.primefaces.page.tools;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.command.menu.MenuManager;
import org.cyk.ui.web.primefaces.EventCalendar;
import org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage;

@Named @ViewScoped @Getter @Setter
public class CalendarPage extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	private EventCalendar eventCalendar;
	
	@Override
	protected void initialisation() { 
		super.initialisation();
		contentTitle=text("calendar");
		eventCalendar = (EventCalendar) eventCalendarInstance(null);
		eventCalendar.getParties().add(getUserSession().getUser());
		contextualMenu = MenuManager.getInstance().calendarMenu(userSession);
	}
	
}
