package org.cyk.ui.web.primefaces.test.automation.control.input;

import java.io.Serializable;
import java.util.List;

import org.cyk.ui.api.CascadeStyleSheet;
import org.cyk.ui.api.data.collector.control.AbstractControl;
import org.cyk.ui.web.primefaces.test.automation.SeleniumHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class InputOneAutoComplete extends AbstractInputChoice implements Serializable {
	
	private static final long serialVersionUID = 4859361310039407240L;
	
	private WebElement resultsContainerWebElement;
	
	public InputOneAutoComplete(String fieldName, String filter,Integer value) {
		super(fieldName, filter,value);
		resultsContainerWebElement = SeleniumHelper.getInstance().getElementByClassContains(CascadeStyleSheet.RESULTS_CONTAINER_CLASS_PREFIX
				+AbstractControl.getControlType(org.cyk.ui.web.primefaces.data.collector.control.InputOneAutoComplete.class).toLowerCase()+className);
	}
	
	@Override
	protected WebElement findWebElement() {
		return super.findWebElement().findElement(By.tagName(TAG_NAME_INPUT));
	}
	
	@Override
	public Boolean getSendTabKeyAfterSendKeys() {
		return Boolean.FALSE;
	}
		
	@Override
	protected List<WebElement> getChoices() {
		return resultsContainerWebElement.findElements(By.cssSelector("ul > li"));
	}
	
}
