package org.cyk.ui.api.model.table;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.pattern.tree.AbstractDataTreeNodeBusiness;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeNode;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.UIProvider;
import org.cyk.ui.api.command.CommandListener;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UICommandable.CommandRequestType;
import org.cyk.ui.api.command.UICommandable.IconType;
import org.cyk.ui.api.command.UICommandable.Parameter;
import org.cyk.ui.api.command.UICommandable.ViewType;
import org.cyk.ui.api.command.menu.DefaultMenu;
import org.cyk.ui.api.command.menu.UIMenu;
import org.cyk.ui.api.config.IdentifiableConfiguration;
import org.cyk.ui.api.data.collector.control.AbstractFieldSorter.FieldSorter;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.api.model.AbstractTree;
import org.cyk.ui.api.model.HierarchyNode;
import org.cyk.ui.api.model.TreeAdapter;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.model.table.Table;
import org.cyk.utility.common.model.table.TableAdapter;


@Getter @Setter
public abstract class AbstractTable<DATA,NODE,MODEL extends HierarchyNode> extends Table<Row<DATA>, Column, DATA, String, Cell, String> implements CommandListener,Serializable {

	private static final long serialVersionUID = 581883275700805955L;
 
	public enum RowMenuLocation{MAIN_MENU,BY_ROW}
	public enum UsedFor{ENTITY_INPUT,FIELD_INPUT}
	public enum RenderType{TABLE,LIST,GRID}
	
	protected List<DATA> editing = new ArrayList<>();
	private Boolean __justAdded__ = null;
	
	protected UsedFor usedFor = UsedFor.ENTITY_INPUT;
	protected Crud crud;
	protected Class<? extends AbstractIdentifiable> identifiableClass;
	protected IdentifiableConfiguration identifiableConfiguration;
	protected BusinessEntityInfos businessEntityInfos;
	protected String title,reportIdentifier=RootBusinessLayer.getInstance().getParameterGenericReportBasedOnDynamicBuilder();
	protected Boolean editable=null,selectable=Boolean.TRUE,inplaceEdit=Boolean.TRUE,lazyLoad=null,globalFilter=null,showToolBar=Boolean.FALSE,
			showEditColumn,showAddRemoveColumn,persistOnApplyRowEdit,persistOnRemoveRow;
	protected AbstractTree<NODE,MODEL> tree;
	protected UICommandable addRowCommandable,initRowEditCommandable,cancelRowEditCommandable,applyRowEditCommandable,removeRowCommandable,openRowCommandable,
		crudOneRowCommandable,searchCommandable,exportCommandable,exportToPdfCommandable,exportToXlsCommandable,printCommandable;
	protected UIMenu exportMenu = new DefaultMenu();
	protected Boolean showHierarchy,showOpenCommand=Boolean.FALSE,showFooterCommandBlock=Boolean.TRUE,showHeader=Boolean.TRUE,showFooter=Boolean.FALSE;
	
	protected DATA master;
	protected List<DATA> hierarchyData = new ArrayList<>();
	
	protected UICommand lastExecutedCommand;
	protected RenderType renderType = RenderType.TABLE;
	
