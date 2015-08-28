package org.cyk.ui.api.model.table;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.CascadeStyleSheet;
import org.cyk.utility.common.model.table.AbstractColumn;

@Getter @Setter
public class Column extends AbstractColumn<String, Cell, String> implements Serializable {

	private static final long serialVersionUID = 8311076255598465773L;
	
	private CascadeStyleSheet cascadeStyleSheet = new CascadeStyleSheet();

}
