package org.cyk.ui.web.primefaces;

import java.util.Collection;

import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.persistence.api.geography.LocalityDao;


public class ReadLocalityIT extends AbstractBusinessIT {
	   
	private static final long serialVersionUID = 8691254326402622637L;
		
	protected void createAll() {}
	
    @Override
    protected void create() {}

    @Override
    protected void read() {}
    
    @Override
    protected void update() {}
    
    @Override
    protected void delete() {}
		
    @Override
    protected void finds() {}

    @Override
    protected void businesses() {
    	Locality coteDivoire = inject(LocalityDao.class).read(RootConstant.Code.Country.COTE_DIVOIRE);
    	Collection<Locality> localities  = inject(LocalityDao.class).readByParent(coteDivoire);
    	System.out.println(localities);
    	System.exit(0);
    }

}
