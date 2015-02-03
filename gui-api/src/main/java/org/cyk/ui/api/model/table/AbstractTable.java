package org.cyk.ui.api.model.table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeNode;
import org.cyk.ui.api.AbstractTree;
import org.cyk.ui.api.TreeAdapter;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.UIProvider;
import org.cyk.ui.api.command.CommandListener;
import org.cyk.ui.api.command.DefaultMenu;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UICommandable.CommandRequestType;
import org.cyk.ui.api.command.UICommandable.IconType;
import org.cyk.ui.api.command.UICommandable.Parameter;
import org.cyk.ui.api.command.UICommandable.ViewType;
import org.cyk.ui.api.command.UIMenu;
import org.cyk.ui.api.config.IdentifiableConfiguration;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.model.table.Table;
import org.cyk.utility.common.model.table.TableAdapter;


@Getter @Setter
public abstract class AbstractTable<DATA,NODE,MODEL extends HierarchyNode> extends Table<Row<DATA>, Column, DATA, String, Cell, String> implements CommandListener,Serializable {

	private static final long serialVersionUID = 581883275700805955L;
 
	public enum RowMenuLocation{MAIN_MENU,BY_ROW}
	public enum UsedFor{ENTITY_INPUT,FIELD_INPUT}
	
	protected List<DATA> editing = new ArrayList<>();
	private Boolean __justAdded__ = null;
	
	protected UsedFor usedFor = UsedFor.ENTITY_INPUT;
	protected Crud crud;
	protected IdentifiableConfiguration identifiableConfiguration;
	protected BusinessEntityInfos businessEntityInfos;
	protected String title;
	protected Boolean editable=Boolean.FALSE,selectable=Boolean.TRUE,inplaceEdit=Boolean.TRUE,lazyLoad=null,globalFilter=null,showToolBar=Boolean.TRUE,
			showEditColumn,showAddRemoveColumn,persistOnApplyRowEdit,persistOnRemoveRow;
	protected AbstractTree<NODE,MODEL> tree;
	protected UICommandable addRowCommandable,initRowEditCommandable,cancelRowEditCommandable,applyRowEditCommandable,removeRowCommandable,openRowCommandable,
		crudOneRowCommandable,searchCommandable,exportCommandable,exportToPdfCommandable,exportToXlsCommandable,printCommandable;
	protected UIMenu exportMenu = new DefaultMenu();
	protected Boolean showHierarchy,showOpenCommand=Boolean.TRUE,showFooterCommandBlock=Boolean.TRUE;
	
	
	
	protected AbstractIdentifiable master;
	protected List<DATA> hierarchyData = new ArrayList<>();
	
	protected UICommand lastExecutedCommand;
	
	@SuppressWarnings("unchecked")
	public AbstractTable() {
		super(null/*(Class<? extends Row<DATA>>) Row.class*/, null, Column.class, Cell.class);	
		rowClass = (Class<Row<DATA>>) commonUtils.classFormName(Row.class.getName());
	}
	
