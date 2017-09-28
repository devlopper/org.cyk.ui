package org.cyk.ui.api;

import java.io.Serializable;

import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.MarkupLanguageHelper;

public class AbstractUserInterfaceComponent extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Object instanciatePropertiesMap() {
		return new MarkupLanguageHelper.Attributes();
	}
	
}
