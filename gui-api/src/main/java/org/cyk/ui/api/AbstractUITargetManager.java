package org.cyk.ui.api;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.AbstractModelElement;
import org.cyk.system.root.model.file.File;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.data.collector.control.Control;
import org.cyk.ui.api.data.collector.control.InputChoice;
import org.cyk.utility.common.CommonUtils;
import org.cyk.utility.common.annotation.user.interfaces.FieldOverride;
import org.cyk.utility.common.cdi.AbstractBean;

public abstract class AbstractUITargetManager<MODEL,ROW,LABEL,CONTROL,SELECTITEM> extends AbstractBean implements 
	UIProviderListener<MODEL,ROW,LABEL,CONTROL,SELECTITEM> , Serializable {

	private static final long serialVersionUID = -2692873330809223761L;

	@Inject protected UIProvider uiProvider;
	
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
		FieldOverride fieldOverride = data.getClass().getAnnotation(FieldOverride.class);
		Class<?> type = null;
		if(fieldOverride==null || !field.getName().equals(fieldOverride.name())){
			type = field.getType();
		}else{
			type = fieldOverride.type();
		}
		
		if(List.class.equals(type))
	        type = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
		
		Boolean itemWrapper = itemWrapper(inputChoice);
		
		if(AbstractIdentifiable.class.isAssignableFrom(type)){
			for(Object object : findAll((Class<? extends AbstractIdentifiable>)type)){
				AbstractIdentifiable identifiable = (AbstractIdentifiable) object;
				list.add(Boolean.TRUE.equals(itemWrapper)?item(identifiable):identifiable);
			}
		}else if(type.isEnum()){
			for(Enum<?> value : (Enum<?>[])type.getEnumConstants())
				list.add(item(value));
		}
		
		
		
	}
	
	protected Collection<AbstractIdentifiable> findAll(Class<? extends AbstractIdentifiable> aClass){
		return UIManager.getInstance().getGenericBusiness().use(aClass).find().all();
	}
	
	protected Boolean itemWrapper(InputChoice<?, ?, ?, ?, ?, ?> inputChoice){
		return Boolean.TRUE;
	}
	
	protected abstract SELECTITEM item(AbstractIdentifiable identifiable);

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
			value = "";
		return formatValue(field, value);
	}
	
	@Override
	public String formatValue(Field field, Object value) {
		if(value.getClass().getName().startsWith("org.cyk.")){//TODO make org.cyk. as constant
			if(value instanceof File)
				return ((File)value).getIdentifier().toString();// A mechanism, will be used to retrieve file data from business using file identifier
			else if(value instanceof AbstractModelElement)
				return ((AbstractModelElement)value).getUiString();
			else
				return value.toString();
		}else if(value instanceof Date)
			return UIManager.getInstance().findDateFormatter(field).format((Date)value);
		else if(value instanceof Collection<?>){
			Collection<?> collection = (Collection<?>) value;
			Collection<String> strings = new ArrayList<>();
			int i = 0;
			for(Object object : collection)
				strings.add( (collection.size()>1?++i+" - ":"")+formatValue(field,object));
			return StringUtils.join(strings,contentType().getNewLineMarker());
		}
			
		return value.toString();
	}

}
