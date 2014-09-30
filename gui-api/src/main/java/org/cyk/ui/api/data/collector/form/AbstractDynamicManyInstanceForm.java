package org.cyk.ui.api.data.collector.form;

/*
public abstract class AbstractDynamicManyInstanceForm<DATA> extends AbstractManyInstanceForm<DATA> implements Serializable {

	private static final long serialVersionUID = 930035128436508708L;

	public AbstractDynamicManyInstanceForm(Class<DATA> dataClass,List<DATA> list) {
		super(dataClass,list);
		
		Collection<Field> fields = CommonUtils.getInstance().getAllFields(dataClass, Input.class);
		
		for(Field field : fields){
			table.addColumn(field);
		}
		
		for(DATA record : data){
			table.addRow(record, this, fields);
		}
		
	}

}*/

