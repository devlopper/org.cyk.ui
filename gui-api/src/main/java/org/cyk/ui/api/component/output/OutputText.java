package org.cyk.ui.api.component.output;

import java.io.Serializable;

public class OutputText extends AbstractOutputComponent<String> implements Serializable, UIOutputText {

	private static final long serialVersionUID = 422762056026115157L;

	public OutputText(String aValue) {
		super(String.class,aValue);
	}
	
}
