package org.cyk.ui.api.editor.input;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.LinkedHashSet;

import org.apache.commons.lang3.ClassUtils;

import lombok.Getter;

@Getter
public class InputSelectOne extends AbstractInputComponent<Object> implements Serializable, UIInputSelectOne<Object,ISelectItem> {

	private static final long serialVersionUID = -7367234616039323949L;

	@Getter
	protected Collection<ISelectItem> items = new LinkedHashSet<>();
	
	public InputSelectOne(String aLabel, Field aField,Object anObject) {
		super(aLabel, aField,anObject);
		if(isBoolean()){
			items.add(new DefaultSelectItem("OUI",Boolean.TRUE));
			items.add(new DefaultSelectItem("NON",Boolean.FALSE));
		}else if(isEnum()){
			for(Object value : aField.getType().getEnumConstants())
				items.add(new DefaultSelectItem(value.toString(),value));
		}
	}
	
	@Override
	public Boolean isBoolean() {
		return Boolean.class.equals( ClassUtils.primitiveToWrapper(getField().getType()));
	}
	
	@Override
	public Boolean isEnum() {
		return getField().getType().isEnum();
	}
	
	@Override
	public Boolean getAddable() {
		return Boolean.FALSE;
	}
	
	@Override
	public Boolean isSelectItemEditable() {
		return !isBoolean() && !isEnum();
	}
}
