package org.cyk.ui.web.primefaces.test.automation;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.openqa.selenium.interactions.Actions;

@Getter @Setter
public abstract class AbstractMenu extends AbstractElement implements Serializable {

	private static final long serialVersionUID = 1L;

	public AbstractMenu(String className) {
		super(className);
	}
	
	@Override
	protected String buildClassName(String className) {
		return className;
	}
	
	
	public void click(String...itemClassNames){
		if(itemClassNames==null || itemClassNames.length==0)
			return;
		for(int i=0;i<itemClassNames.length-1;i++)
			new Actions(getDriver()).moveToElement(SeleniumHelper.getInstance().getElementByClassContains(itemClassNames[i])).build().perform();
		SeleniumHelper.getInstance().getElementByClassContains(itemClassNames[itemClassNames.length-1]).click();
	}
		
}
