package org.cyk.ui.web.primefaces.test.automation;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.CascadeStyleSheet;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

@Getter @Setter
public class GlobalMenu extends AbstractElement implements Serializable {

	private static final long serialVersionUID = 1L;

	public GlobalMenu() {
		super(CascadeStyleSheet.GLOBAL_MENU_CLASS);
	}
	
	@Override
	protected String buildClassName(String className) {
		return className;
	}
	
	@Override
	protected WebElement findWebElement() {
		return super.findWebElement().findElement(By.tagName("ul"));
	}
	
	public void click(String...labels){
		if(labels==null || labels.length==0)
			return;
		if(labels.length>1)
			for(int i=0;i<labels.length-1;i++)
				new Actions(getDriver()).moveToElement(getDriver().findElement(By.linkText(labels[i]))).build().perform();
		getDriver().findElement(By.linkText(labels[labels.length-1])).click();
	}
	
}
