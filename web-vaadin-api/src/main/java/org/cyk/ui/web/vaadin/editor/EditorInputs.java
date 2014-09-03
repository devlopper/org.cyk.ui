package org.cyk.ui.web.vaadin.editor;

import java.io.Serializable;

import lombok.Getter;

import org.cyk.ui.api.component.UIInputOutputComponent;
import org.cyk.ui.api.component.output.UIOutputComponent;
import org.cyk.ui.api.editor.AbstractEditorInputs;

import com.vaadin.ui.AbstractField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class EditorInputs extends AbstractEditorInputs<FormLayout,Label,AbstractField<?>,Object> implements Serializable {

	private static final long serialVersionUID = 5514311939767908514L;
	
	@Getter private Panel panel;
	
	@Override
	public UIOutputComponent<?> output(UIOutputComponent<?> anIOutput) {
		//WebUIOutputComponent<?> component = null;
		//if(anIOutput instanceof UIOutputText)
			//component = outputText((UIOutputText) anIOutput);
		return null;//component;
	}

	@Override
	public void createRow() {}

	@Override
	public FormLayout createDataModel() {
		FormLayout layout = new FormLayout();
		layout.setMargin(true);
		layout.setSizeUndefined();
		return layout;
	}

	@Override
	public Object createComponent(UIInputOutputComponent<?> aComponent) {
		return Input.createComponent(aComponent);
	}

	@Override
	public void link(Label anOutputLabel, AbstractField<?> anInput) {
		anInput.setCaption(anOutputLabel.getValue());
		dataModel.addComponent(anInput);
	}

	@Override
	public void targetDependentInitialisation() {
		VerticalLayout layout = new VerticalLayout();
		layout.addComponent(dataModel);
	
		layout.addComponent(((Editor)getEditor()).getSubmitButton());
		
		panel = new Panel(layout);
		panel.setSizeUndefined();
		
	}
	
}
