package org.cyk.ui.web.primefaces.test.automation;

import java.io.Serializable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class InputOneAutoComplete extends AbstractInputChoice implements Serializable {

	private static final long serialVersionUID = 4859361310039407240L;

	public InputOneAutoComplete(String fieldName, String filter,Integer value) {
		super(fieldName, filter,value);
	}
	
	@Override
	public Boolean getSendTabKeyAfterSendKeys() {
		return Boolean.FALSE;
	}
	
	@Override
	public InputOneAutoComplete sendKeys(String value) {
		InputOneAutoComplete inputOneAutoComplete = (InputOneAutoComplete) super.sendKeys(value);
		WebElement div = SeleniumHelper.getInstance().getElementByClassContains("results_container_inputoneautocomplete_"+className);
		List<WebElement> choices = div.findElements(By.cssSelector("ul > li"));
		choices.get(this.value).click();
		return inputOneAutoComplete;
	}

}