	@Override
	protected void initialisation() {
		// TODO Auto-generated method stub
		super.initialisation();
		//rowClass = (Class<Row<DATA>>) Class.forName(Row.class.getName());
		addRowCommandable = UIProvider.getInstance().createCommandable(this, "command.add", IconType.ACTION_ADD, null, null);
		initRowEditCommandable = UIProvider.getInstance().createCommandable(this, "command.edit", IconType.ACTION_EDIT, null, null);
		cancelRowEditCommandable = UIProvider.getInstance().createCommandable(this, "command.cancel", IconType.ACTION_CANCEL, null, null);
		applyRowEditCommandable = UIProvider.getInstance().createCommandable(this, "command.apply", IconType.ACTION_APPLY, null, null);
		removeRowCommandable = UIProvider.getInstance().createCommandable(this, "command.remove", IconType.ACTION_REMOVE, null, null);
		removeRowCommandable.setShowLabel(Boolean.FALSE);
		removeRowCommandable.getCommand().setConfirm( CrudStrategy.ENUMERATION.equals(businessEntityInfos.getCrudStrategy()) );
		openRowCommandable = UIProvider.getInstance().createCommandable(this, "command.open", IconType.ACTION_OPEN, null, null);
		openRowCommandable.setShowLabel(Boolean.FALSE);
		
		crudOneRowCommandable = UIProvider.getInstance().createCommandable(this, "command.edit", IconType.ACTION_EDIT, null, null);
		crudOneRowCommandable.setShowLabel(Boolean.FALSE);
		
		searchCommandable = UIProvider.getInstance().createCommandable(this, "command.search", IconType.ACTION_SEARCH, null, null);
		searchCommandable.setShowLabel(Boolean.FALSE);
		
		exportCommandable = UIProvider.getInstance().createCommandable(null, "command.export", IconType.ACTION_EXPORT, null, null);
		
		exportToPdfCommandable = UIProvider.getInstance().createCommandable(this, "command.export.pdf", IconType.ACTION_EXPORT_PDF, null, null);
		exportToPdfCommandable.setViewType(ViewType.TOOLS_EXPORT_DATA_TABLE_TO_PDF);
		exportToPdfCommandable.setCommandRequestType(CommandRequestType.UI_VIEW);
		exportToPdfCommandable.getParameters().add(new Parameter(UIManager.getInstance().getClassParameter(),UIManager.getInstance().keyFromClass(identifiableClass())));
		
		exportMenu.getCommandables().add(exportToPdfCommandable);
		
		exportToXlsCommandable = UIProvider.getInstance().createCommandable(this, "command.export.excel", IconType.ACTION_EXPORT_EXCEL, null, null);
		exportToXlsCommandable.setViewType(ViewType.TOOLS_EXPORT_DATA_TABLE_TO_XLS);
		exportToXlsCommandable.setCommandRequestType(CommandRequestType.UI_VIEW);
		exportToXlsCommandable.getParameters().add(new Parameter(UIManager.getInstance().getClassParameter(),UIManager.getInstance().keyFromClass(identifiableClass())));
		
		exportMenu.getCommandables().add(exportToXlsCommandable);
		
		printCommandable = UIProvider.getInstance().createCommandable(this, "command.print", IconType.ACTION_PRINT, null, null);
		printCommandable.setViewType(ViewType.TOOLS_PRINT_DATA_TABLE);
		
		getTableListeners().add(new TableAdapter<Row<DATA>, Column, DATA, String, Cell, String>(){
			@Override
			public void columnAdded(Column column) {
				super.columnAdded(column);
				column.setTitle(UIManager.getInstance().fieldLabel(column.getField()));
			}
			@Override
			public void cellAdded(Row<DATA> row, Column column, Cell cell) {
				cell.setValue(UIProvider.getInstance().readOnlyValue(column.getField(), row.getData()));
				if(!Boolean.TRUE.equals(lazyLoad)){
					cell.setControl(UIProvider.getInstance().createFieldControl(row.getData(), column.getField()));
				}
			}
		});	
		
		showToolBar = UsedFor.ENTITY_INPUT.equals(usedFor);
	}
	
