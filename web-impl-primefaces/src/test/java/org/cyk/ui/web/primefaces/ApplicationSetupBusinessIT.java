package org.cyk.ui.web.primefaces;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;

public class ApplicationSetupBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;
 
    @Deployment
    public static Archive<?> createDeployment() {
        return createRootDeployment();
    }
    
    @Override
    protected void businesses() {
    	installApplication();
    	System.exit(0);
    }
    
    @Override protected void finds() {}
    @Override protected void create() {}
    @Override protected void delete() {}
    @Override protected void read() {}
    @Override protected void update() {}
    
    
}
