package org.cyk.ui.web.api.form.input;

import javax.faces.model.SelectItem;

import org.cyk.ui.api.editor.input.UIInputSelect;

public interface WebUIInputSelect<VALUE_TYPE,FORM> extends WebUIInputComponent<VALUE_TYPE>,UIInputSelect<VALUE_TYPE, SelectItem> {
	
	
}