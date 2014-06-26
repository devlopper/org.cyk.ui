package org.cyk.ui.web.primefaces.editor;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.geography.PhoneNumberType;
import org.cyk.ui.api.editor.input.UIInputMany;
import org.cyk.ui.api.model.table.TableCell;
import org.cyk.ui.api.model.table.TableColumn;
import org.cyk.ui.api.model.table.TableRow;
import org.cyk.ui.web.api.editor.WebEditorInputs;
import org.cyk.ui.web.api.editor.input.InputMany;

@Getter @Setter
public class PrimefacesInputMany extends InputMany implements Serializable  {

	private static final long serialVersionUID = 7029658406107605595L;

	@SuppressWarnings("unchecked")
	public PrimefacesInputMany(WebEditorInputs<?, ?, ?, ?> editorInputs,UIInputMany input) {
		super(editorInputs,input);
		debug(editorInputs);
		table = editorInputs.getEditor().getWindow().tableInstance(PhoneNumberType.class);
		//table.setEditable(Boolean.TRUE);
		table.build(PhoneNumberType.class, TableRow.class, TableColumn.class, TableCell.class);
		table.addRow(new PhoneNumberType());
		table.addRow(new PhoneNumberType());
	}
	
}
