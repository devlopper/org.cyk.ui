package org.cyk.ui.web.vaadin.editor;

import java.io.Serializable;

import lombok.Getter;

import org.cyk.ui.api.editor.AbstractEditor;
import org.cyk.ui.api.editor.EditorInputs;
import org.cyk.ui.api.editor.input.UIInputSelectOne;
import org.cyk.ui.web.vaadin.CommandBuilder;

import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;

@Getter
public class Editor extends AbstractEditor<FormLayout,Label,AbstractField<?>,Object> implements Serializable {

	private static final long serialVersionUID = 5514311939767908514L;
	
	private Button submitButton;
	
	@Override
	public Object item(Object object) {
		return null;//new SelectItem(object,object.toString());
	}
	
	@Override
	public void addItem(UIInputSelectOne<?,Object> anInput, Object object) {
		//((WebUIInputSelect<?, ?>)anInput).getItems().add(item(object));
	}

	@Override
	public EditorInputs<FormLayout, Label, AbstractField<?>, Object> createEditorInputs() {
		return new org.cyk.ui.web.vaadin.editor.EditorInputs();
	}

	@Override
	public void targetDependentInitialisation() {
		submitButton = CommandBuilder.getInstance().button(getSubmitCommand());
	}
	
	
	
}
