package org.cyk.ui.web.api.editor.input;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.ui.api.editor.input.UIInputMany;
import org.cyk.ui.api.model.table.Table;
import org.cyk.ui.api.model.table.Table.UsedFor;
import org.cyk.ui.api.model.table.TableRow;
import org.cyk.ui.web.api.editor.WebEditorInputs;
import org.cyk.utility.common.CommonUtils;
import org.cyk.utility.common.annotation.UIField;

@SuppressWarnings("rawtypes")
@Getter @Setter @Log
public class InputMany extends AbstractWebInputComponent<Table> implements WebUIInputMany,UIInputMany, Serializable  {

	private static final long serialVersionUID = 7029658406107605595L;

	protected Table table;
	
	@SuppressWarnings("unchecked")
	public InputMany(WebEditorInputs<?, ?, ?, ?> editorInputs,UIInputMany input) {
		super(editorInputs,input);
		table = editorInputs.getEditor().getWindow().tableInstance(manyClass(input.getField(), input.getAnnotation()),UsedFor.FIELD_INPUT,editorInputs.getEditor().getCrud());
		table.addRow((Collection<?>) CommonUtils.getInstance().readField(input.getObject(), field, Boolean.FALSE));
	}
	
	private Class<?> manyClass(Field field,UIField uiField){
		Class<?> clazz = uiField.manyRelationshipClass();
		if(Void.class.equals(clazz))
			if(field.getGenericType() instanceof ParameterizedType)
				clazz = (Class<?>) ((ParameterizedType)field.getGenericType()).getActualTypeArguments()[0];
		
		if(Void.class.equals(uiField)){
			log.severe("No many class can be found for field <"+field+">");
		}
		return clazz;
	}
	
	@Override
	public void updateValue() throws Exception {
		Collection<Object> collection = new ArrayList<>();
		for(Object row : table.getRows())
			collection.add( ((TableRow<?>)row).getData() );
		FieldUtils.writeField(field, object, collection, true);
	}
	
}
