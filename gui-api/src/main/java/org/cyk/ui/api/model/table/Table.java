package org.cyk.ui.api.model.table;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.UIWindow;
import org.cyk.ui.api.UIWindowPart;
import org.cyk.ui.api.command.DefaultCommand;
import org.cyk.ui.api.command.DefaultMenu;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.command.UIMenu;
import org.cyk.utility.common.AbstractMethod;
import org.cyk.utility.common.model.table.AbstractClassFieldValueTable;

@Getter @Setter
public class Table<DATA> extends AbstractClassFieldValueTable<DATA, TableRow<DATA>,TableColumn,TableCell> implements UIWindowPart,Serializable {

	private static final long serialVersionUID = -7832418987283686453L;
	
	protected UIMenu menu = new DefaultMenu();
	protected UIWindow<?, ?, ?, ?,?> window;
	protected String title;
	
	/*
	@SuppressWarnings("unchecked")
	public Table() {
		build(aDataClass,(Class<? extends TableRow<DATA>>) TableRow.class, TableColumn.class,TableCell.class);
		//this.window = aWindow;
		
	}
	*/
	
	@Override
	protected void initialisation() {
		super.initialisation();
		crudCommand();	
	}
	
	private void addCommand(UICommand command){
		command.setMessageManager(window.getMessageManager());
		menu.getCommands().add(command);
	}
	
	protected void crudCommand(){
		UICommand command = new DefaultCommand();
		command.setLabel(text("button.add"));
		command.setIcon("ui-icon-plus");
		addCommand(command);
		
		command.setExecuteMethod(new AbstractMethod<Object, Object>() {
			private static final long serialVersionUID = -2421175279479434675L;
			@Override protected Object __execute__(Object parameter) {return null;}
		});
		
		
		
		command = new DefaultCommand();
		command.setLabel(text("button.delete.selected.rows"));
		command.setIcon("ui-icon-close");
		addCommand(command);
		
		command = new DefaultCommand();
		command.setLabel(text("button.export"));
		command.setIcon("ui-icon-document");
		addCommand(command);
	}
	
	/**/
	
	private String text(String id){
		return window.getUiManager().getLanguageBusiness().findText(id);
	}
	
	public void targetDependentInitialisation(){}
	
}