	@Override
	public void build() {
		lazyLoad = lazyLoad==null?!CrudStrategy.ENUMERATION.equals(businessEntityInfos.getCrudStrategy()):lazyLoad;
		//globalFilter = lazyLoad;
		if(globalFilter==null)
			globalFilter = identifiableConfiguration==null?lazyLoad:identifiableConfiguration.getGlobalFiltering();
		inplaceEdit = !lazyLoad;
		showEditColumn = true;//UsedFor.ENTITY_INPUT.equals(usedFor);
		showAddRemoveColumn = Boolean.TRUE;
		persistOnApplyRowEdit = persistOnRemoveRow = UsedFor.ENTITY_INPUT.equals(usedFor);
		editable = Crud.CREATE.equals(crud) || Crud.UPDATE.equals(crud);
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
			if(!Boolean.TRUE.equals(getLazyLoad()))
				fetchData(null,null,null,null,null,null);
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
	
	public Boolean isDataTreeType(){
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
	/*
	@SuppressWarnings("unchecked")
	public <B extends AbstractDataTreeNodeBusiness<? extends AbstractDataTreeNode>> void handle(B business,Collection<Object> rows){
		//for(AbstractDataTreeNode node : business.findHierarchies())
		//	hierarchyData.add((DATA) node);
		addRowOfRoot(master,rows);
	}*/
	/*
	@SuppressWarnings("unchecked")
	protected void addRowOfRoot(AbstractIdentifiable root,Collection<Object> rows){
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
			rows.add(node);
			//addRow(node);
		
		master = root;
	}*/
	
	@SuppressWarnings("unchecked")
	public DATA getReferenceFromHierarchy(AbstractIdentifiable identifiable,List<DATA> list){
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
	/*
	@SuppressWarnings("unchecked")
	@Override
	public void fetchData(Integer first, Integer pageSize,String sortField, Boolean ascendingOrder,Map<String, Object> filters,String globalFilter){
		System.out.println("AbstractTable.fetchData()");
		rows.clear();
		if(AbstractDataTreeNode.class.isAssignableFrom(rowDataClass)){
			showHierarchy = Boolean.TRUE;
			@SuppressWarnings("rawtypes")
			AbstractDataTreeNodeBusiness bean = (AbstractDataTreeNodeBusiness) BusinessLocator.getInstance().locate((Class<AbstractIdentifiable>) rowDataClass);
			handle(bean);
		}else{
			Collection<? extends AbstractIdentifiable> records = null;
			//if(AbstractIdentifiable.class.isAssignableFrom(rowDataClass)){
			//	records = UIManager.getInstance().getGenericBusiness().use((Class<? extends AbstractIdentifiable>) rowDataClass).find().all();
			//	addRow((Collection<DATA>)records);
			//}else{
				if(Boolean.TRUE.equals(lazyLoad)){
					if(Boolean.TRUE.equals(identifiableConfiguration==null?this.globalFilter:identifiableConfiguration.getGlobalFiltering()))
						records = UIManager.getInstance().find(identifiableClass(), first, pageSize, sortField, ascendingOrder, globalFilter);
					else
						records = UIManager.getInstance().find(identifiableClass(), first, pageSize, sortField, ascendingOrder, filters);
				}else
					records = UIManager.getInstance().getGenericBusiness().use(identifiableClass()).find().all();
				
				if(AbstractIdentifiable.class.isAssignableFrom(rowDataClass))
					addRow((Collection<DATA>)records);
				else	
					if(records!=null)
						for(AbstractIdentifiable identifiable : records)	
							addRow((DATA) AbstractFormModel.instance(rowDataClass, identifiable));

			//}
		}
	}*/
	
	//TODO commands should be treated in specific pages
	
	private void transfer(Object parameter) throws IllegalAccessException{
		if(parameter==null)
			return;
		Row<?> row = (Row<?>) parameter;
		for(Cell cell : row.getCells()){
			((org.cyk.ui.api.data.collector.control.Input<?, ?, ?, ?, ?, ?>)cell.getControl()).applyValueToField();
			//debug((org.cyk.ui.api.data.collector.control.Input<?, ?, ?, ?, ?, ?>)cell.getControl());
		}
		
		if(AbstractFormModel.class.isAssignableFrom(parameter.getClass())){
			((AbstractFormModel<?>)parameter).write();
		}
		
	}
	
	@Override
	public void transfer(UICommand command, Object parameter) throws Exception {
		if(applyRowEditCommandable.getCommand()==command){
			//if(UsedFor.ENTITY_INPUT.equals(usedFor)){
				transfer(parameter);
				/*
				Row<?> row = (Row<?>) parameter;
				for(Cell cell : row.getCells())
					((org.cyk.ui.api.data.collector.control.Input<?, ?, ?, ?, ?, ?>)cell.getControl()).applyValueToField();
				
				if(parameter!=null){
					if(AbstractFormModel.class.isAssignableFrom(parameter.getClass())){
						((AbstractFormModel<?>)parameter).write();
					}
				}
				*/
			//}
		}else if(addRowCommandable.getCommand()==command){
			/*if(UsedFor.FIELD_INPUT.equals(usedFor)){
				System.out.println("AbstractTable.transfer()");
				for(Row<?> row : rows)
					transfer(row);
			}*/
			
		}
		/*
		if(table.getApplyRowEditCommandable().getCommand()==command){
			if(parameter!=null){
				if(AbstractFormModel.class.isAssignableFrom(parameter.getClass())){
					((AbstractFormModel<?>)parameter).write();
				}
			}
		}
		*/
	}
	
	@Override
	public Boolean validate(UICommand command, Object parameter) {
		if(command==addRowCommandable.getCommand()){
			//if(UsedFor.ENTITY_INPUT.equals(usedFor))
				return editing.isEmpty();
			//else
			//	return UsedFor.FIELD_INPUT.equals(usedFor);
		}else if(command==initRowEditCommandable.getCommand()){
			return Boolean.TRUE.equals(__justAdded__) || editing.isEmpty();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void serve(UICommand command, Object parameter) {
		lastExecutedCommand = command;
		if(command==addRowCommandable.getCommand()){
			if(Boolean.TRUE.equals(lazyLoad)){
				crudOnePage();
			}else{
				DATA d = newInstance(rowDataClass);
				editing.add(d);
				addRow(d);
				__justAdded__ =  Boolean.TRUE;
			}
				
		}else if(command==initRowEditCommandable.getCommand()){
			Row<DATA> row = (Row<DATA>) parameter;
			editing.add(row.getData());
		}else if(command==applyRowEditCommandable.getCommand()){
			if(Boolean.TRUE.equals(persistOnApplyRowEdit))
				for(DATA data : editing){
					UIManager.getInstance().getGenericBusiness().save((AbstractIdentifiable) data);//TODO should not be here. specific to CrudMany
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
			if(!Boolean.TRUE.equals(lazyLoad)){
				if(AbstractIdentifiable.class.isAssignableFrom(rowDataClass)){
					AbstractIdentifiable identifiable = (AbstractIdentifiable) row.getData();
					if(Boolean.TRUE.equals(persistOnRemoveRow))
						UIManager.getInstance().getGenericBusiness().delete(identifiable);
					deleteRowAt(row.getIndex().intValue());
				}
			}else{
				crudOnePage(row.getData(),Crud.DELETE);
			}
			
		}else if(command==crudOneRowCommandable.getCommand()){
			Row<DATA> row = (Row<DATA>) parameter;
			crudOnePage(row.getData(),Crud.UPDATE);
		}else if(command==exportToPdfCommandable.getCommand()){
			exportDataTableToPdfPage();
		}else if(command==exportToXlsCommandable.getCommand()){
			exportDataTableToXlsPage();
		}else if(command==printCommandable.getCommand()){
			printDataPage();
		}
		
	}
	
	protected abstract void crudOnePage(DATA data,Crud crud);

	protected abstract void crudOnePage();
	protected abstract void exportDataTableToPdfPage();
	protected abstract void exportDataTableToXlsPage();
	protected abstract void printDataPage();
	
	@Override
	public Object succeed(UICommand command, Object parameter) {
		return null;
	}

	@Override
	public Object fail(UICommand command, Object parameter, Throwable throwable) {
		return null;
	}

	@Override
	public Boolean notifyAfterServe(UICommand command, AfterServeState state) {
		return null;
	}

	@Override
	public String notificationMessageIdAfterServe(UICommand command, Object parameter, AfterServeState state) {
		return null;
	}	
	
}
