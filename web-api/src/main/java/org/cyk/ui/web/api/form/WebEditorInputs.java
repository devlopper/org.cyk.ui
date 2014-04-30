package org.cyk.ui.web.api.form;

import org.cyk.ui.api.component.output.UIOutputText;
import org.cyk.ui.api.editor.EditorInputs;
import org.cyk.ui.api.editor.input.ISelectItem;
import org.cyk.ui.api.editor.input.UIInputDate;
import org.cyk.ui.api.editor.input.UIInputNumber;
import org.cyk.ui.api.editor.input.UIInputSelectOne;
import org.cyk.ui.api.editor.input.UIInputText;
import org.cyk.ui.web.api.form.input.WebUIInputDate;
import org.cyk.ui.web.api.form.input.WebUIInputNumber;
import org.cyk.ui.web.api.form.input.WebUIInputSelectOne;
import org.cyk.ui.web.api.form.input.WebUIInputText;
import org.cyk.ui.web.api.form.input.WebUIOutputText;

public interface WebEditorInputs<FORM,OUTPUTLABEL,INPUT,SELECTITEM> extends EditorInputs<FORM,OUTPUTLABEL,INPUT,SELECTITEM> {

	WebUIInputText inputText(UIInputText anIInput);
	WebUIInputNumber inputNumber(UIInputNumber anIInput);
	WebUIInputSelectOne<Object,FORM> inputSelectOne(UIInputSelectOne<Object,ISelectItem> anIInput);
	WebUIInputDate inputDate(UIInputDate anIInput);
	
	WebUIOutputText outputText(UIOutputText anIOutputText);
	
	String getInputTemplateFile();
	String getInputTemplateFileAtRight();
	String getInputTemplateFileAtTop();
}
