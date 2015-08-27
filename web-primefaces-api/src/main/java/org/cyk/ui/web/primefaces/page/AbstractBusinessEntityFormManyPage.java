package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.CascadeStyleSheet;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.UIProvider;
import org.cyk.ui.api.command.CommandAdapter;
import org.cyk.ui.api.command.CommandListener;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UICommandable.IconType;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.api.model.table.Cell;
import org.cyk.ui.api.model.table.CellAdapter;
import org.cyk.ui.api.model.table.Column;
import org.cyk.ui.api.model.table.ColumnAdapter;
import org.cyk.ui.api.model.table.Row;
import org.cyk.ui.api.model.table.RowAdapter;
import org.cyk.ui.web.api.WebNavigationManager;
import org.cyk.ui.web.primefaces.PrimefacesManager;
import org.cyk.ui.web.primefaces.Table;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.model.table.Dimension.DimensionType;

@Getter @Setter //TODO should extends Row , Column , Cell , Table Listener to avoid creating specific methods
public abstract class AbstractBusinessEntityFormManyPage<ENTITY extends AbstractIdentifiable> extends AbstractBusinessEntityPrimefacesPage<ENTITY> 
	implements CommandListener, Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	protected Table<Object> table;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void initialisation() { 
		super.initialisation();
		for(BusinessEntityFormManyPageListener<?> listener : getListeners())
			listener.initialisationStarted(this); 
		
		/*Class<?> aFormClass = __formModelClass__();
		if(aFormClass==null){
			// TODO Form can be provided by URL parameter registered in Form Map
			aFormClass = UIManager.FORM_MODEL_MAP.get(webManager.getRequestParameterFormModel());
		}*/
		table = (Table<Object>) createTable(businessEntityInfos.getClazz(),identifiableConfiguration,(Class<AbstractFormModel<?>>) formModelClass);
		
		table.getRowListeners().add(new RowAdapter<Object>(){
			@Override
			public void created(Row<Object> row) {
				row.setType(getRowType(row));
				row.setCountable(row.getIsDetail());
				AbstractBusinessEntityFormManyPage.this.rowCreated(row);
			}
			@Override
			public void added(Row<Object> row) {
				super.added(row);
				CascadeStyleSheet css = getRowCss(row.getType());
				if(css!=null){
					row.getCascadeStyleSheet().addClass(css.getClazz());
					row.getCascadeStyleSheet().addInline(css.getInline());
				}
				AbstractBusinessEntityFormManyPage.this.rowAdded(row);
			}
		});
		table.getColumnListeners().add(new ColumnAdapter(){
			@Override
			public Boolean isColumn(Field field) {
				Input input = field.getAnnotation(Input.class);
				IncludeInputs includeInputs = field.getAnnotation(IncludeInputs.class);
				return input != null || includeInputs!=null;
			}
			@Override
			public void added(Column column) {
				super.added(column);
				AbstractBusinessEntityFormManyPage.this.columnAdded(column);
			}
		});
		table.getCellListeners().add(new CellAdapter<Object>(){
			@Override
			public void added(Row<Object> row, Column column, Cell cell) {
				super.added(row, column, cell);
				AbstractBusinessEntityFormManyPage.this.cellAdded(row,column,cell);
			}
		});
		
		table.getAddRowCommandable().getCommand().getCommandListeners().add(this);
		table.getApplyRowEditCommandable().getCommand().getCommandListeners().add(this);
		
		table.setMaster(identifiable);
		table.getCrudOneRowCommandable().getCommand().getCommandListeners().add(new CommandAdapter(){
			private static final long serialVersionUID = 2679004450545381808L;
			@Override
			public void serve(UICommand command, Object parameter) {
				Object data = ((Row<Object>)parameter).getData();
				redirectToCrudOne(Crud.UPDATE,data);
			}
		});
		contentTitle = text("page.crud.many")+" "+contentTitle;
		title = contentTitle;
		
		paginatorTemplate();
		
		onDocumentLoadJavaScript = "$('."+PrimefacesManager.CSS_CLASS_CYK_DATATABLE_SUMMARY_ROW+"').removeClass('ui-datatable-even ui-datatable-odd');";
		/*
		onDocumentLoadJavaScript = onDocumentLoadJavaScript + "$('.ui-table-columntoggle > tbody > tr').eq(2)."
				+ "after('<tr data-ri=\"2\" class=\"ui-widget-content ui-datatable-even\" role=\"row\" > <td>1</td> <td>zougou</td> <td>3</td> </tr>');";
		*/
		
		for(BusinessEntityFormManyPageListener<?> listener : getListeners())
			listener.initialisationEnded(this); 
	}
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		for(BusinessEntityFormManyPageListener<?> listener : getListeners())
			listener.afterInitialisationStarted(this); 
		
		// Your code here
		
		for(BusinessEntityFormManyPageListener<?> listener : getListeners())
			listener.afterInitialisationEnded(this); 
	}
	
	protected void redirectToCrudOne(Crud crud,Object data){
		BusinessEntityFormManyPageListener<?> redirectListener = null;
		for(BusinessEntityFormManyPageListener<?> listener : getListeners())
			if(Boolean.TRUE.equals(listener.canRedirect(crud, data)))
				redirectListener = listener; 
		if(redirectListener==null){
			AbstractIdentifiable identifiable = (AbstractIdentifiable) (data instanceof AbstractIdentifiable ? data:((AbstractFormModel<?>)data).getIdentifiable());
			WebNavigationManager.getInstance().redirectToDynamicCrudOne(identifiable,crud);
		}else{
			redirectListener.redirect(crud, data);
		}
	}
	
	protected CascadeStyleSheet getRowCss(DimensionType dimensionType){
		return Table.CASCADE_STYLE_SHEET_MAP.get(dimensionType);
	}
	
	protected DimensionType getRowType(Row<Object> row){
		return row.getType()==null?DimensionType.DETAIL:row.getType();
	}
	
	protected void paginatorTemplate(){
		table.getDataTable().setPaginatorTemplate("{CurrentPageReport} {FirstPageLink} {PreviousPageLink} {PageLinks} {NextPageLink} {LastPageLink}");
		table.getDataTable().setCurrentPageReportTemplate("{totalRecords} "+UIManager.getInstance().text("datatable.results"));
	}
	
	protected Collection<Object> datas(Collection<ENTITY> records){
		Collection<Object> collection = new ArrayList<>();
		if(records!=null){
			/*if(AbstractIdentifiable.class.isAssignableFrom(table.getRowDataClass()))
				for(AbstractIdentifiable identifiable : records)	
					collection.add(identifiable);
			else
				for(AbstractIdentifiable identifiable : records)	
					collection.add(AbstractFormModel.instance(table.getRowDataClass(), identifiable));
					*/
			for(AbstractIdentifiable identifiable : records)	
				collection.add(buildRowData(identifiable));
		}
		return collection;
	}
	
	protected Object buildRowData(AbstractIdentifiable entity){
		if(AbstractIdentifiable.class.isAssignableFrom(table.getRowDataClass()))
			return entity;
		else if(AbstractFormModel.class.isAssignableFrom(table.getRowDataClass()))
			return AbstractFormModel.instance(table.getRowDataClass(), entity);
		else
			try {
				//return table.getRowDataClass().getConstructor(entity.getClass()).newInstance(entity);
				return commonUtils.getConstructor(table.getRowDataClass(),new Class<?>[]{entity.getClass()}).newInstance(entity);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
	}
	
	private Collection<BusinessEntityFormManyPageListener<?>> getListeners(){
		return primefacesManager.getBusinessEntityFormManyPageListeners(businessEntityInfos.getClazz());
	}
	
	protected void rowCreated(Row<Object> row){}
	protected void rowAdded(Row<Object> row){}
	protected void columnAdded(Column column){}
	protected void cellAdded(Row<Object> row, Column column, Cell cell){}
	
	@Override
	public void transfer(UICommand command, Object parameter) throws Exception {
		
	}

	@Override
	public Boolean validate(UICommand command, Object parameter) {
		return null;
	}
	
	@Override
	public void serve(UICommand command, Object parameter) {
		if(table.getExportCommandable().getCommand()==command){
			
		}
	}

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
	/*
	@Override
	public Boolean getShowContentMenu() {
		return Boolean.TRUE;
	}
	*/
	@Override
	public Boolean getShowContextualMenu() {
		return Boolean.TRUE.equals(super.getShowContextualMenu()) || Boolean.TRUE.equals(table.getShowHierarchy());
	}
	
	/**/
	
	protected UICommandable createCommandable(String labelId,IconType icon){
		return UIProvider.getInstance().createCommandable(this, labelId, icon, null, null);
	}

}
