package org.cyk.ui.web.primefaces.test.automation;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

@Getter @Setter
public class ContextMenu extends AbstractElement implements Serializable {

	private static final long serialVersionUID = 1L;

	public ContextMenu(String className) {
		super(className);
	}
	
	@Override
	protected String buildClassName(String className) {
		return className;
	}
	
	@Override
	protected WebElement findWebElement() {
		return super.findWebElement().findElement(By.tagName("ul"));
	}
	
	public void click(Integer actionIndex){
    	pause(1000 * 1l);
    	getDriver().findElement(By.xpath("/html/body/div[2]/form/div[1]/ul/li["+(actionIndex+1)+"]/a")).click();
    }
	public void clickEdit(){
    	click(1);
    }
	public void clickDelete(){
    	click(2);
    }
	
}
