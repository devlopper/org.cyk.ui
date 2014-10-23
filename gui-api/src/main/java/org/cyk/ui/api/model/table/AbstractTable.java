package org.cyk.ui.api.model.table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.pattern.tree.AbstractDataTreeNodeBusiness;
import org.cyk.system.root.business.impl.BusinessLocator;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeNode;
import org.cyk.ui.api.AbstractTree;
import org.cyk.ui.api.TreeAdapter;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.UIProvider;
import org.cyk.ui.api.command.CommandListener;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UICommandable.IconType;
import org.cyk.ui.api.config.IdentifiableConfiguration;
import org.cyk.ui.api.data.collector.control.Input;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.utility.common.model.table.Table;
import org.cyk.utility.common.model.table.TableAdapter;


@Getter @Setter
public abstract class AbstractTable<DATA,NODE,MODEL extends HierarchyNode> extends Table<Row<DATA>, Column, DATA, String, Cell, String> implements CommandListener,Serializable {

	private static final long serialVersionUID = 581883275700805955L;
 
	public enum RowMenuLocation{MAIN_MENU,BY_ROW}
	public enum UsedFor{ENTITY_INPUT,FIELD_INPUT}
	
	protected List<DATA> editing = new ArrayList<>();
	
	protected UsedFor usedFor = UsedFor.ENTITY_INPUT;
	protected Crud crud;
	protected IdentifiableConfiguration identifiableConfiguration;
	protected String title;
	protected Boolean editable=Boolean.FALSE,selectable=Boolean.TRUE,inplaceEdit=Boolean.TRUE,lazyLoad=Boolean.FALSE;
	protected AbstractTree<NODE,MODEL> tree;
	protected UICommandable addRowCommandable,initRowEditCommandable,cancelRowEditCommandable,applyRowEditCommandable,removeRowCommandable,openRowCommandable,
		crudOneRowCommandable,searchCommandable;
	protected Boolean showHierarchy,showOpenCommand=Boolean.TRUE,showFooterCommandBlock=Boolean.TRUE;
	
	protected AbstractIdentifiable master;
	protected List<DATA> hierarchyData = new ArrayList<>();
	
	protected UICommand lastExecutedCommand;
	
	@SuppressWarnings("unchecked")
	public AbstractTable() {
		super(null/*(Class<? extends Row<DATA>>) Row.class*/, null, Column.class, Cell.class);
		//rowClass = (Class<Row<DATA>>) Class.forName(Row.class.getName());
		rowClass = (Class<Row<DATA>>) commonUtils.classFormName(Row.class.getName());
		addRowCommandable = UIProvider.getInstance().createCommandable(this, "command.add", IconType.ACTION_ADD, null, null);
		initRowEditCommandable = UIProvider.getInstance().createCommandable(this, "command.edit", IconType.ACTION_EDIT, null, null);
		cancelRowEditCommandable = UIProvider.getInstance().createCommandable(this, "command.cancel", IconType.ACTION_CANCEL, null, null);
		applyRowEditCommandable = UIProvider.getInstance().createCommandable(this, "command.apply", IconType.ACTION_APPLY, null, null);
		removeRowCommandable = UIProvider.getInstance().createCommandable(this, "command.remove", IconType.ACTION_REMOVE, null, null);
		removeRowCommandable.setShowLabel(Boolean.FALSE);
		openRowCommandable = UIProvider.getInstance().createCommandable(this, "command.open", IconType.ACTION_OPEN, null, null);
		openRowCommandable.setShowLabel(Boolean.FALSE);
		getTableListeners().add(new TableAdapter<Row<DATA>, Column, DATA, String, Cell, String>(){
			@Override
			public void columnAdded(Column column) {
				super.columnAdded(column);
				column.setTitle(UIManager.getInstance().fieldLabel(column.getField()));
			}
			@Override
			public void cellAdded(Row<DATA> row, Column column, Cell cell) {
				cell.setControl(UIProvider.getInstance().createFieldControl(row.getData(), column.getField()));
			}
		});	
		
		crudOneRowCommandable = UIProvider.getInstance().createCommandable(this, "command.edit", IconType.ACTION_EDIT, null, null);
		crudOneRowCommandable.setShowLabel(Boolean.FALSE);
		
		searchCommandable = UIProvider.getInstance().createCommandable(this, "command.search", IconType.ACTION_SEARCH, null, null);
		searchCommandable.setShowLabel(Boolean.FALSE);
	}
	
