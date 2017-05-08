package org.cyk.ui.web.primefaces;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.system.root.business.api.pattern.tree.AbstractDataTreeNodeBusiness;
import org.cyk.system.root.business.api.userinterface.style.CascadeStyleSheetBusiness;
import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.business.impl.BusinessInterfaceLocator;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.AbstractModelElement;
import org.cyk.system.root.model.ContentType;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeNode;
import org.cyk.ui.api.AbstractUITargetManager;
import org.cyk.ui.api.SelectItemBuilderListener;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.UIProvider;
import org.cyk.ui.api.command.CommandAdapter;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.data.collector.control.Control;
import org.cyk.ui.api.data.collector.control.InputChoice;
import org.cyk.ui.api.data.collector.control.InputManyAutoComplete;
import org.cyk.ui.api.data.collector.control.InputManyChoice;
import org.cyk.ui.api.data.collector.control.InputOneAutoComplete;
import org.cyk.ui.web.api.JavaScriptHelper;
import org.cyk.ui.web.api.WebManager;
import org.cyk.ui.web.api.data.collector.control.WebInput;
import org.cyk.ui.web.api.data.collector.control.WebOutputSeparator;
import org.cyk.ui.web.api.data.collector.control.WebOutputText;
import org.cyk.ui.web.primefaces.data.collector.control.InputAutoCompleteCommon;
import org.cyk.ui.web.primefaces.data.collector.control.InputManyPickList;
import org.cyk.ui.web.primefaces.data.collector.control.InputOneCascadeList;
import org.cyk.ui.web.primefaces.data.collector.control.InputOneCombo;
import org.cyk.ui.web.primefaces.data.collector.control.InputText;
import org.cyk.ui.web.primefaces.page.DetailsConfiguration;
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

import lombok.Getter;
import lombok.Setter;

@Singleton @Named @Deployment(initialisationType=InitialisationType.EAGER) @Getter @Setter
public class PrimefacesManager extends AbstractUITargetManager<DynaFormModel,DynaFormRow,DynaFormLabel,DynaFormControl,SelectItem,String> implements Serializable {

	private static final long serialVersionUID = -3546850417728323300L;

	private static PrimefacesManager INSTANCE;
	
	private static final Map<Class<? extends AbstractOutputDetails<?>>, DetailsConfiguration> DETAILS_CONFIGURATION_MAP = new HashMap<>();
	private static final DetailsConfiguration DETAILS_CONFIGURATION = new DetailsConfiguration();
	public static final Map<Class<?>, InputAutoCompleteCommon.Listener<?>> INPUT_AUTO_COMPLETE_COMMON_LISTENER_MAP = new HashMap<>();
		
	public static final String PUSH_CHANNEL_GLOBAL = "/pushChannelGlobal";
	public static final String PUSH_CHANNEL_USER = "/pushChannelUser";
	private static final String SELECTOR_FORMAT = "@(%s)";
	private static final String CLASS_SELECTOR_FORMAT = String.format(SELECTOR_FORMAT, ".%s");
	
	public static final String CSS_CLASS_CYK_DATATABLE_SUMMARY_ROW = "ui-cyk-datatable-summaryrow";
	public static final String CSS_CLASS_DATATABLE_ROW_EVEN = "ui-datatable-even";
	public static final String CSS_CLASS_DATATABLE_ROW_ODD = "ui-datatable-odd";
	public static final String CSS_CLASS_DATATABLE_ROW_SUMMARY = "ui-datatable-summaryrow";
	public static final String CSS_CLASS_WIDGET_HEADER = "ui-widget-header";
	public static final String CSS_CLASS_TABLE_VERTICAL_HEADER = "tableVerticalHeaderStyleClass";
	
	private static final String SCRIPT_START_FORMAT = "PF('%s').start();";
	private static final String SCRIPT_CANCEL_FORMAT = "PF('%s').cancel();";
	
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
		UIProvider uiProvider = inject(UIProvider.class);
		uiProvider.setControlBasePackage(InputText.class.getPackage());
		uiProvider.setCommandableClass(Commandable.class);
		uiProvider.getUiProviderListeners().add(this);
		
