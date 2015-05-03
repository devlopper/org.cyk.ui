package org.cyk.ui.web.primefaces;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.model.event.Event;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.model.AbstractEventCalendar;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleModel;
import org.primefaces.model.menu.MenuModel;

public class EventCalendar extends AbstractEventCalendar implements Serializable {

	private static final long serialVersionUID = -2915809915934469649L;
	
	@Getter private MenuModel menuModel;
	@Getter private ScheduleModel scheduleModel;
	@Getter private Event selectedEvent;
	
	@Override
	public void targetDependentInitialisation() {
		//Field field = commonUtils.getField(getWindow(), this);
		//menuModel = CommandBuilder.getInstance().menuModel(menu, window.getClass(), field.getName());
		scheduleModel = new LazyScheduleModel(){
			private static final long serialVersionUID = -2583150177323754107L;
			@Override
			public void loadEvents(Date start, Date end) {
				for(Event event : EventCalendar.this.events(start,end)){
					String object = event.getType().getName();
					if(StringUtils.isBlank(object))
						object = event.getObject();
					else
						object += " - "+ event.getObject();
					DefaultScheduleEvent scheduleEvent = new DefaultScheduleEvent(object,event.getPeriod().getFromDate(),event.getPeriod().getToDate(),event);
					
					/*
					if(event.getAlarm().getPeriod().getFromDate()!=null)
						scheduleEvent.setDescription("Alarm : "+UIManager.getInstance().formatDate(event.getAlarm().getPeriod().getFromDate(), Boolean.TRUE)+
							" - "+UIManager.getInstance().formatDate(event.getAlarm().getPeriod().getToDate(), Boolean.TRUE));
					*/
					
					scheduleEvent.setEditable(false);
					addEvent(scheduleEvent);
				}
			}
		};	
	}
	
	public void onEventSelect(SelectEvent selectEvent){
		selectedEvent = (Event) ((DefaultScheduleEvent) selectEvent.getObject()).getData();
		//editor( selectedEvent,Boolean.FALSE,Crud.READ);
	}
	
	public String selectedEventDetails(){
		return UIManager.getInstance().getLanguageBusiness().findText("calendar.event.details",new Object[]{
				selectedEvent.getType().getName(),UIManager.getInstance().getTimeBusiness().formatDate(selectedEvent.getPeriod().getFromDate()),
				UIManager.getInstance().getTimeBusiness().formatTime(selectedEvent.getPeriod().getFromDate())
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
		
}
