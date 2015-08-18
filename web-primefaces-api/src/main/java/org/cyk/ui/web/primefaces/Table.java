package org.cyk.ui.web.primefaces;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.command.CommandAdapter;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.api.model.table.AbstractTable;
import org.cyk.ui.api.model.table.Cell;
import org.cyk.ui.api.model.table.Row;
import org.cyk.ui.web.api.JavaScriptHelper;
import org.cyk.ui.web.api.WebHierarchyNode;
import org.cyk.ui.web.api.WebManager;
import org.cyk.ui.web.api.WebNavigationManager;
import org.omnifaces.util.Ajax;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.primefaces.model.TreeNode;
import org.primefaces.model.menu.MenuModel;

public class Table<DATA> extends AbstractTable<DATA,TreeNode,WebHierarchyNode> implements Serializable {

	private static final long serialVersionUID = -2915809915934469649L;
	
	private static final String HIGHLIGHT_MATCH_FORMAT = "<span class=\"cyk-ui-table-search-match\">%s</span>";
	
	@Getter private MenuModel menuModel;
	@Getter private Commandable primefacesAddRowCommand,primefacesDeleteRowCommand,primefacesOpenRowCommand;
	@Getter private String updateStyleClass;
	private JavaScriptHelper javaScriptHelper = JavaScriptHelper.getInstance();
	
	protected TypedBusiness<?> business;
	protected LazyDataModel<Row<DATA>> dataModel;
	@Getter @Setter protected Boolean fetch = Boolean.TRUE;
	@Getter protected Long resultsCount=0l;
	@Getter protected DataTable dataTable;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		exportToPdfCommandable.getParameters().add(new UICommandable.Parameter(WebManager.getInstance().getRequestParameterOutcome(), 
				WebNavigationManager.getInstance().getOutcomeReportTable()));
		
		exportToXlsCommandable.getParameters().add(new UICommandable.Parameter(WebManager.getInstance().getRequestParameterOutcome(), 
				WebNavigationManager.getInstance().getOutcomeReportTable()));
	}
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		dataTable = new DataTable();
		updateStyleClass = RandomStringUtils.randomAlphabetic(2)+""+System.currentTimeMillis();
		Commandable commandable = (Commandable) addRowCommandable;
		commandable.getButton().setUpdate("@(."+updateStyleClass+")");
		//if(UsedFor.ENTITY_INPUT.equals(usedFor))
			commandable.getButton().setOncomplete("clickEditButtonLastRow('"+updateStyleClass+"')");
		//else
		//	commandable.getButton().setOncomplete("clickEditButtonAllRow('"+updateStyleClass+"')");
		
		commandable = (Commandable) searchCommandable;
		commandable.getButton().setType("button");
		commandable.getButton().setOnclick("PF('dataTableWidgetVar').filter();");
		
		commandable.getButton().setWidgetVar("searchCommandWidgetVar");
		
	}
	
	@Override
	public void build() {
		super.build();
		((Commandable)exportCommandable).setMenu(CommandBuilder.getInstance().menuModel(exportMenu, Table.class, ""));
		
		if(Boolean.TRUE.equals(lazyLoad)){
			openRowCommandable.getCommand().getCommandListeners().add(new CommandAdapter(){
				private static final long serialVersionUID = 1120566504648934547L;
				@SuppressWarnings("unchecked")
				@Override
				public void serve(UICommand command, Object parameter) {
					AbstractIdentifiable identifiable;
					if(((Row<?>)parameter).getData() instanceof AbstractFormModel<?>)
						identifiable = ((AbstractFormModel<?>)((Row<?>)parameter).getData()).getIdentifiable();
					else
						identifiable = ((Row<AbstractIdentifiable>)parameter).getData();
					
					WebNavigationManager.getInstance().redirectTo(businessEntityInfos.getUiConsultViewId(), 
							new Object[]{WebManager.getInstance().getRequestParameterClass(),UIManager.getInstance().keyFromClass(businessEntityInfos)
						,WebManager.getInstance().getRequestParameterIdentifiable(),identifiable.getIdentifier().toString(),
						UIManager.getInstance().getCrudParameter(),businessEntityInfos.getUiEditViewId().equals(businessEntityInfos.getUiConsultViewId())
						?UIManager.getInstance().getCrudReadParameter():null});
				}
			});
		}
		
		dataTable.setEditable(getEditable());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void open(DATA data) {
		WebNavigationManager.getInstance().redirectToDynamicCrudMany((Class<AbstractIdentifiable>) rowDataClass,(AbstractIdentifiable) data);
	}
	
	@Override
	protected void __createTree__() {
		tree = new Tree();
	}
	
	@Override
	protected WebHierarchyNode createHierarchyNode() {
		return new WebHierarchyNode(null);
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

	@Override
	protected void crudOnePage(DATA data,Crud crud) {
		AbstractIdentifiable identifiable = (AbstractIdentifiable) (data instanceof AbstractIdentifiable ? data:((AbstractFormModel<?>)data).getIdentifiable());
		WebNavigationManager.getInstance().redirectToDynamicCrudOne(identifiable,crud);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void crudOnePage() {
		if(addRowCommandable.getViewId()==null){
			WebNavigationManager.getInstance().redirectToDynamicCrudOne((Class<AbstractIdentifiable>) (identifiableConfiguration==null?identifiableClass/*rowDataClass*/:identifiableConfiguration.getIdentifiableClass()));
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
		printCommandable.getParameters().add(new UICommandable.Parameter(WebManager.getInstance().getRequestParameterOutcome(),WebNavigationManager.getInstance().getOutcomeReportTable()));
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
		if(Boolean.TRUE.equals(lazyLoad))
			return getDataModel();
		return rows;
	}
	
	public LazyDataModel<Row<DATA>> getDataModel() {
		if(dataModel==null){
			dataModel = new LazyDataModel<Row<DATA>>() {
				private static final long serialVersionUID = -5029807106028522292L;
				@Override
				public List<Row<DATA>> load(int first, int pageSize,String sortField, SortOrder sortOrder,Map<String, Object> filters) {
					String filter = (String)filters.get("globalFilter");
					if(Boolean.TRUE.equals(fetch))
						fetchData(first, pageSize, sortField,SortOrder.ASCENDING.equals(sortOrder), filters,filter);
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
					resultsCount =  count(filter);
					return rows;
				}
				
				@Override
				public int getRowCount() {
					return resultsCount==null?0:resultsCount.intValue();
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
	
}
