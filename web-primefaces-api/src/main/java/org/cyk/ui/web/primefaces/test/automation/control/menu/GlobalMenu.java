package org.cyk.ui.web.primefaces.test.automation.control.menu;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.CascadeStyleSheet;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

@Getter @Setter
public class GlobalMenu extends AbstractMenu implements Serializable {

	private static final long serialVersionUID = 1L;

	public GlobalMenu() {
		super(CascadeStyleSheet.GLOBAL_MENU_CLASS);
	}
	
	@Override
	protected WebElement findWebElement() {
		return super.findWebElement().findElement(By.tagName("ul"));
	}
	
}
