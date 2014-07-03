package org.cyk.ui.api.editor.output;

import java.io.Serializable;

import org.cyk.ui.api.component.output.OutputText;

public class OutputSeparator extends OutputText implements Serializable,UIOutputSeparator {

	private static final long serialVersionUID = 422762056026115157L;

	public OutputSeparator(String aValue) {
		super(aValue);
	}
	
}
