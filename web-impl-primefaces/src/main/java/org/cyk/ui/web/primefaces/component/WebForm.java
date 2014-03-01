package org.cyk.ui.web.primefaces.component;

import java.util.Date;

import lombok.Getter;
import lombok.extern.java.Log;

import org.cyk.ui.api.command.DefaultActionCommand;
import org.cyk.ui.api.component.IComponent;
import org.cyk.ui.api.component.input.IInputBoolean;
import org.cyk.ui.api.component.input.IInputComponent;
import org.cyk.ui.api.component.input.IInputDate;
import org.cyk.ui.api.component.input.IInputNumber;
import org.cyk.ui.api.component.input.IInputSelectOne;
import org.cyk.ui.api.component.input.IInputText;
import org.cyk.ui.api.component.output.OutputLabel;
import org.cyk.ui.web.api.AbstractWebForm;
import org.cyk.ui.web.api.component.IWebInputComponent;
import org.cyk.ui.web.api.component.InputBoolean;
import org.cyk.ui.web.api.component.InputDate;
import org.cyk.ui.web.api.component.InputNumber;
import org.cyk.ui.web.api.component.InputSelectOne;
import org.cyk.ui.web.api.component.InputText;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;

@Log
public class WebForm extends AbstractWebForm/*<Object>*/ {

	private static final long serialVersionUID = -2915809915934469649L;

	@Getter protected DynaFormModel model;
	@Getter protected DefaultActionCommand command;
	
	private DynaFormRow currentRow;
	private DynaFormLabel currentLabel;
	private DynaFormControl currentControl;
	
	//private ViewBuilder viewBuilder;
	/*
	public WebForm(ViewBuilder aViewBuilder,MessageManager messageManager,Object aDto) {
		super(aDto);
		this.viewBuilder = aViewBuilder;
		 
		command = new DefaultActionCommand();
		command.setMessageManager(messageManager);

		command.setExecuteMethod(new AbstractMethod<Object, Object>() {
			private static final long serialVersionUID = -3554292967012003944L;
			@Override
			protected Object __execute__(Object parameter) {
				System.out.println("WebForm.WebForm(...).new AbstractMethod() {...}.__execute__()");
				return null;
			}
		});		
	}*/
	
	@Override
	protected void initialisation() {
		super.initialisation();
		model = new DynaFormModel();
	}
	
	@Override
	public void createRow() {
		currentRow = model.createRegularRow();
	}
	
	@Override
	public void add(IComponent<?> component) {
		if(component instanceof OutputLabel)
			currentLabel = currentRow.addLabel(((OutputLabel) component).getValue());
		else{
			if(currentLabel!=null){
				IWebInputComponent<?> input = input((IInputComponent<?>) component);
				if(input==null){
					log.warning("No component can be found for Type "+component.getFamily()+". It will be ignored");
					return;
				}
				currentControl = currentRow.addControl(input, input.getFamily());
				currentLabel.setForControl(currentControl);
				currentLabel = null;
			}
			
		}
	}
	

	/*
	@Override
	public void build() {
		super.build();
		model = new DynaFormModel();
		Collection<ILayoutRow> rows = new LinkedList<>(getRows());
		this.rows.clear();
		for (ILayoutRow layoutRow : rows) {
			//DynaFormRow row = model.createRegularRow();
			List<IComponent<?>> components = (List<IComponent<?>>) layoutRow.getComponents();
			for (int j=0;j<components.size();) {
				IComponent<?> component = components.get(j);
				
				
				if(component instanceof OutputLabel){
					currentLabel = currentRow.addLabel(((OutputLabel) component).getValue());
					IWebInputComponent<?> input = input((IInputComponent<?>) components.get(++j));
					currentControl = currentRow.addControl(input,input.getFamily());
					currentLabel.setForControl(currentControl);
				}else
					currentRow.addControl(component, component.getFamily());
				
				j++;
			}
		}
	}*/
	
	@SuppressWarnings("unchecked")
	private IWebInputComponent<?> input(IInputComponent<?> aComponent){
		IWebInputComponent<?> component = null;
		if(String.class.equals(aComponent.getField().getType()))
			component = new InputText((IInputText) aComponent);
		else if(Date.class.equals(aComponent.getField().getType()))
			component = new InputDate((IInputDate) aComponent);
		else if(Boolean.class.equals(aComponent.getField().getType()))
			component = new InputBoolean((IInputBoolean) aComponent);
		else if(commonUtils.isNumberClass(aComponent.getField().getType()))
			component = new InputNumber((IInputNumber) aComponent);
		else if(aComponent.getField().getType().isEnum())
			component = new InputSelectOne((IInputSelectOne<Object>) aComponent);
		
		return component;
	}

}
