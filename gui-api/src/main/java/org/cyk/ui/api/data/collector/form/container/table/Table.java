package org.cyk.ui.api.data.collector.form.container.table;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.pattern.tree.AbstractDataTreeNodeBusiness;
import org.cyk.system.root.business.api.validation.ValidationPolicy;
import org.cyk.system.root.business.impl.BusinessLocator;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeNode;
import org.cyk.system.root.model.pattern.tree.DataTreeType;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.UIMessageManager.SeverityType;
import org.cyk.ui.api.UIWindow;
import org.cyk.ui.api.UIWindowPart;
import org.cyk.ui.api.command.DefaultMenu;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UICommandable.EventListener;
import org.cyk.ui.api.command.UICommandable.IconType;
import org.cyk.ui.api.command.UICommandable.ProcessGroup;
import org.cyk.ui.api.command.UIMenu;
import org.cyk.ui.api.component.UIFieldDiscoverer;
import org.cyk.ui.api.component.UIFieldInfos;
import org.cyk.ui.api.component.UIInputOutputComponent;
import org.cyk.ui.api.data.collector.form.ManyDataContainer;
import org.cyk.ui.api.editor.input.AbstractInputComponent;
import org.cyk.ui.api.editor.input.UIInputComponent;
import org.cyk.utility.common.AbstractMethod;
import org.cyk.utility.common.annotation.UIField;
import org.cyk.utility.common.model.table.AbstractClassFieldValueTable;
import org.cyk.utility.common.model.table.DefaultCell;

@Getter @Setter
public class Table<DATA> extends AbstractClassFieldValueTable<DATA, TableRow<DATA>,TableColumn,TableCell> implements Serializable {

	private static final long serialVersionUID = -7832418987283686453L;
	
	public enum RowMenuLocation{MAIN_MENU,BY_ROW}
	
	public enum UsedFor{ENTITY_INPUT,FIELD_INPUT}
	
	protected ValidationPolicy validationPolicy; 
	protected AbstractIdentifiable master;
	
	protected String title;
	protected Boolean editable=Boolean.FALSE,selectable=Boolean.TRUE;
	
	protected DATA dataAdding;
	protected TableRow<DATA> selectedRow,editingRow;
	protected Integer lastEditedRowIndex;
	protected Boolean showHierarchy,showOpenCommand,showFooterCommandBlock;
	protected List<DATA> hierarchyData = new ArrayList<>();
	protected UsedFor usedFor = UsedFor.ENTITY_INPUT;
	protected Crud crud;
	protected AbstractMethod<Object, Object> selectObjectMethod;
	
	protected UICommandable lastExecutedCommandable;
	
