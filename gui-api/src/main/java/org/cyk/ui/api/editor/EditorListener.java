package org.cyk.ui.api.editor;


public interface EditorListener<FORM,OUTPUTLABEL,INPUT,SELECTITEM> {

	void editorInputsCreated(Editor<FORM,OUTPUTLABEL,INPUT,SELECTITEM> editor,EditorInputs<FORM,OUTPUTLABEL,INPUT,SELECTITEM> editorInputs);
	
}
