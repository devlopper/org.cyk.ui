package org.cyk.ui.web.primefaces.page.tools;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.event.EventBusiness;
import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.party.Party;
import org.cyk.ui.api.command.menu.MenuManager;
import org.cyk.ui.web.primefaces.Table;
import org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Named @ViewScoped @Getter @Setter
public class EventListPage extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	@Inject private EventBusiness eventBusiness;
	
	private Table<EventDetails> pastEventTable,currentEventTable,onComingEventTable;
	private Collection<Party> parties;
	private EventDetails selected;
	
	@Override
	protected void initialisation() { 
		super.initialisation();
		contentTitle=text("list")+" "+text("model.entity.event");
		parties = Arrays.asList(getUserSession().getUser());
		contextualMenu = MenuManager.getInstance().calendarMenu(userSession,userDeviceType);
	}
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		pastEventTable = createDetailsTable(EventDetails.class, eventDetails(eventBusiness.findPasts(parties)), null);
		currentEventTable = createDetailsTable(EventDetails.class, eventDetails(eventBusiness.findCurrents(parties)), null);
		onComingEventTable = createDetailsTable(EventDetails.class, eventDetails(eventBusiness.findOnComings(parties)), null);
	}
	
	private Collection<EventDetails> eventDetails(Collection<Event> events){
		Collection<EventDetails> collection = new ArrayList<EventDetails>();
		for(Event event : events)
			collection.add(new EventDetails(event));
		return collection;
	}
	
	public String details(EventDetails eventDetails){
		this.selected = eventDetails;
		return mobilePageOutcome("details");
	}
	
	/**/
	
	@Getter @Setter
	public class EventDetails implements Serializable{
		
		private static final long serialVersionUID = 288408870949080442L;
		private Event event;
		@Input @InputText private String type,object,date;
		public EventDetails(Event event) {
			this.event = event;
			type = event.getType().getName();
			object = event.getObject();
			date = timeBusiness.formatDateTime(event.getPeriod().getFromDate(),event.getPeriod().getToDate());
		}
	}
	
}
