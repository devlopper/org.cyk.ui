package org.cyk.ui.web.api.component;

import java.io.Serializable;

import org.cyk.ui.api.component.output.IOutputComponent;

public class OutputText extends AbstractWebOutputComponent<String> implements IWebOutputText,Serializable  {

	private static final long serialVersionUID = 7029658406107605595L;

	public OutputText(IOutputComponent<String> input) {
		super(input);
	}
	
}
