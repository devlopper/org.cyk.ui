package org.cyk.ui.web.primefaces.api.integration;

import org.cyk.ui.web.primefaces.test.automation.event.PersonWebITRunner;

public class PersonWebIT extends AbstractWebIT {

	private static final long serialVersionUID = 1L;
	
	public PersonWebIT() {
		runnables.add(new PersonWebITRunner());
	}
	   
}
