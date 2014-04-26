package org.cyk.ui.web.primefaces.api;

import java.io.File;
import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@RunWith(Arquillian.class)
public class LoginScreenGrapheneIT {
    private static final String WEBAPP_SRC = "src/main/webapp";
    
    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "login.war")
            .addClasses(Credentials.class, User.class, LoginController.class)
            .addAsWebResource(new File(WEBAPP_SRC, "login.xhtml"))
            .addAsWebResource(new File(WEBAPP_SRC, "home.xhtml"))
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
            .addAsWebInfResource(EmptyAsset.INSTANCE, "faces-config.xml")
            /*
            .addAsWebInfResource(
                new StringAsset("<faces-config version=\"2.0\"/>"),
                "faces-config.xml")*/
            ;
    }
    
    @Drone
    private WebDriver browser;
    
    @ArquillianResource
    private URL deploymentUrl;
    
    @FindBy                                     // 1. injects an element by default location strategy ("idOrName")
    private WebElement userName;

    @FindBy
    private WebElement password;

    @FindBy(id = "login")
    private WebElement loginButton;

    @FindBy(tagName = "li")                     // 2. injects a first element with given tag name
    private WebElement facesMessage;

    @FindByJQuery("p:visible")                  // 3. injects an element using jQuery selector
    private WebElement signedAs;

    @FindBy(css = "input[type=submit]")
    private WebElement whoAmI;
    
    @Test
    public void should_login_successfully() {
        browser.get(deploymentUrl.toExternalForm() + "login.jsf");      // 1. open the tested page
        
        userName.sendKeys("demo");                                      // 3. control the page
        password.sendKeys("demo");

        //Graphene.waitModel().until().element(facesMessage).is().present();     // once the element is present, page is loaded
        
        Graphene.guardHttp(loginButton).click(); // 1. synchronize full-page request
        Assert.assertEquals("Welcome", facesMessage.getText().trim());
        
        whoAmI.click();
        Graphene.waitAjax().until().element(signedAs).is().present();
        Assert.assertTrue(signedAs.getText().contains("demo"));
        
    }
}