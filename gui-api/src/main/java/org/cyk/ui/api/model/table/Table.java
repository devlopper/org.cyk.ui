package org.cyk.ui.api.model.table;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.UIMessageManager.SeverityType;
import org.cyk.ui.api.UIWindow;
import org.cyk.ui.api.UIWindowPart;
import org.cyk.ui.api.command.DefaultCommand;
import org.cyk.ui.api.command.DefaultCommandable;
import org.cyk.ui.api.command.DefaultMenu;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UIMenu;
import org.cyk.ui.api.component.UIInputFieldDiscoverer;
import org.cyk.ui.api.editor.input.AbstractInputComponent;
import org.cyk.ui.api.editor.input.UIInputComponent;
import org.cyk.utility.common.AbstractMethod;
import org.cyk.utility.common.annotation.UIField;
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
	protected UICommandable addRowCommand,deleteRowCommand;
	protected UICommand saveRowCommand,cancelRowCommand;
	protected RowSaveEventMethod rowSaveEventMethod;
	protected DATA dataAdding;
	protected Integer lastEditedRowIndex;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		crudCommand();	
		
		addRowCommand = new DefaultCommandable();
		addRowCommand.setCommand(new DefaultCommand());
		addRowCommand.setLabel(text("command.add"));
		addRowCommand.setIcon("ui-icon-plus");
		addRowCommand.getCommand().setExecuteMethod(new AbstractMethod<Object, Object>() {
			private static final long serialVersionUID = 1074893365570711794L;
			@Override
			protected Object __execute__(Object parameter) {
				if(dataAdding==null)
					try {
						addRow(dataAdding = (DATA) rowDataClass.newInstance());
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				else
					addRowCommand.getCommand().getMessageManager().message(SeverityType.WARNING, "warning.table.canaddoneatatime", true).showDialog();
				return null;
			}
		});
		
		saveRowCommand = new DefaultCommand();
		saveRowCommand.setExecuteMethod(new AbstractMethod<Object, Object>() {
			private static final long serialVersionUID = 4758954266295164539L;
			@SuppressWarnings("unchecked")
			@Override
			protected Object __execute__(Object object) {
				//throw new BusinessException("This is a business exception");
				//throw new RuntimeException("This is a runtime exception");
				lastEditedRowIndex = (object==dataAdding)?rows.size()-1:rowIndex((DATA)object);
				getWindow().getGenericBusiness().save((AbstractIdentifiable)object);
				dataAdding = null;
				return null;
			}
		});
		
		cancelRowCommand = new DefaultCommand();
		cancelRowCommand.setExecuteMethod(new AbstractMethod<Object, Object>() {
			private static final long serialVersionUID = 4758954266295164539L;
			@SuppressWarnings("unchecked")
			@Override
			protected Object __execute__(Object object) {
				if(object==dataAdding){
					deleteRow(rows.size()-1);
					dataAdding = null;
				}else{
					DATA initialData = (DATA) getWindow().getGenericBusiness().find( ((AbstractIdentifiable)object).getIdentifier() );
					System.out.println("Table.initialisation().new AbstractMethod() {...}.__execute__() : "+initialData);
					updateRow(rows.get(rowIndex((DATA) object).intValue()), initialData);
				}
				return null;
			}
		});
		
		deleteRowCommand = new DefaultCommandable();
		deleteRowCommand.setCommand(new DefaultCommand());
		deleteRowCommand.setLabel(text("command.delete"));
		deleteRowCommand.setIcon("ui-icon-close");
		deleteRowCommand.getCommand().setExecuteMethod(new AbstractMethod<Object, Object>() {
			private static final long serialVersionUID = 1074893365570711794L;
			@SuppressWarnings("unchecked")
			@Override
			protected Object __execute__(Object index) {
				AbstractIdentifiable identifiable = (AbstractIdentifiable)rows.get((Integer)index).getData();
				getWindow().getGenericBusiness().delete(identifiable);
				deleteRow((DATA) identifiable);
				//deleteRowCommand.getCommand().getMessageManager().message(SeverityType.WARNING, "SUCCES", false).showDialog();
				return null;
			}
		});
	}
	
	@Override
	protected UIField uiField(Field field, Class<DATA> clazz) {
		UIField f = super.uiField(field,clazz);
		if(f==null){
			Method getter = MethodUtils.getAccessibleMethod(clazz,"get"+StringUtils.capitalize(field.getName()));
			if(getter!=null){
				return getter.getAnnotation(UIField.class);
			}
		}
		return f;
	}
	
	@Override
	public boolean addColumn(TableColumn column) {
		Boolean r = super.addColumn(column);
		column.setTitle(AbstractInputComponent.COMPUTE_LABEL_VALUE_METHOD.execute(new Object[]{column.getFieldName(),
				FieldUtils.getField(rowDataClass, column.getFieldName(), true).getAnnotation(UIField.class)}));
		return r;
	}
	
	@Override
	public boolean addRow(TableRow<DATA> row) {
		discoverer.setObjectModel(row.getData());
		row.getInputComponents().clear();
		for(UIInputComponent<?> inputComponent : discoverer.run().getInputComponents()){
			row.getInputComponents().add(inputComponent);
		}
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
	
	/*
	private void addCommandable(UICommandable commandable){
		commandable.getCommand().setMessageManager(window.getMessageManager());
		menu.getCommandables().add(commandable);
	}*/
	
	protected void crudCommand(){
		
		
		//addCommandable(commandable);
		
		/*
		commandable = new DefaultCommandable();
		commandable.setLabel(text("command.delete.selected.rows"));
		commandable.setIcon("ui-icon-close");
		commandable.setCommand(new DefaultCommand());
		commandable.getCommand().setExecuteMethod(new AbstractMethod<Object, Object>() {
			private static final long serialVersionUID = -2421175279479434675L;
			@Override protected Object __execute__(Object parameter) {return null;}
		});
		addCommandable(commandable);
		
		commandable = new DefaultCommandable();
		commandable.setLabel(text("command.export"));
		commandable.setIcon("ui-icon-document");
		commandable.setCommand(new DefaultCommand());
		commandable.getCommand().setExecuteMethod(new AbstractMethod<Object, Object>() {
			private static final long serialVersionUID = -2421175279479434675L;
			@Override protected Object __execute__(Object parameter) {return null;}
		});
		addCommandable(commandable);
		*/
	}
	
	/**/
	
	private String text(String id){
		return window.getUiManager().getLanguageBusiness().findText(id);
	}
	
	public void targetDependentInitialisation(){}
	
	/**/
	
	public static abstract class RowAddEventMethod extends AbstractMethod<Object, Object>{
		private static final long serialVersionUID = -145475519122234694L;
		@Override protected final Object __execute__(Object object) {onEvent();return null;}
		protected abstract void onEvent();
	}
	
	public static abstract class RowSaveEventMethod extends AbstractMethod<Object, Object>{
		private static final long serialVersionUID = -145475519122234694L;
		@Override protected final Object __execute__(Object object) {onEvent();return null;}
		protected abstract void onEvent();
	}
	
}
