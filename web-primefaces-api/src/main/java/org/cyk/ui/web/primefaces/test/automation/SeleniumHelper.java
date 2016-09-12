package org.cyk.ui.web.primefaces.test.automation;

import java.io.Serializable;
import java.util.List;

import org.cyk.system.root.business.impl.language.LanguageBusinessImpl;
import org.cyk.ui.api.CascadeStyleSheet;
import org.cyk.ui.web.primefaces.page.security.LoginPage;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.cdi.BeanAdapter;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import lombok.Getter;
import lombok.Setter;

public class SeleniumHelper extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;

	/* Attributes */
	
	public static String getAttributeValueMatchsFormat(String match){
		return "[%s"+match+"='%s']";
	}
	
	public static final String ATTRIBUTE_MATCHS = getAttributeValueMatchsFormat(Constant.JAVA_STRING_FORMAT_MARKER_S);
	public static final String ATTRIBUTE_STARTS_WITH = getAttributeValueMatchsFormat(Constant.CHARACTER_CARET_CIRCUMFLEX.toString());
	public static final String ATTRIBUTE_CONTAINS = getAttributeValueMatchsFormat(Constant.CHARACTER_STAR.toString());
	public static final String ATTRIBUTE_ENDS_WITH = getAttributeValueMatchsFormat(Constant.CHARACTER_DOLLAR.toString());
	
	private static final String URL = "%s://%s:%s/%s/private/%s.jsf";
	private static final String COMMAND_UNIQUE_CLASS_PART = "command_%s_";
	
	@Getter @Setter private WebDriver driver;
	@Getter @Setter private String context,scheme="http",host="localhost",port="8080";
	
	/* Functionalities */
	
	public void login(String username,String password){
		sendKeysOnInput(LoginPage.Form.FIELD_USERNAME,username);
		sendKeysOnInput(LoginPage.Form.FIELD_PASSWORD,password);
		clickCommand("connect");
	}
	
	public void logout(String username){
		clickOnMenuItem(username,"Se deconnecter");
	}
	
	/* Pages */
	
	public void goToLoginPage() {
		goToPage("index"); 
	}
	
	/* Inputs */
	
	public WebElement sendKeysOnInput(String labelIdPart,String value){
		return sendKeys(getInput(labelIdPart),value);
	}
	public void clickOnRadioInput(String labelIdPart,Integer index){
		List<WebElement> webElements = getInput(labelIdPart).findElements(By.cssSelector(".ui-radiobutton-icon"));
        webElements.get(index).click();
	}
	public void sendKeysOnAutocompleteInput(String labelIdPart,String value,Integer selectedIndex){
		sendKeysOnInput(labelIdPart, value);
		WebElement div = getElementByClassContains("results_container_inputoneautocomplete_field_"+labelIdPart);
		List<WebElement> choices = div.findElements(By.cssSelector("ul > li"));
		choices.get(selectedIndex).click();
	}
	
	
	/* Commands */
	
	public void clickCommand(String labelIdPart){
		getCommandable(labelIdPart).click();
	}
	
	/*UI action*/
	
	public void goToPage(String relativeUrl) {
		 driver.get(String.format(URL, scheme,host,port,context,relativeUrl));
	}
	
	public void clickOnMenuItem(String...labels){
		if(labels==null || labels.length==0)
			return;
		if(labels.length>1)
			for(int i=0;i<labels.length-1;i++)
				new Actions(driver).moveToElement(driver.findElement(By.linkText(labels[i]))).build().perform();
		driver.findElement(By.linkText(labels[labels.length-1])).click();
	}
	
	/* Core methods */
	
	public WebElement getElementByAttributeMatchs(String attribute,String value,String match){
		return driver.findElement(By.cssSelector(String.format(ATTRIBUTE_MATCHS, attribute,match,value)));
	}
	public WebElement getElementByAttributeContains(String attribute,String value){
		return driver.findElement(By.cssSelector(String.format(ATTRIBUTE_CONTAINS, attribute,value)));
	}
	public WebElement getElementByClassContains(String value){
		return driver.findElement(By.cssSelector(String.format(ATTRIBUTE_CONTAINS, ATTRIBUTE_NAME_CLASS,value)));
	}
	public WebElement getInput(String labelIdPart){
		String classNamePart = CascadeStyleSheet.generateClassFrom(null, LanguageBusinessImpl.buildIdentifierFromFieldName(labelIdPart,LanguageBusinessImpl.FIELD_MARKER_START));
		WebElement webElement =  getElementByClassContains(classNamePart);
		if(webElement.getAttribute("class").contains("ui-calendar"))
			webElement = webElement.findElement(By.tagName("input"));
		else if(webElement.getAttribute("class").contains("ui-autocomplete"))
			webElement = webElement.findElement(By.tagName("input"));
		return webElement;
	}
	public WebElement getCommandable(String labelIdPart){
		return getElementByClassContains(String.format(COMMAND_UNIQUE_CLASS_PART, labelIdPart));
	}
	
	public WebElement sendKeys(WebElement element,String value,Boolean autoClear){
		if(Boolean.TRUE.equals(autoClear))
			element.clear();
		element.sendKeys(value);
		if(!element.getAttribute("class").contains("ui-autocomplete-input") && !element.getAttribute("type").equals("file"))
			element.sendKeys(Keys.TAB);
		return element;
	}
	public WebElement sendKeys(WebElement element,String value){
		return sendKeys(element, value, Boolean.TRUE);
	}
	public WebElement sendKeys(String elementClassPart,String value,Boolean autoClear){
		return sendKeys(getElementByClassContains(elementClassPart), value,autoClear);
	}
	public WebElement sendKeys(String elementClassPart,String value){
		return sendKeys(elementClassPart, value, Boolean.TRUE);
	}
	
	/*
	protected void logout(String username){
		clickOnMenuItem(username,"Se deconnecter");
	}*/
	
	/**/
	
	public static final String ATTRIBUTE_NAME_CLASS = "class";
	
	/**/
	
	private static final SeleniumHelper INSTANCE = new SeleniumHelper();
	public static SeleniumHelper getInstance(){
		return INSTANCE;
	}
	
	/**/
	
	public static interface Listener<WEB_ELEMENT> {
		
		/**/
		
		public static class Adapter extends BeanAdapter implements Serializable {

			private static final long serialVersionUID = 1L;
			
		}
	}
}
