package org.cyk.ui.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;

import org.cyk.ui.api.editor.Editor;
import org.cyk.ui.api.model.table.Table;
import org.cyk.utility.common.cdi.AbstractBean;

public abstract class AbstractWindow<FORM,OUTPUTLABEL,INPUT,SELECTITEM> extends AbstractBean implements UIWindow<FORM,OUTPUTLABEL,INPUT,SELECTITEM>,Serializable {

	private static final long serialVersionUID = 7282005324574303823L;

	protected Collection<Editor<?,?,?,?>> editors = new ArrayList<>();
	
	@Getter protected String title;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		
	}
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		for(Editor<?,?,?,?> editor : editors)
			editor.targetDependentInitialisation();
	}
	
	@Override
	public Editor<FORM,OUTPUTLABEL,INPUT,SELECTITEM> editorInstance(Object anObjectModel) {
		Editor<FORM,OUTPUTLABEL,INPUT,SELECTITEM> editor = editorInstance();
		editor.setWindow(this);
		((AbstractBean)editor).postConstruct();
		editor.build(anObjectModel);
		editors.add(editor);
		return editor;
	}
	
	@Override
	public <DATA> Table<DATA> tableInstance(Class<DATA> aDataClass) {
		return new Table<>(aDataClass, this);
	}
		
}
