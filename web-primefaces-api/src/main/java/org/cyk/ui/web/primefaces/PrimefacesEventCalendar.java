package org.cyk.ui.web.primefaces;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.event.Event;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.model.EventCalendar;
import org.cyk.ui.web.api.WebManager;
import org.cyk.ui.web.api.WebNavigationManager;
import org.cyk.utility.common.AbstractMethod;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleModel;
import org.primefaces.model.menu.MenuModel;

public class PrimefacesEventCalendar extends EventCalendar implements Serializable {

	private static final long serialVersionUID = -2915809915934469649L;
	
	@Getter private MenuModel menuModel;
	@Getter private ScheduleModel scheduleModel;
	@Getter private Command primefacesAddEventCommand;
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		/*addEventCommand.getCommand().setExecuteMethod(new AbstractMethod<Object, Object>() {
			private static final long serialVersionUID = -1864325491109531292L;
			@Override
			protected Object __execute__(Object parameter) {
				editor(null,Boolean.FALSE,Crud.CREATE);
				return null;
			}
		});*/
		primefacesAddEventCommand =  new Command(addEventCommand);
		
		
	}
	
	@Override
	public void targetDependentInitialisation() {
		Field field = commonUtils.getField(getWindow(), this);
		menuModel = CommandBuilder.getInstance().menuModel(menu, window.getClass(), field.getName());
		scheduleModel = new LazyScheduleModel(){
			private static final long serialVersionUID = -2583150177323754107L;
			@Override
			public void loadEvents(Date start, Date end) {
				for(Event event : PrimefacesEventCalendar.this.events(start,end)){
					DefaultScheduleEvent scheduleEvent = new DefaultScheduleEvent(event.getTitle(),event.getPeriod().getFromDate(),event.getPeriod().getToDate());
					scheduleEvent.setData(event);
					scheduleEvent.setEditable(false);
					addEvent(scheduleEvent);
				}
			}
		};	
	}
	
	public void onEventSelect(SelectEvent selectEvent){
		editor( (Event) ((DefaultScheduleEvent) selectEvent.getObject()).getData(),Boolean.FALSE,Crud.READ);
	}
	
	private void editor(Event event,Boolean popup,Crud crud){
		if(Boolean.TRUE.equals(popup)){
			Map<String, Object> map = new HashMap<>();
			Map<String, List<String>> params = new HashMap<>();
			params.put(WebManager.getInstance().getRequestParameterClass(), Arrays.asList(UIManager.getInstance().keyFromClass(Event.class)));
			params.put(UIManager.getInstance().getCrudParameter(), Arrays.asList(UIManager.getInstance().getCrudParameterValue(crud)));
			if(event!=null){
				params.put(WebManager.getInstance().getRequestParameterIdentifiable(), Arrays.asList(event.getIdentifier().toString()));
			}
			PrimefacesManager.getInstance().openDialog("dynamiceditor", map, params);
		}else{
			WebNavigationManager.getInstance().redirectTo("dynamiceditor",new Object[]{
					WebManager.getInstance().getRequestParameterClass(), UIManager.getInstance().keyFromClass(Event.class),
					WebManager.getInstance().getRequestParameterIdentifiable(),event==null?null:event.getIdentifier(),
					UIManager.getInstance().getCrudParameter(),UIManager.getInstance().getCrudParameterValue(crud)
			});
		}
	}
	
	/*
	private void redirectTo(DATA object){
		WebNavigationManager.getInstance().redirectTo("dynamictable",new Object[]{
				WebManager.getInstance().getRequestParameterClass(), UIManager.getInstance().keyFromClass(rowDataClass),
				WebManager.getInstance().getRequestParameterIdentifiable(), object==null?null:((AbstractIdentifiable)object).getIdentifier()
		});
	}*/


	
	/**/
		
		
}
