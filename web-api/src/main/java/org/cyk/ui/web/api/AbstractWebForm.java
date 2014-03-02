package org.cyk.ui.web.api;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashSet;

import javax.faces.model.SelectItem;

import lombok.extern.java.Log;

import org.cyk.ui.api.component.IComponent;
import org.cyk.ui.api.component.input.IInputBoolean;
import org.cyk.ui.api.component.input.IInputComponent;
import org.cyk.ui.api.component.input.IInputDate;
import org.cyk.ui.api.component.input.IInputNumber;
import org.cyk.ui.api.component.input.IInputSelectOne;
import org.cyk.ui.api.component.input.IInputText;
import org.cyk.ui.api.component.output.IOutputLabel;
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

@Log
public abstract class AbstractWebForm<OUTPUTLABEL,INPUT> extends AbstractForm<OUTPUTLABEL,INPUT,SelectItem> implements IWebForm<OUTPUTLABEL,INPUT,SelectItem> , Serializable {

	private static final long serialVersionUID = 5514311939767908514L;
		
	@Override
	public void add(IComponent<?> component) {
		if(component instanceof IOutputLabel)
			currentLabel = createOutputLabel((IOutputLabel) component);
		else{
			if(currentLabel!=null){
				IWebInputComponent<?> webInputComponent = input((IInputComponent<?>) component);
				if(webInputComponent==null){
					log.warning("No component can be found for Type "+component.getFamily()+". It will be ignored");
					return;
				}
				INPUT input = createInput(webInputComponent);
				link(currentLabel, input);
				currentLabel = null;
			}
		}
	}
		
	@SuppressWarnings("unchecked")
	protected IWebInputComponent<?> input(IInputComponent<?> aComponent){
		IWebInputComponent<?> component = null;
		if(aComponent instanceof IInputText)
			component = inputText((IInputText) aComponent);
		else if(aComponent instanceof IInputDate)
			component = inputDate((IInputDate) aComponent);
		else if(aComponent instanceof IInputBoolean)
			component = inputBoolean((IInputBoolean) aComponent);
		else if(aComponent instanceof IInputNumber)
			component = inputNumber((IInputNumber) aComponent);
		else if(aComponent instanceof IInputSelectOne<?>)
			component = inputSelectOne((IInputSelectOne<Object>) aComponent);
		
		return component;
	}
	
	@Override
	public IWebInputBoolean inputBoolean(IInputBoolean anIInput) {
		return new InputBoolean(anIInput);
	}
	
	@Override
	public IWebInputDate inputDate(IInputDate anIInput) {
		return new InputDate(anIInput);
	}
	
	@Override
	public IWebInputNumber inputNumber(IInputNumber anIInput) {
		return new InputNumber(anIInput);
	}
	
	@Override
	public IWebInputSelectOne<Object> inputSelectOne(IInputSelectOne<Object> anIInput) {
		IWebInputSelectOne<Object> inputSelectOne = new InputSelectOne(anIInput);
		if(inputSelectOne.getItems()==null || inputSelectOne.getItems().isEmpty()){
			Collection<SelectItem> items = new LinkedHashSet<>();
			for(Object object : load(anIInput.getField().getType()))
				items.add(item(object));
			inputSelectOne.setItems(items);
		}
		
		return inputSelectOne;
	}
	
	@Override
	public IWebInputText inputText(IInputText anIInput) {
		return new InputText(anIInput);
	}
}
