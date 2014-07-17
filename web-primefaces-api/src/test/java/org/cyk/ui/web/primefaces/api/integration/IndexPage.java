package org.cyk.ui.web.primefaces.api.integration;

import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class IndexPage {

    @FindBy(tagName="h1")
    private WebElement welcome;

    public void assertOnPage() {
        Assert.assertEquals("We should be on index page", "Welcome!!", getMessage());
    }
    
    public String getMessage() {
        return welcome.getText().trim();
    }

}