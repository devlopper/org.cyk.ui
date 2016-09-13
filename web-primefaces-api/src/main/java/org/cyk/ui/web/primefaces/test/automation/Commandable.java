package org.cyk.ui.web.primefaces.test.automation;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.CascadeStyleSheet;

@Getter @Setter
public class Commandable extends AbstractElement implements Serializable {

	private static final long serialVersionUID = 1L;

	private Boolean confirmed,notified;
	
	public Commandable(String labelIdentifier) {
		super(labelIdentifier);
	}
	
	@Override
	protected String buildClassName(String labelIdentifier) {
		return CascadeStyleSheet.generateClassFrom(CascadeStyleSheet.COMMANDABLE_CLASS_PREFIX, labelIdentifier);
	}
	
	@Override
	public Commandable click() {
		super.click();
		if(Boolean.TRUE.equals(confirmed)){
			pause(1000 * 1l);
			SeleniumHelper.getInstance().getElementByClassContains(CascadeStyleSheet.CONFIRMATION_DIALOG_YES_COMMANDABLE_CLASS).click();
		}
		if(Boolean.TRUE.equals(notified)){
			pause(1000 * 2l);
			SeleniumHelper.getInstance().getElementByClassContains(CascadeStyleSheet.NOTIFICATION_DIALOG_OK_COMMANDABLE_CLASS).click();
		}
		return this;
	}
	
}
