package org.cyk.ui.web.primefaces;

import org.cyk.system.root.business.impl.party.person.AbstractActorDetails;
import org.cyk.ui.test.model.Actor;
import org.cyk.utility.common.CommonUtils;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class AnyUT extends AbstractUnitTest {

	private static final long serialVersionUID = -6691092648665798471L;
	
	@Test
	public void actorDetails(){
		AbstractActorDetails.AbstractDefault.Default actorDetails = CommonUtils.getInstance().instanciate(AbstractActorDetails.AbstractDefault.Default.class,new Class<?>[]{Actor.class},new Object[]{new Actor()});
		assertThat("Actor details created", actorDetails!=null);
	}
	
	
}