	@SuppressWarnings("unchecked")
	public AbstractTable() {
		super(null/*(Class<? extends Row<DATA>>) Row.class*/, null, Column.class, Cell.class);	
		rowClass = (Class<Row<DATA>>) commonUtils.classFormName(Row.class.getName());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void initialisation() {
		super.initialisation();
		//rowClass = (Class<Row<DATA>>) Class.forName(Row.class.getName());
		identifiableClass = (Class<? extends AbstractIdentifiable>) (identifiableConfiguration==null?rowDataClass:identifiableConfiguration.getIdentifiableClass());
		addRowCommandable = UIProvider.getInstance().createCommandable(this, "command.add", IconType.ACTION_ADD, null, null);
		initRowEditCommandable = UIProvider.getInstance().createCommandable(this, "command.edit", IconType.ACTION_EDIT, null, null);
		cancelRowEditCommandable = UIProvider.getInstance().createCommandable(this, "command.cancel", IconType.ACTION_CANCEL, null, null);
		applyRowEditCommandable = UIProvider.getInstance().createCommandable(this, "command.apply", IconType.ACTION_APPLY, null, null);
		removeRowCommandable = UIProvider.getInstance().createCommandable(this, "command.remove", IconType.ACTION_REMOVE, null, null);
		removeRowCommandable.setShowLabel(Boolean.FALSE);
		
		openRowCommandable = UIProvider.getInstance().createCommandable(this, "command.open", IconType.ACTION_OPEN, null, null);
		openRowCommandable.setShowLabel(Boolean.FALSE);
		
		crudOneRowCommandable = UIProvider.getInstance().createCommandable(this, "command.edit", IconType.ACTION_EDIT, null, null);
		crudOneRowCommandable.setShowLabel(Boolean.FALSE);
		
		searchCommandable = UIProvider.getInstance().createCommandable(this, "command.search", IconType.ACTION_SEARCH, null, null);
		searchCommandable.setShowLabel(Boolean.FALSE);
		
		exportCommandable = UIProvider.getInstance().createCommandable(null, "command.export", IconType.ACTION_EXPORT, null, null);
		exportCommandable.setRendered(Boolean.FALSE);
		
		exportToPdfCommandable = UIProvider.getInstance().createCommandable(this, "command.export.pdf", IconType.ACTION_EXPORT_PDF, null, null);
		reportCommandable(exportToPdfCommandable, UIManager.getInstance().getPdfParameter());
		exportMenu.getCommandables().add(exportToPdfCommandable);
		
		exportToXlsCommandable = UIProvider.getInstance().createCommandable(this, "command.export.excel", IconType.ACTION_EXPORT_EXCEL, null, null);
		reportCommandable(exportToXlsCommandable, UIManager.getInstance().getXlsParameter());
		exportMenu.getCommandables().add(exportToXlsCommandable);
		
		printCommandable = UIProvider.getInstance().createCommandable(this, "command.print", IconType.ACTION_PRINT, null, null);
		//reportCommandable(printCommandable, UIManager.getInstance().getPdfParameter()); //has been move on demand because of customization
		printCommandable.getParameters().add(new Parameter(UIManager.getInstance().getPrintParameter(),Boolean.TRUE));
		
		getTableListeners().add(new TableAdapter<Row<DATA>, Column, DATA, String, Cell, String>(){
			@Override
			public void addColumnFromDataClass(Class<?> aClass,Collection<Field> fields) {
				List<Field> f = new ArrayList<>();
				__fields__(f, rowDataClass,UIProvider.getInstance().annotationClasses());
				fields.addAll(f);
			}
			@Override
			public void columnAdded(Column column) {
				super.columnAdded(column);
				column.setTitle(UIManager.getInstance().getLanguageBusiness().findFieldLabelText(column.getField()));
			}
			
			@Override
			public String cellValue(Row<DATA> row, Column column) {
				Object value = commonUtils.readField(row.getData(), column.getField(),!column.getField().getDeclaringClass().isAssignableFrom(rowDataClass),Boolean.FALSE,
						UIProvider.getInstance().annotationClasses());
				//if( value!=null )
					//System.out.println(column.getField().getName()+" - "+value);
				return UIProvider.getInstance().formatValue(column.getField(), value);
			}
			
			@Override
			public void cellAdded(Row<DATA> row, Column column, Cell cell) {
				/*
				Object object = null;
				if(column.getField().getDeclaringClass().equals(rowDataClass)){
					object = row.getData();
				}
				if(object!=null)
					cell.setValue(UIProvider.getInstance().readOnlyValue(column.getField(), object));
				*/
				cell.setIsFile(UIProvider.getInstance().isFile(column.getField()));
				if(Boolean.TRUE.equals(cell.getIsFile()))
					cell.setIsImage(UIProvider.getInstance().isImage(column.getField()));
				if(Boolean.TRUE.equals(lazyLoad)){
					
				}else{
					cell.setControl(UIProvider.getInstance().createFieldControl(row.getData(), column.getField()));
				}
			}
		});	
		
		showToolBar = UsedFor.ENTITY_INPUT.equals(usedFor);
		
		
	}
	
	protected void reportCommandable(UICommandable commandable,String fileExtension){
		commandable.setViewType(ViewType.TOOLS_REPORT);
		commandable.setCommandRequestType(CommandRequestType.UI_VIEW);
		commandable.getParameters().add(new Parameter(UIManager.getInstance().getClassParameter(),UIManager.getInstance().keyFromClass(identifiableClass)));
		commandable.getParameters().add(new Parameter(UIManager.getInstance().getFileExtensionParameter(),fileExtension));
		commandable.getParameters().add(new Parameter(UIManager.getInstance().getReportIdentifierParameter(),reportIdentifier));
	}
	
	@Override
	public void build() {
		lazyLoad = lazyLoad==null?businessEntityInfos!=null && !CrudStrategy.ENUMERATION.equals(businessEntityInfos.getCrudStrategy()):lazyLoad;
		//globalFilter = lazyLoad;
		if(globalFilter==null)
			globalFilter = identifiableConfiguration==null?lazyLoad:identifiableConfiguration.getGlobalFiltering();
		inplaceEdit = businessEntityInfos==null?Boolean.FALSE:StringUtils.isBlank(businessEntityInfos.getUiEditViewId()) /*|| !lazyLoad*/;
		showEditColumn = businessEntityInfos!=null; //true;//UsedFor.ENTITY_INPUT.equals(usedFor);
		showAddRemoveColumn = businessEntityInfos!=null; //Boolean.TRUE;
		persistOnApplyRowEdit = persistOnRemoveRow = UsedFor.ENTITY_INPUT.equals(usedFor);
		if(editable==null)
			editable = inplaceEdit || Crud.CREATE.equals(crud) || Crud.UPDATE.equals(crud);
		
		removeRowCommandable.getCommand().setConfirm(inplaceEdit);
		
		super.build();
		if(UsedFor.ENTITY_INPUT.equals(usedFor)){
			if(!Boolean.TRUE.equals(getLazyLoad())){
				fetchData(null,null,null,null,null,null);
			}
			if(Boolean.TRUE.equals(getShowHierarchy())){
				MODEL hierarchyNode = createHierarchyNode();
				hierarchyNode.setLabel(UIManager.getInstance().getLanguageBusiness().findClassLabelText(rowDataClass));
				createTree();
				/*
				tree.build(hierarchyNode);
				for(DATA d : hierarchyData)
					tree.populate(d);	
				tree.expand(master, Boolean.TRUE);
				*/
				tree.build(rowDataClass, hierarchyData, (DATA)master);
				
				showOpenCommand = getShowHierarchy();
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
					open(tree.nodeModel(node).getData() instanceof AbstractIdentifiable?(DATA) tree.nodeModel(node).getData():null);
				}
			});
		}
	}
	
	protected abstract MODEL createHierarchyNode();
	
	protected abstract void open(DATA data);
	
	public Boolean isDataTreeType(){
		return AbstractDataTreeNode.class.isAssignableFrom(rowDataClass);//TODO should be businessEntityInfos.getClazz()
	}
	/*
	public String getTitle(){
		if(title==null)
			title = UIManager.getInstance().textOfClass(rowDataClass);
		return title;
	}*/
	
	public Boolean getShowHierarchy(){
		return Boolean.TRUE.equals(isDataTreeType());
	}
	
	public Boolean getShowFooterCommandBlock(){
		return UsedFor.FIELD_INPUT.equals(usedFor);
	}
	
	//TODO to be moved later in CrudManyPage i think. For now I need it to work
	
	
	public <B extends AbstractDataTreeNodeBusiness<? extends AbstractDataTreeNode>> void handle(B business,Collection<Object> rows){
		//for(AbstractDataTreeNode node : business.findHierarchies())
		//	hierarchyData.add((DATA) node);
		addRowOfRoot(master,rows);
	}
	
	@SuppressWarnings("unchecked")
	protected void addRowOfRoot(DATA root,Collection<Object> rows){
		Collection<DATA> collection = new ArrayList<>();
		if(root==null)
			for(DATA node : hierarchyData)
				collection.add(node);
		else{
			//business.findHierarchy( master);
			root = getReferenceFromHierarchy(root,hierarchyData);
			if( ((AbstractDataTreeNode)root).getChildren()!=null)
				for(AbstractDataTreeNode node : ((AbstractDataTreeNode)root).getChildren())
					collection.add((DATA) node);	
		}
		
		clear();
		for(DATA node : collection)
			rows.add(node);
			//addRow(node);
		
		master = root;
	}
	
	@SuppressWarnings("unchecked")
	public DATA getReferenceFromHierarchy(DATA identifiable,List<DATA> list){
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
			transfer(parameter);
		}else if(addRowCommandable.getCommand()==command){
			
		}
	}
	
	@Override
	public Boolean validate(UICommand command, Object parameter) {
		if(command==addRowCommandable.getCommand()){
			return editing.isEmpty();
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
			if(Boolean.TRUE.equals(inplaceEdit)){
				DATA d;
				if(AbstractFormModel.class.isAssignableFrom(rowDataClass)){
					d = (DATA) AbstractFormModel.instance(rowDataClass, (AbstractIdentifiable) newInstance(businessEntityInfos.getClazz()));
				}else{
					d = newInstance(rowDataClass);
					if(isDataTreeType())
						if(master==null)
							;
						else
							((AbstractDataTreeNode)d).setNode(new NestedSetNode( ((AbstractDataTreeNode)master).getNode().getSet(), 
								((AbstractDataTreeNode)master).getNode()));// debug(master);
				}
				editing.add(d);
				addRow(d);
				__justAdded__ =  Boolean.TRUE;
			}else{
				crudOnePage();
			}
				
		}else if(command==initRowEditCommandable.getCommand()){
			Row<DATA> row = (Row<DATA>) parameter;
			editing.add(row.getData());
		}else if(command==applyRowEditCommandable.getCommand()){
			if(Boolean.TRUE.equals(persistOnApplyRowEdit))
				for(DATA data : editing){
					AbstractIdentifiable identifiable = null;
					if(data instanceof AbstractFormModel<?>){
						((AbstractFormModel<?>)data).write();
						identifiable = ((AbstractFormModel<?>)data).getIdentifiable();
					}else
						identifiable = (AbstractIdentifiable) data;
					UIManager.getInstance().getGenericBusiness().save(identifiable);//TODO should not be here. specific to CrudMany
				}
			Row<DATA> row = (Row<DATA>) parameter;
			updateRow(row, row.getData());
			//for()
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
			if(Boolean.TRUE.equals(inplaceEdit)){
				AbstractIdentifiable identifiable = null;
				if(AbstractIdentifiable.class.isAssignableFrom(rowDataClass)){
					identifiable = (AbstractIdentifiable) row.getData();
				}else{
					identifiable = ((AbstractFormModel<?>)row.getData()).getIdentifiable();
				}
				if(Boolean.TRUE.equals(persistOnRemoveRow))
					UIManager.getInstance().getGenericBusiness().delete(identifiable);
				deleteRowAt(row.getIndex().intValue());
			}else{
				crudOnePage(row.getData(),Crud.DELETE);
			}
			
		}else if(command==crudOneRowCommandable.getCommand()){
			//System.out.println("AbstractTable.serve() : CrudOne");
			//Row<DATA> row = (Row<DATA>) parameter;
			//crudOnePage(row.getData(),Crud.UPDATE);
		}else if(command==exportToPdfCommandable.getCommand()){
			exportDataTableToPdfPage();
		}else if(command==exportToXlsCommandable.getCommand()){
			exportDataTableToXlsPage();
		}else if(command==printCommandable.getCommand()){
			reportCommandable(printCommandable, UIManager.getInstance().getPdfParameter());
			/*
			for(Parameter p : printCommandable.getParameters())
				if(p.getValue()==null)
					if(p.getName().equals(UIManager.getInstance().getClassParameter()))
						p.setValue(UIManager.getInstance().keyFromClass(identifiableClass));
					else if(p.getName().equals(UIManager.getInstance().getReportIdentifierParameter()))
						p.setValue(reportIdentifier);
			*/	
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
	 
	/**/
	
	private void __fields__(List<Field> fields,Class<?> aClass,Collection<Class<? extends Annotation>> annotations){
		List<Field> candidates = new ArrayList<>(commonUtils.getAllFields(aClass, annotations));
		
		new FieldSorter(candidates,aClass).sort();
		
		for(Field field : candidates){
			if(field.getAnnotation(Input.class)!=null){
				fields.add(field);
			} else if(field.getAnnotation(IncludeInputs.class)!=null){
				__fields__(fields,field.getType(),annotations);
			}
		}
	}
	
}
