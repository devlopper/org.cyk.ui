package org.cyk.ui.api.editor.output;

import java.io.Serializable;

import org.cyk.ui.api.component.output.OutputText;

public class OutputMessage extends OutputText implements Serializable,UIOutputMessage {

	private static final long serialVersionUID = 422762056026115157L;

	public OutputMessage(String anInputId) {
		super(anInputId);
	}
	
}
