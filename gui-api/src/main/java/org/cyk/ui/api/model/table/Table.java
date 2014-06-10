package org.cyk.ui.api.model.table;

import java.io.Serializable;
import java.lang.reflect.Field;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.UIMessageManager.SeverityType;
import org.cyk.ui.api.UIWindow;
import org.cyk.ui.api.UIWindowPart;
import org.cyk.ui.api.command.DefaultCommand;
import org.cyk.ui.api.command.DefaultCommandable;
import org.cyk.ui.api.command.DefaultMenu;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UIMenu;
import org.cyk.ui.api.command.UICommandable.IconType;
import org.cyk.ui.api.component.UIFieldInfos;
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
	
	protected AbstractIdentifiable master;
	protected UIMenu menu = new DefaultMenu();
	protected UIWindow<?, ?, ?, ?,?> window;
	protected String title;
	protected Boolean editable=Boolean.FALSE;
	protected UIInputFieldDiscoverer discoverer = new UIInputFieldDiscoverer();
	protected UICommandable addRowCommand,deleteRowCommand,openRowCommand;
	protected UICommand saveRowCommand,cancelRowCommand;
	protected RowSaveEventMethod rowSaveEventMethod;
	protected RowNavigateEventMethod rowNavigateEventMethod;
	protected DATA dataAdding;
	protected Integer lastEditedRowIndex;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		
		addRowCommand = createCommandable("command.add", IconType.ACTION_ADD, new AbstractMethod<Object, Object>() {
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
		
		openRowCommand = createCommandable("command.open", IconType.ACTION_OPEN, new AbstractMethod<Object, Object>() {
			private static final long serialVersionUID = 1074893365570711794L;
			@Override
			protected Object __execute__(Object parameter) {
				rowNavigateEventMethod.execute((TableRow<?>) parameter);
				return null;
			}
		});
		
		saveRowCommand = createCommand(new AbstractMethod<Object, Object>() {
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
		
		cancelRowCommand = createCommand(new AbstractMethod<Object, Object>() {
			private static final long serialVersionUID = 4758954266295164539L;
			@SuppressWarnings("unchecked")
			@Override
			protected Object __execute__(Object object) {
				if(object==dataAdding){
					deleteRow(rows.size()-1);
					dataAdding = null;
				}else{
					DATA initialData = (DATA) getWindow().getGenericBusiness().find( ((AbstractIdentifiable)object).getIdentifier() );
					updateRow(rows.get(rowIndex((DATA) object).intValue()), initialData);
				}
				return null;
			}
		});
		
		deleteRowCommand = createCommandable("command.delete", IconType.ACTION_REMOVE, new AbstractMethod<Object, Object>() {
			private static final long serialVersionUID = 1074893365570711794L;
			@Override
			protected Object __execute__(Object object) {
				@SuppressWarnings("unchecked")
				TableRow<DATA> row = (TableRow<DATA>) object;
				getWindow().getGenericBusiness().delete((AbstractIdentifiable) row.getData());
				deleteRow(row);
				//deleteRowCommand.getCommand().getMessageManager().message(SeverityType.WARNING, "SUCCES", false).showDialog();
				return null;
			}
		});
		
	}
	
	@Override
	protected UIField uiField(Field field, Class<DATA> clazz) {
		UIFieldInfos uiFieldInfos = UIInputFieldDiscoverer.uiFieldOf(field,clazz);
		return uiFieldInfos==null?null:uiFieldInfos.getAnnotation();
	}
		
	@Override
	public boolean addColumn(TableColumn column) {
		Boolean r = super.addColumn(column);
		column.setTitle(AbstractInputComponent.COMPUTE_LABEL_VALUE_METHOD.execute(new Object[]{column.getFieldName(),
				UIInputFieldDiscoverer.uiFieldOf(FieldUtils.getField(rowDataClass, column.getFieldName(), true),rowDataClass).getAnnotation()}));
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
	
	protected UICommandable createCommandable(String labelId,IconType iconType,AbstractMethod<Object, Object> action){
		UICommandable commandable = new DefaultCommandable();
		commandable.setCommand(createCommand(action));
		commandable.setLabel(text(labelId));
		commandable.setIconType(iconType);
		return commandable;
	}
	
	protected UICommand createCommand(AbstractMethod<Object, Object> action){
		UICommand command = new DefaultCommand();
		command.setMessageManager(getWindow().getMessageManager());
		command.setExecuteMethod(action);
		return command;
	}
	
	/**/
	
	private String text(String id){
		return getWindow().getUiManager().getLanguageBusiness().findText(id);
	}
	
	public String getTitle(){
		if(title==null)
			title = UIManager.getInstance().text(rowDataClass);
		return title;
	}
	
	public void targetDependentInitialisation(){}
	
	/**/
	
	public static abstract class RowAddEventMethod extends AbstractMethod<Object, Object>{
		private static final long serialVersionUID = -145475519122234694L;
		@Override protected final Object __execute__(Object object) {onEvent();return null;}
		protected abstract void onEvent();
	}
	
	public static abstract class RowSaveEventMethod extends AbstractMethod<Object, TableRow<?>>{
		private static final long serialVersionUID = -145475519122234694L;
		@Override protected final Object __execute__(TableRow<?> row) {onEvent();return null;}
		protected abstract void onEvent();
	}
	
	public static abstract class RowNavigateEventMethod extends AbstractMethod<Object, TableRow<?>>{
		private static final long serialVersionUID = -145475519122234694L;
		@Override protected final Object __execute__(TableRow<?> row) {onEvent(row);return null;}
		protected abstract void onEvent(TableRow<?> row);
	}
	
}
