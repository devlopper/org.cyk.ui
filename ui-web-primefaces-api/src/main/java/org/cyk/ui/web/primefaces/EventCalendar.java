package org.cyk.ui.web.primefaces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.event.EventPartyBusiness;
import org.cyk.system.root.model.ContentType;
import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.event.EventParty;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.model.event.AbstractEventCalendar;
import org.cyk.utility.common.helper.EventHelper;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleModel;
import org.primefaces.model.menu.MenuModel;

import lombok.Getter;
import lombok.Setter;

public class EventCalendar extends AbstractEventCalendar implements Serializable {

	private static final long serialVersionUID = -2915809915934469649L;
	
	@Getter private MenuModel menuModel;
	@Getter private ScheduleModel scheduleModel;
	@Getter private Event selectedEvent;
	@Getter private EventInfos selectedEventInfos;
	
	@Override
	public void targetDependentInitialisation() {
		//Field field = commonUtils.getField(getWindow(), this);
		//menuModel = CommandBuilder.getInstance().menuModel(menu, window.getClass(), field.getName());
		scheduleModel = new LazyScheduleModel(){
			private static final long serialVersionUID = -2583150177323754107L;
			@Override
			public void loadEvents(Date start, Date end) {
				for(EventHelper.Event event : EventHelper.getInstance().get(start, end)){
					DefaultScheduleEvent scheduleEvent = new DefaultScheduleEvent(event.getName(),event.getFrom(),event.getTo());
					scheduleEvent.setEditable(false);
					addEvent(scheduleEvent);
				}
			}
		};	
	}
	
	public void onEventSelect(SelectEvent selectEvent){
		selectedEvent = (Event) ((DefaultScheduleEvent) selectEvent.getObject()).getData();
		selectedEventInfos = new EventInfos(selectedEvent);
		//editor( selectedEvent,Boolean.FALSE,Crud.READ);
	}
	
	public String selectedEventDetails(){
		return UIManager.getInstance().getLanguageBusiness().findText("calendar.event.details",new Object[]{
				//selectedEvent.getType().getName(),UIManager.getInstance().getTimeBusiness().formatDate(selectedEvent.getExistencePeriod().getFromDate()),
				UIManager.getInstance().getTimeBusiness().formatTime(selectedEvent.getExistencePeriod().getFromDate())
		});
	}
	
	/*
	private void editor(Event event,Boolean popup,Crud crud){
		if(Boolean.TRUE.equals(popup)){
			Map<String, Object> map = new HashMap<>();
			Map<String, List<String>> params = new HashMap<>();
			params.put(WebManager.getInstance().getRequestParameterClass(), Arrays.asList(UIManager.getInstance().keyFromClass(Event.class)));
			params.put(UIManager.getInstance().getCrudParameter(), Arrays.asList(UIManager.getInstance().getCrudParameterValue(crud)));
			if(event!=null){
				params.put(WebManager.getInstance().getRequestParameterIdentifiable(), Arrays.asList(event.getIdentifier().toString()));
			}
			PrimefacesManager.getInstance().openDialog(WebNavigationManager.getInstance().getOutcomeDynamicCrudOne(), map, params);
		}else{
			WebNavigationManager.getInstance().redirectTo(WebNavigationManager.getInstance().getOutcomeEventCrudOne(),new Object[]{
					WebManager.getInstance().getRequestParameterIdentifiable(),event==null?null:event.getIdentifier(),
					UIManager.getInstance().getCrudParameter(),UIManager.getInstance().getCrudParameterValue(crud)
			});
		}
	}
	*/
	
	@Getter @Setter
	public static class EventInfos implements Serializable{
		private static final long serialVersionUID = -6277816820166800295L;
		
		private Event event;
		private String object,comments,date,parties;
		
		public EventInfos(Event event) {
			this.event = event;
			//object = event.getObject();
			//comments = event.getComments(); 
			date = UIManager.getInstance().getTimeBusiness().formatPeriodFromTo(event.getExistencePeriod());
			List<String> partiyList = new ArrayList<>();
			for(EventParty eventParticipation : inject(EventPartyBusiness.class).findByEvent(event)){
				StringBuilder builder = new StringBuilder();
				if(eventParticipation.getParty() instanceof Person){
					Person person = (Person)eventParticipation.getParty();
					UIManager.getInstance().getPersonBusiness().load(person);
					builder.append( person.getNames() );
					if(person.getContactCollection().getPhoneNumbers()!=null && !person.getContactCollection().getPhoneNumbers().isEmpty())
						builder.append(" - "+person.getContactCollection().getPhoneNumbers().iterator().next().getNumber());
				}else
					builder.append( eventParticipation.getParty().getName() );
				
				partiyList.add(builder.toString());
			}
			parties = StringUtils.join(partiyList,ContentType.HTML.getNewLineMarker());
		}
	}
		
}
