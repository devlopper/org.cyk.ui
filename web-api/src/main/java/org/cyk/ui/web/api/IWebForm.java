package org.cyk.ui.web.api;

import org.cyk.ui.api.component.input.IInputBoolean;
import org.cyk.ui.api.component.input.IInputDate;
import org.cyk.ui.api.component.input.IInputNumber;
import org.cyk.ui.api.component.input.IInputSelectOne;
import org.cyk.ui.api.component.input.IInputText;
import org.cyk.ui.api.form.IForm;
import org.cyk.ui.web.api.component.IWebInputBoolean;
import org.cyk.ui.web.api.component.IWebInputDate;
import org.cyk.ui.web.api.component.IWebInputNumber;
import org.cyk.ui.web.api.component.IWebInputSelectOne;
import org.cyk.ui.web.api.component.IWebInputText;

public interface IWebForm<FORM,OUTPUTLABEL,INPUT,SELECTITEM> extends IForm<FORM,OUTPUTLABEL,INPUT,SELECTITEM> {

	IWebInputText inputText(IInputText anIInput);
	IWebInputNumber inputNumber(IInputNumber anIInput);
	IWebInputBoolean inputBoolean(IInputBoolean anIInput);
	IWebInputSelectOne<Object,FORM> inputSelectOne(IInputSelectOne<Object> anIInput);
	IWebInputDate inputDate(IInputDate anIInput);
	
	void link(OUTPUTLABEL anOutputLabel,INPUT anInput);
	
}
