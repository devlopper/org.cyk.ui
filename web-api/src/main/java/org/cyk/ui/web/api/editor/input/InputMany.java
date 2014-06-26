package org.cyk.ui.web.api.editor.input;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.geography.PhoneNumberType;
import org.cyk.ui.api.editor.input.UIInputMany;
import org.cyk.ui.api.model.table.Table;
import org.cyk.ui.api.model.table.TableCell;
import org.cyk.ui.api.model.table.TableColumn;
import org.cyk.ui.api.model.table.TableRow;
import org.cyk.ui.web.api.editor.WebEditorInputs;

@SuppressWarnings("rawtypes")
@Getter @Setter
public class InputMany extends AbstractWebInputComponent<Table> implements WebUIInputMany,UIInputMany, Serializable  {

	private static final long serialVersionUID = 7029658406107605595L;

	protected Table table;
	
	@SuppressWarnings("unchecked")
	public InputMany(WebEditorInputs<?, ?, ?, ?> editorInputs,UIInputMany input) {
		super(editorInputs,input);
		debug(editorInputs);
		table = editorInputs.getEditor().getWindow().tableInstance(PhoneNumberType.class);
		//table.setEditable(Boolean.TRUE);
		table.build(PhoneNumberType.class, TableRow.class, TableColumn.class, TableCell.class);
		table.addRow(new PhoneNumberType());
		table.addRow(new PhoneNumberType());
	}
	
}
