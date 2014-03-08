package org.cyk.ui.api.component.input;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.LinkedHashSet;

import lombok.Getter;

@Getter
public abstract class AbstractInputSelectOne<VALUE_TYPE> extends AbstractInputComponent<VALUE_TYPE> implements Serializable, IInputSelectOne<VALUE_TYPE,ISelectItem> {

	private static final long serialVersionUID = -7367234616039323949L;

	protected Collection<ISelectItem> items;
	
	public AbstractInputSelectOne(String aLabel, Field aField,Object anObject,Collection<ISelectItem> aSelectItems) {
		super(aLabel, aField,anObject);
		this.items = aSelectItems;
	}
	
	protected void addItem(ISelectItem selectItem){
		if(items==null)
			items = new LinkedHashSet<>();
		items.add(selectItem);
	}
	
}
