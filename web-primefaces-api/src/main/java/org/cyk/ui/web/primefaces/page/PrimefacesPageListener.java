package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;

import org.cyk.ui.api.command.menu.UIMenu;
import org.cyk.utility.common.cdi.BeanListener;
import org.cyk.utility.common.cdi.DefaultBeanAdapter;

public interface PrimefacesPageListener extends BeanListener {

	void targetDependentInitialisationStarted(AbstractPrimefacesPage abstractPrimefacesPage);
	void targetDependentInitialisationEnded(AbstractPrimefacesPage abstractPrimefacesPage);
	void processContextualMenu(AbstractPrimefacesPage abstractPrimefacesPage,UIMenu contextualMenu);
	
	/**/
	
	public static class Adapter extends DefaultBeanAdapter implements PrimefacesPageListener,Serializable {
		private static final long serialVersionUID = -7944074776241690783L;
		@Override public void targetDependentInitialisationStarted(AbstractPrimefacesPage abstractPrimefacesPage) {}
		@Override public void targetDependentInitialisationEnded(AbstractPrimefacesPage abstractPrimefacesPage) {}
		@Override public void processContextualMenu(AbstractPrimefacesPage abstractPrimefacesPage, UIMenu contextualMenu) {}
	}

}
