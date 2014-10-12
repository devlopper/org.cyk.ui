package org.cyk.ui.api.editor.input;

import java.io.Serializable;
import java.lang.reflect.Field;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.model.table.AbstractTable;
import org.cyk.utility.common.annotation.UIField;

@SuppressWarnings("rawtypes")
@Getter @Setter
public class InputMany extends AbstractInputComponent<AbstractTable> implements Serializable, UIInputMany {

	private static final long serialVersionUID = -7367234616039323949L;

	private AbstractTable table;
	
	public InputMany(Field aField,Class<?> fieldType,UIField annotation,Object anObject) {
		super(aField,fieldType,annotation,anObject);
		
	}
	
	@Override
	protected Class<AbstractTable> valueTypeClass() {
		return AbstractTable.class;
	}
	
}
