package org.cyk.ui.api.model.table;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.data.collector.control.Control;
import org.cyk.utility.common.model.table.DefaultCell;

public class Cell extends DefaultCell implements Serializable {

	private static final long serialVersionUID = -2816856709391842461L;
	
	@Getter @Setter private Control<?, ?, ?, ?, ?> control;
	
}
