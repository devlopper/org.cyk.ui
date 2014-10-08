package org.cyk.ui.api.data.collector.form.container.table;

import java.io.Serializable;

import lombok.NoArgsConstructor;

import org.cyk.utility.common.model.table.DefaultTableColumn;

@NoArgsConstructor
public class TableColumn extends DefaultTableColumn implements Serializable {

	private static final long serialVersionUID = -1546359593321487355L;

	public TableColumn(String title,String fieldName) {
		super(title,fieldName);
	}
	
	

}
