package org.cyk.ui.api.model.table;

import org.cyk.ui.api.command.UICommandable;

public interface TableListener {
	
	@Deprecated
	public enum RowEvent{CREATE,EDIT,SAVE,UPDATE,DELETE,CANCEL_EDIT}
	
	void rowEvent(UICommandable commandable,TableRow<?> row);

}
