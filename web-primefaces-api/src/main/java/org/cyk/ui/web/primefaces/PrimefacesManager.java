package org.cyk.ui.web.primefaces;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.inject.Singleton;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.ContentType;
import org.cyk.system.root.model.Identifiable;
import org.cyk.ui.api.AbstractUITargetManager;
import org.cyk.ui.api.SelectItemBuilderListener;
import org.cyk.ui.api.command.CommandAdapter;
import org.cyk.ui.api.command.IconType;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.data.collector.control.Control;
import org.cyk.ui.api.data.collector.control.InputChoice;
import org.cyk.ui.web.api.JavaScriptHelper;
import org.cyk.ui.web.api.WebManager;
import org.cyk.ui.web.api.data.collector.control.WebInput;
import org.cyk.ui.web.api.data.collector.control.WebOutputSeparator;
import org.cyk.ui.web.api.data.collector.control.WebOutputText;
import org.cyk.ui.web.primefaces.data.collector.control.InputManyPickList;
import org.cyk.ui.web.primefaces.data.collector.control.InputOneCombo;
import org.cyk.ui.web.primefaces.data.collector.control.InputText;
import org.cyk.ui.web.primefaces.page.AbstractProcessManyPage;
import org.cyk.ui.web.primefaces.page.AbstractSelectManyPage;
import org.cyk.ui.web.primefaces.page.AbstractSelectOnePage;
import org.cyk.ui.web.primefaces.page.BusinessEntityFormManyPageListener;
import org.cyk.ui.web.primefaces.page.BusinessEntityFormOnePageListener;
import org.cyk.ui.web.primefaces.page.BusinessEntityFormPageListener;
import org.cyk.ui.web.primefaces.page.ConsultPageListener;
import org.cyk.ui.web.primefaces.page.PrimefacesPageListener;
import org.cyk.ui.web.primefaces.page.ReportPageListener;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice.ChoiceSet;
import org.omnifaces.util.Ajax;
import org.primefaces.context.RequestContext;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;

@Singleton @Named @Deployment(initialisationType=InitialisationType.EAGER) @Getter @Setter
public class PrimefacesManager extends AbstractUITargetManager<DynaFormModel,DynaFormRow,DynaFormLabel,DynaFormControl,SelectItem> implements Serializable {

	private static final long serialVersionUID = -3546850417728323300L;

	private static PrimefacesManager INSTANCE;
	public static final String PUSH_CHANNEL_GLOBAL = "/pushChannelGlobal";
	public static final String PUSH_CHANNEL_USER = "/pushChannelUser";
	private static final String SELECTOR_FORMAT = "@(%s)";
	private static final String CLASS_SELECTOR_FORMAT = String.format(SELECTOR_FORMAT, ".%s");
	
	public static final String CSS_CLASS_CYK_DATATABLE_SUMMARY_ROW = "ui-cyk-datatable-summaryrow";
	public static final String CSS_CLASS_DATATABLE_ROW_EVEN = "ui-datatable-even";
	public static final String CSS_CLASS_DATATABLE_ROW_ODD = "ui-datatable-odd";
	public static final String CSS_CLASS_DATATABLE_ROW_SUMMARY = "ui-datatable-summaryrow";
	public static final String CSS_CLASS_WIDGET_HEADER = "ui-widget-header";
	
	private static final String SCRIPT_START_FORMAT = "PF('%s').start();";
	private static final String SCRIPT_CANCEL_FORMAT = "PF('%s').cancel();";
	
	private final Collection<PrimefacesPageListener> pageListeners = new ArrayList<>();
	private final Collection<BusinessEntityFormPageListener<?>> businessEntityFormPageListeners = new ArrayList<>();
	private final Collection<BusinessEntityFormOnePageListener<?>> businessEntityFormOnePageListeners = new ArrayList<>();
	private final Collection<BusinessEntityFormManyPageListener<?>> businessEntityFormManyPageListeners = new ArrayList<>();
	private final Collection<ConsultPageListener<?>> consultPageListeners = new ArrayList<>();
	private final Collection<AbstractSelectOnePage.Listener<?,?>> selectOnePageListeners = new ArrayList<>();
	private final Collection<AbstractSelectManyPage.Listener<?,?>> selectManyPageListeners = new ArrayList<>();
	private final Collection<AbstractProcessManyPage.Listener<?,?>> processManyPageListeners = new ArrayList<>();
	private final Collection<ReportPageListener<?>> reportPageListeners = new ArrayList<>();
	
