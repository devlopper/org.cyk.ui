package org.cyk.ui.web.primefaces.page;

import org.cyk.ui.api.command.menu.UIMenu;
import org.cyk.utility.common.cdi.BeanListener;

public interface PrimefacesPageListener extends BeanListener {

	void targetDependentInitialisationStarted(AbstractPrimefacesPage abstractPrimefacesPage);

	void targetDependentInitialisationEnded(AbstractPrimefacesPage abstractPrimefacesPage);

	void processContextualMenu(AbstractPrimefacesPage abstractPrimefacesPage,UIMenu contextualMenu);
}
