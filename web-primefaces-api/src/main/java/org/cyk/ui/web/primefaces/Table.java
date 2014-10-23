package org.cyk.ui.web.primefaces;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.Getter;

import org.apache.commons.lang3.RandomStringUtils;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.api.model.table.AbstractTable;
import org.cyk.ui.api.model.table.Row;
import org.cyk.ui.web.api.WebHierarchyNode;
import org.cyk.ui.web.api.WebNavigationManager;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.primefaces.model.TreeNode;
import org.primefaces.model.menu.MenuModel;

public class Table<DATA> extends AbstractTable<DATA,TreeNode,WebHierarchyNode> implements Serializable {

	private static final long serialVersionUID = -2915809915934469649L;
	
	@Getter private MenuModel menuModel;
	@Getter private Commandable primefacesAddRowCommand,primefacesDeleteRowCommand,primefacesOpenRowCommand;
	@Getter private String updateStyleClass;
	
	protected TypedBusiness<?> business;
	protected LazyDataModel<Row<DATA>> dataModel;
	@Getter protected Long resultsCount=0l;
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		updateStyleClass = RandomStringUtils.randomAlphabetic(2)+""+System.currentTimeMillis();
		Commandable commandable = (Commandable) addRowCommandable;
		commandable.getButton().setUpdate("@(."+updateStyleClass+")");
		commandable.getButton().setOncomplete("clickEditButtonLastRow('"+updateStyleClass+"');");
		
		commandable = (Commandable) searchCommandable;
		commandable.getButton().setType("button");
		commandable.getButton().setOnclick("PF('dataTableWidgetVar').filter();");
		
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
		WebNavigationManager.getInstance().redirectToDynamicCrudOne((Class<AbstractIdentifiable>) (identifiableConfiguration==null?rowDataClass:identifiableConfiguration.getIdentifiableClass()));
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
					//business.getDataReadConfig().setFirstResultIndex((long) first);
					fetchData(first, pageSize, sortField,SortOrder.ASCENDING.equals(sortOrder), filters);
					//List<Object> list = (List<Object>) (collection instanceof List?collection:new ArrayList<>(collection));
					resultsCount =  count((String) filters.get("globalFilter")); //10;//__count__(first, pageSize, sortField, sortOrder, filters);
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
	
}