	@Override
	public void build() {
		super.build();
		if(UsedFor.ENTITY_INPUT.equals(usedFor)){
			if(Boolean.TRUE.equals(getShowHierarchy())){
				MODEL hierarchyNode = createHierarchyNode();
				hierarchyNode.setLabel(getTitle());
				createTree();
				tree.build(hierarchyNode);
				for(DATA d : hierarchyData)
					tree.populate(d);	
				
				/*
				
				
				selectObjectMethod = new AbstractMethod<Object, Object>() {
					private static final long serialVersionUID = -9071786035119019765L;
					@Override
					protected Object __execute__(Object parameter) {
						primefacesTree.expand(parameter,Boolean.TRUE);
						return null;
					}
				};
				*/
				
				//select(master);
				tree.expand(master, Boolean.TRUE);
			}
		}else if(UsedFor.FIELD_INPUT.equals(usedFor)){
			
		}
	}
	
	protected abstract void __createTree__();
	protected void createTree(){
		__createTree__();
		if(tree!=null){
			tree.getTreeListeners().add(new TreeAdapter<NODE,MODEL>(){
				private static final long serialVersionUID = 6817293162423539828L;
				@SuppressWarnings("unchecked")
				@Override
				public void nodeSelected(NODE node) {
					open( (DATA) tree.nodeModel(node).getData());
				}
				
				@Override
				public Collection<Object> children(Object object) {
					if(object instanceof AbstractDataTreeNode){
						Collection<Object> collection = new ArrayList<>();
						if(((AbstractDataTreeNode)object).getChildren()!=null)
							for(Object o : ((AbstractDataTreeNode)object).getChildren())
								collection.add(o);
						return collection;
					}
					return super.children(object);
				}
			});
		}
	}
	
	protected abstract MODEL createHierarchyNode();
	
	protected abstract void open(DATA data);
	
	protected Boolean isDataTreeType(){
		return AbstractDataTreeNode.class.isAssignableFrom(rowDataClass);
	}
	
	public String getTitle(){
		if(title==null)
			title = UIManager.getInstance().textOfClass(rowDataClass);
		return title;
	}
	
	public Boolean getShowHierarchy(){
		return Boolean.TRUE.equals(isDataTreeType());
	}
	
	public Boolean getShowOpenCommand(){
		return getShowHierarchy();
	}
	
