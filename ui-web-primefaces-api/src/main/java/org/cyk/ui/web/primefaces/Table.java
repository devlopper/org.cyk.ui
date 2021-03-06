package org.cyk.ui.web.primefaces;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.CommonBusinessAction;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.system.root.model.userinterface.style.CascadeStyleSheet;
import org.cyk.ui.api.AbstractUserSession;
import org.cyk.ui.api.Icon;
import org.cyk.ui.api.command.AbstractCommandable.Builder;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UICommandable.Parameter;
import org.cyk.ui.api.model.table.AbstractTable;
import org.cyk.ui.api.model.table.Cell;
import org.cyk.ui.api.model.table.Column;
import org.cyk.ui.api.model.table.Row;
import org.cyk.ui.web.api.JavaScriptHelper;
import org.cyk.ui.web.api.WebManager;
import org.cyk.ui.web.api.WebNavigationManager;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.LogMessage;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.computation.DataReadConfiguration;
import org.cyk.utility.common.model.table.Dimension.DimensionType;
import org.omnifaces.util.Ajax;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.data.FilterEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.primefaces.model.TreeNode;
import org.primefaces.model.menu.MenuModel;

public class Table<DATA> extends AbstractTable<DATA,TreeNode,HierarchyNode> implements Serializable {

	private static final long serialVersionUID = -2915809915934469649L;
	
	private static final String HIGHLIGHT_MATCH_FORMAT = "<span class=\"cyk-ui-table-search-match\">%s</span>";
	
	public static final Map<DimensionType, CascadeStyleSheet> CASCADE_STYLE_SHEET_MAP = new HashMap<>();
	public static final String f = "";
	
	static{
		CascadeStyleSheet cascadeStyleSheet = new CascadeStyleSheet();
		cascadeStyleSheet.addClasses(PrimefacesManager.CSS_CLASS_CYK_DATATABLE_SUMMARY_ROW,PrimefacesManager.CSS_CLASS_DATATABLE_ROW_SUMMARY
				,PrimefacesManager.CSS_CLASS_WIDGET_HEADER);	
		CASCADE_STYLE_SHEET_MAP.put(DimensionType.SUMMARY, cascadeStyleSheet);
		
		cascadeStyleSheet = new CascadeStyleSheet();
		cascadeStyleSheet.addClasses(PrimefacesManager.CSS_CLASS_CYK_DATATABLE_SUMMARY_ROW,PrimefacesManager.CSS_CLASS_DATATABLE_ROW_SUMMARY,
				PrimefacesManager.CSS_CLASS_WIDGET_HEADER);	
		CASCADE_STYLE_SHEET_MAP.put(DimensionType.HEADER, cascadeStyleSheet);
	}
	
	@Getter private MenuModel menuModel;
	@Getter private Commandable primefacesAddRowCommand,primefacesDeleteRowCommand,primefacesOpenRowCommand;
	@Getter @Setter private String updateStyleClass;
	private JavaScriptHelper javaScriptHelper = JavaScriptHelper.getInstance();
	
	protected TypedBusiness<?> business;
	protected LazyDataModel<Row<DATA>> dataModel;
	@Getter @Setter protected Boolean fetch = Boolean.TRUE;
	@Getter protected Long resultsCount=0l,maximumResultCount=10l;
	@Getter @Setter protected Integer rowCount;
	@Getter protected DataTable dataTable = new DataTable();
	@Getter private CascadeStyleSheet cascadeStyleSheet = new CascadeStyleSheet();
	@Getter @Setter private Boolean useDataModel;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		exportToPdfCommandable.getParameters().add(new UICommandable.Parameter(UniformResourceLocatorParameter.VIEW_IDENTIFIER, 
				WebNavigationManager.getInstance().getOutcomeReportTable()));
		
