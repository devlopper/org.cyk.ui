package org.cyk.ui.web.primefaces.api.integration;

import java.io.File;
import java.net.URL;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.impl.BusinessIntegrationTestHelper;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.persistence.impl.GenericDaoImpl;
import org.cyk.utility.test.AbstractIntegrationTestJpaBased;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.openqa.selenium.WebDriver;

public abstract class AbstractWebIT extends AbstractIntegrationTestJpaBased {
   
	private static final long serialVersionUID = -3278155860493768455L;

	private static final String WEBAPP_SRC = "src/main/resources/META-INF/pages";
    
    public static WebArchive webArchive() {
        return ShrinkWrap.create(WebArchive.class, "webui.war")
    		.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
            .addAsWebInfResource(EmptyAsset.INSTANCE, "faces-config.xml")
            /*
            .addAsWebInfResource(
                new StringAsset("<faces-config version=\"2.0\"/>"),
                "faces-config.xml")*/
    		//business classes	
            
            .addClasses(BusinessIntegrationTestHelper.classes())
            .addPackages(Boolean.FALSE,BusinessIntegrationTestHelper.packages())
            //controllers
            
            //web pages
            .addAsWebResource(new File(WEBAPP_SRC, "login.xhtml"))
            .addAsWebResource(new File(WEBAPP_SRC, "home.xhtml"))
            .addAsWebResource(new File(WEBAPP_SRC, "index.xhtml"))
            ;
    }
    
    @Drone protected WebDriver browser;
    
    @ArquillianResource private URL deploymentUrl;
     
	protected @Inject GenericDaoImpl g;
	protected @Inject GenericBusiness genericBusiness;
	
    @Override
    public EntityManager getEntityManager() {
        return g.getEntityManager();
    }
	
    @Override
    protected void _execute_() {
    	System.out.println("AbstractWebIT._execute_()");
    	System.out.println(deploymentUrl);
        super._execute_();
        create();    
        read(); 
        update();    
        delete();    
        functions();
    }
    
	protected abstract void functions();
	
	@Override
	protected final void create() {}
	
	@Override
	protected final void read() {}
	
	@Override
	protected final void update() {}
	
	@Override
	protected final void delete() {}
	
	/* Shortcut */
    
    protected AbstractIdentifiable create(AbstractIdentifiable object){
        return genericBusiness.create(object);
    }
    
}