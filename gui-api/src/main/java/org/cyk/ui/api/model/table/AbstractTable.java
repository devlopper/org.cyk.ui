package org.cyk.ui.api.model.table;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.pattern.tree.AbstractDataTreeNodeBusiness;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeNode;
import org.cyk.ui.api.AbstractUserSession;
import org.cyk.ui.api.Icon;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.UIProvider;
import org.cyk.ui.api.command.AbstractCommandable.Builder;
import org.cyk.ui.api.command.CommandListener;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UICommandable.CommandRequestType;
import org.cyk.ui.api.command.UICommandable.Parameter;
import org.cyk.ui.api.command.UICommandable.ViewType;
import org.cyk.ui.api.command.menu.DefaultMenu;
import org.cyk.ui.api.command.menu.UIMenu;
import org.cyk.ui.api.config.IdentifiableConfiguration;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.api.data.collector.form.AbstractFormOneData;
import org.cyk.ui.api.model.AbstractHierarchyNode;
import org.cyk.ui.api.model.AbstractTree;
import org.cyk.utility.common.AbstractFieldSorter.FieldSorter;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.computation.DataReadConfiguration;
import org.cyk.utility.common.model.table.Table;


@Getter @Setter
public abstract class AbstractTable<DATA,NODE,MODEL extends AbstractHierarchyNode> extends Table<Row<DATA>, Column, DATA, String, Cell, String> implements CommandListener,Serializable {

	private static final long serialVersionUID = 581883275700805955L;
 
	public static final String COMMANDABLE_ADD_IDENTIFIER = "add";
	public static final String COMMANDABLE_EXPORT_PDF_IDENTIFIER = "print";
	
	public enum RowMenuLocation{MAIN_MENU,BY_ROW}
	public enum RenderType{TABLE,LIST,GRID}
	
	protected List<DATA> editing = new ArrayList<>();
	private Boolean __justAdded__ = null;
	
	protected Crud crud;
	protected Class<? extends AbstractIdentifiable> identifiableClass;
	protected IdentifiableConfiguration identifiableConfiguration;
	protected BusinessEntityInfos businessEntityInfos;
	protected String title,reportIdentifier=RootBusinessLayer.getInstance().getParameterGenericReportBasedOnDynamicBuilder();
	protected Boolean editable=null,selectable=Boolean.TRUE,inplaceEdit=Boolean.TRUE,lazyLoad=null,globalFilter=null,showToolBar=Boolean.TRUE,
			showEditColumn,showAddRemoveColumn,persistOnApplyRowEdit,persistOnRemoveRow,rendered=Boolean.TRUE;
	protected AbstractTree<NODE,MODEL> tree;
	protected UICommandable addRowCommandable,initRowEditCommandable,cancelRowEditCommandable,applyRowEditCommandable,removeRowCommandable,openRowCommandable,
		updateRowCommandable,searchCommandable,exportCommandable,exportToPdfCommandable,exportToXlsCommandable,printCommandable;
	protected UIMenu exportMenu = new DefaultMenu();
	protected Boolean showHierarchy,showOpenCommand=Boolean.FALSE,showFooterCommandBlock=Boolean.TRUE,showHeader=Boolean.TRUE,showFooter=Boolean.FALSE,built=Boolean.FALSE;
	
	protected DATA master;
	protected List<DATA> hierarchyData = new ArrayList<>();
	protected Collection<DATA> initialData;
	
	protected UICommand lastExecutedCommand;
	protected RenderType renderType = RenderType.TABLE;
	protected LanguageBusiness languageBusiness = UIManager.getInstance().getLanguageBusiness();
	protected UIProvider uiProvider = UIProvider.getInstance();
	protected Integer numberOfColumnsHorizontalHeader = 10;
	protected AbstractUserSession<?, ?> userSession;
	
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
		identifiableClass = (Class<? extends AbstractIdentifiable>) (identifiableConfiguration==null?(businessEntityInfos==null?rowDataClass:businessEntityInfos.getClazz()):identifiableConfiguration.getClazz());
		addRowCommandable = Builder.instanciateOne().setCommandListener(this).setLabelFromId("command.add").setIcon(Icon.ACTION_ADD)
				.setIdentifier(COMMANDABLE_ADD_IDENTIFIER).create();
		
