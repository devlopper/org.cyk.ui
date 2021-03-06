package org.cyk.ui.api;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.ClazzBusiness;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.event.EventBusiness;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.mathematics.NumberBusiness;
import org.cyk.system.root.business.api.time.TimeBusiness;
import org.cyk.system.root.business.api.userinterface.style.CascadeStyleSheetBusiness;
import org.cyk.system.root.business.api.validation.ValidationPolicy;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.UIMessageManager.SeverityType;
import org.cyk.ui.api.command.AbstractCommandable;
import org.cyk.ui.api.command.AbstractCommandable.Builder;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.menu.DefaultMenu;
import org.cyk.ui.api.command.menu.UIMenu;
import org.cyk.ui.api.config.IdentifiableConfiguration;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.api.data.collector.form.FormOneData;
import org.cyk.ui.api.model.FormOneDataCollection;
import org.cyk.ui.api.model.event.AbstractEventCalendar;
import org.cyk.ui.api.model.table.AbstractTable;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.computation.ExecutionProgress;
import org.joda.time.DateTimeConstants;

import lombok.Getter;
import lombok.Setter;

public abstract class AbstractWindow<FORM,ROW,LABEL,CONTROL,SELECTITEM,COMANDABLE extends AbstractCommandable> extends AbstractBean implements UIWindow<FORM,LABEL,CONTROL,SELECTITEM>,Serializable {

	private static final long serialVersionUID = 7282005324574303823L;

	@Inject @Getter transient protected ValidationPolicy validationPolicy;
	
	@Inject @Getter transient protected GenericBusiness genericBusiness;
	//@Inject @Getter transient protected DataTreeTypeBusiness dataTreeTypeBusiness;
	@Inject @Getter transient protected EventBusiness eventBusiness;
	@Inject @Getter transient protected LanguageBusiness languageBusiness;
	@Inject transient protected NumberBusiness numberBusiness;
	@Inject transient protected TimeBusiness timeBusiness;
	@Inject transient protected RootBusinessLayer rootBusinessLayer;
	
	@Getter @Setter protected Long userActiveTimeout = DateTimeConstants.MILLIS_PER_MINUTE * 10l;//TODO should come from outside
	@Getter @Setter protected ExecutionProgress executionProgress;
	protected ExecutionProgress.Listener executionProgressListener;
	
	@Getter @Setter protected UserDeviceType userDeviceType;
	@Getter @Setter protected UIMenu mainMenu,contextualMenu,contentMenu,windowHierachyMenu,detailsMenu;
	
	//protected Collection<FormOneData<?, FORM, ROW, LABEL, CONTROL, SELECTITEM>> formOneDatas = new ArrayList<>();
	@Getter @Setter protected FormOneDataCollection formOneDataCollection = new FormOneDataCollection();
	protected Collection<AbstractTable<?,?,?>> tables = new ArrayList<>();
	protected Collection<AbstractEventCalendar> eventCalendars = new ArrayList<>();
	
	@Getter @Setter protected String title,contentTitle="Content";
	@Getter @Setter protected String text;
	@Getter @Setter protected String actionIdentifier;
	
	@Override
	protected void initialisation() {
		setUserDeviceType();
		super.initialisation();
		mainMenu = UIManager.getInstance().isMobileDevice(userDeviceType)?getUserSession().getMobileApplicationMenu():getUserSession().getApplicationMenu();
		if(mainMenu!=null)
			inject(CascadeStyleSheetBusiness.class).addClasses(mainMenu.getCascadeStyleSheet(),UIManager.GLOBAL_MENU_CLASS);
	}
	
	protected void setUserDeviceType() {
		userDeviceType = UserDeviceType.DESKTOP;
	}

	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		contentTitle = buildContentTitle();
		title = contentTitle;
		Collection<UICommandable> contextualCommandables = contextualCommandables();
		if(contextualCommandables!=null){
			if(contextualMenu==null)
				contextualMenu = new DefaultMenu();
			inject(CascadeStyleSheetBusiness.class).addClasses(contextualMenu.getCascadeStyleSheet(),UIManager.CONTEXTUAL_MENU_CLASS);
			for(UICommandable subMenu : contextualCommandables){
				contextualMenu.addCommandable(subMenu);
				
			}
		}
		
		if(contextualMenu!=null && UIManager.getInstance().isMobileDevice(userDeviceType)){
			contextualMenu.setRenderType(UIMenu.RenderType.TAB);
			
		}
	
