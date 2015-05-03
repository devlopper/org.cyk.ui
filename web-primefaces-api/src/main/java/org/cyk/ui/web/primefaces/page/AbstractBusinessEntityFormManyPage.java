package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.UIProvider;
import org.cyk.ui.api.command.CommandAdapter;
import org.cyk.ui.api.command.CommandListener;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UICommandable.IconType;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.api.model.table.Cell;
import org.cyk.ui.api.model.table.Column;
import org.cyk.ui.api.model.table.Row;
import org.cyk.ui.web.api.WebNavigationManager;
import org.cyk.ui.web.primefaces.Table;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.model.table.TableAdapter;

@Getter
@Setter
public abstract class AbstractBusinessEntityFormManyPage<ENTITY extends AbstractIdentifiable> extends AbstractBusinessEntityPrimefacesPage<ENTITY> 
	implements CommandListener, Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	protected Table<Object> table;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void initialisation() { 
		super.initialisation();
		Class<? extends AbstractFormModel<?>> aFormClass = __formModelClass__();
		if(aFormClass==null)
			aFormClass = UIManager.DEFAULT_MANY_FORM_MODEL_MAP.get(businessEntityInfos.getClazz());
		table = (Table<Object>) createTable(businessEntityInfos.getClazz(),identifiableConfiguration,(Class<AbstractFormModel<?>>) aFormClass);
		table.getTableListeners().add(new TableAdapter<Row<Object>, Column, Object, String, Cell, String>(){
			@Override
			public Boolean ignore(Field field) {
				Input input = field.getAnnotation(Input.class);
				IncludeInputs includeInputs = field.getAnnotation(IncludeInputs.class);
				return input == null && includeInputs==null;
			}
			@Override
			public void rowAdded(Row<Object> row) {
				super.rowAdded(row);
				AbstractBusinessEntityFormManyPage.this.rowAdded(row);
			}
			@Override
			public void columnAdded(Column column) {
				super.columnAdded(column);
				AbstractBusinessEntityFormManyPage.this.columnAdded(column);
			}
			@Override
			public void cellAdded(Row<Object> row, Column column, Cell cell) {
				super.cellAdded(row, column, cell);
				AbstractBusinessEntityFormManyPage.this.cellAdded(row,column,cell);
			}
		});
		table.getAddRowCommandable().getCommand().getCommandListeners().add(this);
		table.getApplyRowEditCommandable().getCommand().getCommandListeners().add(this);
		table.addColumnFromDataClass();
		table.setMaster(identifiable);
		table.getCrudOneRowCommandable().getCommand().getCommandListeners().add(new CommandAdapter(){
			private static final long serialVersionUID = 2679004450545381808L;
			@Override
			public void serve(UICommand command, Object parameter) {
				Object data = ((Row<Object>)parameter).getData();
				AbstractIdentifiable identifiable = (AbstractIdentifiable) (data instanceof AbstractIdentifiable ? data:((AbstractFormModel<?>)data).getIdentifiable());
				WebNavigationManager.getInstance().redirectToDynamicCrudOne(identifiable,Crud.UPDATE);
			}
		});
		contentTitle = text("page.crud.many")+" "+contentTitle;
		title = contentTitle;
		
		paginatorTemplate();
	}
	
	protected void paginatorTemplate(){
		table.getDataTable().setPaginatorTemplate("{CurrentPageReport} {FirstPageLink} {PreviousPageLink} {PageLinks} {NextPageLink} {LastPageLink}");
		table.getDataTable().setCurrentPageReportTemplate("{totalRecords} "+UIManager.getInstance().text("datatable.results"));
	}
	
	protected Collection<Object> datas(Collection<AbstractIdentifiable> records){
		Collection<Object> collection = new ArrayList<>();
		if(records!=null){
			if(AbstractIdentifiable.class.isAssignableFrom(table.getRowDataClass()))
				for(AbstractIdentifiable identifiable : records)	
					collection.add(identifiable);
			else
				for(AbstractIdentifiable identifiable : records)	
					collection.add(AbstractFormModel.instance(table.getRowDataClass(), identifiable));
		}
		return collection;
	}
	
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
