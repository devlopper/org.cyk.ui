package org.cyk.ui.api;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.geography.LocalityBusiness;
import org.cyk.system.root.business.api.globalidentification.GlobalIdentifierBusiness;
import org.cyk.system.root.business.api.security.RoleBusiness;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.AbstractModelElement;
import org.cyk.system.root.model.ContentType;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.language.LanguageEntry;
import org.cyk.system.root.model.security.Role;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.data.collector.control.Control;
import org.cyk.ui.api.data.collector.control.InputChoice;
import org.cyk.ui.api.data.collector.control.InputOneCascadeList;
import org.cyk.utility.common.CommonUtils;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice.ChoiceSet;
import org.cyk.utility.common.cdi.AbstractBean;

public abstract class AbstractUITargetManager<MODEL,ROW,LABEL,CONTROL,SELECTITEM,ICON_IDENTIFIER> extends AbstractBean implements 
	UIProviderListener<MODEL,ROW,LABEL,CONTROL,SELECTITEM> , Serializable {

	private static final long serialVersionUID = -2692873330809223761L;

	@Inject protected UIProvider uiProvider;
	@Inject protected LocalityBusiness localityBusiness;
	@Inject protected RoleBusiness roleBusiness;
	
	@Override
	public Class<? extends Control<?, ?, ?, ?, ?>> controlClassSelected(Class<? extends Control<?, ?, ?, ?, ?>> aClass) {
		return null;
	}

	@Override
	public void controlInstanceCreated(Control<?, ?, ?, ?, ?> control) {
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void choices(InputChoice<?,?,?,?,?,?> inputChoice,Object data, Field field, List<Object> list) {
		ChoiceSet choiceSet = field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputChoice.class).set();
		Class<?> type = commonUtils.getFieldType(data.getClass(), field);
		if(List.class.equals(type))
	        type = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
		
		Boolean itemWrapper = itemWrapper(inputChoice);
		
		if(AbstractIdentifiable.class.isAssignableFrom(type)){
			if(AbstractIdentifiable.class.equals(type)){
				logError("Cannot find data from entity {}", type);
			}else{
				if(inputChoice instanceof InputOneCascadeList){
					
				}else
					for(Object object : findAll((Class<? extends AbstractIdentifiable>)type,inputChoice,data,field)){
						AbstractIdentifiable identifiable = (AbstractIdentifiable) object;
						list.add(Boolean.TRUE.equals(itemWrapper)?item(identifiable):identifiable);
					}
			}
		}else if(type.isEnum()){
			for(Enum<?> value : (Enum<?>[])type.getEnumConstants())
				list.add(item(value));
		}else if(Boolean.class.equals(type) || boolean.class.equals(type)){
			list.addAll(getChoiceSetSelectItems(choiceSet,field.getAnnotation(NotNull.class)==null));		
		}else if(GlobalIdentifier.class.equals(type)){
			for(GlobalIdentifier globalIdentifier : inject(GlobalIdentifierBusiness.class).findAll()){
				list.add(Boolean.TRUE.equals(itemWrapper)?item(globalIdentifier):globalIdentifier);
			}		
		}
	}
	
	protected abstract Collection<SELECTITEM> getChoiceSetSelectItems(ChoiceSet choiceSet,Boolean nullable);
	
	@SuppressWarnings("unchecked")
	protected Collection<AbstractIdentifiable> findAll(Class<? extends AbstractIdentifiable> aClass,InputChoice<?,?,?,?,?,?> inputChoice,Object data, Field field){
		Collection<AbstractIdentifiable> collection = null;
		/*if(field.getName().equals("nationality")){
			collection = new ArrayList<>();
			for(Locality locality : localityBusiness.findByType(RootBusinessLayer.getInstance().getCountryLocalityType()))
				collection.add(locality);
			return collection;
		}else */if(field.getName().equals("roles")){
			collection = new ArrayList<>();
			for(Role role : roleBusiness.findAllExclude(Arrays.asList(inject(RoleBusiness.class).find(Role.ADMINISTRATOR))))
				collection.add(role);
			return collection;
		}else{
			return UIManager.getInstance().getGenericBusiness().use((Class<? extends AbstractIdentifiable>) commonUtils.getFieldType(data.getClass(), field)).find().all();
		}
	}
	
	protected Boolean itemWrapper(InputChoice<?, ?, ?, ?, ?, ?> inputChoice){
		return Boolean.TRUE;
	}
	
	protected abstract SELECTITEM item(AbstractModelElement identifiable);

	protected abstract SELECTITEM item(Enum<?> anEnum);
	
	@Override
	public Class<? extends UICommandable> commandableClassSelected(Class<? extends UICommandable> aClass) {
		return null;
	}

	@Override
	public void commandableInstanceCreated(UICommandable aCommandable) {
		
	}
	
	@Override
	public String readOnlyValue(Field field, Object object) {
		Object value = null;
		value = CommonUtils.getInstance().readField(object,field,Boolean.FALSE);
		if(value==null)
			value = Constant.EMPTY_STRING;
		return formatValue(field, value);
	}
	
	@Override
	public String formatValue(Field field, Object value) {
		if(value.getClass().getName().startsWith("org.cyk.")){//TODO make org.cyk. as constant
			if(value instanceof File)
				return ((File)value).getIdentifier().toString();// A mechanism, will be used to retrieve file data from business using file identifier
			else if(value instanceof AbstractModelElement)
				return RootBusinessLayer.getInstance().getFormatterBusiness().format(value);
			else
				return RootBusinessLayer.getInstance().getFormatterBusiness().format(value);
		}else if(value instanceof Date)
			return UIManager.getInstance().getTimeBusiness().format(field, (Date)value);
		else if(value instanceof Boolean)
			if(Boolean.TRUE.equals(value))
				return UIManager.getInstance().text(LanguageEntry.YES);
			else if(Boolean.FALSE.equals(value))
				return UIManager.getInstance().text(LanguageEntry.NO);
			else
				return UIManager.getInstance().text("notspecified");
		else if(value instanceof Collection<?>){
			Collection<?> collection = (Collection<?>) value;
			Collection<String> strings = new ArrayList<>();
			int i = 0;
			for(Object object : collection)
				strings.add( (collection.size()>1?++i+" - ":Constant.EMPTY_STRING)+formatValue(field,object));
			return StringUtils.join(strings,contentType().getNewLineMarker());
		}else if(value instanceof BigDecimal)
			return UIManager.getInstance().getNumberBusiness().format((Number) value);
		else if(value instanceof String){
			value = StringUtils.replace((String)value, ContentType.TEXT.getNewLineMarker(), UIManager.CONTENT_TYPE.getNewLineMarker());	
			
		}

		return value.toString();
	}

}
