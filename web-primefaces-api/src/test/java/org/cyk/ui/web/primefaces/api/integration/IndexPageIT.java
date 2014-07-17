package org.cyk.ui.web.primefaces.api.integration;

import org.cyk.ui.web.primefaces.api.todel.LoginPage;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.graphene.page.InitialPage;
import org.jboss.arquillian.graphene.page.Page;
import org.jboss.shrinkwrap.api.spec.WebArchive;

public class IndexPageIT extends AbstractPrimefacesIT {
    
	private static final long serialVersionUID = 1816081753342866340L;

	@Deployment(testable = false)
    public static WebArchive createDeployment() {
        return webArchive();
    }
    
    @Override
	protected void populate() {
		// TODO Auto-generated method stub
		
	}
     
    @Page
    private IndexPage indexPage;
    
    //@Test
    public void should_login_successfully( @InitialPage LoginPage loginPage ) {
       loginPage.getLoginForm().login("demo", "demo");
       indexPage.assertOnPage();
       //Assert.assertEquals(homePage.getUserName(), "Thank you JESUS demo.");
    }

	@Override
	protected void functions() {
		indexPage.assertOnPage();		
	}

	

}