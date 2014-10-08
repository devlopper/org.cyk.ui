package org.cyk.ui.api.data.collector.form.container.table;

import org.cyk.ui.api.command.UICommandable;

public interface TableListener {
	
	void createRow(UICommandable commandable);

	void editRow(UICommandable commandable,TableRow<?> row);
	
	void saveRow(UICommandable commandable,TableRow<?> row);
	
	void cancelRowEdit(UICommandable commandable,TableRow<?> row);
	
	void updateRow(UICommandable commandable,TableRow<?> row);
	
	void deleteRow(UICommandable commandable,TableRow<?> row);
}
