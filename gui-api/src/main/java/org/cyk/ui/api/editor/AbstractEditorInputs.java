package org.cyk.ui.api.editor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import org.cyk.ui.api.component.UIInputFieldDiscoverer;
import org.cyk.ui.api.component.UIInputOutputComponent;
import org.cyk.ui.api.component.output.UIOutputComponent;
import org.cyk.ui.api.editor.input.AbstractInputComponent;
import org.cyk.ui.api.editor.input.UIInputComponent;
import org.cyk.ui.api.editor.output.IOutputLabel;
import org.cyk.ui.api.editor.output.IOutputMessage;
import org.cyk.ui.api.editor.output.OutputLabel;
import org.cyk.ui.api.editor.output.OutputMessage;
import org.cyk.ui.api.layout.GridLayout;
import org.cyk.ui.api.layout.UILayout;
import org.cyk.utility.common.AbstractMethod;
import org.cyk.utility.common.cdi.AbstractBean;

@Log
public abstract class AbstractEditorInputs<FORM,OUTPUTLABEL,INPUT,SELECTITEM> extends AbstractBean implements EditorInputs<FORM,OUTPUTLABEL,INPUT,SELECTITEM> , Serializable {

	private static final long serialVersionUID = -3666969590470758214L;
	
	@Getter protected FORM dataModel;
	protected OUTPUTLABEL currentLabel;
	protected UIInputComponent<?> currentInput;
	@Getter protected Collection<UIInputComponent<?>> inputFields = new ArrayList<>();
	@Getter @Setter protected UIInputComponent<?> parentField;
	
	@Getter @Setter protected UILayout layout = new GridLayout();
	@Getter protected String title;
	@Getter @Setter protected Editor<FORM,OUTPUTLABEL,INPUT,SELECTITEM> editor;
	
	@Getter @Setter protected Object objectModel;
	protected Collection<Class<?>> groups = new LinkedHashSet<>();
	@Getter protected Collection<UIInputOutputComponent<?>> components = new LinkedHashSet<>();
	
	protected UIInputFieldDiscoverer discoverer = new UIInputFieldDiscoverer();
	
	public AbstractEditorInputs() {
		layout.setOnAddRow(new AbstractMethod<Object, Object>() {
			private static final long serialVersionUID = 4575306741618826683L;
			@Override
			protected Object __execute__(Object parameter) {
				createRow();
				return null;
			}
		});
	}
	
	@Override
	public void group(Class<?>... theGroupsClasses) {
		for(Class<?> clazz : theGroupsClasses)
			groups.add(clazz);
	}
	 
	@Override
	public void build() {
		dataModel = createDataModel();
		((AbstractBean)layout).postConstruct();
		layout.addRow();
		discoverer.setObjectModel(objectModel);
		for(UIInputComponent<?> input : discoverer.run().getInputComponents()){
			//for each input we need a label
			add(new OutputLabel(input.getLabel()));
			add(input);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void add(UIInputOutputComponent<?> component) {
		if(component instanceof IOutputLabel)
			currentLabel = (OUTPUTLABEL) createComponent((IOutputLabel) component);
		else if(component instanceof UIInputComponent<?>){
			if(currentLabel!=null){
				UIInputComponent<?> iinput = AbstractInputComponent.create((UIInputComponent<?>) component);
				if(iinput==null){
					log.warning("No input component implementation can be found for Type "+component.getFamily()+". It will be ignored");
					return;
				}
				getInputFields().add(iinput);
				INPUT input = (INPUT) createComponent(iinput);
				link(currentLabel, input);
				currentLabel = null;
				currentInput = iinput;
			}
		}else if(component instanceof UIOutputComponent<?>){
			UIOutputComponent<?> ioutput = output((UIOutputComponent<?>) component);
			if(ioutput==null){
				log.warning("No output component implementation can be found for Type "+component.getFamily()+". It will be ignored");
				return;
			}
			createComponent(ioutput);
		}else if(component instanceof IOutputMessage){
			if(currentInput!=null){
				IOutputMessage message = new OutputMessage(currentInput.getId());
				createComponent(message);
			}
		}
		
		layout.addColumn(component);
	}
		
	@Override
	public void updateFieldsValue() throws Exception {
		for(UIInputComponent<?> input : getInputFields()){
			input.updateValue();
			//System.out.println(input.getField().getName()+" - "+commonUtils.readField(input.getObject(), input.getField(), false));
		}
	}
	
	
	
}
