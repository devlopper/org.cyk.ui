package org.cyk.ui.api.model.table;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.UIWindow;
import org.cyk.ui.api.UIWindowPart;
import org.cyk.ui.api.command.DefaultCommand;
import org.cyk.ui.api.command.DefaultCommandable;
import org.cyk.ui.api.command.DefaultMenu;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UIMenu;
import org.cyk.ui.api.component.UIInputFieldDiscoverer;
import org.cyk.ui.api.editor.input.AbstractInputComponent;
import org.cyk.ui.api.editor.input.UIInputComponent;
import org.cyk.utility.common.AbstractMethod;
import org.cyk.utility.common.model.table.AbstractClassFieldValueTable;
import org.cyk.utility.common.model.table.DefaultCell;

@Getter @Setter
public class Table<DATA> extends AbstractClassFieldValueTable<DATA, TableRow<DATA>,TableColumn,TableCell> implements UIWindowPart,Serializable {

	private static final long serialVersionUID = -7832418987283686453L;
	
	protected UIMenu menu = new DefaultMenu();
	protected UIWindow<?, ?, ?, ?,?> window;
	protected String title;
	protected Boolean editable=Boolean.FALSE;
	protected UIInputFieldDiscoverer discoverer = new UIInputFieldDiscoverer();
		
	@Override
	protected void initialisation() {
		super.initialisation();
		crudCommand();	
	}
	
	@Override
	public boolean addRow(TableRow<DATA> row) {
		discoverer.setObjectModel(row.getData());
		row.setInputComponents(discoverer.run().getInputComponents());
		return super.addRow(row);
		
	}
	
	@Override
	public boolean addCell(TableRow<DATA> row, TableColumn column, DefaultCell cell) {
		for(UIInputComponent<?> input : row.getInputComponents())
			if(input.getField().getName().equals(column.getFieldName())){
				((TableCell)cell).setInputComponent(AbstractInputComponent.create(input));
				break;
			}
		return super.addCell(row, column, cell);
	}
	
	private void addCommandable(UICommandable commandable){
		commandable.getCommand().setMessageManager(window.getMessageManager());
		menu.getCommandables().add(commandable);
	}
	
	protected void crudCommand(){
		UICommandable commandable = new DefaultCommandable();
		commandable.setCommand(new DefaultCommand());
		commandable.setLabel(text("button.add"));
		commandable.setIcon("ui-icon-plus");
		addCommandable(commandable);
		
		commandable.getCommand().setExecuteMethod(new AbstractMethod<Object, Object>() {
			private static final long serialVersionUID = -2421175279479434675L;
			@Override protected Object __execute__(Object parameter) {return null;}
		});
		
		
		commandable = new DefaultCommandable();
		commandable.setLabel(text("button.delete.selected.rows"));
		commandable.setIcon("ui-icon-close");
		addCommandable(commandable);
		
		commandable = new DefaultCommandable();
		commandable.setLabel(text("button.export"));
		commandable.setIcon("ui-icon-document");
		addCommandable(commandable);
	}
	
	/**/
	
	private String text(String id){
		return window.getUiManager().getLanguageBusiness().findText(id);
	}
	
	
	
	public void targetDependentInitialisation(){}
	
}
