package org.cyk.ui.web.primefaces.test.automation.control.input;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.openqa.selenium.WebElement;

@Getter @Setter
public abstract class AbstractInputChoice extends AbstractInput<Integer> implements Serializable {

	private static final long serialVersionUID = -3394592929247102811L;

	private String filter;
	
	public AbstractInputChoice(String fieldName,String filter, Integer value) {
		super(fieldName, value);
		this.filter = filter;
	}
	
	@Override
	protected String getKeysToSend() {
		return filter;
	}
	
	@Override
	public AbstractInputChoice sendKeys() {
		super.sendKeys();
		List<WebElement> choices = getChoices();
		choices.get(value).click();
		return this;
	}
	
	protected abstract List<WebElement> getChoices();
	
}
