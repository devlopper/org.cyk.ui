package org.cyk.ui.web.primefaces.api.integration;

import org.cyk.ui.web.primefaces.test.automation.event.EventWebITRunner;
import org.cyk.ui.web.primefaces.test.automation.event.PersonWebITRunner;

public class AllWebIT extends AbstractWebIT {

	private static final long serialVersionUID = 1L;
	
	public AllWebIT() {
		runnables.add(new EventWebITRunner());
		runnables.add(new PersonWebITRunner());
	}
	   
}
