package org.cyk.ui.web.primefaces.api;

import java.io.File;
import java.net.URL;

import org.cyk.ui.web.primefaces.api.todel.Credentials;
import org.cyk.ui.web.primefaces.api.todel.HomePage;
import org.cyk.ui.web.primefaces.api.todel.LoginController;
import org.cyk.ui.web.primefaces.api.todel.LoginPage;
import org.cyk.ui.web.primefaces.api.todel.User;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.page.InitialPage;
import org.jboss.arquillian.graphene.page.Page;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

@RunWith(Arquillian.class)
public class LoginScreenIT {
    private static final String WEBAPP_SRC = "src/main/resources/META-INF/pages";
    
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
    
    @Page
    private HomePage homePage;
    
    @Test
    public void should_login_successfully( @InitialPage LoginPage loginPage ) {
       loginPage.getLoginForm().login("demo", "demo");
       homePage.assertOnHomePage();
       Assert.assertEquals(homePage.getUserName(), "Thank you JESUS demo.");
    }
}