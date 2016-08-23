package org.cyk.ui.web.primefaces;

import org.cyk.system.root.business.impl.party.person.AbstractActorDetails;
import org.cyk.system.test.model.actor.Actor;
import org.cyk.utility.common.CommonUtils;

public class AnyIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;
 
    @Override
    protected void businesses() {
    	AbstractActorDetails.AbstractDefault.Default actorDetails = CommonUtils.getInstance().instanciate(AbstractActorDetails.AbstractDefault.Default.class,new Class<?>[]{Actor.class},new Object[]{new Actor()});
		assertThat("Actor details created", actorDetails!=null);
    }
    
    @Override protected void finds() {}
    @Override protected void create() {}
    @Override protected void delete() {}
    @Override protected void read() {}
    @Override protected void update() {}
    
    
}
