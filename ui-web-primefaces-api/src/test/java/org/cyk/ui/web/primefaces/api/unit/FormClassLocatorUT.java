package org.cyk.ui.web.primefaces.api.unit;

import java.util.Collection;

import org.cyk.system.root.model.geography.Locality;
import org.cyk.ui.web.primefaces.FormClassLocator;
import org.cyk.ui.web.primefaces.page.geography.LocalityEditPage;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.mockito.InjectMocks;

public class FormClassLocatorUT extends AbstractUnitTest {

	private static final long serialVersionUID = 3681555921786058917L;
	
	@InjectMocks private FormClassLocator locator;
	
	@Override
	protected void registerBeans(Collection<Object> collection) {
		super.registerBeans(collection);
		collection.add(locator);
	}
	
	@Override
	protected void _execute_() {
		super._execute_();
		assertEquals("Locality edit form not found", LocalityEditPage.Form.class, locator.locate(Locality.class));
	}
	
	
	
}
