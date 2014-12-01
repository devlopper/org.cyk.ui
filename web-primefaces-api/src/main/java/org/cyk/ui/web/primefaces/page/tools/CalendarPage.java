package org.cyk.ui.web.primefaces.page.tools;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.web.primefaces.PrimefacesEventCalendar;
import org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage;

@Named
@RequestScoped
@Getter
@Setter
public class CalendarPage extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	private PrimefacesEventCalendar eventCalendar;
	
	@Override
	protected void initialisation() { 
		super.initialisation();
		contentTitle=text("calendar");
		eventCalendar = (PrimefacesEventCalendar) eventCalendarInstance(null);
	}
	
	@Override
	public Boolean getShowContentMenu() {
		return Boolean.TRUE;
	}
	
}
