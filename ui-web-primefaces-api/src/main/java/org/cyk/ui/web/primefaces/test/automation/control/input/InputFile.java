package org.cyk.ui.web.primefaces.test.automation.control.input;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InputFile extends AbstractInputText implements Serializable {

	private static final long serialVersionUID = -3394592929247102811L;

	public InputFile(String fieldName, String value) {
		super(fieldName, value);
	}
	
	@Override
	public Boolean getSendTabKeyAfterSendKeys() {
		return Boolean.FALSE;
	}
	
}
