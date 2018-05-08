package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.mathematics.movement.MovementCollectionBusiness;
import org.cyk.system.root.business.api.mathematics.movement.MovementCollectionIdentifiableGlobalIdentifierBusiness;
import org.cyk.system.root.business.api.party.StoreBusiness;
import org.cyk.system.root.business.impl.party.ApplicationBusinessImpl;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.mathematics.movement.MovementCollection;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.party.Store;
import org.cyk.system.root.model.security.Installation;
import org.cyk.system.root.persistence.api.party.PartyDao;
import org.cyk.utility.common.helper.ClassHelper;

public class ApplicationSetupBusinessIT extends AbstractBusinessIT {
    private static final long serialVersionUID = -6691092648665798471L;
 
    static {
		ClassHelper.getInstance().map(ApplicationBusinessImpl.Listener.class, ApplicationBusinessAdapter.class);
	}
    
    @Override
    protected void businesses() {
    	installApplication();
    	for(Object[] storeArray : new Object[][]{{"ENTREPOT","Entrepot"},{"BCOC","Boutique cocody"},{"BYOP","Boutique yopougon"}}){
    		Store store = inject(StoreBusiness.class).instanciateOne();
    		store.setCode((String)storeArray[0]).setName((String)storeArray[1]);
    		store.setHasPartyAsCompany(Boolean.TRUE);
    		inject(GenericBusiness.class).create(store);
    		for(Object[] productArray : new Object[][]{{"OMO","Omo"},{"JAV","Javel"},{"SAC","Sac"}}){
    			MovementCollection movementCollection = inject(MovementCollectionBusiness.class).instanciateOne();
    			movementCollection.setCode(store.getCode()+productArray[0]);
    			movementCollection.setName((String)productArray[1]);
    			movementCollection.setTypeFromCode(RootConstant.Code.MovementCollectionType.STOCK_REGISTER);
    			movementCollection.setIsCreateBufferAutomatically(Boolean.TRUE);
    			inject(GenericBusiness.class).create(movementCollection);
    			
    			MovementCollectionIdentifiableGlobalIdentifier movementCollectionIdentifiableGlobalIdentifier = 
    					inject(MovementCollectionIdentifiableGlobalIdentifierBusiness.class).instanciateOne();
    			movementCollectionIdentifiableGlobalIdentifier.setMovementCollection(movementCollection);
    			movementCollectionIdentifiableGlobalIdentifier.setIdentifiableGlobalIdentifier(inject(PartyDao.class).read(store.getCode()).getGlobalIdentifier());
    			inject(GenericBusiness.class).create(movementCollectionIdentifiableGlobalIdentifier);
    					
    		}
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