		for(FormOneData<?, ?, ?, ?, ?, ?> form : formOneDataCollection.getCollection() ){
			form.build();
			
			//for(ComponentCreationListener listener : UIManager.componentCreationListeners)
			//	listener.formOneDataCreated(form);
		}
		
		targetDependentInitialisation();
		
		buildTables();

		for(AbstractEventCalendar eventCalendar : eventCalendars)
			eventCalendar.targetDependentInitialisation();
		
		if(executionProgress!=null && executionProgressListener==null){
			executionProgressListener = new ExecutionProgress.Listener.Adapter.Default(){
				private static final long serialVersionUID = 6123373968139743440L;
				@Override
				public void valueChanged(ExecutionProgress executionProgress,String fieldName, Object oldValue) {
					AbstractWindow.this.executionProgress.setCurrentAmountOfWorkDone(executionProgress.getCurrentAmountOfWorkDone());
					super.valueChanged(executionProgress, fieldName, oldValue);
				}
			};
		}
	}
	
	protected String buildContentTitle(){
		return "Content Title";
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
	public Boolean getShowDetailsMenu(){
		return detailsMenu!=null && detailsMenu.getCommandables().size()>1;
	}
	
	protected abstract <DATA> FormOneData<DATA, FORM, ROW, LABEL, CONTROL, SELECTITEM> __createFormOneData__(String submitCommandableLabelId);
	
	protected <DATA> FormOneData<DATA, FORM, ROW, LABEL, CONTROL, SELECTITEM> createFormOneData(DATA data,Crud crud,String submitCommandableLabelId){
		FormOneData<DATA, FORM, ROW, LABEL, CONTROL, SELECTITEM> form = __createFormOneData__(submitCommandableLabelId);
		form.setWindow(this);
		form.setUserSession(getUserSession());
		form.setEditable(!Crud.READ.equals(crud) && !Crud.DELETE.equals(crud));
		form.setData(data);
		if(StringUtils.isBlank(form.getTabTitle()) && data instanceof AbstractFormModel)
			form.setTabTitle(inject(LanguageBusiness.class).findClassLabelText(((AbstractFormModel<?>)data).getIdentifiable().getClass()));
		
		form.setUserDeviceType(userDeviceType);
		if(Boolean.TRUE.equals(form.getEditable())){
			form.setFieldsRequiredMessage(text("ui.form.fields.required.message"));
		}
		form.setCollection(formOneDataCollection);
		formOneDataCollection.add(form);
		return form;
	}
	public <DATA> FormOneData<DATA, FORM, ROW, LABEL, CONTROL, SELECTITEM> createFormOneData(DATA data,Crud crud){
		return createFormOneData(data, crud,"command.execute");
	}
	
	protected abstract AbstractTable<Object,?,?> __createTable__();
	
	@SuppressWarnings("unchecked")
	public AbstractTable<Object,?,?> createTable(Class<?> aDataClass,IdentifiableConfiguration configuration,Class<?> customFormModelClass,Crud crud,@SuppressWarnings("rawtypes") AbstractTable.Listener listener) {
		AbstractTable<Object,?,?> table = __createTable__();
		/*
		System.out.println("AbstractWindow.createTable() : "+customFormModelClass);
		System.out.println(aDataClass);
		System.out.println(configuration);
		System.out.println(customFormModelClass);
		*/
		if(listener!=null){
			table.getListeners().add(listener);
			listener.setTable(table);
		}
		table.setUserSession(getUserSession());
		table.setRowDataClass((Class<Object>) (customFormModelClass==null?(configuration==null?aDataClass:configuration.getFormMap().getManyRead()):customFormModelClass));
		table.setIdentifiableConfiguration(configuration);
		//table.setIdentifiableClass((Class<? extends AbstractIdentifiable>) (configuration==null?aDataClass:configuration.getIdentifiableClass()));
		
		table.setCrud(crud);
		table.setBusinessEntityInfos(UIManager.getInstance().businessEntityInfos(aDataClass));
		((AbstractBean)table).postConstruct();		
		tables.add(table);
		return table;
	}
	
	public <DATA> AbstractTable<?,?,?> createTable(Class<?> aDataClass,IdentifiableConfiguration crudConfig,Class<?> customFormModelClass,@SuppressWarnings("rawtypes") AbstractTable.Listener listener) {
		return createTable(aDataClass, crudConfig,customFormModelClass, Crud.READ,listener);
	}
	public <DATA> AbstractTable<?,?,?> createTable(Class<?> aDataClass,IdentifiableConfiguration crudConfig,Class<?> customFormModelClass) {
		return createTable(aDataClass, crudConfig,customFormModelClass, Crud.READ,null);
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
	/*
	protected UICommandable createViewCommandRequest(String labelId,Icon icon,ViewType viewType){
		UICommandable commandable = UIProvider.getInstance().createCommandable(Builder.instanciateOne().setLabelFromId(labelId).setView(viewType)
				.setIcon(icon).addDefaultParameters());
		//UICommandable commandable = UIProvider.getInstance().createCommandable(labelId, iconType,viewType);
		//commandable.setCommandRequestType(CommandRequestType.UI_VIEW);
		//commandable.addDefaultParameters();
		return commandable;
	}
	protected UICommandable createViewCommandRequest(String labelId,ViewType viewType){
		return createViewCommandRequest(labelId, null, viewType);
	}
	protected UICommandable createViewCommandRequest(String labelId,Icon iconType,Object viewId){
		UICommandable commandable = UIProvider.getInstance().createCommandable(Builder.instanciateOne().setLabelFromId(labelId).setView(viewId).setIcon(null)
				.addDefaultParameters());
		//UICommandable commandable = UIProvider.getInstance().createCommandable(labelId, iconType,viewId);
		//commandable.addDefaultParameters();
		return commandable;
	}
	protected UICommandable createViewCommandRequest(String labelId,Object viewId){
		return createViewCommandRequest(labelId, null, viewId);
	}
	*/
	@Override
	public Boolean getShowWindowHierachyMenu() {
		return windowHierachyMenu!=null && !windowHierachyMenu.getCommandables().isEmpty();
	}
	
	protected String formatUsingBusiness(Object[] objects,String separator){
		List<String> list = new ArrayList<>();
		for(Object object : objects)
			list.add(RootBusinessLayer.getInstance().getFormatterBusiness().format(object));
		return StringUtils.join(list,separator);
	}
	protected String formatUsingBusiness(Object[] objects){
		return formatUsingBusiness(objects, Constant.CHARACTER_SPACE.toString()+Constant.CHARACTER_SLASH+Constant.CHARACTER_SPACE);
	}
	
	protected String formatUsingBusiness(Collection<Object> objects,String separator){
		return formatUsingBusiness(objects.toArray(), separator);
	}
	
	protected String formatUsingBusiness(Collection<Object> objects){
		return formatUsingBusiness(objects.toArray());
	}
	
	protected String formatUsingBusiness(Object object){
		return formatUsingBusiness(new Object[]{object});
	}
	
	protected String formatPathUsingBusiness(Class<?> rootClass,Object object){
		if(object==null)
			return Constant.EMPTY_STRING;
		return formatUsingBusiness(inject(ClazzBusiness.class).findPathOf(rootClass, object));
	}

	/**/
	/**
	 * Is call when the user becomes inactive after an amount of time on the window
	 */
	public void onUserInactive(){
		//System.out.println("AbstractWindow.onUserInactive()");
		getMessageManager().message(SeverityType.WARNING, "window.idle.warning", Boolean.TRUE).showDialog();
	}
	/**
	 * Is call when the user becomes active on the window
	 */
	public void onUserActive(){
		//System.out.println("AbstractWindow.onUserActive()");
		//getMessageManager().message(SeverityType.WARNING, "window.idle.warning", Boolean.TRUE).showGrowl();
	}
	
	/**/
	
	protected org.cyk.ui.api.command.AbstractCommandable.Builder<COMANDABLE> instanciateCommandableBuilder(){
		return Builder.instanciateOne();
	}
	
	protected WindowInstanceManager getWindowsInstanceManager(){
		return WindowInstanceManager.INSTANCE;
	}
	
	/**/
	
	public static class WindowInstanceManager implements Serializable {

		private static final long serialVersionUID = 2796674164692183550L;

		public static WindowInstanceManager INSTANCE = new WindowInstanceManager();
		
		public Boolean isShowDetails(Class<?> detailsClass,AbstractIdentifiable identifiable,AbstractWindow<?, ?, ?, ?, ?, ?> window){
			return Boolean.TRUE;
		}
		
		protected Boolean isClassIn(Class<?> aClass,Class<?>...classes){
			return ArrayUtils.contains(classes, aClass);
		}
		
	}
}
