package org.cyk.ui.web.primefaces.test.automation.control.input;

import java.io.Serializable;

import org.cyk.system.root.business.impl.language.LanguageBusinessImpl;
import org.cyk.system.root.business.impl.userinterface.style.CascadeStyleSheetBusinessImpl;
import org.cyk.ui.web.primefaces.test.automation.AbstractElement;
import org.cyk.utility.common.Constant;
import org.openqa.selenium.Keys;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractInput<TYPE> extends AbstractElement implements Serializable {

	private static final long serialVersionUID = -3394592929247102811L;

	protected TYPE value;
	protected Boolean clearBeforeSendKeys = Boolean.TRUE,sendTabKeyAfterSendKeys = Boolean.TRUE;
	
	public AbstractInput(String fieldName, TYPE value) {
		super(fieldName);
		this.value = value;
	}
	
	@Override
	protected String buildClassName(String fieldName) {
		return new CascadeStyleSheetBusinessImpl().generateClass(Constant.EMPTY_STRING, LanguageBusinessImpl.buildIdentifierFromFieldName(fieldName,LanguageBusinessImpl.FIELD_MARKER_START));
	}
	
	public AbstractInput<TYPE> clear(){
		webElement.clear();
		return this;
	}
	protected AbstractInput<TYPE> __sendKeys__(){
		String keysToSend = getKeysToSend();
		if(keysToSend!=null)
			webElement.sendKeys(keysToSend);
		return this;
	}
	protected String getKeysToSend(){
		if(value instanceof String)
			return (String) value;
		return null;
	}
	
	public AbstractInput<TYPE> sendTabKey(){
		webElement.sendKeys(Keys.TAB);
		pause(500);
		return this;
	}
	
	public AbstractInput<TYPE> sendKeys(){
		if(Boolean.TRUE.equals(getClearBeforeSendKeys()))
			clear();
		__sendKeys__();
		if(Boolean.TRUE.equals(getSendTabKeyAfterSendKeys()))
			sendTabKey();
		return this;
	}
	
	/**/
	
	public static final String TAG_NAME_INPUT = "input";
	
}
