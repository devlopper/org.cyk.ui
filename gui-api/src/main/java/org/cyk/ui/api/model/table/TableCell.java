package org.cyk.ui.api.model.table;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.ui.api.editor.input.UIInputComponent;
import org.cyk.utility.common.model.table.DefaultCell;

@Getter @Setter @NoArgsConstructor
public class TableCell extends DefaultCell implements Serializable {

	private static final long serialVersionUID = 3633221262937015949L;

	private UIInputComponent<?> inputComponent;
	
	public TableCell(String value) {
		super(value);
	}
	
	
	
}
