package org.cyk.ui.web.api.editor.input;



public interface WebUIInputText extends WebUIInputComponent<String> {

	String getFilterMask();
	
	Integer getSize();
}