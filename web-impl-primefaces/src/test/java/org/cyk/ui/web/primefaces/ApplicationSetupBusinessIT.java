package org.cyk.ui.web.primefaces;

import org.cyk.system.root.business.api.BusinessLayer;
import org.cyk.system.root.business.api.BusinessLayerListener;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.security.Installation;
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
    	RootBusinessLayer.getInstance().getBusinessLayerListeners().add(new BusinessLayerListener.Adapter.Default(){
			private static final long serialVersionUID = 6148913289155659043L;
			@Override
    		public void beforeInstall(BusinessLayer businessLayer,Installation installation) {
    			installation.getApplication().setUniformResourceLocatorFilteringEnabled(Boolean.TRUE);
    			installation.getApplication().setWebContext("gui-primefaces");
    			installation.getApplication().setName("GuiApp");
    			installation.getManagerCredentials().setUsername("zadi");
    			super.beforeInstall(businessLayer, installation);
    		}
    	});
    	installApplication();
    	System.exit(0);
    }
    
    @Override protected void finds() {}
    @Override protected void create() {}
    @Override protected void delete() {}
    @Override protected void read() {}
    @Override protected void update() {}
    
    
}
