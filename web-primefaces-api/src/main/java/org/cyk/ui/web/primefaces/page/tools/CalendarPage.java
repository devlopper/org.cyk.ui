package org.cyk.ui.web.primefaces.page.tools;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.UIProvider;
import org.cyk.ui.api.command.CommandAdapter;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UICommandable.CommandRequestType;
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
		contextualMenu = MenuManager.getInstance().calendarMenu(userSession);
		
		UICommandable c = UIProvider.getInstance().createCommandable("alarm", null);
		c.setCommandRequestType(CommandRequestType.BUSINESS_PROCESSING);
		c.getCommand().getCommandListeners().add(new CommandAdapter(){
			private static final long serialVersionUID = 6350067344396642437L;
			@Override
			public void serve(UICommand command, Object parameter) {
				System.out
						.println("CalendarPage.initialisation().new CommandAdapter() {...}.serve()");
			}
		});
		contextualMenu.getCommandables().add(c);
	}
	
}
