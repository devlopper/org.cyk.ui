package org.cyk.ui.web.primefaces.test.automation.control.input;

import java.io.Serializable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class InputOneRadio extends AbstractInputChoice implements Serializable {

	private static final long serialVersionUID = 4859361310039407240L;

	public InputOneRadio(String fieldName, Integer index) {
		super(fieldName,null, index);
	}
	
	@Override
	public Boolean getClearBeforeSendKeys() {
		return Boolean.FALSE;
	}
	
	@Override
	public Boolean getSendTabKeyAfterSendKeys() {
		return Boolean.FALSE;
	}
	
	@Override
	protected List<WebElement> getChoices() {
		return getWebElement().findElements(By.cssSelector(".ui-radiobutton-icon"));
	}
	
}
