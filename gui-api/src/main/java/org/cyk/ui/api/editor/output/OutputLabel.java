package org.cyk.ui.api.editor.output;

import java.io.Serializable;

import org.cyk.ui.api.component.output.OutputText;

public class OutputLabel extends OutputText implements Serializable,IOutputLabel {

	private static final long serialVersionUID = 422762056026115157L;

	public OutputLabel(String aValue) {
		super(aValue);
	}
	
}