		initRowEditCommandable = Builder.instanciateOne().setCommandListener(this).setLabelFromId("command.edit").setIcon(Icon.ACTION_EDIT).create();
		cancelRowEditCommandable = Builder.instanciateOne().setCommandListener(this).setLabelFromId("command.cancel").setIcon(Icon.ACTION_CANCEL).create();
		applyRowEditCommandable = Builder.instanciateOne().setCommandListener(this).setLabelFromId("command.apply").setIcon(Icon.ACTION_APPLY).create();
		removeRowCommandable = Builder.instanciateOne().setCommandListener(this).setLabelFromId("command.remove").setIcon(Icon.ACTION_REMOVE)
				.setShowLabel(Boolean.FALSE).create();
		
		openRowCommandable = Builder.instanciateOne().setCommandListener(this).setLabelFromId("command.open").setIcon(Icon.ACTION_OPEN)
				.setShowLabel(Boolean.FALSE).create();
	
		updateRowCommandable = Builder.instanciateOne().setCommandListener(this).setLabelFromId("command.edit").setIcon(Icon.ACTION_EDIT)
				.setShowLabel(Boolean.FALSE).create();
		
		searchCommandable = Builder.instanciateOne().setCommandListener(this).setLabelFromId("command.search").setIcon(Icon.ACTION_SEARCH)
				.setShowLabel(Boolean.FALSE).create();
		
		exportCommandable = Builder.instanciateOne().setCommandListener(this).setLabelFromId("command.export").setIcon(Icon.ACTION_EXPORT)
				.setShowLabel(Boolean.FALSE).setRendered(Boolean.FALSE).create();
		
		exportToPdfCommandable = Builder.instanciateOne().setCommandListener(this).setLabelFromId("command.export.pdf").setIcon(Icon.ACTION_EXPORT_PDF)
				.setIdentifier(COMMANDABLE_EXPORT_PDF_IDENTIFIER).create();
		reportCommandable(exportToPdfCommandable, UniformResourceLocatorParameter.PDF);
		exportMenu.getCommandables().add(exportToPdfCommandable);
		
		exportToXlsCommandable = Builder.instanciateOne().setCommandListener(this).setLabelFromId("command.export.excel").setIcon(Icon.ACTION_EXPORT_EXCEL)
				.create();
		reportCommandable(exportToXlsCommandable, UniformResourceLocatorParameter.XLS);
		exportMenu.getCommandables().add(exportToXlsCommandable);
		
		printCommandable = Builder.instanciateOne().setCommandListener(this).setLabelFromId("command.print").setIcon(Icon.ACTION_PRINT)
				.setRendered(Boolean.FALSE).addParameter(UniformResourceLocatorParameter.PRINT,Boolean.TRUE).create(); 
		//reportCommandable(printCommandable, UIManager.getInstance().getPdfParameter()); //has been move on demand because of customization
		//printCommandable.getParameters().add(new Parameter(UIManager.getInstance().getPrintParameter(),Boolean.TRUE));
		//printCommandable.setRendered(Boolean.FALSE);
		
		columnListeners.add(new ColumnAdapter(){
			@Override
			public void populateFromDataClass(Class<?> aClass, List<Field> fields) {
				List<Field> f = new ArrayList<>();
				__fields__(f, rowDataClass,uiProvider.annotationClasses());
				fields.addAll(f);
			}
			@Override
			public void added(Column column) {
				super.added(column);
				column.setTitle(languageBusiness.findFieldLabelText(column.getField()));
			}
		});
		rowListeners.add(new RowAdapter<DATA>(){
						
			@Override
			public void added(Row<DATA> row) {
				super.added(row);
				if(Boolean.TRUE.equals(getShowHierarchy()))
					row.setDeletable(Boolean.TRUE);
				if(Boolean.TRUE.equals(inplaceEdit) && AbstractFormModel.class.isAssignableFrom(rowDataClass) && 
						row.getData() instanceof AbstractIdentifiable){
					row.setData((DATA) AbstractFormModel.instance(rowDataClass, (AbstractIdentifiable) row.getData()));
				}
			}
		});
		
