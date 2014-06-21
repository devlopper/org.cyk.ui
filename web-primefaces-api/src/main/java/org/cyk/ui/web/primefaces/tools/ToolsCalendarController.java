package org.cyk.ui.web.primefaces.tools;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.web.primefaces.AbstractPrimefacesPage;
import org.cyk.ui.web.primefaces.PrimefacesEventCalendar;

@Named
@ViewScoped
@Getter
@Setter
public class ToolsCalendarController extends AbstractPrimefacesPage implements Serializable {

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
