package org.cyk.ui.web.primefaces.test.automation;

import java.io.Serializable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InputCalendar extends AbstractInputText implements Serializable {

	private static final long serialVersionUID = -3394592929247102811L;

	public InputCalendar(String fieldName, String value) {
		super(fieldName, value);
	}
	
	@Override
	protected WebElement findWebElement() {
		return super.findWebElement().findElement(By.tagName("input"));
	}
	
}
