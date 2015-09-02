package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.cyk.ui.api.model.table.Column;
import org.cyk.ui.api.model.table.ColumnAdapter;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.Input;

public class DefaultColumnAdapter extends ColumnAdapter implements Serializable {

	private static final long serialVersionUID = 5607226813206637755L;

	@Override
	public Boolean isColumn(Field field) {
		Input input = field.getAnnotation(Input.class);
		IncludeInputs includeInputs = field.getAnnotation(IncludeInputs.class);
		return input != null || includeInputs!=null;
	}
	@Override
	public void added(Column column) {
		super.added(column);
		column.getCascadeStyleSheet().addClass(
				column.getField().getDeclaringClass().getSimpleName().toLowerCase()+
				"-"+column.getField().getName().toLowerCase());
	}
	
}
