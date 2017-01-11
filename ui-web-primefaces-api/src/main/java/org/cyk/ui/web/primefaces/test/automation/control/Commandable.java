package org.cyk.ui.web.primefaces.test.automation.control;

import java.io.Serializable;

import org.cyk.system.root.business.impl.userinterface.style.CascadeStyleSheetBusinessImpl;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.web.primefaces.test.automation.AbstractElement;
import org.cyk.ui.web.primefaces.test.automation.SeleniumHelper;
import org.cyk.utility.common.annotation.user.interfaces.Event;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Commandable extends AbstractElement implements Serializable {

	private static final long serialVersionUID = 1L;

	private Boolean confirmed,notified;
	
	public Commandable(String labelIdentifier) {
		super(labelIdentifier);
	}
	
	@Override
	protected String buildClassName(String labelIdentifier) {
		return new CascadeStyleSheetBusinessImpl().generateClass(UIManager.COMMANDABLE_CLASS_PREFIX, labelIdentifier);
	}
	
	@Override
	public Commandable click() {
		super.click();
		if(Boolean.TRUE.equals(confirmed)){
			pause(CONFIRMED_PAUSE);
			SeleniumHelper.getInstance().getElementByClassContains(UIManager.CONFIRMATION_DIALOG_YES_COMMANDABLE_CLASS).click();
		}
		if(Boolean.TRUE.equals(notified)){
			pause(NOTIFIED_PAUSE);
			SeleniumHelper.getInstance().getElementByClassContains(UIManager.NOTIFICATION_DIALOG_OK_COMMANDABLE_CLASS).click();
		}
		return this;
	}
	
	/**/
	
	public static Event.Listener EVENT_LISTENER;
	public static Long CONFIRMED_PAUSE = 1000l * 1l;
	public static Long NOTIFIED_PAUSE = 1000l * 2l;
	
}
