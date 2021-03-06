package org.cyk.ui.web.primefaces.test.automation.control.menu;

import java.io.Serializable;

import org.cyk.ui.api.UIManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GlobalMenu extends AbstractMenu implements Serializable {

	private static final long serialVersionUID = 1L;

	public GlobalMenu() {
		super(UIManager.GLOBAL_MENU_CLASS);
	}
	
	@Override
	protected WebElement findWebElement() {
		return super.findWebElement().findElement(By.tagName("ul"));
	}
	
}
