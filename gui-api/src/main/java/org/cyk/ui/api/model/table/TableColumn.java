package org.cyk.ui.api.model.table;

import java.io.Serializable;

import org.cyk.utility.common.model.table.DefaultTableColumn;

public class TableColumn extends DefaultTableColumn implements Serializable {

	private static final long serialVersionUID = -1546359593321487355L;

	public TableColumn(String title,String fieldName) {
		super(title,fieldName);
	}

}
