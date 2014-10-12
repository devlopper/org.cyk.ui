package org.cyk.ui.api;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.event.EventBusiness;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.pattern.tree.DataTreeTypeBusiness;
import org.cyk.system.root.business.api.validation.ValidationPolicy;
import org.cyk.ui.api.MenuManager.Type;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UIMenu;
import org.cyk.ui.api.data.collector.form.FormOneData;
import org.cyk.ui.api.model.EventCalendar;
import org.cyk.ui.api.model.table.AbstractTable;
import org.cyk.ui.api.model.table.AbstractTable.UsedFor;
import org.cyk.utility.common.cdi.AbstractBean;

public abstract class AbstractWindow<FORM,ROW,LABEL,CONTROL,SELECTITEM> extends AbstractBean implements UIWindow<FORM,LABEL,CONTROL,SELECTITEM>,Serializable {

	private static final long serialVersionUID = 7282005324574303823L;

	@Inject @Getter protected ValidationPolicy validationPolicy;
	
	@Inject @Getter protected GenericBusiness genericBusiness;
	@Inject @Getter protected DataTreeTypeBusiness dataTreeTypeBusiness;
	@Inject @Getter protected EventBusiness eventBusiness;
	@Inject @Getter protected LanguageBusiness languageBusiness;
	
	@Getter protected Locale locale = Locale.FRENCH;
	@Getter @Setter protected UIMenu mainMenu,contextualMenu,contentMenu;
	//@Getter protected Boolean showContentMenu = Boolean.FALSE,showContextualMenu=Boolean.TRUE;
	protected Collection<FormOneData<?, FORM, ROW, LABEL, CONTROL, SELECTITEM>> formOneDatas = new ArrayList<>();
	protected Collection<AbstractTable<?,?,?>> tables = new ArrayList<>();
	protected Collection<EventCalendar> eventCalendars = new ArrayList<>();
	//protected Collection<HierarchycalData<?>> hierarchicalDatas = new ArrayList<>();
	@Inject protected MenuManager menuManager;
	@Getter protected String title,contentTitle="Content";
	
	@Override
	protected void initialisation() {
		super.initialisation();
		mainMenu = menuManager.build(Type.APPLICATION);
	}
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		//if(editors.size()==1)
		//	contentMenu = editors.iterator().next().getMenu();
		
		for(FormOneData<?, FORM, ROW, LABEL, CONTROL, SELECTITEM> form : formOneDatas )
			form.build();
		
		targetDependentInitialisation();
		
		for(AbstractTable<?,?,?> table : tables)
			table.build();
		/*
		for(HierarchycalData<?> hierarchicalData : hierarchicalDatas)
			hierarchicalData.targetDependentInitialisation();
		*/
		for(EventCalendar eventCalendar : eventCalendars)
			eventCalendar.targetDependentInitialisation();
	}
	
	public Boolean getShowContextualMenu(){
		return contextualMenu!=null;
	}
	
	public Boolean getShowContentMenu(){
		return contentMenu!=null;
	}
	
	public Boolean getShowMainMenu(){
		return Boolean.TRUE;
	}
	
	protected abstract <DATA> FormOneData<DATA, FORM, ROW, LABEL, CONTROL, SELECTITEM> __createFormOneData__();
	
	protected <DATA> FormOneData<DATA, FORM, ROW, LABEL, CONTROL, SELECTITEM> createFormOneData(DATA data,Crud crud){
		FormOneData<DATA, FORM, ROW, LABEL, CONTROL, SELECTITEM> form = __createFormOneData__();
		form.setData(data);
		formOneDatas.add(form);
		return form;
	}
	
	protected abstract <DATA> AbstractTable<DATA,?,?> __createTable__();
	
	public <DATA> AbstractTable<DATA,?,?> createTable(Class<DATA> aDataClass,UsedFor usedFor,Crud crud) {
		AbstractTable<DATA,?,?> table = __createTable__();
		table.setUsedFor(usedFor);
		table.setRowDataClass(aDataClass);
		//table.setCrud(crud);
		//table.setWindow(this);
		//configureBeforeConstruct(table);
		((AbstractBean)table).postConstruct();
		//table.build();
		//configureAfterConstruct(table);
		//table.getAddRowCommand().getCommand().setMessageManager(getMessageManager());
		//table.getSaveRowCommand().setMessageManager(getMessageManager());
		//table.getDeleteRowCommand().getCommand().setMessageManager(getMessageManager());
		//table.getCancelRowCommand().setMessageManager(getMessageManager());
		
		tables.add(table);
		return table;
	}
	
	public <DATA> AbstractTable<DATA,?,?> createTable(Class<DATA> aDataClass) {
		return createTable(aDataClass, UsedFor.ENTITY_INPUT, Crud.READ);
	}
	
	/*
	@Override
	public <DATA> HierarchycalData<DATA> hierarchyInstance(Class<DATA> aNodeClass) {
		HierarchycalData<DATA> hierarchicalData = hierarchyInstance();
		hierarchicalData.setWindow(this);
		hierarchicalData.setNodeDataClass(aNodeClass);
		((AbstractBean)hierarchicalData).postConstruct();
		hierarchicalData.getAddNodeCommand().getCommand().setMessageManager(getMessageManager());
		hierarchicalDatas.add(hierarchicalData);
		return hierarchicalData;
	}*/
	
	@Override
	public EventCalendar eventCalendarInstance(Class<?> aClass) {
		EventCalendar eventCalendar = eventCalendarInstance();
		//eventCalendar.setWindow(this);
		configureBeforeConstruct(eventCalendar);
		((AbstractBean)eventCalendar).postConstruct();
		configureAfterConstruct(eventCalendar);
		//eventCalendar.getAddEventCommand().getCommand().setMessageManager(getMessageManager());
		eventCalendars.add(eventCalendar);
		return eventCalendar;
	}
	
	public void onMessageDialogBoxClosed(){
		System.out.println("AbstractWindow.onMessageDialogBoxClosed()");
	}
	
	private void configureBeforeConstruct(UIWindowPart aWindowPart){
		aWindowPart.setValidationPolicy(getValidationPolicy());
	}
	
	private void configureAfterConstruct(UIWindowPart aWindowPart){
		Collection<Field> fields = commonUtils.getAllFields(aWindowPart.getClass());
		for(Field field : fields)
			if(UICommandable.class.isAssignableFrom(field.getType())){
				UICommandable commandable = ((UICommandable)commonUtils.readField(aWindowPart, field, Boolean.FALSE));
				if(commandable!=null)
					configureCommand( commandable.getCommand() );
			}else if(UICommand.class.isAssignableFrom(field.getType())){
				configureCommand( (UICommand)commonUtils.readField(aWindowPart, field, Boolean.FALSE) );
			}
	}
	
	private void configureCommand(UICommand aCommand){
		if(aCommand==null)
			return;
		aCommand.setMessageManager(getMessageManager());
	}
	
	public String text(String code) {
		return UIManager.getInstance().text(code);
	}

}
