package org.cyk.ui.web.primefaces.api.todel;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.GrapheneElement;
import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage {

    @FindBy(tagName = "li")
    private WebElement facesMessage;

    @FindByJQuery("p:visible")
    private WebElement signedAs;

    @FindBy(css = "input[type=submit]")
    private GrapheneElement whoAmI;

    public void assertOnHomePage() {
        Assert.assertEquals("We should be on home page", "Welcome", getMessage());
    }
    
    public String getMessage() {
        return facesMessage.getText().trim();
    }

    public String getUserName() {
        //if (signedAs.isDisplayed())
        	Graphene.guardAjax(whoAmI).click();
        
        return signedAs.getText();
    }
}