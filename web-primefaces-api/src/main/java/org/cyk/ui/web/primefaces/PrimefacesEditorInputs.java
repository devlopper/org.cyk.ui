package org.cyk.ui.web.primefaces;

import org.cyk.ui.api.component.UIInputOutputComponent;
import org.cyk.ui.api.editor.output.UIOutputLabel;
import org.cyk.ui.api.editor.output.UIOutputSeparator;
import org.cyk.ui.web.api.form.AbstractWebEditorInputs;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;

public class PrimefacesEditorInputs extends AbstractWebEditorInputs<DynaFormModel,DynaFormLabel,DynaFormControl> {

	private static final long serialVersionUID = -2915809915934469649L;
	
	private DynaFormRow currentRow;
	
	public PrimefacesEditorInputs() {}
	
	@Override
	public DynaFormModel createDataModel() {
		return new DynaFormModel();
	}
	
	@Override 
	public void createRow() {
		currentRow = dataModel.createRegularRow();
	}
	
	
	@Override
	public Object createComponent(UIInputOutputComponent<?> aComponent) {
		if(aComponent instanceof UIOutputLabel)
			return currentRow.addLabel((String) aComponent.getValue(),aComponent.getWidth(),aComponent.getHeight());
		
		Object c = currentRow.addControl(aComponent, aComponent.getFamily(),aComponent.getWidth(),aComponent.getHeight());
		if(aComponent instanceof UIOutputSeparator)
			;//createRow();
		return c;
	}
	
	@Override
	public void link(DynaFormLabel anOutputLabel, DynaFormControl anInput) {
		anOutputLabel.setForControl(anInput);
	}

	@Override
	public String getInputTemplateFileAtRight() {
		return "template/editor/inputMessageAtRight.xhtml";
	}

	@Override
	public String getInputTemplateFileAtTop() {
		return "template/editor/inputMessageAtTop.xhtml";
	}
	
	@Override
	public void targetDependentInitialisation() {}
			
}
