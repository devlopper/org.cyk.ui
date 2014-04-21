package org.cyk.ui.api;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

import org.cyk.utility.common.cdi.AbstractBean;

public abstract class AbstractViewContainer<FORM,OUTPUTLABEL,INPUT,SELECTITEM> extends AbstractBean implements UIContainer<FORM,OUTPUTLABEL,INPUT,SELECTITEM>,Serializable {

	private static final long serialVersionUID = 7282005324574303823L;

	@Getter @Setter protected UIWindow<FORM,OUTPUTLABEL,INPUT,SELECTITEM> window;
	@Getter protected String title;
	//@Getter protected Collection<UIView> views = new LinkedList<>();
	@Getter protected Object objectModel;

	/**/
	
	protected String text(String id){
		return getWindow().getUiManager().getLanguageBusiness().findText(id);
	}
	
}
