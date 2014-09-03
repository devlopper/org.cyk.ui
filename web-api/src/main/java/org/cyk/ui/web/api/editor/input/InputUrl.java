package org.cyk.ui.web.api.editor.input;

import java.io.Serializable;
import java.net.URL;

import lombok.Getter;

import org.cyk.ui.api.editor.input.UIInputUrl;
import org.cyk.ui.web.api.editor.WebEditorInputs;

@Getter
public class InputUrl extends AbstractWebInputComponent<URL> implements WebUIInputUrl,Serializable  {

	private static final long serialVersionUID = 7029658406107605595L;

	public InputUrl(WebEditorInputs<?, ?, ?, ?> containerForm,UIInputUrl input) {
		super(containerForm,input);
	}
	
}
