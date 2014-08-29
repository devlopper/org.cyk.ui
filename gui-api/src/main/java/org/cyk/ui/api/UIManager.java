package org.cyk.ui.api;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.BusinessManager;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.command.DefaultCommand;
import org.cyk.ui.api.command.DefaultCommandable;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UICommandable.EventListener;
import org.cyk.ui.api.command.UICommandable.IconType;
import org.cyk.ui.api.command.UICommandable.ProcessGroup;
import org.cyk.ui.api.editor.EditorInputs;
import org.cyk.ui.api.editor.input.UIInputComponent;
import org.cyk.ui.api.editor.input.UIInputNumber;
import org.cyk.ui.api.editor.input.UIInputSelectOne;
import org.cyk.ui.api.editor.input.UIInputText;
import org.cyk.utility.common.AbstractMethod;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.UIField.TextValueType;
import org.cyk.utility.common.cdi.AbstractStartupBean;

@Singleton @Getter @Setter @Named(value="uiManager") @Deployment(initialisationType=InitialisationType.EAGER)
public class UIManager extends AbstractStartupBean implements Serializable {

	private static final long serialVersionUID = -9062523105492591265L;
	
	private static final Map<Class<?>,BusinessEntityInfos> BUSINESS_ENTITIES_INFOS_MAP = new HashMap<>();
	
	private static UIManager INSTANCE;
	
	public static UIManager getInstance() {
		return INSTANCE;
	}
	
	private final String consultViewSuffix="ConsultView";
	private final String listViewSuffix="ListView";
	private final String editViewSuffix="EditView";
	
	private CollectionLoadMethod collectionLoadMethod;
	public static ComponentCreateMethod componentCreateMethod;
	private ToStringMethod toStringMethod;
	private SimpleDateFormat dateFormat,timeFormat,dateTimeFormat;
	
	@Inject protected LanguageBusiness languageBusiness;
	@Inject protected BusinessManager businessManager;
	@Inject protected GenericBusiness genericBusiness;
	
	/* constants */
	private final String windowParameter="windowParam";
	private final String crudParameter="crud";
	private final String crudCreateParameter="create";
	private final String crudReadParameter="read";
	private final String crudUpdateParameter="update";
	private final String crudDeleteParameter="delete";
	private final Map<String,BusinessEntityInfos> entitiesRequestParameterIdMap = new HashMap<>();
	
	private String businessSystemName,windowFooter;
	
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
		businessSystemName = businessManager.findSystemName();
		windowFooter = getLanguageBusiness().findText("window.layout.footer",new Object[]{UIManager.getInstance().getBusinessSystemName()});
		languageBusiness.registerResourceBundle("org.cyk.ui.api.resources.message",getClass().getClassLoader());
		dateFormat = new SimpleDateFormat(text("string.format.pattern.date"));
		timeFormat = new SimpleDateFormat(text("string.format.pattern.time"));
		dateTimeFormat = new SimpleDateFormat(text("string.format.pattern.datetime"));
		
		BUSINESS_ENTITIES_INFOS_MAP.clear();
		for(BusinessEntityInfos infos : businessManager.findEntitiesInfos())
			BUSINESS_ENTITIES_INFOS_MAP.put(infos.getClazz(), infos);
		
		for(Entry<Class<?>, BusinessEntityInfos> entry : BUSINESS_ENTITIES_INFOS_MAP.entrySet()){
			registerClassKey(entry.getValue());
		}
				
		collectionLoadMethod = new CollectionLoadMethod() {
			private static final long serialVersionUID = -4679710339375267115L;
			@SuppressWarnings("unchecked")
			@Override
			protected Collection<Object> __execute__(Class<Object> parameter) {
				Class<AbstractIdentifiable> c = null;
				try {
					c = (Class<AbstractIdentifiable>) Class.forName(parameter.getName());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				Collection<AbstractIdentifiable> r = genericBusiness.use(c).find().all();
				Collection<Object> l = new ArrayList<>();
				for(AbstractIdentifiable i : r)
					l.add(i);
				return l;
			}
		};
		 
		toStringMethod = new ToStringMethod() {
			private static final long serialVersionUID = 7479681304478557922L;
			@Override
			protected String __execute__(Object parameter) {
				return parameter.toString();
			}
		};
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
	
	public ComponentCreateMethod getComponentCreateMethod() {
		return componentCreateMethod;
	}
	
	public String text(String code){
		return languageBusiness.findText(code);
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
	
	public Boolean isInputText(UIInputComponent<?> inputComponent){
		return inputComponent instanceof UIInputText;
	}
	
	public Boolean isInputSelectOne(UIInputComponent<?> inputComponent){
		return inputComponent instanceof UIInputSelectOne;
	}
	
	public Boolean isInputNumber(UIInputComponent<?> inputComponent){
		return inputComponent instanceof UIInputNumber;
	}
	
	/**/
	
	public String formatDate(Date date,Boolean dateTime){
		return (Boolean.TRUE.equals(dateTime)?dateTimeFormat:dateFormat).format(date);
	}
	
	public String formatTime(Date time){
		return timeFormat.format(time);
	}
	
	/**/
	
	//
	//String labelId,IconType iconType,AbstractMethod<Object, Object> action
	public UICommandable createCommandable(String labelId,IconType iconType,AbstractMethod<Object, Object> executeMethod,EventListener anExecutionPhase,ProcessGroup aProcessGroup){
		UICommandable commandable = new DefaultCommandable();
		commandable.setCommand(createCommand(executeMethod));
		commandable.setLabel(text(labelId));
		commandable.setIconType(iconType);
		commandable.setEventListener(anExecutionPhase);
		commandable.setProcessGroup(aProcessGroup);
		return commandable;
	}
	
	public UICommand createCommand(AbstractMethod<Object, Object> action){
		UICommand command = new DefaultCommand();
		command.setExecuteMethod(action);
		return command;
	}
	
	public String annotationTextValue(TextValueType textValueType,String textValue,String defaultValue) {
		switch(textValueType){
		case I18N_ID:return text(StringUtils.isEmpty(textValue)?defaultValue:textValue);
		case I18N_VALUE:return StringUtils.isEmpty(textValue)?defaultValue:textValue;
		case VALUE:return null;
		default : return null;
		}
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
	
	
	public static abstract class ComponentCreateMethod extends AbstractMethod<UIInputComponent<?>, Object[]> {
		private static final long serialVersionUID = 4855972832374849032L;
		
		@Override
		protected final UIInputComponent<?> __execute__(Object[] parameter) {
			return component((EditorInputs<?, ?, ?, ?>)parameter[0], (UIInputComponent<?>)parameter[1]);
		}
		
		protected abstract UIInputComponent<?> component(EditorInputs<?, ?, ?, ?> anEditorInputs,UIInputComponent<?> aComponent);
		
	}
	
	
}
