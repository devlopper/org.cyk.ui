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
import org.cyk.utility.common.annotation.UIField.TextValueType;
import org.cyk.utility.common.cdi.AbstractStartupBean;

@Singleton @Getter @Setter @Named(value="uiManager") @Deployment(initialisationType=InitialisationType.EAGER)
public class UIManager extends AbstractStartupBean implements Serializable {

	private static final long serialVersionUID = -9062523105492591265L;

	private static UIManager INSTANCE;
	
	public static UIManager getInstance() {
		return INSTANCE;
	}
	
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
		//System.out.println("string.format.pattern.date : "+text("string.format.pattern.date"));
		dateFormat = new SimpleDateFormat(text("string.format.pattern.date"));
		timeFormat = new SimpleDateFormat(text("string.format.pattern.time"));
		dateTimeFormat = new SimpleDateFormat(text("string.format.pattern.datetime"));
		
		for(BusinessEntityInfos infos : businessManager.findEntitiesInfos()){
			registerClassKey(infos);
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
			BusinessEntityInfos clazz = entitiesRequestParameterIdMap.get(infos.getIdentifier());
			if(clazz==null)
				entitiesRequestParameterIdMap.put(infos.getIdentifier(),infos);
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
		for(Entry<String, BusinessEntityInfos> entry : entitiesRequestParameterIdMap.entrySet())
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
