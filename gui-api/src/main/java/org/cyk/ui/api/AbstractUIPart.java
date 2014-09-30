package org.cyk.ui.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.cyk.utility.common.cdi.AbstractBean;

@Getter @Setter
public abstract class AbstractUIPart extends AbstractBean implements UIPart, Serializable {

	private static final long serialVersionUID = 898216365028456783L;
	
	protected String title,template;
	protected UIManager uiManager = UIManager.getInstance();
	protected Collection<UIPartListener> uiPartlisteners = new ArrayList<>();
	
	protected String text(String code){
		return UIManager.getInstance().text(code);
	}
	
}