	protected Collection<TableListener> tableListeners = new ArrayList<>();
	protected RowMenuLocation rowMenuLocation = RowMenuLocation.MAIN_MENU;
	
/*	@Override
	protected void initialisation() {
		super.initialisation();
		nullValue="";
		
		addRowCommand = UIManager.getInstance().createCommandable(menu,"command.add", IconType.ACTION_ADD, new AbstractMethod<Object, Object>() {
			private static final long serialVersionUID = 1074893365570711794L;
			@Override
			protected Object __execute__(Object parameter) {
				if(UsedFor.FIELD_INPUT.equals(usedFor)){
					
				}else{
					
				}
				if(editingRow==null)
					try {
						addRow(dataAdding = (DATA) rowDataClass.newInstance());
						editingRow = selectedRow = rows.get(rows.size()-1);
						if(isDataTreeType() && master!=null ){
							((DataTreeType)dataAdding).setNode(new NestedSetNode(((DataTreeType)master).getNode().getSet(), ((DataTreeType)master).getNode()));
						}
						__createRow__(editingRow);
						fire(addRowCommand);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				else
					addRowCommand.getCommand().getMessageManager().message(SeverityType.WARNING, "warning.table.canaddoneatatime", true).showDialog();
				return null;
			}
		},EventListener.NONE,UsedFor.FIELD_INPUT.equals(usedFor)?ProcessGroup.THIS:ProcessGroup.FORM);
		
		openRowCommand = rowCommandable("command.open", IconType.ACTION_OPEN, new AbstractMethod<Object, Object>() {
			private static final long serialVersionUID = 1074893365570711794L;
			@Override
			protected Object __execute__(Object parameter) {
				rowNavigateEventMethod.execute((TableRow<?>) parameter);
				return null;
			}
		});
		
		editRowCommand = rowCommandable("command.edit", IconType.ACTION_EDIT, new AbstractMethod<Object, Object>() {
			private static final long serialVersionUID = 1074893365570711794L;
			@Override
			protected Object __execute__(Object parameter) {
				editingRow = selectedRow;
				fire(editRowCommand);
				return null;
			}
		});
		
		saveRowCommand = rowCommandable("command.apply", IconType.ACTION_APPLY, new AbstractMethod<Object, Object>() {
			private static final long serialVersionUID = 1074893365570711794L;
			@SuppressWarnings("unchecked")
			@Override
			protected Object __execute__(Object object) {
				//debug(editingRow.getData());
				
				if(UsedFor.FIELD_INPUT.equals(usedFor)){
					
				}else{
					lastEditedRowIndex = (object==dataAdding)?rows.size()-1:rowIndex((DATA)object);
					getWindow().getGenericBusiness().save((AbstractIdentifiable)object);
					updateRow(rows.get(lastEditedRowIndex), (DATA) object);
				}
				dataAdding = null;
				
				editingRow = null;
				fire(saveRowCommand);
				return null;
			}
		});
		
		cancelRowCommand = UIManager.getInstance().createCommand(new AbstractMethod<Object, Object>() {
			private static final long serialVersionUID = 4758954266295164539L;
			@SuppressWarnings("unchecked")
			@Override
			protected Object __execute__(Object object) {
				
				if(object==dataAdding){
					if(UsedFor.FIELD_INPUT.equals(usedFor)){
						
					}else{
						
					}
					deleteRow(rows.size()-1);
					dataAdding = null;
				}else{
					if(UsedFor.FIELD_INPUT.equals(usedFor)){
						
					}else{
						DATA initialData = (DATA) getWindow().getGenericBusiness().find( ((AbstractIdentifiable)object).getIdentifier() );
						updateRow(rows.get(rowIndex((DATA) object).intValue()), initialData);
					}
					
				}	
				
				return null;
			}
		});
		
		deleteRowCommand = UIManager.getInstance().createCommandable(menu,"command.delete", IconType.ACTION_REMOVE, new AbstractMethod<Object, Object>() {
			private static final long serialVersionUID = 1074893365570711794L;
			@Override
			protected Object __execute__(Object object) {
				@SuppressWarnings("unchecked")
				TableRow<DATA> row = (TableRow<DATA>) object;
				if(UsedFor.FIELD_INPUT.equals(usedFor)){
					
				}else{
					getWindow().getGenericBusiness().delete((AbstractIdentifiable) row.getData());
					//deleteRowCommand.getCommand().getMessageManager().message(SeverityType.WARNING, "SUCCES", false).showDialog();	
				}
				deleteRow(row);
				return null;
			}
		},EventListener.NONE,UsedFor.FIELD_INPUT.equals(usedFor)?ProcessGroup.THIS:ProcessGroup.FORM);
		
		cancelCommand = editRowMenu("command.cancel", IconType.ACTION_CANCEL, new AbstractMethod<Object, Object>() {
			private static final long serialVersionUID = 1074893365570711794L;
			@Override
			protected Object __execute__(Object object) {
				if(lastExecutedCommandable == addRowCommand){
					deleteRow(editingRow);
					__deleteRow__(editingRow);
				}
				editingRow = null;
				fire(cancelCommand);
				return null;
			}
		},EventListener.NONE,UsedFor.FIELD_INPUT.equals(usedFor)?ProcessGroup.THIS:ProcessGroup.FORM);
		
		if(RowMenuLocation.MAIN_MENU.equals(rowMenuLocation))
			menu.getCommandables().addAll(editRowMenu.getCommandables());
		
		if(UsedFor.FIELD_INPUT.equals(usedFor)){
			setEditable(!Crud.READ.equals(crud));
			addRowCommand.setShowLabel(Boolean.FALSE);
			addRowCommand.setRendered(getEditable());
		}else{
			exportCommand = UIManager.getInstance().createCommandable(menu,"command.export", IconType.ACTION_EXPORT, new AbstractMethod<Object, Object>() {
				private static final long serialVersionUID = 1074893365570711794L;
				@Override
				protected Object __execute__(Object object) {return null;}
			},EventListener.NONE,UsedFor.FIELD_INPUT.equals(usedFor)?ProcessGroup.THIS:ProcessGroup.FORM);
			UIManager.getInstance().createCommandable(exportCommand,"command.export.pdf", IconType.ACTION_EXPORT_PDF, new AbstractMethod<Object, Object>() {
				private static final long serialVersionUID = 1074893365570711794L;
				@Override
				protected Object __execute__(Object object) {return null;}
			},EventListener.NONE,UsedFor.FIELD_INPUT.equals(usedFor)?ProcessGroup.THIS:ProcessGroup.FORM);
			UIManager.getInstance().createCommandable(exportCommand,"command.export.excel", IconType.ACTION_EXPORT_EXCEL, new AbstractMethod<Object, Object>() {
				private static final long serialVersionUID = 1074893365570711794L;
				@Override
				protected Object __execute__(Object object) {return null;}
			},EventListener.NONE,UsedFor.FIELD_INPUT.equals(usedFor)?ProcessGroup.THIS:ProcessGroup.FORM);
			
		}
		
	}*/
	
	protected UICommandable editRowMenu(String labelId,IconType iconType,AbstractMethod<Object, Object> executeMethod,EventListener eventListener,ProcessGroup processGroup){
		return null;//UIManager.getInstance().createCommandable(editRowMenu,labelId, iconType, executeMethod,eventListener,processGroup);
	}
	
	protected UICommandable rowCommandable(String labelId,IconType iconType,AbstractMethod<Object, Object> executeMethod){
		//UICommandable commandable = editRowMenu(labelId, iconType, executeMethod,EventListener.NONE,ProcessGroup.FORM);
		//rowCommandables.add(commandable);
		return null;//commandable;
	}
	