		cellListeners.add(new CellAdapter<DATA>(){
			@Override
			public String getValue(Row<DATA> row, Column column) {
				Object value = commonUtils.readField(row.getData(), column.getField(),!column.getField().getDeclaringClass().isAssignableFrom(rowDataClass),Boolean.FALSE,
						UIProvider.getInstance().annotationClasses());
				return UIProvider.getInstance().formatValue(column.getField(), value);
			}
			@Override
			public void added(Row<DATA> row, Column column, Cell cell) {
				cell.setIsFile(UIProvider.getInstance().isFile(column.getField()));
				if(Boolean.TRUE.equals(cell.getIsFile())){
					cell.setIsImage(UIProvider.getInstance().isImage(row.getData(),column.getField()));
					cell.setShowFileLink(UIProvider.getInstance().isShowFileLink(row.getData(),column.getField()));
				}
				if(Boolean.TRUE.equals(lazyLoad)){
					
				}else{
					Object value = commonUtils.getFieldValueContainer(row.getData(), column.getField());
					if(value!=null){
						cell.setControl(UIProvider.getInstance().createFieldControl(value, column.getField()));
					}
					
				}
			}
		});
		
	}
	
	protected void reportCommandable(UICommandable commandable,String fileExtension){
		commandable.setViewType(ViewType.TOOLS_REPORT);
		commandable.setCommandRequestType(CommandRequestType.UI_VIEW);
		commandable.getParameters().add(new Parameter(UniformResourceLocatorParameter.CLASS,UIManager.getInstance().keyFromClass(identifiableClass)));
		commandable.getParameters().add(new Parameter(UniformResourceLocatorParameter.FILE_EXTENSION,fileExtension));
		commandable.getParameters().add(new Parameter(UniformResourceLocatorParameter.REPORT_IDENTIFIER,reportIdentifier));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void build() {
		if(Boolean.TRUE.equals(built) || Boolean.FALSE.equals(rendered))
			return;
		lazyLoad = lazyLoad==null?businessEntityInfos!=null && !CrudStrategy.ENUMERATION.equals(businessEntityInfos.getCrudStrategy()):lazyLoad;
		//globalFilter = lazyLoad;
		if(globalFilter==null)
			globalFilter = identifiableConfiguration==null?lazyLoad:identifiableConfiguration.getGlobalFiltering();
		inplaceEdit = businessEntityInfos==null?Boolean.FALSE:StringUtils.isBlank(businessEntityInfos.getUserInterface().getEditViewId()) /*|| !lazyLoad*/;
		if(showEditColumn==null)
			showEditColumn = businessEntityInfos!=null; //true;//UsedFor.ENTITY_INPUT.equals(usedFor);
		if(showAddRemoveColumn==null)
			showAddRemoveColumn = businessEntityInfos!=null; //Boolean.TRUE;
		persistOnApplyRowEdit = persistOnRemoveRow = Boolean.TRUE;
		if(editable==null)
			editable = inplaceEdit || Crud.CREATE.equals(crud) || Crud.UPDATE.equals(crud);
		
		if(removeRowCommandable!=null)
			removeRowCommandable.getCommand().setConfirm(inplaceEdit);
		
		logTrace("Table build - Identifiable {}", identifiableClass==null?null:identifiableClass.getSimpleName());
		super.build();
		
		if(!Boolean.TRUE.equals(getLazyLoad())){
			DataReadConfiguration configuration = new DataReadConfiguration();
			load(configuration);
		}
		if(Boolean.TRUE.equals(getShowHierarchy())){
			MODEL hierarchyNode = createHierarchyNode();
			hierarchyNode.setLabel(UIManager.getInstance().getLanguageBusiness().findClassLabelText(businessEntityInfos.getClazz()));
			createTree();
			tree.build((Class<DATA>)businessEntityInfos.getClazz(), hierarchyData, (DATA)master);
			
			showOpenCommand = getShowHierarchy();
		}
			
		if(initialData!=null){
			addRows(initialData);
		}
		
		built = Boolean.TRUE;
	}
	
	protected abstract void __createTree__();
	protected void createTree(){
		__createTree__();	
		if(tree!=null){
			tree.setBusinessEntityInfos(businessEntityInfos);
			tree.setUseSpecificRedirectOnNodeSelected(Boolean.FALSE);
			tree.getTreeListeners().add(new AbstractTree.Listener.Adapter.Default<NODE,MODEL>(){
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
		return rowDataClass==null?Boolean.FALSE:AbstractDataTreeNode.class.isAssignableFrom(businessEntityInfos.getClazz());//TODO should be businessEntityInfos.getClazz()
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
		return Boolean.FALSE;
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
			//System.out.println("AbstractTable.addRowOfRoot()");
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
		if(list==null){
			 
		}else{
			Integer index = list.indexOf(identifiable);
			if(index>-1)
				return list.get(index);
			for(DATA data : list){
				//System.out.println("AbstractTable.getReferenceFromHierarchy() : "+((AbstractDataTreeNode)data).getChildren());
				DATA c = getReferenceFromHierarchy(identifiable, (List<DATA>) ((AbstractDataTreeNode)data).getChildren() );
				if(c!=null)
					return c;
			}
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
				DATA d = (DATA) AbstractFormModel.instance(rowDataClass, (AbstractIdentifiable) newInstance(businessEntityInfos.getClazz()));	
				if(isDataTreeType())
					if(master==null)
						;
					else{
						((AbstractDataTreeNode)((AbstractFormModel<?>)d).getIdentifiable()).setParent((AbstractDataTreeNode)master);
					}
				editing.add(d);
				addRow(d);
				__justAdded__ =  Boolean.TRUE;
			}else{
				crudOnePage(addRowCommandable.getParameters());
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
				if(Boolean.TRUE.equals(persistOnRemoveRow)){
					UIManager.getInstance().getGenericBusiness().delete(identifiable);
				}
				deleteRowAt(row.getIndex().intValue());
			}else{
				//System.out.println("AbstractTable.serve() : DEL");
				//crudOnePage(row.getData(),Crud.DELETE);//This has been done as update
			}
			
		}else if(command==updateRowCommandable.getCommand()){
			//System.out.println("AbstractTable.serve() : CrudOne");
			//Row<DATA> row = (Row<DATA>) parameter;
			//crudOnePage(row.getData(),Crud.UPDATE);
		}else if(command==exportToPdfCommandable.getCommand()){
			exportDataTableToPdfPage();
		}else if(command==exportToXlsCommandable.getCommand()){
			exportDataTableToXlsPage();
		}else if(command==printCommandable.getCommand()){
			reportCommandable(printCommandable, UniformResourceLocatorParameter.PDF);
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

	protected abstract void crudOnePage(Collection<Parameter> parameters);
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
	
	public void setColumnFooter(String fieldName,String value){
		Column column = getColumn(fieldName);
		if(column!=null)
			column.setFooter(value);
	}
	public void setColumnFooter(String fieldName,BigDecimal value){
		Column column = getColumn(fieldName);
		if(column!=null)
			column.setFooter(UIManager.getInstance().getNumberBusiness().format(value));
	}
	
	/**/
	
	private void __fields__(List<Field> fields,Class<?> aClass,Collection<Class<? extends Annotation>> annotations){
		List<Field> candidates = new ArrayList<>(commonUtils.getAllFields(aClass, annotations));
		
		new FieldSorter(candidates,aClass).sort();
		
		for(Field field : candidates){
			if(field.getAnnotation(Input.class)!=null && AbstractFormOneData.isInput(field, getUserSession())){
				fields.add(field);
			} else if(field.getAnnotation(IncludeInputs.class)!=null){
				__fields__(fields,field.getType(),annotations);
			}
		}
	}
	
	public Collection<DATA> getInitialData(){
		if(initialData==null)
			initialData = new ArrayList<>();
		return initialData;
	}
	
}
