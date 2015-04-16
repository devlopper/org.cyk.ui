package org.cyk.ui.api;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.event.EventBusiness;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.mathematics.NumberBusiness;
import org.cyk.system.root.business.api.pattern.tree.DataTreeTypeBusiness;
import org.cyk.system.root.business.api.time.TimeBusiness;
import org.cyk.system.root.business.api.validation.ValidationPolicy;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.menu.DefaultMenu;
import org.cyk.ui.api.command.menu.UIMenu;
import org.cyk.ui.api.config.IdentifiableConfiguration;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.api.data.collector.form.FormOneData;
import org.cyk.ui.api.model.AbstractEventCalendar;
import org.cyk.ui.api.model.table.AbstractTable;
import org.cyk.ui.api.model.table.AbstractTable.UsedFor;
import org.cyk.utility.common.cdi.AbstractBean;

public abstract class AbstractWindow<FORM,ROW,LABEL,CONTROL,SELECTITEM> extends AbstractBean implements UIWindow<FORM,LABEL,CONTROL,SELECTITEM>,Serializable {

	private static final long serialVersionUID = 7282005324574303823L;

	@Inject @Getter transient protected ValidationPolicy validationPolicy;
	
	@Inject @Getter transient protected GenericBusiness genericBusiness;
	@Inject @Getter transient protected DataTreeTypeBusiness dataTreeTypeBusiness;
	@Inject @Getter transient protected EventBusiness eventBusiness;
	@Inject @Getter transient protected LanguageBusiness languageBusiness;
	@Inject transient protected NumberBusiness numberBusiness;
	@Inject transient protected TimeBusiness timeBusiness;
	
	@Getter @Setter protected UserDeviceType userDeviceType;
	@Getter @Setter protected UIMenu mainMenu,contextualMenu,contentMenu;
	
	
	protected Collection<FormOneData<?, FORM, ROW, LABEL, CONTROL, SELECTITEM>> formOneDatas = new ArrayList<>();
	protected Collection<AbstractTable<?,?,?>> tables = new ArrayList<>();
	protected Collection<AbstractEventCalendar> eventCalendars = new ArrayList<>();
	
	@Getter protected String title,contentTitle="Content";
	
	@Override
	protected void initialisation() {
		setUserDeviceType();
		super.initialisation();
		mainMenu = UIManager.getInstance().isMobileDevice(userDeviceType)?getUserSession().getMobileApplicationMenu():getUserSession().getApplicationMenu();
	}
	
	protected void setUserDeviceType() {
		userDeviceType = UserDeviceType.DESKTOP;
	}

	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		Collection<UICommandable> contextualCommandables = contextualCommandables();
		if(contextualCommandables!=null){
			if(contextualMenu==null)
				contextualMenu = new DefaultMenu();
			for(UICommandable subMenu : contextualCommandables){
				contextualMenu.addCommandable(subMenu);
				
			}
		}
		
		if(contextualMenu!=null && UIManager.getInstance().isMobileDevice(userDeviceType))
			contextualMenu.setRenderType(UIMenu.RenderType.TAB);
	
		for(FormOneData<?, FORM, ROW, LABEL, CONTROL, SELECTITEM> form : formOneDatas ){
			form.build();
			
			//for(ComponentCreationListener listener : UIManager.componentCreationListeners)
			//	listener.formOneDataCreated(form);
		}
		
		targetDependentInitialisation();
		
		buildTables();

		for(AbstractEventCalendar eventCalendar : eventCalendars)
			eventCalendar.targetDependentInitialisation();
	}
	
	protected void buildTables(){
		for(AbstractTable<?,?,?> table : tables)
			buildTable(table);
	}
	
	protected void buildTable(AbstractTable<?, ?, ?> table){
		table.build();
	}
	
	public Boolean getShowContextualMenu(){
		return getUserSession().getContextualMenu()!=null || contextualMenu!=null;
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
		form.setEditable(!Crud.READ.equals(crud) && !Crud.DELETE.equals(crud));
		form.setData(data);
		form.setUserDeviceType(userDeviceType);
		formOneDatas.add(form);
		return form;
	}
	
	protected abstract AbstractTable<Object,?,?> __createTable__();
	
	@SuppressWarnings("unchecked")
	public AbstractTable<Object,?,?> createTable(Class<?> aDataClass,IdentifiableConfiguration configuration,Class<AbstractFormModel<?>> customFormModelClass,UsedFor usedFor,Crud crud) {
		AbstractTable<Object,?,?> table = __createTable__();
		table.setUsedFor(usedFor);
		table.setRowDataClass((Class<Object>) (customFormModelClass==null?(configuration==null?aDataClass:configuration.getFormModelClass()):customFormModelClass));
		table.setIdentifiableConfiguration(configuration);
		table.setCrud(crud);
		table.setBusinessEntityInfos(UIManager.getInstance().businessEntityInfos(aDataClass));
		if(UsedFor.FIELD_INPUT.equals(usedFor))
			table.setLazyLoad(Boolean.FALSE);
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
	
	public <DATA> AbstractTable<?,?,?> createTable(Class<?> aDataClass,IdentifiableConfiguration crudConfig,Class<AbstractFormModel<?>> customFormModelClass) {
		return createTable(aDataClass, crudConfig,customFormModelClass,UsedFor.ENTITY_INPUT, Crud.READ);
	}
	
	@Override
	public AbstractEventCalendar eventCalendarInstance(Class<?> aClass) {
		AbstractEventCalendar eventCalendar = eventCalendarInstance();
		eventCalendar.setWindow(this);
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
	
	protected Collection<UICommandable> contextualCommandables(){
		return null;
	}

}
