package org.cyk.ui.api;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.BusinessListener;
import org.cyk.system.root.business.api.BusinessManager;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.mathematics.NumberBusiness;
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.system.root.business.api.time.TimeBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.party.Application;
import org.cyk.system.root.model.time.Period;
import org.cyk.ui.api.config.IdentifiableConfiguration;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.utility.common.AbstractMethod;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.user.interfaces.Text;
import org.cyk.utility.common.cdi.AbstractStartupBean;

@Singleton @Getter @Setter @Named(value="uiManager") @Deployment(initialisationType=InitialisationType.EAGER)
public class UIManager extends AbstractStartupBean implements Serializable {

	private static final long serialVersionUID = -9062523105492591265L;
	
	private static final Map<Class<?>,BusinessEntityInfos> BUSINESS_ENTITIES_INFOS_MAP = new HashMap<>();
	private static final Map<Class<? extends AbstractIdentifiable>,IdentifiableConfiguration> IDENTIFIABLE_CONFIGURATION_MAP = new HashMap<>();
	public static final Map<String, Class<? extends AbstractFormModel<? extends AbstractIdentifiable>>> FORM_MODEL_MAP = new HashMap<>();
	public static final Map<Class<? extends AbstractIdentifiable>,Class<? extends AbstractFormModel<? extends AbstractIdentifiable>>> DEFAULT_MANY_FORM_MODEL_MAP = new HashMap<>();
	public static final Map<Class<? extends AbstractIdentifiable>,Class<? extends AbstractFormModel<? extends AbstractIdentifiable>>> DEFAULT_ONE_FORM_MODEL_MAP = new HashMap<>();
	
	public static final String PUSH_CHANNEL_VAR = "channel";
	public static final String PUSH_RECEIVER_VAR = "receiver";
	public static final String PUSH_NOTIFICATION_CHANNEL = "notification_channel";
	
	private static UIManager INSTANCE;
	public static UIManager getInstance() {
		return INSTANCE;
	}
	
	private static final Collection<BusinessListener> businessListeners = new ArrayList<>();
	public static final Collection<ComponentCreationListener> componentCreationListeners = new ArrayList<>();
	private static final Collection<AbstractApplicationUIManager> applicationUIManagers = new ArrayList<>();
	
	private final String consultViewSuffix="ConsultView";
	private final String listViewSuffix="ListView";
	private final String editViewSuffix="EditView";
	
	private final String pushNotificationChannel=PUSH_NOTIFICATION_CHANNEL;
	
	private CollectionLoadMethod collectionLoadMethod;
	
	@Inject private LanguageBusiness languageBusiness;
	@Inject private ApplicationBusiness applicationBusiness;
	@Inject private BusinessManager businessManager;
	@Inject private GenericBusiness genericBusiness;
	@Inject private FileBusiness fileBusiness;
	@Inject private TimeBusiness timeBusiness;
	@Inject private NumberBusiness numberBusiness;
	
	private Locale locale = Locale.FRENCH;
	
	/* constants */
	private final String userAccountParameter = "ridp";
	private final String reportIdentifierParameter = "ridp";
	private final String formModelParameter = "formmodel";
	private final String formModelActorParameter = "afm";
	private final String classParameter = "clazz";
	private final String identifiableParameter = "identifiable";
	private final String windowParameter="windowParam";
	private final String fileExtensionParameter="fileExtensionParam";
	private final String pdfParameter="pdf";
	private final String xlsParameter="xls";
	private final String crudParameter="crud";
	private final String crudCreateParameter="create";
	private final String crudReadParameter="read";
	private final String crudUpdateParameter="update";
	private final String crudDeleteParameter="delete";
	private final Map<String,BusinessEntityInfos> entitiesRequestParameterIdMap = new HashMap<>();
	
	private String windowFooter;
	
	public String getCrudParameterValue(Crud crud){
		crud=crud==null?Crud.READ:crud;
		switch(crud){
		case CREATE:return crudCreateParameter;
		case READ:return crudReadParameter;
		case UPDATE:return crudUpdateParameter;
		case DELETE:return crudDeleteParameter;
		default: return null;
		}
	}
	
	public Crud getCrudValue(String crudParameterValue){
		switch(crudParameterValue){
		case crudCreateParameter:return Crud.CREATE;
		case crudReadParameter:return Crud.READ;
		case crudUpdateParameter:return Crud.UPDATE;
		case crudDeleteParameter:return Crud.DELETE;
		default: return null;
		}
	}
	
