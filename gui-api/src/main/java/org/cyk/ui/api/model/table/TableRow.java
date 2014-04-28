package org.cyk.ui.api.model.table;

import java.io.Serializable;

import lombok.NoArgsConstructor;

import org.cyk.utility.common.model.table.DefaultTableRow;
@NoArgsConstructor
public class TableRow<DATA> extends DefaultTableRow<DATA> implements Serializable {

	private static final long serialVersionUID = -1546359593321487355L;

	public TableRow(DATA data, String title) {
		super(data, title);
	}

}
