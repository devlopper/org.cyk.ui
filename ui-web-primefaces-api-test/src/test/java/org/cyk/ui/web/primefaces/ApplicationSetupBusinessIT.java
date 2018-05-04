package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.mathematics.movement.MovementCollectionBusiness;
import org.cyk.system.root.business.impl.party.ApplicationBusinessImpl;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.mathematics.movement.MovementCollection;
import org.cyk.system.root.model.security.Installation;
import org.cyk.utility.common.helper.ClassHelper;

public class ApplicationSetupBusinessIT extends AbstractBusinessIT {
    private static final long serialVersionUID = -6691092648665798471L;
 
    static {
		ClassHelper.getInstance().map(ApplicationBusinessImpl.Listener.class, ApplicationBusinessAdapter.class);
	}
    
    @Override
    protected void businesses() {
    	installApplication();
    	for(Object[] store : new Object[][]{{"ENTREPOT"},{"COCODY"},{"YOPOUGON"}})
    		for(Object[] product : new Object[][]{{"OMO"},{"JAVEL"},{"SAC"}}){
    			MovementCollection movementCollection = inject(MovementCollectionBusiness.class).instanciateOne();
    			movementCollection.setCode((String)store[0]+product[0]);
    			movementCollection.setName(store[0]+" "+product[0]);
    			movementCollection.setTypeFromCode(RootConstant.Code.MovementCollectionType.STOCK_REGISTER);
    			movementCollection.setIsCreateBufferAutomatically(Boolean.TRUE);
    			inject(GenericBusiness.class).create(movementCollection);
    		}
    	System.exit(0);
    }
    
    @Override protected void finds() {}
    @Override protected void create() {}
    @Override protected void delete() {}
    @Override protected void read() {}
    @Override protected void update() {}
    
    /**/
    
    public static class ApplicationBusinessAdapter extends AbstractBusinessIT.ApplicationBusinessAdapter implements Serializable {
		private static final long serialVersionUID = 1L;
    	
		@Override
		public void installationStarted(Installation installation) {
			super.installationStarted(installation);
			installation.setIsCreateAccounts(Boolean.TRUE);
			installation.getApplication().setUniformResourceLocatorFiltered(Boolean.FALSE);			
		}		
    }
}
