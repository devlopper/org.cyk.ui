package org.cyk.ui.web.primefaces;

import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.business.impl.party.ApplicationBusinessImpl;
import org.cyk.system.root.business.impl.party.ApplicationBusinessImplListener;
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
    	ApplicationBusinessImpl.LISTENERS.add(new ApplicationBusinessImplListener.Adapter.Default(){
			private static final long serialVersionUID = -7737204312141333272L;
    		@Override
    		public void installationStarted(Installation installation) {
    			installation.getApplication().setUniformResourceLocatorFilteringEnabled(Boolean.FALSE);
    			installation.getApplication().setWebContext("gui-primefaces");
    			installation.getApplication().setName("GuiApp");
    			super.installationStarted(installation);
    		}
    	});
    	installApplication();
    	create(RootBusinessLayer.getInstance().getMovementCollectionBusiness().instanciateOne("MyMovCol", "ComeIn", "ComeOut"));
    	System.exit(0);
    }
    
    @Override protected void finds() {}
    @Override protected void create() {}
    @Override protected void delete() {}
    @Override protected void read() {}
    @Override protected void update() {}
    
    
}
