package org.cyk.ui.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.BusinessManager;
import org.cyk.system.root.business.api.ClazzBusiness;
import org.cyk.system.root.business.api.FormatterBusiness;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.geography.ContactCollectionBusiness;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.mathematics.NumberBusiness;
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.business.api.time.TimeBusiness;
import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.CommonBusinessAction;
import org.cyk.system.root.model.ContentType;
import org.cyk.system.root.model.party.Application;
import org.cyk.ui.api.config.IdentifiableConfiguration;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.api.model.AbstractItemCollectionItem;
import org.cyk.utility.common.AbstractMethod;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.ListenerUtils;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.cdi.AbstractStartupBean;
import org.cyk.utility.common.cdi.BeanAdapter;

import lombok.Getter;
import lombok.Setter;

@Singleton @Getter @Setter @Named(value="uiManager") @Deployment(initialisationType=InitialisationType.EAGER)
public class UIManager extends AbstractStartupBean implements Serializable {

	private static final long serialVersionUID = -9062523105492591265L;
	
	public static ContentType CONTENT_TYPE = ContentType.TEXT;
	static{
		IdentifierProvider.COLLECTION.add(new IdentifierProvider.Adapter.Default());
	}
	public static final String CHARACTER_ENCODING = Constant.ENCODING_UTF8;
	
	private static final Map<Class<?>,BusinessEntityInfos> BUSINESS_ENTITIES_INFOS_MAP = new HashMap<>();
	private static final Map<Class<?>,SelectItemBuilderListener> SELECTITEM_BUILD_LISTENER_MAP = new HashMap<>();
	private static final Map<Class<? extends AbstractIdentifiable>,IdentifiableConfiguration> IDENTIFIABLE_CONFIGURATION_MAP = new HashMap<>();
	public static final Map<String, Class<?>> FORM_MODEL_MAP = new HashMap<>();
	
	public static final String PUSH_CHANNEL_VAR = "channel";
	public static final String PUSH_RECEIVER_VAR = "receiver";
	public static final String PUSH_NOTIFICATION_CHANNEL = "notification_channel";
	
	private static UIManager INSTANCE;
	public static UIManager getInstance() {
		return INSTANCE;
	}
	
	//private static final Collection<BusinessListener> businessListeners = new ArrayList<>();
	public static final Collection<ComponentCreationListener> componentCreationListeners = new ArrayList<>();
	@SuppressWarnings("rawtypes")
	private static final Collection<AbstractApplicationUIManager> applicationUIManagers = new ArrayList<>();
	
	private final String selectOneViewSuffix="SelectOneView";
	private final String selectManyViewSuffix="SelectManyView";
	private final String processManyViewSuffix="ProcessManyView";
	private final String consultViewSuffix="ConsultView";
	private final String listViewSuffix="ListView";
	private final String editViewSuffix="EditView";
	private final String createManyViewSuffix="CreateManyView";
	private String selectItemNullLabel;
	
	private final String pushNotificationChannel=PUSH_NOTIFICATION_CHANNEL;
	
	private CollectionLoadMethod collectionLoadMethod;
	
	@Inject private FormatterBusiness formatterBusiness;
	@Inject private LanguageBusiness languageBusiness;
	@Inject private ContactCollectionBusiness contactCollectionBusiness;
	@Inject private ApplicationBusiness applicationBusiness;
	@Inject private BusinessManager businessManager;
	@Inject private GenericBusiness genericBusiness;
	@Inject private FileBusiness fileBusiness;
	@Inject private TimeBusiness timeBusiness;
	@Inject private NumberBusiness numberBusiness;
	@Inject private PersonBusiness personBusiness;
	@Inject private ClazzBusiness clazzBusiness;
	
	private Locale locale = Locale.FRENCH;
	//private final Collection<Listener> uiManagerListeners = new ArrayList<>();
	/* constants */
	private final Map<String,BusinessEntityInfos> entitiesRequestParameterIdMap = new HashMap<>();
	
	@Getter @Setter protected Icon.GetIdentifierListener<String> iconIdentifier;
	
	private String windowFooter;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		INSTANCE = this;
		languageBusiness.registerResourceBundle("org.cyk.ui.api.resources.message",getClass().getClassLoader());
		languageBusiness.registerResourceBundle("org.cyk.ui.api.resources.field",getClass().getClassLoader());
		
		selectItemNullLabel = languageBusiness.findText("input.choice.select.message");
		
