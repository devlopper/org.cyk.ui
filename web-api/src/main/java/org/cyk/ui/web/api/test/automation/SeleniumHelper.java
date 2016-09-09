package org.cyk.ui.web.api.test.automation;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.utility.common.Constant;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.cdi.BeanAdapter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

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
	
	@Getter @Setter private WebDriver driver;
	@Getter @Setter private String context,scheme="http",host="localhost",port="8080";
	
	/* Functionalities */
	
	public void login(String username,String password){
		getElementByClassContains("inputtext_nom_d_utilisateur_").sendKeys(username);
		getElementByClassContains("inputpassword_mot_de_passe_").sendKeys(password);
		driver.findElement(By.xpath("/html/body/div[1]/div[2]/form/div[1]/div/table/tbody/tr/td/div/div/button")).click();
	}
	
	public void logout(String username){
		clickOnMenuItem(username,"Se deconnecter");
	}
	
	/* Pages */
	
	public void goToLoginPage() {
		goToPage("index"); 
	}
	
	/* Inputs */
	
	public void sendKeys(WebElement element,String value){
		element.clear();
		element.sendKeys(value);
	}
	public void sendKeys(String elementClassPart,String value){
		sendKeys(getElementByClassContains(elementClassPart), value);
	}
	
	public void sendAutocompleteKeys(WebElement element,String value,WebElement selectedElement){
		sendKeys(element, value);
        selectedElement.click();
	}
	public void sendAutocompleteKeys(String elementClassPart,String value,String selectedElementClassPart){
		sendAutocompleteKeys(getElementByClassContains(elementClassPart),value,getElementByClassContains(selectedElementClassPart));
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
	
	
	
	/*
	protected void logout(String username){
		clickOnMenuItem(username,"Se deconnecter");
	}*/
	
	/**/
	
	public static final String ATTRIBUTE_NAME_CLASS = "class";
	
	/**/
	
	public static interface Listener<WEB_ELEMENT> {
		
		/**/
		
		public static class Adapter extends BeanAdapter implements Serializable {

			private static final long serialVersionUID = 1L;
			
		}
	}
}
