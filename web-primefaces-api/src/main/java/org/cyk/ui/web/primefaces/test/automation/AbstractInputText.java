package org.cyk.ui.web.primefaces.test.automation;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractInputText extends AbstractInput<String> implements Serializable {

	private static final long serialVersionUID = -3394592929247102811L;

	public AbstractInputText(String fieldName, String value) {
		super(fieldName, value);
	}
	
}
