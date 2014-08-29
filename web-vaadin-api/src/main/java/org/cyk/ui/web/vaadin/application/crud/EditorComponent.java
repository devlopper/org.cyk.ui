package org.cyk.ui.web.vaadin.application.crud;

import java.io.Serializable;

import lombok.Getter;

import org.cyk.ui.web.vaadin.AbstractCustomComponent;
import org.cyk.ui.web.vaadin.editor.Editor;
import org.cyk.ui.web.vaadin.editor.EditorInputs;

import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

@Getter
public class EditorComponent extends AbstractCustomComponent<Panel,VerticalLayout> implements Serializable {

	private static final long serialVersionUID = 4894464840887074438L;

	private Editor editor;
	private Object object;
	
	public EditorComponent(Object object) {
		super();
		this.object = object;
		fill();
	}

	@Override
	protected void fill() {
		if(object==null)
			return;
		editor = createEditor(object);
		addComponent( ((EditorInputs)editor.getSelected()).getPanel() );
	}

}
