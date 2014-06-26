package org.cyk.ui.web.api.editor.input;

import java.io.Serializable;

import lombok.Getter;

import org.cyk.ui.api.editor.input.UIInputText;
import org.cyk.ui.web.api.editor.WebEditorInputs;

@Getter
public class InputText extends AbstractWebInputComponent<String> implements WebUIInputText,UIInputText, Serializable  {

	private static final long serialVersionUID = 7029658406107605595L;

	@Getter private Integer rowCount,columnCount,size=10;
	private String filterMask;
	
	public InputText(WebEditorInputs<?, ?, ?, ?> containerForm,UIInputText input) {
		super(containerForm,input);
		rowCount = input.getRowCount();
		columnCount = input.getColumnCount();
	}
	
	@Override
	public String getFamily() {
		return annotation.textArea()?"InputTextArea":super.getFamily();
	}
	
}
