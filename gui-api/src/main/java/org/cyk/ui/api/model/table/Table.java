package org.cyk.ui.api.model.table;

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
import org.cyk.ui.api.UIManager;
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
import org.cyk.ui.api.editor.input.AbstractInputComponent;
import org.cyk.ui.api.editor.input.UIInputComponent;
import org.cyk.utility.common.AbstractMethod;
import org.cyk.utility.common.annotation.UIField;
import org.cyk.utility.common.model.table.AbstractClassFieldValueTable;
import org.cyk.utility.common.model.table.DefaultCell;

@Getter @Setter
public class Table<DATA> extends AbstractClassFieldValueTable<DATA, TableRow<DATA>,TableColumn,TableCell> implements UIWindowPart,Serializable {

	private static final long serialVersionUID = -7832418987283686453L;
	
	public enum RowMenuLocation{MAIN_MENU,BY_ROW}
	
	public enum UsedFor{ENTITY_INPUT,FIELD_INPUT}
	protected ValidationPolicy validationPolicy; 
	protected AbstractIdentifiable master;
	protected UIMenu menu = new DefaultMenu(), rowMenu = new DefaultMenu(),editRowMenu = new DefaultMenu();
	protected UIWindow<?, ?, ?, ?,?> window;
	protected String title;
	protected Boolean editable=Boolean.FALSE,selectable=Boolean.TRUE;
	protected UIFieldDiscoverer discoverer = new UIFieldDiscoverer();
	protected UICommandable addRowCommand,deleteRowCommand,editRowCommand,openRowCommand,cancelCommand,saveRowCommand,exportCommand;
	protected Collection<UICommandable> rowCommandables = new ArrayList<>();
	protected UICommand /*saveRowCommandOld,*/cancelRowCommand;
	
	protected DATA dataAdding;
	protected TableRow<DATA> selectedRow,editingRow;
	protected Integer lastEditedRowIndex;
	protected Boolean showHierarchy,showOpenCommand,showFooterCommandBlock;
	protected List<DATA> hierarchyData = new ArrayList<>();
	protected UsedFor usedFor = UsedFor.ENTITY_INPUT;
	protected Crud crud;
	protected AbstractMethod<Object, Object> selectObjectMethod;
	//protected RowEvent rowEvent;
	protected UICommandable lastExecutedCommandable;
	
	protected Collection<TableListener> listeners = new ArrayList<>();
	protected RowMenuLocation rowMenuLocation = RowMenuLocation.MAIN_MENU;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		nullValue="";
		
		
		
	}
	
	protected UICommandable editRowMenu(String labelId,IconType iconType,AbstractMethod<Object, Object> executeMethod,EventListener eventListener,ProcessGroup processGroup){
		return UIManager.getInstance().createCommandable(editRowMenu,labelId, iconType, executeMethod,eventListener,processGroup);
	}
	
	protected UICommandable rowCommandable(String labelId,IconType iconType,AbstractMethod<Object, Object> executeMethod){
		UICommandable commandable = editRowMenu(labelId, iconType, executeMethod,EventListener.NONE,ProcessGroup.FORM);
		rowCommandables.add(commandable);
		return commandable;
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
		discoverer.setObjectModel(row.getData());
		row.getInputComponents().clear();
		for(UIInputOutputComponent<?> component : discoverer.run(Crud.UPDATE).getComponents()){
			if(component instanceof UIInputComponent<?>)
				row.getInputComponents().add((UIInputComponent<?>) component);
		}
		return super.addRow(row);
		
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
	
	protected void fire(UICommandable commandable,TableRow<?> row){
		for(TableListener listener : listeners)
			listener.rowEvent(commandable,row);
		lastExecutedCommandable = commandable;
	}
	
	protected void fire(UICommandable commandable){
		fire(commandable, editingRow);
	}
	
	/**/
	
}