	@Override
	protected void initialisation() {
		super.initialisation();
		INSTANCE = this;
		languageBusiness.registerResourceBundle("org.cyk.ui.api.resources.message",getClass().getClassLoader());
		languageBusiness.registerResourceBundle("org.cyk.ui.api.resources.field",getClass().getClassLoader());
		windowFooter = getLanguageBusiness().findText("window.layout.footer",new Object[]{getApplication()==null?"CYK":getApplication().getName()});
		BUSINESS_ENTITIES_INFOS_MAP.clear();
		for(BusinessEntityInfos infos : applicationBusiness.findBusinessEntitiesInfos()){
			BUSINESS_ENTITIES_INFOS_MAP.put(infos.getClazz(), infos);
			infos.setUiLabel(text(infos.getUiLabelId()));//for those registered late
		}
		
		for(Entry<Class<?>, BusinessEntityInfos> entry : BUSINESS_ENTITIES_INFOS_MAP.entrySet()){
			registerClassKey(entry.getValue());
		}
				
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
	
	public Collection<AbstractApplicationUIManager> getApplicationUImanagers() {
		return applicationUIManagers;
	}
	
	public void registerApplicationUImanager(AbstractApplicationUIManager applicationUIManager){
		applicationUIManagers.add(applicationUIManager);
	}
	
	public Application getApplication(){
		return applicationBusiness.findCurrentInstance();
	}
	
	public void configBusinessIdentifiable(Class<?> clazz,String iconName,String iconExtension,String consultViewId,String listViewId,String editViewId){
		BusinessEntityInfos businessEntityInfos = businessEntityInfos(clazz);
		businessEntityInfos.setUiIconName(iconName);
		businessEntityInfos.setUiIconExtension(iconExtension);
		businessEntityInfos.setUiConsultViewId(consultViewId);
		businessEntityInfos.setUiListViewId(listViewId);
		businessEntityInfos.setUiEditViewId(editViewId);
	}
	public void configBusinessIdentifiable(Class<?> clazz,String iconName){
		BusinessEntityInfos businessEntityInfos = businessEntityInfos(clazz);
		configBusinessIdentifiable(clazz, iconName, "png", businessEntityInfos.getVarName()+consultViewSuffix,
				businessEntityInfos.getVarName()+listViewSuffix,businessEntityInfos.getVarName()+editViewSuffix);
	}
	
	public String identifiableConsultViewId(AbstractIdentifiable identifiable){
		return businessEntityInfos(identifiable.getClass()).getUiConsultViewId();
	}
	
	public String identifiableEditViewId(AbstractIdentifiable identifiable){
		return businessEntityInfos(identifiable.getClass()).getUiEditViewId();
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
	
	public String textOfClass(Class<?> aClass){
		BusinessEntityInfos businessEntityInfos = businessEntityInfos(aClass);
		if(businessEntityInfos==null)
			return "###???###";
		return text(businessEntityInfos.getUiLabelId());
	}
	
	public String uiLabelIdOfClass(Class<?> aClass){
		BusinessEntityInfos businessEntityInfos = businessEntityInfos(aClass);
		if(businessEntityInfos==null)
			return "?UNKNOWN_UI_ID-"+aClass.getSimpleName()+"?";
		return businessEntityInfos.getUiLabelId();
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
	
	public BusinessEntityInfos classFromKey(String key){
		return entitiesRequestParameterIdMap.get(key);
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
	
	public IdentifiableConfiguration findConfiguration(Class<? extends AbstractIdentifiable> aClass){
		IdentifiableConfiguration config = IDENTIFIABLE_CONFIGURATION_MAP.get(aClass);
		return config;
	}
	
	public void registerConfiguration(IdentifiableConfiguration configuration){
		IDENTIFIABLE_CONFIGURATION_MAP.put(configuration.getIdentifiableClass(), configuration);
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
	
	public String formatDate(Date date,Boolean dateTime){
		return Boolean.TRUE.equals(dateTime)?timeBusiness.formatDateTime(date):timeBusiness.formatDate(date);
	}
	
	public String formatTime(Date time){
		return timeBusiness.formatTime(time);
	}
	
	public String formatDuration(Period period){
		return timeBusiness.formatDuration(timeBusiness.findDuration(period));
	}
	
	/**/
	
	public String textAnnotationValue(Field field,Text text) {
		return languageBusiness.findAnnotationText(field, text);
	}
	
	public String fieldLabel(Field field) {
		return languageBusiness.findFieldLabelText(field);
	}
		
	/**/
	public static abstract class AbstractLoadDataMethod<T> extends AbstractMethod<Collection<T>, Class<T>> {
		private static final long serialVersionUID = 1175379361365502915L;
	}
	
	
	public static abstract class CollectionLoadMethod extends AbstractLoadDataMethod<Object>{
		private static final long serialVersionUID = 7640865186916095212L;
	}
	
	
	public static abstract class ToStringMethod extends AbstractMethod<String, Object> {
		private static final long serialVersionUID = 1175379361365502915L;
	}


	public <T extends AbstractIdentifiable> Long count(Class<T> aClass, Map<String, Object> filters) {
		for(BusinessListener listener : businessListeners){
			Long count = listener.count(aClass, filters);
			if(count!=null)
				return count;
		}	
		return null;
	}

	public <T extends AbstractIdentifiable> Long count(Class<T> aClass, String filter) {
		for(BusinessListener listener : businessListeners){
			Long count = listener.count(aClass,filter);
			if(count!=null)
				return count;
		}	
		return null;
	}

	public <T extends AbstractIdentifiable> Collection<T> find(Class<T> aClass, Integer first, Integer pageSize,String sortField, Boolean ascendingOrder,Map<String, Object> filters) {
		for(BusinessListener listener : businessListeners){
			Collection<T> collection = listener.find(aClass, first, pageSize, sortField, ascendingOrder, filters);
			if(collection!=null)
				return collection;
		}
		return null;
	}
	
	public <T extends AbstractIdentifiable> Collection<T> find(Class<T> aClass, Integer first, Integer pageSize,String sortField, Boolean ascendingOrder,String filter) {
		for(BusinessListener listener : businessListeners){
			Collection<T> collection = listener.find(aClass, first, pageSize, sortField, ascendingOrder, filter);
			if(collection!=null)
				return collection;
		}
		return null;
	}
	
	public Collection<BusinessListener> getBusinesslisteners() {
		return businessListeners;
	}
	
	public Boolean isMobileDevice(UserDeviceType userDeviceType){
		return userDeviceType==null || !UserDeviceType.DESKTOP.equals(userDeviceType);
	}
	
}
