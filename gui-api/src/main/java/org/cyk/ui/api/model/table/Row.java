package org.cyk.ui.api.model.table;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.CascadeStyleSheet;
import org.cyk.utility.common.model.table.AbstractRow;

@Getter @Setter
public class Row<DATA> extends AbstractRow<DATA,Cell,String> implements Serializable {

	private static final long serialVersionUID = -3855944230298132423L;

	private CascadeStyleSheet cascadeStyleSheet = new CascadeStyleSheet();
	private Boolean editable=Boolean.TRUE,deletable=Boolean.TRUE,openable=Boolean.FALSE;
	
}