package org.cyk.ui.web.primefaces.test.automation;

import java.io.Serializable;

import org.cyk.system.root.business.impl.language.LanguageBusinessImpl;
import org.cyk.ui.api.CascadeStyleSheet;
import org.cyk.utility.common.cdi.AbstractBean;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractInput<TYPE> extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -3394592929247102811L;

	protected String fieldName;
	protected String className;
	protected TYPE value;
	protected WebElement webElement;
	protected Boolean clearBeforeSendKeys = Boolean.TRUE,sendTabKeyAfterSendKeys = Boolean.TRUE;
	
	public AbstractInput(String fieldName, TYPE value) {
		super();
		this.fieldName = fieldName;
		this.className = CascadeStyleSheet.generateClassFrom(null, LanguageBusinessImpl.buildIdentifierFromFieldName(this.fieldName,LanguageBusinessImpl.FIELD_MARKER_START));
		this.value = value;
		webElement =  SeleniumHelper.getInstance().getElementByClassContains(this.className);
		if(webElement.getAttribute("class").contains("ui-calendar"))
			webElement = webElement.findElement(By.tagName("input"));
		else if(webElement.getAttribute("class").contains("ui-autocomplete"))
			webElement = webElement.findElement(By.tagName("input"));
	
	}
	
	public AbstractInput<TYPE> clear(){
		webElement.clear();
		return this;
	}
	protected abstract AbstractInput<TYPE> __sendKeys__();
	
	public AbstractInput<TYPE> sendTabKey(){
		webElement.sendKeys(Keys.TAB);
		return this;
	}
	
	public AbstractInput<TYPE> sendKeys(){
		if(Boolean.TRUE.equals(clearBeforeSendKeys))
			clear();
		__sendKeys__();
		if(Boolean.TRUE.equals(sendTabKeyAfterSendKeys))
			sendTabKey();
		return this;
	}
	
	
	

	/**/

	public static class Default extends AbstractInput<String> implements Serializable {

		private static final long serialVersionUID = 5519338294670669750L;

		public Default(String fieldName, String value) {
			super(fieldName, value);
		}
		
	}
}
