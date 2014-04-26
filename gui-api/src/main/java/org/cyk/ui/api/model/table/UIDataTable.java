package org.cyk.ui.api.model.table;

import java.io.Serializable;

import org.cyk.utility.common.model.table.AbstractClassFieldValueTable;

public class UIDataTable<ROW_DATA,COLUMN_DATA> extends AbstractClassFieldValueTable<ROW_DATA, TableRow<ROW_DATA>,TableColumn,TableCell> implements Serializable {
 
	private static final long serialVersionUID = -7832418987283686453L;

	@Override
	protected String nullValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String valueOf(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	

	
	
	
}