		exportToXlsCommandable.getParameters().add(new UICommandable.Parameter(UniformResourceLocatorParameter.VIEW_IDENTIFIER, 
				WebNavigationManager.getInstance().getOutcomeReportTable()));
	}
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		if(StringUtils.isBlank(updateStyleClass))
			updateStyleClass = RandomStringUtils.randomAlphabetic(2)+Constant.EMPTY_STRING+System.currentTimeMillis();
		Commandable commandable = (Commandable) addRowCommandable;
		commandable.getButton().setUpdate("@(."+updateStyleClass+")");
		//if(UsedFor.ENTITY_INPUT.equals(usedFor))
			commandable.getButton().setOncomplete("clickEditButtonLastRow('"+updateStyleClass+"')");
		//else
		//	commandable.getButton().setOncomplete("clickEditButtonAllRow('"+updateStyleClass+"')");
		
		commandable = (Commandable) searchCommandable;
			
		if(Boolean.TRUE.equals(isDataTreeType()) && StringUtils.isBlank(WebManager.getInstance().getRequestParameter(UniformResourceLocatorParameter.FILTER)) ){
			/*UrlStringBuilder urlStringBuilder = new UrlStringBuilder();
			urlStringBuilder.getQueryStringBuilder().addParameter(UniformResourceLocatorParameter.LAZY, UniformResourceLocatorParameter.LAZY_TRUE)
			.addParameter("clazz", UIManager.getInstance().keyFromClass(identifiableClass));
			urlStringBuilder.setHost("localhost");
			urlStringBuilder.setPathIdentifier(IdentifierProvider.Adapter.getViewOf(identifiableClass, CommonBusinessAction.LIST, Boolean.TRUE));
			urlStringBuilder.setPort(8080);
			urlStringBuilder.addPathTokenReplacement(".xhtml", ".jsf");
			
			String url = urlStringBuilder.build()+"&"+UniformResourceLocatorParameter.FILTER+"=";
			*/
			//commandable.getButton().setOnclick("window.location='"+url+"'+document.getElementsByClassName('cyk_inputtext_globalfilter')[0].value;");
		}else{
			commandable.getButton().setType("button");
			commandable.getButton().setOnclick("PF('dataTableWidgetVar').filter();");
		}
		//commandable.getButton().setOnclick("window.location='"+url+"'+document.getElementsByClassName('cyk_inputtext_globalfilter')[0].value;");
		//commandable.getButton().setOnclick("PF('dataTableWidgetVar').filter();");
		
		commandable.getButton().setWidgetVar("searchCommandWidgetVar");
		
	}
	
	@Override
	public void build() {
		super.build();
		if(Boolean.FALSE.equals(rendered))
			return;
		if(exportCommandable!=null)
			((Commandable)exportCommandable).setMenu(CommandBuilder.getInstance().menuModel(exportMenu, Table.class, ""));
		
		if(Boolean.TRUE.equals(lazyLoad)){
			
		}
		
		setNumberOfNullUiIndex(null);
		
		if(Boolean.TRUE.equals(columns.size()>numberOfColumnsHorizontalHeader))
			getCascadeStyleSheet().addClasses(PrimefacesManager.CSS_CLASS_TABLE_VERTICAL_HEADER);
			
		dataTable.setEditable(getEditable());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void open(DATA data) {
		//WebNavigationManager.getInstance().redirectTo(AbstractCommandable.Builder.createList(data));
		//WebNavigationManager.getInstance().redirectToDynamicCrudMany((Class<AbstractIdentifiable>) businessEntityInfos.getClazz(),(AbstractIdentifiable) data);
		if(Boolean.TRUE.equals(inplaceEdit))
			WebNavigationManager.getInstance().redirectToCrudMany((Class<AbstractIdentifiable>) businessEntityInfos.getClazz(),(AbstractIdentifiable) data);
		else
			WebNavigationManager.getInstance().redirectToCrudOne((AbstractIdentifiable) data,Crud.READ,CommonBusinessAction.CONSULT);
	}
	
	@Override
	protected void __createTree__() {
		tree = new Tree();
	}
	
	@Override
	protected HierarchyNode createHierarchyNode() {
		return new HierarchyNode(null);
	}
	
	public void onRowEditInit(RowEditEvent rowEditEvent){
		initRowEditCommandable.getCommand().execute(rowEditEvent.getObject());
	}
	
	public void onRowEdit(RowEditEvent rowEditEvent){
		applyRowEditCommandable.getCommand().execute(rowEditEvent.getObject());
	}
	
	public void onRowEditCancel(RowEditEvent rowEditEvent){
		cancelRowEditCommandable.getCommand().execute(rowEditEvent.getObject());
	}
	
	public void listenFilter(FilterEvent event){
		
	}

	/*@Override
	public void crudOnePage(DATA data,Crud crud) {
		AbstractIdentifiable identifiable = (AbstractIdentifiable) (data instanceof AbstractIdentifiable ? data
				:( data instanceof AbstractFormModel<?> ? ((AbstractFormModel<?>)data).getIdentifiable() : ((AbstractOutputDetails<?>)data).getMaster() ) );
		WebNavigationManager.getInstance().redirectToDynamicCrudOne(identifiable,crud);
	}*/
	
	public UICommandable redirectToSelectBeforeAdd(Boolean one,Class<? extends AbstractIdentifiable> aClass,String actionIdentifier,Icon icon){
		UICommandable commandable = null;
		if(Boolean.TRUE.equals(one))
			commandable = Builder.createSelectOne(aClass,actionIdentifier ,icon).setLabel(addRowCommandable.getLabel());
		else if(Boolean.FALSE.equals(one))
			commandable = Builder.createSelectMany(aClass,actionIdentifier ,icon).setLabel(addRowCommandable.getLabel());
		return commandable;
	}
	
	@Override
	public void crudOnePage(Collection<Parameter> parameters) {
		if(addRowCommandable.getViewId()==null){
			WebNavigationManager.getInstance().redirectToDynamicCrudOne(identifiableClass,parameters);
		}else{
			WebNavigationManager.getInstance().redirectTo(addRowCommandable);
		}
			
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void exportDataTableToPdfPage() {
		WebNavigationManager.getInstance().redirectToExportDataTableToPdf((Class<AbstractIdentifiable>) identifiableClass);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void exportDataTableToXlsPage() {
		WebNavigationManager.getInstance().redirectToExportDataTableToXls((Class<AbstractIdentifiable>) identifiableClass);
	}
	
	@Override
	protected void printDataPage() {
		printCommandable.getParameters().add(new UICommandable.Parameter(UniformResourceLocatorParameter.VIEW_IDENTIFIER,WebNavigationManager.getInstance().getOutcomeReportTable()));
		WebNavigationManager.getInstance().redirectToPrintData(printCommandable.getParameters());
	}
	
	/**/
	
	/*@Override
	public boolean addCell(TableRow<DATA> row, TableColumn column, DefaultCell cell) {
		Boolean r = super.addCell(row, column, cell);
		if(((TableCell)cell).getInputComponent() instanceof WebUIInputSelectOne<?, ?>)
			((WebUIInputSelectOne<?, ?>)((TableCell)cell).getInputComponent()).getCascadeStyleSheet().setClazz("cyk-ui-table-dynamic-selectonemenu");
		//else if(((TableCell)cell).getInputComponent() instanceof WebUIInputText)
		//	((WebUIInputText)((TableCell)cell).getInputComponent()).getCascadeStyleSheet().setClazz("cyk-ui-table-dynamic-selectonemenu");
		return r;
	}*/
	
	/**/  
	
	public Object getValue(){
		if(Boolean.TRUE.equals(useDataModel) || Boolean.TRUE.equals(lazyLoad))
			return getDataModel();
		return rows;
	}
		
	public LazyDataModel<Row<DATA>> getDataModel() {
		if(dataModel==null){
			dataModel = new LazyDataModel<Row<DATA>>() {
				private static final long serialVersionUID = -5029807106028522292L;
				@Override
				public List<Row<DATA>> load(int first, int pageSize,String sortField, SortOrder sortOrder,Map<String, Object> filters) {
					LogMessage.Builder logMessageBuilder = new LogMessage.Builder("Load","data");
					logMessageBuilder.addParameters("filters",filters);
					String filter = filters == null ? null : (String)filters.get("globalFilter");
					if(StringUtils.isBlank(filter))
						filter = WebManager.getInstance().getRequestParameter(UniformResourceLocatorParameter.FILTER);
					
					logMessageBuilder.addParameters("global filter",filter);
					
					DataReadConfiguration configuration = new DataReadConfiguration((long)first,maximumResultCount, sortField, SortOrder.ASCENDING.equals(sortOrder), filters, filter);
					
					if(Boolean.TRUE.equals(fetch)){
						Table.this.load(configuration);
					}
					fetch = Boolean.TRUE;
					if(StringUtils.isNotBlank(filter)){
						filter = filter.toLowerCase();
						for(Row<DATA> row : rows)
							for(Cell cell : row.getCells()){
								String temp = StringUtils.lowerCase(cell.getValue());
								Integer index = StringUtils.indexOf(temp, filter); 
								if(index==-1){
								 
								}else if(index>=0){
									cell.setValue(StringUtils.substring(cell.getValue(), 0,index)
											+String.format(HIGHLIGHT_MATCH_FORMAT, StringUtils.substring(cell.getValue(), index, index+filter.length()))
													+StringUtils.substring(cell.getValue(), index+filter.length()));
								}
							
							}
					}
					resultsCount =  count(configuration);
					logMessageBuilder.addParameters("result count",resultsCount);
					logTrace(logMessageBuilder);
					return rows;
				}
				
				@Override
				public int getRowCount() {
					return rowCount == null ? (resultsCount==null?0:resultsCount.intValue()) : rowCount;
				}
			};
		}
		return dataModel;
	}
	
	public String getFormatJavaScript() {
		String script = "";
		if(!Boolean.TRUE.equals(getShowHeader()))
			script = javaScriptHelper.add(script,javaScriptHelper.hide("."+getHeaderStyleClass()));
		if(!Boolean.TRUE.equals(getShowFooter()))
			script = javaScriptHelper.add(script,javaScriptHelper.hide("."+getFooterStyleClass()));
		return script;
	}
	
	public String getHeaderStyleClass(){
		return updateStyleClass+" > .ui-datatable-header";
	}
	
	public String getFooterStyleClass(){
		return updateStyleClass+" > .ui-datatable-tablewrapper > table > tfoot";
	}
	
	@Override
	public void serve(UICommand command, Object parameter) {
		super.serve(command, parameter);
		if(command==addRowCommandable.getCommand()){
			if(Boolean.TRUE.equals(inplaceEdit))
				Ajax.oncomplete(getFormatJavaScript());
		}else if(command==removeRowCommandable.getCommand()){
			if(Boolean.TRUE.equals(inplaceEdit))
				Ajax.oncomplete(getFormatJavaScript());
		}
	}
	
	/**/
	
	public static class ColumnAdapter extends org.cyk.ui.api.model.table.ColumnAdapter implements Serializable {

		private static final long serialVersionUID = 5607226813206637755L;

		@Override
		public Boolean isColumn(Field field) {
			Input input = field.getAnnotation(Input.class);
			IncludeInputs includeInputs = field.getAnnotation(IncludeInputs.class);
			return input != null || includeInputs!=null;
		}
		@Override
		public void added(Column column) {
			super.added(column);
			column.getCascadeStyleSheet().addClasses(
					column.getField().getDeclaringClass().getSimpleName().toLowerCase()+
					"-"+column.getField().getName().toLowerCase());
		}
		
	}
	
	public static class RowAdapter<T> extends org.cyk.ui.api.model.table.RowAdapter<T> implements Serializable {

		private static final long serialVersionUID = 5607226813206637755L;

		public RowAdapter(AbstractUserSession<?, ?> userSession) {
			super(userSession);
		}
		
		@Override
		public void created(Row<T> row) {
			super.created(row);
			if(row.getData() instanceof AbstractOutputDetails<?>){
				row.setType(((AbstractOutputDetails<?>)row.getData()).getMaster() == null ? DimensionType.SUMMARY : DimensionType.DETAIL);
			}
			if(row.getCascadeStyleSheet()==null)
				row.setCascadeStyleSheet(CASCADE_STYLE_SHEET_MAP.get(row.getType()));
			else{
				CascadeStyleSheet cascadeStyleSheet = CASCADE_STYLE_SHEET_MAP.get(row.getType());
				if(cascadeStyleSheet!=null){
					row.getCascadeStyleSheet().addClass(cascadeStyleSheet.getClazz());
					row.getCascadeStyleSheet().addInline(cascadeStyleSheet.getInline());
				}
			}
			row.setCountable(row.getIsDetail());
		}
		
	}
	
	/**/
	
	public static interface Listener<DATA> extends AbstractTable.Listener<DATA, TreeNode, HierarchyNode> {
		
		public static class Adapter<DATA> extends AbstractTable.Listener.Adapter.Default<DATA, TreeNode, HierarchyNode> implements Listener<DATA>,Serializable {

			private static final long serialVersionUID = 1L;

			/**/
			
			public static class Default<DATA> extends Listener.Adapter<DATA> implements Serializable {
				private static final long serialVersionUID = 1L;
				
				/**/
				
			}
		}
	}

}
