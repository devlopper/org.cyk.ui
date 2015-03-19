package org.cyk.ui.api.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.validation.ValidationPolicy;
import org.cyk.system.root.model.event.Event;
import org.cyk.ui.api.UIWindow;
import org.cyk.ui.api.UIWindowPart;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.menu.DefaultMenu;
import org.cyk.ui.api.command.menu.UIMenu;
import org.cyk.utility.common.cdi.AbstractBean;

@Getter @Setter
public abstract class AbstractEventCalendar extends AbstractBean implements UIWindowPart,Serializable {

	private static final long serialVersionUID = -7832418987283686453L;
	
	protected ValidationPolicy validationPolicy; 
	protected UIMenu menu = new DefaultMenu();
	protected UIWindow<?, ?, ?, ?> window;
	protected String title;
	protected Boolean editable=Boolean.FALSE;
	protected UICommandable addEventCommandable,deleteEventCommandable,openEventCommandable,saveEventCommandable,cancelEventCommandable;
	protected Event dataAdding;
	/*
	protected Boolean showOpenCommand;
	*/
	
	@Override
	protected void initialisation() {
		super.initialisation();
		
	}
	
	public Collection<Event> events(Date start,Date end){
		return getWindow().getEventBusiness().findWhereFromDateBetweenByStartDateByEndDate(start, end);
	}
	
}
