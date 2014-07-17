package org.cyk.ui.web.primefaces.api.todel;

import lombok.Getter;

import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.support.FindBy;

@Location("login.jsf")
public class LoginPage {

	@FindBy @Getter
    private LoginForm loginForm;        // locates the root of a page fragment on a particular page
    
}