package org.cyk.ui.web.primefaces.test.automation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.cyk.utility.common.annotation.user.interfaces.Event;
import org.cyk.utility.common.cdi.AbstractBean;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@Getter @Setter
public abstract class AbstractElement extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -3394592929247102811L;

	protected String className;
	protected WebElement webElement;
	protected Boolean isStatic = Boolean.TRUE;
	protected Collection<Event.Listener> listeners = new ArrayList<>();
	
	public AbstractElement(String identifier) {
		super();
		setClassName(buildClassName(identifier));
	}
	
	protected abstract String buildClassName(String identifier);
	
	protected WebElement findWebElement(){
		return SeleniumHelper.getInstance().getElementByClassContains(this.className);
	}
	
	public void setClassName(String className){
		this.className = className;
		if(Boolean.TRUE.equals(getIsStatic()))
			webElement = findWebElement();
		else
			webElement = null;
	}
	
	public WebElement getWebElement(){
		if(Boolean.TRUE.equals(getIsStatic()))
			return webElement;
		return findWebElement();
	}
	
	public AbstractElement click(){
		Event.Listener.Adapter.listen(listeners, Event.CLICK, new Runnable() {
			@Override
			public void run() {
				getWebElement().click();
			}
		});
    	return this;
    }
	
	public AbstractElement getElementByClassContains(String...values){
		getHelper().getElementByClassContains(getWebElement(),values).click();
		return this;
    }
	
	public SeleniumHelper getHelper(){
		return SeleniumHelper.getInstance();
	}
	public WebDriver getDriver(){
		return SeleniumHelper.getInstance().getDriver();
	}
	
	/**/
	
}
