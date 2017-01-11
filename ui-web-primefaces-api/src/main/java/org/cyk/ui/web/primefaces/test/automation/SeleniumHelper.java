package org.cyk.ui.web.primefaces.test.automation;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.web.primefaces.page.security.LoginPage;
import org.cyk.ui.web.primefaces.test.automation.control.input.AbstractInput;
import org.cyk.ui.web.primefaces.test.automation.control.menu.ContextMenu;
import org.cyk.ui.web.primefaces.test.automation.control.menu.GlobalMenu;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.cdi.BeanAdapter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
	
	private static final String URL = "%s://%s:%s/%s/private/%s.jsf%s";
	public static Boolean FIND_ELEMENT_BY_LIST = Boolean.FALSE;
	
	@Getter @Setter private WebDriver driver;
	@Getter @Setter private String context,scheme,host,port;
	@Getter @Setter private Long implicitlyWaitNumberOfMillisecond = 1000 * 60l;
	
	private SeleniumHelper() {}
	
	/* Functionalities */
	
	public void login(String username,String password){
		Form form = new Form("connect");
        form.addInputText(LoginPage.Form.FIELD_USERNAME, username)
        	.addInputText(LoginPage.Form.FIELD_PASSWORD,password)
        	;
        form.sendKeys();
        form.getSubmitCommandable().setNotified(Boolean.FALSE);
        form.submit();
	}
	
	public void logout(String username){
		clickGlobalMenu(UIManager.COMMANDABLE_USER_ACCOUNT_MODULE_CLASS_PREFIX,"commandable_command_useraccount_logout_");
	}
	
	/* Pages */
	
	public void goToLoginPage() {
		goToPage("index"); 
	}
	
	/* Inputs */
	
	public void sendKeys(Collection<? extends AbstractInput<?>> inputs){
		for(AbstractInput<?> input : inputs)
			input.sendKeys();
	}
	public void sendKeys(AbstractInput<?>...inputs){
		sendKeys(Arrays.asList(inputs));
	}
	
	/* Commands */
	
	/* Pages */
	
	public void goToPage(String relativeUrl,String query) {
		 driver.get(String.format(URL, scheme,host,port,context,relativeUrl,StringUtils.isBlank(query) ? Constant.EMPTY_STRING : (Constant.CHARACTER_QUESTION_MARK+query)));
	}
	public void goToPage(String relativeUrl) {
		goToPage(relativeUrl, null);
	}
		
	/* Core methods */
	
	public WebElement findElement(WebElement parent,By by,Boolean list){
		if(Boolean.TRUE.equals(list)){
			List<WebElement> collection = driver.findElements(by);
			if(collection.isEmpty() || collection.size()>1)
				return null;
			if(collection.size()>1)
				throw new RuntimeException("Too much results found with "+by);
			return collection.get(0);
		}
		return (parent == null ? driver : parent).findElement(by);
	}
	public WebElement findElement(WebElement parent,By by){
		return findElement(parent,by, FIND_ELEMENT_BY_LIST);
	}
	
	public WebElement getElementByAttributeMatchs(String attribute,String value,String match){
		return findElement(null,By.cssSelector(String.format(ATTRIBUTE_MATCHS, attribute,match,value)));
	}
	public WebElement getElementByAttributeContains(String attribute,String value){
		return findElement(null,By.cssSelector(String.format(ATTRIBUTE_CONTAINS, attribute,value)));
	}
	public WebElement getElementByClassContains(WebElement parent,String value){
		return findElement(parent,By.cssSelector(String.format(ATTRIBUTE_CONTAINS, ATTRIBUTE_NAME_CLASS,value)));
	}
	public WebElement getElementByClassContains(WebElement parent,String...values){
		WebElement element = null;
		for(int i = 0 ; i < values.length ; i++)
			if( i == 0 )
				element = getElementByClassContains(parent, values[i]);
			else
				element = getElementByClassContains(element, values[i]);
		return element;
	}
	public WebElement getElementByClassContains(String...values){
		return getElementByClassContains(null, values);
	}
	
	public void waitForVisibilityOf(Long timeOutInSecond,String...values){
		WebDriverWait wait = new WebDriverWait(getDriver(),timeOutInSecond);
		wait.until(ExpectedConditions.visibilityOf(getElementByClassContains(values)));
	}
	
	/* Menu */
	
	public void clickGlobalMenu(String...labels){
    	new GlobalMenu().click(labels);
    }
    
    public void clickContextualMenuEdit(){
    	new ContextMenu(UIManager.CONTEXTUAL_MENU_CLASS).clickEdit();
    }
    public void clickContextualMenuDelete(){
    	new ContextMenu(UIManager.CONTEXTUAL_MENU_CLASS).clickDelete();
    }
    
    /* Table */
    
    public void clickTableCreate(Class<? extends AbstractIdentifiable> identifiableClass){
    	new Table(identifiableClass,"dataTableStyleClass").clickCreate();
    }
    
    public void clickTableRead(Class<? extends AbstractIdentifiable> identifiableClass,String identifier){
    	new Table(identifiableClass,"dataTableStyleClass").clickRead(identifier);
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
