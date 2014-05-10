package org.cyk.ui.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.MenuManager.Type;
import org.cyk.ui.api.command.UIMenu;
import org.cyk.ui.api.editor.Editor;
import org.cyk.ui.api.model.table.Table;
import org.cyk.ui.api.model.table.TableCell;
import org.cyk.ui.api.model.table.TableColumn;
import org.cyk.ui.api.model.table.TableRow;
import org.cyk.utility.common.cdi.AbstractBean;

public abstract class AbstractWindow<EDITOR,OUTPUTLABEL,INPUT,SELECTITEM,TABLE extends Table<?>> extends AbstractBean implements UIWindow<EDITOR,OUTPUTLABEL,INPUT,SELECTITEM,TABLE>,Serializable {

	private static final long serialVersionUID = 7282005324574303823L;

	@Getter @Setter protected UIMenu mainMenu,contextualMenu,contentMenu;
	protected Collection<Editor<?,?,?,?>> editors = new ArrayList<>();
	protected Collection<TABLE> tables = new ArrayList<>();
	@Inject protected MenuManager menuManager;
	@Getter protected String title = "CYK Systems";
	
	@Override
	protected void initialisation() {
		super.initialisation();
		mainMenu = menuManager.build(Type.APPLICATION);
	}
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		if(editors.size()==1)
			contentMenu = editors.iterator().next().getMenu();
			
		targetDependentInitialisation();
		for(Editor<?,?,?,?> editor : editors)
			editor.targetDependentInitialisation();
		
		for(TABLE table : tables)
			table.targetDependentInitialisation();
	}
	
	@Override
	public Editor<EDITOR,OUTPUTLABEL,INPUT,SELECTITEM> editorInstance(Object anObjectModel) {
		Editor<EDITOR,OUTPUTLABEL,INPUT,SELECTITEM> editor = editorInstance();
		editor.setWindow(this);
		((AbstractBean)editor).postConstruct();
		editor.build(anObjectModel);
		editors.add(editor);
		return editor;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <DATA> TABLE tableInstance(Class<DATA> aDataClass) {
		@SuppressWarnings("rawtypes")
		Table table = tableInstance();
		table.setWindow(this);
		((AbstractBean)table).postConstruct();
		table.build(aDataClass, TableRow.class, TableColumn.class, TableCell.class);
		tables.add((TABLE) table);
		return (TABLE) table;
	}
		
}
