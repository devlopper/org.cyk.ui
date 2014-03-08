package org.cyk.ui.web.api;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;

import javax.faces.model.SelectItem;

import org.cyk.ui.api.component.input.IInputBoolean;
import org.cyk.ui.api.component.input.IInputComponent;
import org.cyk.ui.api.component.input.IInputDate;
import org.cyk.ui.api.component.input.IInputNumber;
import org.cyk.ui.api.component.input.IInputSelectOne;
import org.cyk.ui.api.component.input.IInputText;
import org.cyk.ui.api.component.input.ISelectItem;
import org.cyk.ui.api.form.AbstractForm;
import org.cyk.ui.web.api.component.IWebInputBoolean;
import org.cyk.ui.web.api.component.IWebInputComponent;
import org.cyk.ui.web.api.component.IWebInputDate;
import org.cyk.ui.web.api.component.IWebInputNumber;
import org.cyk.ui.web.api.component.IWebInputSelectOne;
import org.cyk.ui.web.api.component.IWebInputText;
import org.cyk.ui.web.api.component.InputBoolean;
import org.cyk.ui.web.api.component.InputDate;
import org.cyk.ui.web.api.component.InputNumber;
import org.cyk.ui.web.api.component.InputSelectOne;
import org.cyk.ui.web.api.component.InputText;

public abstract class AbstractWebForm<FORM,OUTPUTLABEL,INPUT> extends AbstractForm<FORM,OUTPUTLABEL,INPUT,SelectItem> implements IWebForm<FORM,OUTPUTLABEL,INPUT,SelectItem> , Serializable {

	private static final long serialVersionUID = 5514311939767908514L;
	
	
	
	@SuppressWarnings("unchecked")
	public IInputComponent<?> input(IInputComponent<?> aComponent){
		IWebInputComponent<?> component = null;
		if(aComponent instanceof IInputText)
			component = inputText((IInputText) aComponent);
		else if(aComponent instanceof IInputDate)
			component = inputDate((IInputDate) aComponent);
		else if(aComponent instanceof IInputBoolean)
			component = inputBoolean((IInputBoolean) aComponent);
		else if(aComponent instanceof IInputNumber)
			component = inputNumber((IInputNumber) aComponent);
		else if(aComponent instanceof IInputSelectOne<?,?>)
			component = inputSelectOne((IInputSelectOne<Object,ISelectItem>) aComponent);
		
		return component;
	}
	
	@Override
	public IWebInputBoolean inputBoolean(IInputBoolean anIInput) {
		return new InputBoolean(this,anIInput);
	}
	
	@Override
	public IWebInputDate inputDate(IInputDate anIInput) {
		return new InputDate(this,anIInput);
	}
	
	@Override
	public IWebInputNumber inputNumber(IInputNumber anIInput) {
		return new InputNumber(this,anIInput);
	}
	
	@Override
	public IWebInputSelectOne<Object,FORM> inputSelectOne(IInputSelectOne<Object,ISelectItem> anIInput) {
		if(container==null)
			throw new IllegalArgumentException("No container has been set");
		IWebInputSelectOne<Object,FORM> inputSelectOne = new InputSelectOne<FORM>(this,anIInput);
		if(inputSelectOne.getItems()==null || inputSelectOne.getItems().isEmpty()){
			@SuppressWarnings("unchecked")
			Collection<Object> datas = (Collection<Object>) container.load(anIInput.getField().getType());
			if(anIInput.getValue()!=null){
				if(datas==null)
					datas = Arrays.asList(anIInput.getValue());
				else
					datas.add(anIInput.getValue());
			}
				
			if(datas!=null){
				Collection<SelectItem> items = new LinkedHashSet<>();
				for(Object object : datas)
					items.add(container.item(object));
				inputSelectOne.setItems(items);
			}
		}
		
		return inputSelectOne;
	}
	
	@Override
	public IWebInputText inputText(IInputText anIInput) {
		return new InputText(this,anIInput);
	}
	
}
