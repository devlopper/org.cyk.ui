package org.cyk.ui.web.api.form;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import javax.faces.model.SelectItem;

import org.cyk.ui.api.component.output.UIOutputComponent;
import org.cyk.ui.api.component.output.UIOutputText;
import org.cyk.ui.api.form.AbstractFormData;
import org.cyk.ui.api.form.IFormContainer;
import org.cyk.ui.api.form.input.UIInputComponent;
import org.cyk.ui.api.form.input.UIInputDate;
import org.cyk.ui.api.form.input.UIInputNumber;
import org.cyk.ui.api.form.input.UIInputSelectOne;
import org.cyk.ui.api.form.input.UIInputText;
import org.cyk.ui.api.form.input.ISelectItem;
import org.cyk.ui.web.api.OutputText;
import org.cyk.ui.web.api.form.input.WebUIInputComponent;
import org.cyk.ui.web.api.form.input.WebUIInputDate;
import org.cyk.ui.web.api.form.input.WebUIInputNumber;
import org.cyk.ui.web.api.form.input.WebUIInputSelectOne;
import org.cyk.ui.web.api.form.input.WebUIInputText;
import org.cyk.ui.web.api.form.input.WebUIOutputComponent;
import org.cyk.ui.web.api.form.input.WebUIOutputText;
import org.cyk.ui.web.api.form.input.InputDate;
import org.cyk.ui.web.api.form.input.InputNumber;
import org.cyk.ui.web.api.form.input.InputSelectOne;
import org.cyk.ui.web.api.form.input.InputText;

public abstract class AbstractWebFormData<FORM,OUTPUTLABEL,INPUT> extends AbstractFormData<FORM,OUTPUTLABEL,INPUT,SelectItem> implements IWebForm<FORM,OUTPUTLABEL,INPUT,SelectItem> , Serializable {

	private static final long serialVersionUID = 5514311939767908514L;
	
	
	@SuppressWarnings("unchecked")
	public UIInputComponent<?> input(UIInputComponent<?> aComponent){
		WebUIInputComponent<?> component = null;
		if(aComponent instanceof UIInputText)
			component = inputText((UIInputText) aComponent);
		else if(aComponent instanceof UIInputDate)
			component = inputDate((UIInputDate) aComponent);
		else if(aComponent instanceof UIInputNumber)
			component = inputNumber((UIInputNumber) aComponent);
		else if(aComponent instanceof UIInputSelectOne<?,?>)
			component = inputSelectOne((UIInputSelectOne<Object,ISelectItem>) aComponent);
		
		return component;
	}
	
	@Override
	public UIOutputComponent<?> output(UIOutputComponent<?> anIOutput) {
		WebUIOutputComponent<?> component = null;
		if(anIOutput instanceof UIOutputText)
			component = outputText((UIOutputText) anIOutput);
		return component;
	}
	
	@Override
	public WebUIInputDate inputDate(UIInputDate anIInput) {
		return new InputDate(this,anIInput);
	}
	
	@Override
	public WebUIInputNumber inputNumber(UIInputNumber anIInput) {
		return new InputNumber(this,anIInput);
	}
	
	@Override
	public WebUIInputSelectOne<Object,FORM> inputSelectOne(UIInputSelectOne<Object,ISelectItem> anIInput) {
		if(container==null)
			throw new IllegalArgumentException("No container has been set");
		WebUIInputSelectOne<Object,FORM> inputSelectOne = new InputSelectOne<FORM>(this,anIInput);
		if(inputSelectOne.getItems()==null || inputSelectOne.getItems().isEmpty()){
			@SuppressWarnings("unchecked")
			Collection<Object> datas = (Collection<Object>) ((IFormContainer<?,?,?,?>)container).load(anIInput.getField().getType());
			if(anIInput.getValue()!=null){
				if(datas==null)
					datas = Arrays.asList(anIInput.getValue());
				else
					datas.add(anIInput.getValue());
			}
			/*	
			if(datas!=null){
				Collection<SelectItem> items = new LinkedHashSet<>();
				for(Object object : datas)
					items.add(((IFormContainer<?,?,?,?>)container).item(object));
				inputSelectOne.setItems(items);
			}*/
		}
		
		return inputSelectOne;
	}
	
	@Override
	public WebUIInputText inputText(UIInputText anIInput) {
		return new InputText(this,anIInput);
	}
	
	@Override
	public WebUIOutputText outputText(UIOutputText anIOutputText) {
		return new OutputText((UIOutputText) anIOutputText);
	}
	
}
