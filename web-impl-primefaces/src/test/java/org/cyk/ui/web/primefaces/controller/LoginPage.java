package org.cyk.ui.web.primefaces.controller;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Location("login.jsf")
public class LoginPage {

    @FindBy
    private WebElement userName;

    @FindBy
    private WebElement password;

    @FindBy(id = "login")
    private WebElement loginButton;
    
    public void login(String userName, String password) {
        this.userName.sendKeys(userName);
        this.password.sendKeys(password);
        Graphene.guardHttp(loginButton).click();
    }
}