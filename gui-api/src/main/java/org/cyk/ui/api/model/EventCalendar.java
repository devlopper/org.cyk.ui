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
import org.cyk.ui.api.command.DefaultCommand;
import org.cyk.ui.api.command.DefaultCommandable;
import org.cyk.ui.api.command.DefaultMenu;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UICommandable.IconType;
import org.cyk.ui.api.command.UIMenu;
import org.cyk.utility.common.AbstractMethod;
import org.cyk.utility.common.cdi.AbstractBean;

@Getter @Setter
public class EventCalendar extends AbstractBean implements UIWindowPart,Serializable {

	private static final long serialVersionUID = -7832418987283686453L;
	
	protected ValidationPolicy validationPolicy; 
	protected UIMenu menu = new DefaultMenu();
	protected UIWindow<?, ?, ?, ?,?> window;
	protected String title;
	protected Boolean editable=Boolean.FALSE;
	protected UICommandable addEventCommand,deleteEventCommand,openEventCommand,saveEventCommand,cancelEventCommand;
	protected Event dataAdding;
	/*
	protected Boolean showOpenCommand;
	*/
	
	@Override
	protected void initialisation() {
		super.initialisation();
		
		addEventCommand = createCommandable("command.add", IconType.ACTION_ADD, new AbstractMethod<Object, Object>() {
			private static final long serialVersionUID = 1074893365570711794L;
			@Override
			protected Object __execute__(Object parameter) {
				
				return null;
			}
		});
		
		/*
		openRowCommand = createCommandable("command.open", IconType.ACTION_OPEN, new AbstractMethod<Object, Object>() {
			private static final long serialVersionUID = 1074893365570711794L;
			@Override
			protected Object __execute__(Object parameter) {
				rowNavigateEventMethod.execute((TableRow<?>) parameter);
				return null;
			}
		});
		openRowCommand.setShowLabel(Boolean.FALSE);
		
		saveRowCommand = createCommand(new AbstractMethod<Object, Object>() {
			private static final long serialVersionUID = 4758954266295164539L;
			@SuppressWarnings("unchecked")
			@Override
			protected Object __execute__(Object object) {
				lastEditedRowIndex = (object==dataAdding)?rows.size()-1:rowIndex((DATA)object);
				getWindow().getGenericBusiness().save((AbstractIdentifiable)object);
				dataAdding = null;
				return null;
			}
		});
		
		cancelRowCommand = createCommand(new AbstractMethod<Object, Object>() {
			private static final long serialVersionUID = 4758954266295164539L;
			@SuppressWarnings("unchecked")
			@Override
			protected Object __execute__(Object object) {
				if(object==dataAdding){
					deleteRow(rows.size()-1);
					dataAdding = null;
				}else{
					DATA initialData = (DATA) getWindow().getGenericBusiness().find( ((AbstractIdentifiable)object).getIdentifier() );
					updateRow(rows.get(rowIndex((DATA) object).intValue()), initialData);
				}
				return null;
			}
		});
		
		deleteRowCommand = createCommandable("command.delete", IconType.ACTION_REMOVE, new AbstractMethod<Object, Object>() {
			private static final long serialVersionUID = 1074893365570711794L;
			@Override
			protected Object __execute__(Object object) {
				@SuppressWarnings("unchecked")
				TableRow<DATA> row = (TableRow<DATA>) object;
				getWindow().getGenericBusiness().delete((AbstractIdentifiable) row.getData());
				deleteRow(row);
				//deleteRowCommand.getCommand().getMessageManager().message(SeverityType.WARNING, "SUCCES", false).showDialog();
				return null;
			}
		});
		deleteRowCommand.setShowLabel(Boolean.FALSE);
		*/
	}
	
	public Collection<Event> events(Date start,Date end){
		return getWindow().getEventBusiness().findWhereFromDateBetweenByStartDateByEndDate(start, end);
	}
	
	protected UICommandable createCommandable(String labelId,IconType iconType,AbstractMethod<Object, Object> action){
		UICommandable commandable = new DefaultCommandable();
		commandable.setCommand(createCommand(action));
		commandable.setLabel(text(labelId));
		commandable.setIconType(iconType);
		return commandable;
	}
	
	protected UICommand createCommand(AbstractMethod<Object, Object> action){
		UICommand command = new DefaultCommand();
		command.setMessageManager(getWindow().getMessageManager());
		//command.setExecuteMethod(action);
		return command;
	}
	
	/**/
	
	private String text(String id){
		return getWindow().getUiManager().getLanguageBusiness().findText(id);
	}
	
	public void targetDependentInitialisation(){}
	
	/**/
	
}
