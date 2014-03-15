package org.cyk.ui.api.form.output;

import java.io.Serializable;

import org.cyk.ui.api.component.output.OutputText;

public class OutputMessage extends OutputText implements Serializable,IOutputMessage {

	private static final long serialVersionUID = 422762056026115157L;

	public OutputMessage(String anInputId) {
		super(anInputId);
	}
	
}