	public Boolean getShowFooterCommandBlock(){
		return UsedFor.FIELD_INPUT.equals(usedFor);
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
	protected Class<? extends AbstractIdentifiable> identifiableClass(){
		return (Class<? extends AbstractIdentifiable>) (identifiableConfiguration==null?rowDataClass:identifiableConfiguration.getIdentifiableClass());
	}
	
	@SuppressWarnings("unchecked")
	public void fetchData(Integer first, Integer pageSize,String sortField, Boolean ascendingOrder,Map<String, Object> filters){
		rows.clear();
		if(AbstractDataTreeNode.class.isAssignableFrom(rowDataClass)){
			showHierarchy = Boolean.TRUE;
			@SuppressWarnings("rawtypes")
			AbstractDataTreeNodeBusiness bean = (AbstractDataTreeNodeBusiness) BusinessLocator.getInstance().locate((Class<AbstractIdentifiable>) rowDataClass);
			handle(bean);
		}else{
			Collection<? extends AbstractIdentifiable> records = null;
			if(AbstractIdentifiable.class.isAssignableFrom(rowDataClass)){
				records = UIManager.getInstance().getGenericBusiness().use((Class<? extends AbstractIdentifiable>) rowDataClass).find().all();
				addRow((Collection<DATA>)records);
			}else{
				if(Boolean.TRUE.equals(lazyLoad)){
					records = UIManager.getInstance().find(identifiableClass(), first, pageSize, sortField, ascendingOrder, filters);
				}else
					records = UIManager.getInstance().getGenericBusiness().use((Class<? extends AbstractIdentifiable>) identifiableConfiguration.getIdentifiableClass()).find().all();
				if(records!=null)
					for(AbstractIdentifiable identifiable : records)	
						addRow((DATA) AbstractFormModel.instance(rowDataClass, identifiable));

			}
		}
	}
	
	@Override
	public Long count(String filter) {
		return UIManager.getInstance().count(identifiableClass(), filter);
	}
	
	@Override
	public void transfer(UICommand command, Object parameter) throws Exception {
		if(command==applyRowEditCommandable.getCommand()){
			@SuppressWarnings("unchecked")
			Row<DATA> row = (Row<DATA>) parameter;
			for(Cell cell : row.getCells())
				((Input<?, ?, ?, ?, ?, ?>)cell.getControl()).applyValueToField(row.getData());
		}
	}
	
	@Override
	public Boolean validate(UICommand command, Object parameter) {
		if(command==addRowCommandable.getCommand()){
			return editing.isEmpty();
		}else if(command==initRowEditCommandable.getCommand()){
			return editing.isEmpty();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void serve(UICommand command, Object parameter) {
		lastExecutedCommand = command;
		if(command==addRowCommandable.getCommand()){
			if(identifiableConfiguration==null){
				DATA d = newInstance(rowDataClass);
				editing.add(d);
				addRow(d);
			}else
				crudOnePage();
		}else if(command==initRowEditCommandable.getCommand()){
			Row<DATA> row = (Row<DATA>) parameter;
			editing.add(row.getData());
		}else if(command==applyRowEditCommandable.getCommand()){
			for(DATA data : editing){
				UIManager.getInstance().getGenericBusiness().save((AbstractIdentifiable) data);
			}
			
			Row<DATA> row = (Row<DATA>) parameter;
			updateRow(row, row.getData());
			editing.clear();
		}else if(command==cancelRowEditCommandable.getCommand()){
			if(editing.get(0) instanceof AbstractIdentifiable && ((AbstractIdentifiable)editing.get(0)).getIdentifier()==null)
				deleteRowAt(rows.size()-1);
			else{
				Row<DATA> row = (Row<DATA>) parameter;
				if(AbstractIdentifiable.class.isAssignableFrom(rowDataClass)){
					AbstractIdentifiable identifiable = (AbstractIdentifiable) row.getData();
					updateRow(row, (DATA) UIManager.getInstance().getGenericBusiness().use((Class<? extends AbstractIdentifiable>) rowDataClass).find(identifiable.getIdentifier()));
				}
			}
			editing.clear();
		}else if(command==removeRowCommandable.getCommand()){
			Row<DATA> row = (Row<DATA>) parameter;
			if(identifiableConfiguration==null){
				if(AbstractIdentifiable.class.isAssignableFrom(rowDataClass)){
					AbstractIdentifiable identifiable = (AbstractIdentifiable) row.getData();
					UIManager.getInstance().getGenericBusiness().delete(identifiable);
					deleteRowAt(row.getIndex().intValue());
				}
			}else{
				crudOnePage(row.getData(),Crud.DELETE);
			}
			
		}else if(command==crudOneRowCommandable.getCommand()){
			Row<DATA> row = (Row<DATA>) parameter;
			crudOnePage(row.getData(),Crud.UPDATE);
		}
		
	}
	
	protected abstract void crudOnePage(DATA data,Crud crud);

	protected abstract void crudOnePage();
	
	@Override
	public Object succeed(UICommand command, Object parameter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object fail(UICommand command, Object parameter, Throwable throwable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean notifyAfterServe(UICommand command, AfterServeState state) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String notificationMessageIdAfterServe(UICommand command, Object parameter, AfterServeState state) {
		// TODO Auto-generated method stub
		return null;
	}	
	
}