		//windowFooter = getLanguageBusiness().findText("window.layout.footer",new Object[]{getApplication()==null?"CYK":getApplication().getName()});
		windowFooter = getLanguageBusiness().findText("window.layout.footer",new Object[]{"CYK Systems"});
		BUSINESS_ENTITIES_INFOS_MAP.clear();
		for(BusinessEntityInfos infos : applicationBusiness.findBusinessEntitiesInfos()){
			BUSINESS_ENTITIES_INFOS_MAP.put(infos.getClazz(), infos);
			infos.getUserInterface().setLabel(text(infos.getUserInterface().getLabelId()));//for those registered late
		}
		
		for(Entry<Class<?>, BusinessEntityInfos> entry : BUSINESS_ENTITIES_INFOS_MAP.entrySet()){
			registerClassKey(entry.getValue());
		}
		
		//IdentifiableConfiguration configuration = new IdentifiableConfiguration(Person.class,DefaultPersonEditFormModel.class,DefaultPersonOutputDetails.class,null,PersonEditManyForm.class,PersonQueryManyFormModel.class);
		//registerConfiguration(configuration);
		
		collectionLoadMethod = new CollectionLoadMethod() {
			private static final long serialVersionUID = -4679710339375267115L;
			@SuppressWarnings("unchecked")
			@Override
			protected Collection<Object> __execute__(Class<Object> parameter) {
				Class<AbstractIdentifiable> c = null;
				c = (Class<AbstractIdentifiable>) commonUtils.classFormName(parameter.getName());
				Collection<AbstractIdentifiable> r = genericBusiness.use(c).find().all();
				Collection<Object> l = new ArrayList<>();
				for(AbstractIdentifiable i : r)
					l.add(i);
				return l;
			}
		};
		
	}
	
	@SuppressWarnings("rawtypes")
	public Collection<AbstractApplicationUIManager> getApplicationUImanagers() {
		return applicationUIManagers;
	}
	
	@SuppressWarnings("rawtypes")
	public void registerApplicationUImanager(AbstractApplicationUIManager applicationUIManager){
		applicationUIManagers.add(applicationUIManager);
	}
	
	public Application getApplication(){
		return RootBusinessLayer.getInstance().getApplication();
	}
	
	public void configBusinessIdentifiable(BusinessEntityInfos businessEntityInfos,String iconName,String iconExtension,String consultViewId,String listViewId,String editViewId
			,String createManyViewId,String selectOneViewId,String selectManyViewId,String processManyViewId){
		businessEntityInfos.getUserInterface().setIconName(iconName);
		businessEntityInfos.getUserInterface().setIconExtension(iconExtension);
		businessEntityInfos.getUserInterface().setConsultViewId(consultViewId);
		businessEntityInfos.getUserInterface().setListViewId(listViewId);
		businessEntityInfos.getUserInterface().setEditViewId(editViewId);
		businessEntityInfos.getUserInterface().setCreateManyViewId(createManyViewId);
		businessEntityInfos.getUserInterface().setSelectOneViewId(selectOneViewId);
		businessEntityInfos.getUserInterface().setSelectManyViewId(selectManyViewId);
		businessEntityInfos.getUserInterface().setProcessManyViewId(processManyViewId);
	}
	
	public void configBusinessIdentifiable(Class<?> clazz,String iconName,String iconExtension,String consultViewId,String listViewId,String editViewId,String createManyViewId
			,String selectOneViewId,String selectManyViewId,String processManyViewId){
		BusinessEntityInfos businessEntityInfos = businessEntityInfos(clazz);
		configBusinessIdentifiable(businessEntityInfos, iconName, iconExtension, consultViewId, listViewId, editViewId,createManyViewId,selectOneViewId,selectManyViewId
				,processManyViewId);
	}
	public void configBusinessIdentifiable(Class<?> clazz,String iconName){
		BusinessEntityInfos businessEntityInfos = businessEntityInfos(clazz);
		configBusinessIdentifiable(clazz, iconName, "png", businessEntityInfos.getVarName()+consultViewSuffix,
				businessEntityInfos.getVarName()+listViewSuffix,businessEntityInfos.getVarName()+editViewSuffix,businessEntityInfos.getVarName()+createManyViewSuffix
				,businessEntityInfos.getVarName()+selectOneViewSuffix,businessEntityInfos.getVarName()+selectManyViewSuffix
				,businessEntityInfos.getVarName()+processManyViewSuffix);
	}
	
	public void useCustomConsultView(Class<?> clazz){
		BusinessEntityInfos businessEntityInfos = businessEntityInfos(clazz);
		businessEntityInfos.getUserInterface().setConsultViewId(businessEntityInfos.getVarName()+consultViewSuffix);
	}
	
	public String identifiableConsultViewId(AbstractIdentifiable identifiable){
		return businessEntityInfos(identifiable.getClass()).getUserInterface().getConsultViewId();
	}
	
	public String identifiableEditViewId(AbstractIdentifiable identifiable){
		return businessEntityInfos(identifiable.getClass()).getUserInterface().getEditViewId();
	}
	
	public String text(String code){
		return languageBusiness.findText(code);
	}
	
	public String textOneParam(String code,Object value){
		return languageBusiness.findText(code,new Object[]{value});
	}
	
	public String textInputValueRequired(String nameId){
		return languageBusiness.findText("input.value.required", new Object[]{text(nameId)});
	}
	public String getItemCollectionItemInputValueRequiredMessage(AbstractItemCollectionItem<?> item,String nameId){
		return item.getLabel()+Constant.CHARACTER_SPACE+Constant.CHARACTER_HYPHEN+Constant.CHARACTER_SPACE+languageBusiness.findText("input.value.required", new Object[]{text(nameId)});
	}
	
	public void registerClassKey(BusinessEntityInfos...theClasses){
		for(BusinessEntityInfos infos : theClasses){
			BusinessEntityInfos businessEntityInfos = entitiesRequestParameterIdMap.get(infos.getIdentifier());
			if(businessEntityInfos==null){
				entitiesRequestParameterIdMap.put(infos.getIdentifier(),infos);
				if(infos.getModelBeanAnnotation()!=null && CrudStrategy.BUSINESS.equals(infos.getModelBeanAnnotation().crudStrategy()))
					configBusinessIdentifiable(infos.getClazz(),infos.getModelBeanAnnotation().uiIconName());
			}
		}
	}
	//TODO to be renamed
	public BusinessEntityInfos classFromKey(String key){
		return entitiesRequestParameterIdMap.get(key);
	}
	
	@SuppressWarnings("unchecked")
	public Class<? extends AbstractOutputDetails<?>> getOutputDetailsClassFromKey(String key){
		return (Class<? extends AbstractOutputDetails<?>>) clazzBusiness.find(key).getClazz();
	}
	
	public String keyFromClass(BusinessEntityInfos aBusinessEntityInfos){
		for(Entry<String, BusinessEntityInfos> entry : entitiesRequestParameterIdMap.entrySet())
			if(entry.getValue().equals(aBusinessEntityInfos))
				return entry.getKey();
		return null;
	}
	
	public String keyFromClass(Class<?> aClass){
		for(Entry<String, BusinessEntityInfos> entry : entitiesRequestParameterIdMap.entrySet())
			if(entry.getValue().getClazz().equals(aClass))
				return entry.getKey();
		return null;
	}
	
	
	public void registerConfiguration(IdentifiableConfiguration configuration){
		IDENTIFIABLE_CONFIGURATION_MAP.put(configuration.getClazz(), configuration);
	}
	public IdentifiableConfiguration findConfiguration(Class<? extends AbstractIdentifiable> aClass,Boolean extendToSuperClassIfNull){
		IdentifiableConfiguration config = IDENTIFIABLE_CONFIGURATION_MAP.get(aClass);
		if(config == null && Boolean.TRUE.equals(extendToSuperClassIfNull))
			for(Entry<Class<? extends AbstractIdentifiable>, IdentifiableConfiguration> entry : IDENTIFIABLE_CONFIGURATION_MAP.entrySet())
				if(entry.getKey().isAssignableFrom(aClass) && Boolean.TRUE.equals(entry.getValue().getUsableByChild())){
					config = entry.getValue();
					break;
				}
		return config;
	}
	
	public void registerSelectItemBuildListener(Class<?> aClass,SelectItemBuilderListener listener){
		SELECTITEM_BUILD_LISTENER_MAP.put(aClass, listener);
	}
	public SelectItemBuilderListener findSelectItemBuildListener(Class<?> aClass){
		SelectItemBuilderListener listener = SELECTITEM_BUILD_LISTENER_MAP.get(aClass);
		return listener==null?SelectItemBuilderListener.DEFAULT:listener;
	}
	
	/**/
	
	public BusinessEntityInfos businessEntityInfos(Class<?> aClass){
		for(Entry<Class<?>, BusinessEntityInfos> entry : BUSINESS_ENTITIES_INFOS_MAP.entrySet())
			if(entry.getValue().getClazz().equals(aClass))
				return entry.getValue();
		//We can call the service for more
		return null;
	}
	
	public Integer collectionSize(Collection<?> collection){
		return collection.size();
	}
		
	/**/
	public static abstract class AbstractLoadDataMethod<T> extends AbstractMethod<Collection<T>, Class<T>> {
		private static final long serialVersionUID = 1175379361365502915L;
	}
	
	
	public static abstract class CollectionLoadMethod extends AbstractLoadDataMethod<Object>{
		private static final long serialVersionUID = 7640865186916095212L;
	}
	
	public Boolean isMobileDevice(UserDeviceType userDeviceType){
		return userDeviceType==null || !UserDeviceType.DESKTOP.equals(userDeviceType);
	}
	
	public String format(Object object){
		return RootBusinessLayer.getInstance().getFormatterBusiness().format(object);
	}
	
	public String getViewIdentifier(final Class<?> aClass, final CommonBusinessAction commonBusinessAction, final Boolean one) {
		return ListenerUtils.getInstance().getString(IdentifierProvider.COLLECTION, new ListenerUtils.StringMethod<IdentifierProvider>() {
			@Override
			public String execute(IdentifierProvider listener) {
				return listener.getView(aClass, commonBusinessAction, one);
			}
		});
	}
	
	public String getViewIdentifier(AbstractIdentifiable identifiable, final CommonBusinessAction commonBusinessAction) {
		return getViewIdentifier(identifiable.getClass(), commonBusinessAction, Boolean.TRUE);
	}
	
	public String getViewDynamic(final CommonBusinessAction commonBusinessAction, final Boolean one) {
		return ListenerUtils.getInstance().getString(IdentifierProvider.COLLECTION, new ListenerUtils.StringMethod<IdentifierProvider>() {
			@Override
			public String execute(IdentifierProvider listener) {
				return listener.getViewDynamic(commonBusinessAction, one);
			}
		});
	}

	public AbstractIdentifiable getIdentifiable(Object data){
		if(data==null)
			return null;
		AbstractIdentifiable identifiable = (AbstractIdentifiable) (data instanceof AbstractIdentifiable ? data
				:( data instanceof AbstractFormModel<?> ? ((AbstractFormModel<?>)data).getIdentifiable() : ((AbstractOutputDetails<?>)data).getMaster() ) );
		return identifiable;
	}
	
	public String getCurrentViewUrl(){
		return listenerUtils.getString(Listener.COLLECTION, new ListenerUtils.StringMethod<Listener>() {
			@Override
			public String execute(Listener listener) {
				return listener.getCurrentViewUrl();
			}
		});
	}
	
	public String getViewPath(final String identifier){
		return listenerUtils.getString(Listener.COLLECTION, new ListenerUtils.StringMethod<Listener>() {
			@Override
			public String execute(Listener listener) {
				return listener.getViewPath(identifier);
			}
		});
	}
	
	/**/
	
	public static interface Listener {
		
		Collection<Listener> COLLECTION = new ArrayList<>();
		
		String getCurrentViewUrl();
		String getViewPath(String identifier);
		
		public static class Adapter extends BeanAdapter implements Listener,Serializable {
			
			private static final long serialVersionUID = 1L;
			
			@Override
			public String getCurrentViewUrl() {
				return null;
			}
			
			@Override
			public String getViewPath(String identifier) {
				return null;
			}
			
		}
	}
	
	/**/
	
	public static final String COMMANDABLE_CLASS_PREFIX = "commandable";
	public static final String COMMANDABLE_USER_ACCOUNT_MODULE_CLASS_PREFIX = "commandable_user_account";
	
	public static final String RESULTS_CONTAINER_CLASS_PREFIX = "results_container_";
	public static final String NOTIFICATION_DIALOG_OK_COMMANDABLE_CLASS = "messageDialogStyleClassOkButton";
	public static final String CONFIRMATION_DIALOG_YES_COMMANDABLE_CLASS = "ui-confirmdialog-yes";
	
	public static final String CONTEXTUAL_MENU_CLASS = "contextual_menu";
	public static final String GLOBAL_MENU_CLASS = "global_menu";
	
}
