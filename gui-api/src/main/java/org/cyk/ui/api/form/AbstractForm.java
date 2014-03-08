package org.cyk.ui.api.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import org.cyk.ui.api.AbstractView;
import org.cyk.ui.api.component.IComponent;
import org.cyk.ui.api.component.input.IInputComponent;
import org.cyk.ui.api.component.output.IOutputLabel;

@Log
public abstract class AbstractForm<FORM,OUTPUTLABEL,INPUT,SELECTITEM> extends AbstractView implements IForm<FORM,OUTPUTLABEL,INPUT,SELECTITEM> , Serializable {

	private static final long serialVersionUID = -3666969590470758214L;
	
	@Getter protected IFormContainer<FORM, OUTPUTLABEL, INPUT, SELECTITEM> container;
	@Getter protected FORM model;
	protected OUTPUTLABEL currentLabel;
	@Getter protected Collection<IInputComponent<?>> fields = new ArrayList<>();
	@Getter @Setter protected IInputComponent<?> parentField;
	
	@Override
	public void build() {
		model = createModel();
		super.build();
	}
	
	@Override
	public void setContainer(IFormContainer<FORM, OUTPUTLABEL, INPUT, SELECTITEM> container) {
		this.container = container;
		if(this.container!=null){
			//do set injected beans
			this.commonUtils = this.container.getCommonUtils();
			this.commonMethodProvider = this.container.getCommonMethodProvider();
		}
	}
	
	@Override
	public void add(IComponent<?> component) {
		if(component instanceof IOutputLabel)
			currentLabel = createOutputLabel((IOutputLabel) component);
		else{
			if(currentLabel!=null){
				IInputComponent<?> iinput = input((IInputComponent<?>) component);
				if(iinput==null){
					log.warning("No component implementation can be found for Type "+component.getFamily()+". It will be ignored");
					return;
				}
				getFields().add(iinput);
				INPUT input = createInput(iinput);
				link(currentLabel, input);
				currentLabel = null;
			}
		}
	}
	
	@Override
	public void updateFieldsValue() throws Exception {
		for(IInputComponent<?> field : getFields())
			field.updateValue();
	}
	
}
