package org.cyk.ui.api;

import java.io.Serializable;
import java.lang.reflect.Field;
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
import org.cyk.ui.api.editor.input.UIInputComponent;
import org.cyk.ui.api.editor.input.UIInputNumber;
import org.cyk.ui.api.editor.input.UIInputSelectOne;
import org.cyk.ui.api.editor.input.UIInputText;
import org.cyk.utility.common.AbstractMethod;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.Text.ValueType;
import org.cyk.utility.common.cdi.AbstractStartupBean;

@Singleton @Getter @Setter @Named(value="uiManager") @Deployment(initialisationType=InitialisationType.EAGER)
public class UIManager extends AbstractStartupBean implements Serializable {

	private static final long serialVersionUID = -9062523105492591265L;
	
	private static final Map<Class<?>,BusinessEntityInfos> BUSINESS_ENTITIES_INFOS_MAP = new HashMap<>();
	private static final Map<Class<? extends AbstractIdentifiable>,Class<?>> ENTITY_FORM_DATA_MAP = new HashMap<>();
	
	private static UIManager INSTANCE;
	public static UIManager getInstance() {
		return INSTANCE;
	}
	
	private final String consultViewSuffix="ConsultView";
	private final String listViewSuffix="ListView";
	private final String editViewSuffix="EditView";
	
	private CollectionLoadMethod collectionLoadMethod;
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
		for(BusinessEntityInfos infos : businessManager.findEntitiesInfos()){
			BUSINESS_ENTITIES_INFOS_MAP.put(infos.getClazz(), infos);
			infos.setUiLabel(text(infos.getUiLabelId()));
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
				if(parameter instanceof AbstractIdentifiable)
					return ((AbstractIdentifiable)parameter).getUiString();
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
	
	public Class<?> formData(Class<? extends AbstractIdentifiable> aClass){
		Class<?> clazz = ENTITY_FORM_DATA_MAP.get(aClass);
		if(clazz==null)
			return aClass;
		return clazz;
	}
	
	public void registerFormData(Class<? extends AbstractIdentifiable> identifiableClass,Class<?> formDataClass){
		ENTITY_FORM_DATA_MAP.put(identifiableClass, formDataClass);
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
	
	public String fieldLabel(Field field,Input annotation) {
		ValueType type ;
		String specifiedValue = null;
		if(annotation==null)
			type = ValueType.VALUE; 
		else{
			specifiedValue = annotation.label().value();
			type = ValueType.AUTO.equals(annotation.label().type())?ValueType.ID:annotation.label().type();
		}
		if(ValueType.VALUE.equals(type) && StringUtils.isNotBlank(specifiedValue))
			return specifiedValue;
		String labelId = null;
		if(ValueType.ID.equals(type))
			if(StringUtils.isNotBlank(specifiedValue))
				labelId = specifiedValue;
			else{
				StringBuilder s =new StringBuilder();
				for(int i=0;i<field.getName().length();i++){
					if(Character.isUpperCase(field.getName().charAt(i)))
						s.append('.');
					s.append(Character.toLowerCase(field.getName().charAt(i)));
				}
				labelId = s.toString();
			}
		return text(labelId);
	}
	
	public String fieldLabel(Field field) {
		return fieldLabel(field, field.getAnnotation(Input.class));
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
	
}
