package org.cyk.ui.web.primefaces.test.automation;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.impl.language.LanguageBusinessImpl;
import org.cyk.ui.api.CascadeStyleSheet;
import org.openqa.selenium.Keys;

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
		return CascadeStyleSheet.generateClassFrom(null, LanguageBusinessImpl.buildIdentifierFromFieldName(fieldName,LanguageBusinessImpl.FIELD_MARKER_START));
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
