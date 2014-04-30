package org.cyk.ui.web.api.form;

import javax.faces.model.SelectItem;

import org.cyk.ui.api.editor.Editor;
import org.cyk.ui.web.api.ObjectConverter;

public interface WebEditor<FORM,OUTPUTLABEL,INPUT> extends Editor<FORM,OUTPUTLABEL,INPUT,SelectItem> {

	ObjectConverter getObjectConverter();
	
}