	public static PrimefacesManager getInstance() {
		return INSTANCE;
	}
	
	private EventBus eventBus;
	
	private String templateControlSetDefault = "/org.cyk.ui.web.primefaces/template/controlset/default.xhtml";
	private String formId = WebManager.getInstance().getFormContentFullId();
	
	private String notificationChannelSocketWidgetVar = "notificationChannelWidgetVar";
	private String notificationChannelGrowlWidgetVar = "notificationChannelGrowlWidgetVar";
	private String notificationChannelGrowlId = "notificationChannelGrowlId";
	//@Inject private LocalityBusiness localityBusiness;
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();	
		uiProvider.setControlBasePackage(InputText.class.getPackage());
		uiProvider.setCommandableClass(Commandable.class);
		uiProvider.getUiProviderListeners().add(this);
	}
	
	public EventBus getEventBus(){
		if(eventBus==null){
			if(EventBusFactory.getDefault()==null)
				logInfo("Atmosphere Event Bus cannot be initialized because Default Factory is null");
			else{
				eventBus = EventBusFactory.getDefault().eventBus();
				logInfo("Atmosphere Event Bus has been initialized");
			}
		}
		return eventBus;
	}
	
	@Override
	public ContentType contentType() {
		return ContentType.HTML;
	}
	
	/*public UICommandable createReportCommandable(AbstractIdentifiable identifiable,String reportIdentifier,String labelid,IconType iconType){
		UICommandable commandable = UIProvider.getInstance().createCommandable(labelid, iconType);
		commandable.setCommandRequestType(CommandRequestType.UI_VIEW);
		commandable.setOnClick(JavaScriptHelper.getInstance().openWindow(identifiable.getIdentifier().toString(), 
				WebNavigationManager.getInstance().reportUrl(identifiable, reportIdentifier, "pdf", Boolean.FALSE), 300, 300));
		return commandable;
	}*/
	
	public void openDialog(String outcome,Map<String, Object> dialogParams,Map<String,List<String>> urlParams){
		dialogParams.put("modal", true);  
		dialogParams.put("draggable", false);
		dialogParams.put("resizable", false);  
		dialogParams.put("contentWidth", 800); 
		dialogParams.put("contentHeight", 500); 
		
		urlParams.put(WebManager.getInstance().getRequestParameterWindowMode(),Arrays.asList(WebManager.getInstance().getRequestParameterWindowModeDialog()));
		
		RequestContext.getCurrentInstance().openDialog(outcome, dialogParams, urlParams); 
	}
	
	@Override
	public void controlInstanceCreated(Control<?, ?, ?, ?, ?> control) {
		super.controlInstanceCreated(control);
		if(control instanceof WebOutputText<?, ?, ?, ?>){
			if(!(control instanceof WebOutputSeparator<?, ?,?,?>))
				((WebOutputText<?, ?, ?, ?>)control).getCss().addClass("cyk-ui-form-block-header-title ui-state-highlight");
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void choices(InputChoice<?, ?, ?, ?, ?, ?> inputChoice, Object data,Field field, List<Object> list) {
		super.choices(inputChoice, data, field, list);
		if(inputChoice instanceof InputManyPickList){
			InputManyPickList<Object> pickList = (InputManyPickList<Object>) inputChoice;
			pickList.getDualListModel().setSource(list);
			List<Object> targetList = (List<Object>) commonUtils.readField(data, field, Boolean.TRUE);
			try {
				FieldUtils.writeField(field, data, targetList);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			pickList.getDualListModel().setTarget(targetList);
		}else if(inputChoice instanceof InputOneCombo){
			if( !list.isEmpty() && ((SelectItem)list.get(0)).getValue()!=null )
				list.add(0, WebManager.getInstance().getNullSelectItem(field.getType(), SelectItemBuilderListener.DEFAULT));
		}
	}
	/*
	@Override
	protected Collection<AbstractIdentifiable> findAll(Class<? extends AbstractIdentifiable> aClass,InputChoice<?, ?, ?, ?, ?, ?> inputChoice, Object data, Field field) {
		Collection<AbstractIdentifiable> collection = null;
		if(field.getName().equals("nationality")){
			collection = new ArrayList<>();
			for(Locality locality : localityBusiness.findByType(RootBusinessLayer.getInstance().getCountryLocalityType()))
				collection.add(locality);
			return collection;
		}
		return super.findAll(aClass, inputChoice, data, field);
	}*/
	
	@Override
	protected Boolean itemWrapper(InputChoice<?, ?, ?, ?, ?, ?> inputChoice) {
		return !(inputChoice instanceof InputManyPickList<?>);
	}

	@Override
	protected SelectItem item(AbstractIdentifiable identifiable) {
		return WebManager.getInstance().getSelectItem(identifiable); 
	}
	
	@Override
	protected SelectItem item(Enum<?> anEnum) {
		return WebManager.getInstance().getSelectItem(anEnum); 
	}
	
	@Override
	protected Collection<SelectItem> getChoiceSetSelectItems(ChoiceSet choiceSet,Boolean nullable) {
		Collection<SelectItem> collection = new ArrayList<>();
		switch(choiceSet){
		case YES_NO: 
			collection.addAll(Boolean.TRUE.equals(nullable) ? WebManager.getInstance().getBooleanSelectItems() : WebManager.getInstance().getBooleanSelectItemsNoNull());
			break;
		case AUTO:
			break;
		}
		return collection;
	}
	
	public void connectSocket(String socket,String channel){
		RequestContext.getCurrentInstance().execute("PF('"+socket+"').connect('"+channel+ "');");
	}
	
	public void disconnectSocket(String socket){
		RequestContext.getCurrentInstance().execute("PF('"+socket+"').disconnect();");
	}
	
	public String classSelector(WebInput<?, ?, ?, ?> input){
		if(input==null)
			return "";
		return String.format(CLASS_SELECTOR_FORMAT, input.getUniqueCssClass());
	}
	
	public String getStartScript(String widgetVar){
		return String.format(SCRIPT_START_FORMAT, widgetVar);
	}
	public String getCancelScript(String widgetVar){
		return String.format(SCRIPT_CANCEL_FORMAT, widgetVar);
	}

	public Collection<BusinessEntityFormPageListener<?>> getBusinessEntityFormPageListeners(Class<? extends Identifiable<?>> aClass){
		Collection<BusinessEntityFormPageListener<?>> results = new ArrayList<>();
		if(aClass!=null)
			for(BusinessEntityFormPageListener<?> listener : businessEntityFormPageListeners)
				if(listener.getEntityTypeClass().isAssignableFrom(aClass))
					results.add(listener);
		return results;
	}
	
	public Collection<BusinessEntityFormOnePageListener<?>> getBusinessEntityFormOnePageListeners(Class<? extends Identifiable<?>> aClass){
		Collection<BusinessEntityFormOnePageListener<?>> results = new ArrayList<>();
		if(aClass!=null)
			for(BusinessEntityFormOnePageListener<?> listener : businessEntityFormOnePageListeners)
				if(listener.getEntityTypeClass().isAssignableFrom(aClass))
					results.add(listener);
		return results;
	}
	
	public Collection<BusinessEntityFormManyPageListener<?>> getBusinessEntityFormManyPageListeners(Class<? extends Identifiable<?>> aClass){
		Collection<BusinessEntityFormManyPageListener<?>> results = new ArrayList<>();
		if(aClass!=null)
			for(BusinessEntityFormManyPageListener<?> listener : businessEntityFormManyPageListeners)
				if(listener.getEntityTypeClass().isAssignableFrom(aClass))
					results.add(listener);
		return results;
	}
	
	public Collection<ConsultPageListener<?>> getConsultPageListeners(Class<? extends Identifiable<?>> aClass){
		Collection<ConsultPageListener<?>> results = new ArrayList<>();
		if(aClass!=null)
			for(ConsultPageListener<?> listener : consultPageListeners)
				if(listener.getEntityTypeClass().isAssignableFrom(aClass))
					results.add(listener);
		return results;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<AbstractSelectOnePage.Listener<?,Object>> getSelectOnePageListeners(Class<? extends Identifiable<?>> aClass){
		Collection<AbstractSelectOnePage.Listener<?,Object>> results = new ArrayList<>();
		if(aClass!=null)
			for(AbstractSelectOnePage.Listener<?,?> listener : selectOnePageListeners)
				if(listener.getEntityTypeClass().isAssignableFrom(aClass))
					results.add((AbstractSelectOnePage.Listener<?, Object>) listener);
		return results;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<AbstractSelectManyPage.Listener<?,Object>> getSelectManyPageListeners(Class<? extends Identifiable<?>> aClass){
		Collection<AbstractSelectManyPage.Listener<?,Object>> results = new ArrayList<>();
		if(aClass!=null)
			for(AbstractSelectManyPage.Listener<?,?> listener : selectManyPageListeners)
				if(listener.getEntityTypeClass().isAssignableFrom(aClass))
					results.add((AbstractSelectManyPage.Listener<?, Object>) listener);
		return results;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<AbstractProcessManyPage.Listener<?,Object>> getProcessManyPageListeners(Class<? extends Identifiable<?>> aClass){
		Collection<AbstractProcessManyPage.Listener<?,Object>> results = new ArrayList<>();
		if(aClass!=null)
			for(AbstractProcessManyPage.Listener<?,?> listener : processManyPageListeners)
				if(listener.getEntityTypeClass().isAssignableFrom(aClass))
					results.add((AbstractProcessManyPage.Listener<?, Object>) listener);
		return results;
	}
	
	public void configureProgressBar(UICommandable commandable,final String progressBarWidgetVar){
		if(commandable.getCommand().getExecutionProgress()==null)
			return;
		//Starts on click
		commandable.setOnClick(JavaScriptHelper.getInstance().add(commandable.getOnClick(), PrimefacesManager.getInstance().getStartScript(progressBarWidgetVar)));
		//Cancel on execution ends
		commandable.getCommand().getCommandListeners().add(new CommandAdapter(){
			private static final long serialVersionUID = -4119943624542439662L;	
			@Override
			public void serve(UICommand command, Object parameter) {
				command.getExecutionProgress().clear();
			}
			@Override
			public Boolean notifyAfterServe(UICommand command,AfterServeState state) {
				Ajax.oncomplete(PrimefacesManager.getInstance().getCancelScript(progressBarWidgetVar));
				return super.notifyAfterServe(command, state);
			}
		});
	}
	
	public void configureProgressBar(UICommandable commandable){
		configureProgressBar(commandable, WebManager.getInstance().getProgressBarWidgetId());
	}
	
	public String getIcon(IconType iconType){
		if(iconType==null)
			return null;
		switch(iconType){
		case ACTION_ADD:return "ui-icon-plus";
		case ACTION_CANCEL:return "ui-icon-close";
		case ACTION_OPEN:return "ui-icon-folder-open";
		case ACTION_REMOVE:return "ui-icon-trash";
		case ACTION_ADMINISTRATE:return "ui-icon-gear";
		case ACTION_HELP:return "ui-icon-help";
		case ACTION_APPLY:return  "fa fa-save" /*"fa fa-check" /*"ui-icon-check"*/;
		case ACTION_EDIT:return "ui-icon-pencil";
		case ACTION_GO_BACK:return "ui-icon-arrow-e";
		case ACTION_OK:return "fa fa-check";
		case ACTION_SAVE:return "fa fa-save";
		case ACTION_SEARCH:return "ui-icon-search";
		case ACTION_PREVIEW:return "ui-icon-image";
		case ACTION_LOGOUT:return "ui-icon-extlink";
		case ACTION_EXPORT:return "ui-icon-document";
		case ACTION_PRINT:return "ui-icon-print";
		case ACTION_CLEAR: return "ui-icon-trash";
		case ACTION_EXPORT_EXCEL: return "ui-icon-";
		case ACTION_EXPORT_PDF: return "ui-icon-";
		case ACTION_SET: return "ui-icon-wrench";
		case ACTION_DOWNLOAD: return "fa fa-download";
		case ACTION_UPDATE: return "fa fa-edit";
		case ACTION_DELETE: return "fa fa-remove";
		
		case THING_APPLICATION: return "ui-icon-";
		case THING_CALENDAR: return "ui-icon-calendar";
		case THING_CONTROLPANEL: return "ui-icon-gear";
		case THING_HELP: return "ui-icon-help";
		case THING_LICENCE: return "ui-icon-document";
		case THING_LIST: return "ui-icon-document";
		case THING_NOTIFICATIONS: return "ui-icon-flag";
		case THING_REPORT: return "ui-icon-document";
		case THING_SECURITY: return "ui-icon-key";
		case THING_TOOLS: return "ui-icon-wrench";
		case THING_URL: return "fa fa-link";
		case THING_ROLE: return "fa fa-lock";
		case THING_USERACCOUNT: return "fa fa-key";
		case THING_HOME: return "ui-icon-home";
		case THING_CONNECTED: return "ui-icon-newin";
		case THING_FOLDER_COLLAPSED: return "fa fa-folder";
		case THING_FOLDER_EXPANDED: return "fa fa-folder-open";
		case THING_TABLE: return "fa fa-table";
		case THING_MONEY: return "fa fa-money";
		case THING_AGENDA:return "fa fa-calendar-o";
		
		case PERSON:return "fa fa-user";
		default:return null;
		}
	}
	
}
