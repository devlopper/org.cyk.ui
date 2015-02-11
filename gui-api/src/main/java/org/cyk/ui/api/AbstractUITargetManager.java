package org.cyk.ui.api;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.AbstractModelElement;
import org.cyk.system.root.model.file.File;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.data.collector.control.Control;
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
	public void choices(Object data, Field field, List<Object> list) {
		FieldOverride fieldOverride = data.getClass().getAnnotation(FieldOverride.class);
		Class<?> type = fieldOverride==null?field.getType():fieldOverride.type();
		if(AbstractIdentifiable.class.isAssignableFrom(type)){
			for(Object object : findAll((Class<? extends AbstractIdentifiable>)type)){
				AbstractIdentifiable identifiable = (AbstractIdentifiable) object;
				list.add(item(identifiable));
			}
		}else if(type.isEnum()){
			try {
				for(Enum<?> value : (Enum<?>[])type.getEnumConstants())
					list.add(item(value));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	protected Collection<AbstractIdentifiable> findAll(Class<? extends AbstractIdentifiable> aClass){
		return UIManager.getInstance().getGenericBusiness().use(aClass).find().all();
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
		try {
			value = FieldUtils.readField(field, object, Boolean.TRUE);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		if(value==null)
			return "";
		
		if(value.getClass().getName().startsWith("org.cyk."))
			if(value instanceof File)
				return ((File)value).getIdentifier().toString();// A mechanism, will be used to retrieve file data from business
			else if(value instanceof AbstractModelElement)
				return ((AbstractModelElement)value).getUiString();
			else
				value.toString();
		
		if(value instanceof Date)
			return UIManager.getInstance().findDateFormatter(field).format((Date)value);
		return value.toString();
	}

}
