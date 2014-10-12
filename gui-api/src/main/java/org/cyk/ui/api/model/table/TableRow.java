/*package org.cyk.ui.api.model.table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.ui.api.editor.input.UIInputComponent;
import org.cyk.utility.common.model.table.DefaultCell;
import org.cyk.utility.common.model.table.DefaultTableRow;

@Getter @Setter @NoArgsConstructor
public class TableRow<DATA> extends DefaultTableRow<DATA> implements Serializable {

	private static final long serialVersionUID = -1546359593321487355L;

	private Collection<UIInputComponent<?>> inputComponents = new ArrayList<>();
	
	public TableRow(DATA data, String title) {
		super(data, title);
	}
	
	public void updateFieldValues(){
		for(DefaultCell cell : cells)
			try {
				((TableCell)cell).getInputComponent().updateValue();
				cell.setValue(((TableCell)cell).getInputComponent().getReadOnlyValue());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
	}

}
*/