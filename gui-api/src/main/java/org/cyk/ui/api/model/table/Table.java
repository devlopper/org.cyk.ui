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
import org.cyk.ui.api.editor.input.AbstractInputComponent;
import org.cyk.ui.api.editor.input.UIInputComponent;
import org.cyk.utility.common.AbstractMethod;
import org.cyk.utility.common.annotation.UIField;
import org.cyk.utility.common.model.table.AbstractClassFieldValueTable;
import org.cyk.utility.common.model.table.DefaultCell;

@Getter @Setter
public class Table<DATA> extends AbstractClassFieldValueTable<DATA, TableRow<DATA>,TableColumn,TableCell> implements UIWindowPart,Serializable {

	private static final long serialVersionUID = -7832418987283686453L;
	
	public enum UsedFor{ENTITY_INPUT,FIELD_INPUT}
	protected ValidationPolicy validationPolicy; 
	protected AbstractIdentifiable master;
	protected UIMenu menu = new DefaultMenu();
	protected UIWindow<?, ?, ?, ?,?> window;
	protected String title;
	protected Boolean editable=Boolean.FALSE;
	protected UIFieldDiscoverer discoverer = new UIFieldDiscoverer();
	protected UICommandable addRowCommand,deleteRowCommand,openRowCommand;
	protected UICommand saveRowCommand,cancelRowCommand;
	protected RowSaveEventMethod rowSaveEventMethod;
	protected RowNavigateEventMethod rowNavigateEventMethod;
	protected DATA dataAdding;
	protected Integer lastEditedRowIndex;
	protected Boolean showHierarchy,showOpenCommand,showFooterCommandBlock;
	protected List<DATA> hierarchyData = new ArrayList<>();
	protected UsedFor usedFor = UsedFor.ENTITY_INPUT;
	protected Crud crud;
	protected AbstractMethod<Object, Object> selectObjectMethod;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		
		addRowCommand = UIManager.getInstance().createCommandable("command.add", IconType.ACTION_ADD, new AbstractMethod<Object, Object>() {
			private static final long serialVersionUID = 1074893365570711794L;
			@Override
			protected Object __execute__(Object parameter) {
				/*if(UsedFor.FIELD_INPUT.equals(usedFor)){
					
				}else{
					
				}*/
				if(dataAdding==null)
					try {
						addRow(dataAdding = (DATA) rowDataClass.newInstance());
						if(isDataTreeType() && master!=null ){
							((DataTreeType)dataAdding).setNode(new NestedSetNode(((DataTreeType)master).getNode().getSet(), ((DataTreeType)master).getNode()));
						}
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				else
					addRowCommand.getCommand().getMessageManager().message(SeverityType.WARNING, "warning.table.canaddoneatatime", true).showDialog();
				return null;
			}
		},EventListener.NONE,UsedFor.FIELD_INPUT.equals(usedFor)?ProcessGroup.THIS:ProcessGroup.FORM);
		
		menu.getCommandables().add(addRowCommand);
		
		openRowCommand = UIManager.getInstance().createCommandable("command.open", IconType.ACTION_OPEN, new AbstractMethod<Object, Object>() {
			private static final long serialVersionUID = 1074893365570711794L;
			@Override
			protected Object __execute__(Object parameter) {
				rowNavigateEventMethod.execute((TableRow<?>) parameter);
				return null;
			}
		},EventListener.NONE,ProcessGroup.FORM);
		openRowCommand.setShowLabel(Boolean.FALSE);
		
		saveRowCommand = UIManager.getInstance().createCommand(new AbstractMethod<Object, Object>() {
			private static final long serialVersionUID = 4758954266295164539L;
			@SuppressWarnings("unchecked")
			@Override
			protected Object __execute__(Object object) {
				if(UsedFor.FIELD_INPUT.equals(usedFor)){
						
				}else{
					lastEditedRowIndex = (object==dataAdding)?rows.size()-1:rowIndex((DATA)object);
					getWindow().getGenericBusiness().save((AbstractIdentifiable)object);
					updateRow(rows.get(lastEditedRowIndex), (DATA) object);
				}
				dataAdding = null;
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
		
		deleteRowCommand = UIManager.getInstance().createCommandable("command.delete", IconType.ACTION_REMOVE, new AbstractMethod<Object, Object>() {
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
		deleteRowCommand.setShowLabel(Boolean.FALSE);
		
		if(UsedFor.FIELD_INPUT.equals(usedFor)){
			setEditable(!Crud.READ.equals(crud));
			addRowCommand.setShowLabel(Boolean.FALSE);
			addRowCommand.setRendered(getEditable());
		}else{
			UICommandable export = UIManager.getInstance().createCommandable("command.export", IconType.ACTION_EXPORT, new AbstractMethod<Object, Object>() {
				private static final long serialVersionUID = 1074893365570711794L;
				@Override
				protected Object __execute__(Object object) {return null;}
			},EventListener.NONE,UsedFor.FIELD_INPUT.equals(usedFor)?ProcessGroup.THIS:ProcessGroup.FORM);
			export.getChildren().add(UIManager.getInstance().createCommandable("command.export.pdf", IconType.ACTION_EXPORT_PDF, new AbstractMethod<Object, Object>() {
				private static final long serialVersionUID = 1074893365570711794L;
				@Override
				protected Object __execute__(Object object) {return null;}
			},EventListener.NONE,UsedFor.FIELD_INPUT.equals(usedFor)?ProcessGroup.THIS:ProcessGroup.FORM));
			export.getChildren().add(UIManager.getInstance().createCommandable("command.export.excel", IconType.ACTION_EXPORT_EXCEL, new AbstractMethod<Object, Object>() {
				private static final long serialVersionUID = 1074893365570711794L;
				@Override
				protected Object __execute__(Object object) {return null;}
			},EventListener.NONE,UsedFor.FIELD_INPUT.equals(usedFor)?ProcessGroup.THIS:ProcessGroup.FORM));
			
			menu.getCommandables().add(export);
		}
		
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