		UIManager.getInstance().setIconIdentifier(FontAwesomeIconSet.INSTANCE);
		ContentType.DEFAULT = ContentType.HTML;
	}
	
	public EventBus getEventBus(){
		/*if(eventBus==null){
			if(EventBusFactory.getDefault()==null)
				logInfo("Atmosphere Event Bus cannot be initialized because Default Factory is null");
			else{
				eventBus = EventBusFactory.getDefault().eventBus();
				logInfo("Atmosphere Event Bus has been initialized");
			}
		}*/
		return eventBus;
	}
	
	@Override
	public ContentType contentType() {
		return ContentType.HTML;
	}
		
	public void openDialog(String outcome,Map<String, Object> dialogParams,Map<String,List<String>> urlParams){
		dialogParams.put("modal", true);  
		dialogParams.put("draggable", false);
		dialogParams.put("resizable", false);  
		dialogParams.put("contentWidth", 800); 
		dialogParams.put("contentHeight", 500); 
		
		urlParams.put(UniformResourceLocatorParameter.WINDOW_MODE,Arrays.asList(UniformResourceLocatorParameter.WINDOW_MODE_DIALOG));
		
		RequestContext.getCurrentInstance().openDialog(outcome, dialogParams, urlParams); 
	}
	
	@Override
	public void controlInstanceCreated(Control<?, ?, ?, ?, ?> control) {
		super.controlInstanceCreated(control);
		if(control instanceof WebOutputText<?, ?, ?, ?>){
			if(!(control instanceof WebOutputSeparator<?, ?,?,?>))
				inject(CascadeStyleSheetBusiness.class).addClasses(((WebOutputText<?, ?, ?, ?>)control).getCss(),"cyk-ui-form-block-header-title ui-state-highlight");
		}else if(control instanceof WebInput){
			
		}
	}
	
	@Override
	public void controlCreated(Control<?, ?, ?, ?, ?> control) {
		super.controlCreated(control);
		if(control instanceof WebInput){
			if(control instanceof org.cyk.ui.web.primefaces.data.collector.control.InputOneAutoComplete<?>){
				org.cyk.ui.web.primefaces.data.collector.control.InputOneAutoComplete<?> autoComplete = 
						(org.cyk.ui.web.primefaces.data.collector.control.InputOneAutoComplete<?>) control;
				inject(CascadeStyleSheetBusiness.class).addClasses(autoComplete.getCommon().getResultsContainerCascadeStyleSheet()
						,UIManager.RESULTS_CONTAINER_CLASS_PREFIX+autoComplete.getCss().getUniqueClass());
			}
		} 
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void choices(InputChoice<?, ?, ?, ?, ?, ?> inputChoice, Object data,Field field, List<Object> list) {
		super.choices(inputChoice, data, field, list);
		Class<?> fieldType = commonUtils.getFieldType(data.getClass(), field);
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
			if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputChoice.class).nullable() && !list.isEmpty() && ((SelectItem)list.get(0)).getValue()!=null )
				list.add(0, WebManager.getInstance().getNullSelectItem(field.getType(), SelectItemBuilderListener.DEFAULT));
		}else if(inputChoice instanceof InputOneAutoComplete || inputChoice instanceof InputManyAutoComplete) {
			org.cyk.ui.api.data.collector.control.InputAutoCompleteCommon<?> inputAutoCompleteCommon = null;
			if(inputChoice instanceof InputOneAutoComplete)
				inputAutoCompleteCommon = ((InputOneAutoComplete<?, ?, ?, ?, ?, ?, ?>)inputChoice).getCommon();
			else if(inputChoice instanceof InputManyAutoComplete)
				inputAutoCompleteCommon = ((InputManyAutoComplete<?, ?, ?, ?, ?, ?,?>)inputChoice).getCommon();
			if(inputAutoCompleteCommon!=null){
				Class<AbstractIdentifiable> identifiableClass;
				if(inputChoice instanceof InputManyChoice){
					identifiableClass = (Class<AbstractIdentifiable>) ((ParameterizedType)inputChoice.getField().getGenericType()).getActualTypeArguments()[0];
				}else
					identifiableClass = (Class<AbstractIdentifiable>) fieldType;
				inputAutoCompleteCommon.setIdentifiableClass(identifiableClass);
				InputAutoCompleteCommon.Listener listener = INPUT_AUTO_COMPLETE_COMMON_LISTENER_MAP.get(identifiableClass);
				if(listener == null)
					listener = new InputAutoCompleteCommon.Listener.Adapter.Default();
				((InputAutoCompleteCommon<?>)inputAutoCompleteCommon).getAutoCompleteListeners().add(listener);
			}
		}else if(inputChoice instanceof InputOneCascadeList){
			if(AbstractDataTreeNode.class.isAssignableFrom(fieldType)){
				AbstractDataTreeNodeBusiness<?> business = (AbstractDataTreeNodeBusiness<?>) inject(BusinessInterfaceLocator.class)
						.injectTyped((Class<AbstractIdentifiable>)fieldType);
				list.addAll(WebManager.getInstance().getSelectItemsFromNodes((List<? extends AbstractDataTreeNode>) business.findHierarchies()));	
			}
			
		}
	}
	
	@Override
	protected Boolean itemWrapper(InputChoice<?, ?, ?, ?, ?, ?> inputChoice) {
		return !(inputChoice instanceof InputManyPickList<?>);
	}

	@Override
	protected SelectItem item(AbstractModelElement modelElement) {
		return WebManager.getInstance().getSelectItem(modelElement); 
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
		return String.format(CLASS_SELECTOR_FORMAT, input.getCss().getUniqueClass());
	}
	
	public String getStartScript(String widgetVar){
		return String.format(SCRIPT_START_FORMAT, widgetVar);
	}
	public String getCancelScript(String widgetVar){
		return String.format(SCRIPT_CANCEL_FORMAT, widgetVar);
	}
	
	public void configureProgressBar(UICommandable commandable,final String progressBarWidgetVar){
		if(commandable.getCommand().getExecutionProgress()==null)
			return;
		//Starts on click
		commandable.setOnClick(JavaScriptHelper.getInstance().add(commandable.getOnClick(), getStartScript(progressBarWidgetVar)));
		//Cancel on execution ends
		commandable.getCommand().getCommandListeners().add(new CommandAdapter(){
			private static final long serialVersionUID = -4119943624542439662L;	
			@Override
			public void serve(UICommand command, Object parameter) {
				command.getExecutionProgress().clear();
			}
			@Override
			public Boolean notifyAfterServe(UICommand command,AfterServeState state) {
				Ajax.oncomplete(getCancelScript(progressBarWidgetVar));
				return super.notifyAfterServe(command, state);
			}
		});
	}
	
	public void configureProgressBar(UICommandable commandable){
		configureProgressBar(commandable, WebManager.getInstance().getProgressBarWidgetId());
	}
	
	/*private static DetailsConfiguration getExactDetailsConfiguration(Class<?> detailsClass){
		for(Entry<Class<? extends AbstractOutputDetails<?>>, DetailsConfiguration> entry : DETAILS_CONFIGURATION_MAP.entrySet())
			if(entry.getKey().equals(detailsClass))
				return entry.getValue();
		return null;
	}*/
	
	@SuppressWarnings("unchecked")
	public static void registerDetailsConfiguration(Class<?> detailsClass,DetailsConfiguration detailsConfiguration){
		detailsConfiguration.setIdentifier(detailsClass.getName());
		DetailsConfiguration current = DETAILS_CONFIGURATION_MAP.get(detailsClass);
		if(current!=null)
			System.out.println("   ######   Current Details configuration "+current+" will be replace by "+detailsConfiguration+"   ######");
		//System.out.println("PrimefacesManager.registerDetailsConfiguration() : "+ToStringBuilder.reflectionToString(DETAILS_CONFIGURATION_MAP)+" : "+DETAILS_CONFIGURATION_MAP.size());
		DETAILS_CONFIGURATION_MAP.put((Class<? extends AbstractOutputDetails<?>>) detailsClass, detailsConfiguration);
	}
	
	public static DetailsConfiguration getDetailsConfiguration(Class<?> detailsClass){
		if(DETAILS_CONFIGURATION_MAP.containsKey(detailsClass)){
			return DETAILS_CONFIGURATION_MAP.get(detailsClass);
		}
		//Super class match
		for(Entry<Class<? extends AbstractOutputDetails<?>>, DetailsConfiguration> entry : DETAILS_CONFIGURATION_MAP.entrySet())
			if(entry.getKey().isAssignableFrom(detailsClass)){
				return entry.getValue();
			}
		return DETAILS_CONFIGURATION;
	}
	
	public static Collection<DetailsConfiguration> getDetailsConfigurations(){
		return DETAILS_CONFIGURATION_MAP.values();
	}
}