	@Override
	protected UIField uiField(Field field, Class<DATA> clazz) {
		UIFieldInfos uiFieldInfos = UIFieldDiscoverer.uiFieldOf(field,clazz);
		return uiFieldInfos==null?null:uiFieldInfos.getAnnotation();
	}
		
	@Override
	public boolean addColumn(TableColumn column) {
		Boolean r = super.addColumn(column);
		column.setTitle(AbstractInputComponent.COMPUTE_LABEL_VALUE_METHOD.execute(new Object[]{column.getFieldName(),
				UIFieldDiscoverer.uiFieldOf(FieldUtils.getField(rowDataClass, column.getFieldName(), true),rowDataClass).getAnnotation()}));
		return r;
	}
	
	@Override
	public boolean addRow(TableRow<DATA> row) {
		//discoverer.setObjectModel(row.getData());
		/*row.getInputComponents().clear();
		for(UIInputOutputComponent<?> component : discoverer.run(Crud.UPDATE).getComponents()){
			if(component instanceof UIInputComponent<?>)
				row.getInputComponents().add((UIInputComponent<?>) component);
		}*/
		return false;//super.addRow(row);
		
	}
	
	@Override
	public boolean addCell(TableRow<DATA> row, TableColumn column, DefaultCell cell) {
		for(UIInputComponent<?> input : row.getInputComponents())
			if(input.getField().getName().equals(column.getFieldName())){
				((TableCell)cell).setInputComponent(AbstractInputComponent.create(null,input,validationPolicy));
				break;
			}
		return super.addCell(row, column, cell);
	}
	/*
	@Override
	protected String valueOf(TableCell cell, Object object) {
		cell.getInputComponent().updateReadOnlyValue();
		return AbstractInputComponent.COMPUTE_READ_ONLY_VALUE_METHOD.execute(new Object[]{cell.get});
	}
	*/
	/**/
	
	protected Boolean isDataTreeType(){
		return AbstractDataTreeNode.class.isAssignableFrom(rowDataClass);
	}
	
	public String getTitle(){
		if(title==null)
			title = UIManager.getInstance().textOfClass(rowDataClass);
		return title;
	}
	
	public Boolean getShowHierarchy(){
		return AbstractDataTreeNode.class.isAssignableFrom(rowDataClass);
	}
	
	public Boolean getShowOpenCommand(){
		return getShowHierarchy();
	}
	
	public Boolean getShowFooterCommandBlock(){
		return UsedFor.FIELD_INPUT.equals(usedFor);
	}
	
	public void targetDependentInitialisation(){}
	
	public void select(Object object){
		if(selectObjectMethod==null)
			return;
		selectObjectMethod.execute(object);
	}
	
	@SuppressWarnings("unchecked")
	public <B extends AbstractDataTreeNodeBusiness<? extends AbstractDataTreeNode>> void handle(B business){
		for(AbstractDataTreeNode node : business.findHierarchies())
			hierarchyData.add((DATA) node);
		
		addRowOfRoot(master);
		
	}
	
	@SuppressWarnings("unchecked")
	protected void addRowOfRoot(AbstractIdentifiable root){
		Collection<DATA> collection = new ArrayList<>();
		if(root==null)
			for(DATA node : hierarchyData)
				collection.add((DATA) node);
		else{
			//business.findHierarchy( master);
			root = (AbstractIdentifiable) getReferenceFromHierarchy(root,hierarchyData);
			if( ((AbstractDataTreeNode)root).getChildren()!=null)
				for(AbstractDataTreeNode node : ((AbstractDataTreeNode)root).getChildren())
					collection.add((DATA) node);	
		}
		
		clear();
		for(DATA node : collection)
			addRow(node);
		
		master = root;
	}
	
	@SuppressWarnings("unchecked")
	private DATA getReferenceFromHierarchy(AbstractIdentifiable identifiable,List<DATA> list){
		Integer index = list.indexOf(identifiable);
		if(index>-1)
			return list.get(index);
		for(DATA data : list){
			DATA c = getReferenceFromHierarchy(identifiable, (List<DATA>) ((AbstractDataTreeNode)data).getChildren() );
			if(c!=null)
				return c;
		}
		return null;
	}
	
	protected void __createRow__(TableRow<DATA> row){}
	
	protected void __deleteRow__(TableRow<DATA> row){}
	
	@SuppressWarnings("unchecked")
	public void fetchData(){
		if(AbstractDataTreeNode.class.isAssignableFrom(rowDataClass)){
			@SuppressWarnings("rawtypes")
			AbstractDataTreeNodeBusiness bean = (AbstractDataTreeNodeBusiness) BusinessLocator.getInstance().locate((Class<AbstractIdentifiable>) rowDataClass);
			handle(bean);
		}else{
			addRow(
					(Collection<DATA>)
					UIManager.getInstance().getGenericBusiness().use((Class<? extends AbstractIdentifiable>) rowDataClass).find().all());	
		}
	}
	
}
