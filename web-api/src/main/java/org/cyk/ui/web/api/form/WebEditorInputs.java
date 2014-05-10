package org.cyk.ui.web.api.form;

import org.cyk.ui.api.component.output.UIOutputText;
import org.cyk.ui.api.editor.EditorInputs;
import org.cyk.ui.web.api.form.input.WebUIOutputText;

public interface WebEditorInputs<FORM,OUTPUTLABEL,INPUT,SELECTITEM> extends EditorInputs<FORM,OUTPUTLABEL,INPUT,SELECTITEM> {

	WebUIOutputText outputText(UIOutputText anIOutputText);
	
	String getInputTemplateFile();
	String getInputTemplateFileAtRight();
	String getInputTemplateFileAtTop();
}
