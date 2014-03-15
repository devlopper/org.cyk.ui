package org.cyk.ui.web.api.form;

import org.cyk.ui.api.component.output.UIOutputText;
import org.cyk.ui.api.form.UIFormData;
import org.cyk.ui.api.form.input.UIInputDate;
import org.cyk.ui.api.form.input.UIInputNumber;
import org.cyk.ui.api.form.input.UIInputSelectOne;
import org.cyk.ui.api.form.input.UIInputText;
import org.cyk.ui.api.form.input.ISelectItem;
import org.cyk.ui.web.api.form.input.WebUIInputDate;
import org.cyk.ui.web.api.form.input.WebUIInputNumber;
import org.cyk.ui.web.api.form.input.WebUIInputSelectOne;
import org.cyk.ui.web.api.form.input.WebUIInputText;
import org.cyk.ui.web.api.form.input.WebUIOutputText;

public interface IWebForm<FORM,OUTPUTLABEL,INPUT,SELECTITEM> extends UIFormData<FORM,OUTPUTLABEL,INPUT,SELECTITEM> {

	WebUIInputText inputText(UIInputText anIInput);
	WebUIInputNumber inputNumber(UIInputNumber anIInput);
	WebUIInputSelectOne<Object,FORM> inputSelectOne(UIInputSelectOne<Object,ISelectItem> anIInput);
	WebUIInputDate inputDate(UIInputDate anIInput);
	
	WebUIOutputText outputText(UIOutputText anIOutputText);
}
