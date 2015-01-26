package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import javax.faces.model.SelectItem;

import lombok.Getter;

import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.data.collector.control.Control;
import org.cyk.ui.api.data.collector.control.OutputLabel;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.api.data.collector.form.ControlSet;
import org.cyk.ui.api.data.collector.form.ControlSetListener;
import org.cyk.ui.api.model.table.Cell;
import org.cyk.ui.api.model.table.Column;
import org.cyk.ui.api.model.table.Row;
import org.cyk.ui.web.primefaces.Commandable;
import org.cyk.ui.web.primefaces.data.collector.form.FormOneData;
import org.cyk.utility.common.model.table.TableAdapter;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;

public abstract class AbstractBusinessQueryPage<ENTITY extends AbstractIdentifiable,QUERY,RESULT> extends AbstractBusinessEntityFormManyPage<ENTITY> 
	implements ControlSetListener<ENTITY, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem>,Serializable {

	private static final long serialVersionUID = -5996159828897377343L;

	protected Class<ENTITY> entityClass;
	protected Class<QUERY> queryClass;
	protected Class<RESULT> resultClass;
	
	protected QUERY query;
	//protected Integer pageSize = 10;
	@Getter protected FormOneData<QUERY> form;
	
	public AbstractBusinessQueryPage() {
		/*
		System.out.println(getClass());
		System.out.println(getClass().getGenericSuperclass());
		entityClass = (Class<ENTITY>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		queryClass = (Class<QUERY>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[1];
		resultClass = (Class<RESULT>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[2];
		*/
		
		entityClass = __entityClass__();
		queryClass = __queryClass__(); 
		resultClass = __resultClass__();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void initialisation() {
		super.initialisation();
		table.setGlobalFilter(Boolean.FALSE);
		table.setShowToolBar(Boolean.FALSE);
		form = (FormOneData<QUERY>) createFormOneData(query = newInstance(queryClass), Crud.CREATE);
		form.setControlSetListener((ControlSetListener<QUERY, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem>) this);
		form.setShowCommands(Boolean.FALSE);
		
		form.getSubmitCommandable().setLabel(text("command.search"));
		form.getSubmitCommandable().getCommand().setMessageManager(messageManager);
		form.getSubmitCommandable().getCommand().getCommandListeners().add(this);
		((Commandable)form.getSubmitCommandable()).getButton().setUpdate(":form:"+componentId()+":resultsOutputPanel");
		
		table.getTableListeners().add(new TableAdapter<Row<Object>, Column, Object, String, Cell, String>(){
			@Override
			public Collection<Object> fetchData(Integer first, Integer pageSize, String sortField, Boolean ascendingOrder, Map<String, Object> filters, String globalFilter) {
				if(Boolean.TRUE.equals(form.getSubmitCommandable().getRequested()))
					return datas((Collection<AbstractIdentifiable>) __query__());
				return null;
			}
			
			@Override
			public Long count(String filter) {
				if(Boolean.TRUE.equals(form.getSubmitCommandable().getRequested()))
					return __count__();
				return null;
			}
		});
	}
	
	protected abstract Class<ENTITY> __entityClass__();
	protected abstract Class<QUERY> __queryClass__();
	protected abstract Class<RESULT> __resultClass__();
	
	protected abstract Collection<ENTITY> __query__();
	protected abstract Long __count__();
	
	protected String componentId(){
		return "query";
	}
	
	@Override
	protected BusinessEntityInfos fetchBusinessEntityInfos() {
		return uiManager.businessEntityInfos(entityClass);
	}
	
	@Override
	public void transfer(UICommand command, Object parameter) throws Exception {
		if(form.getSubmitCommandable().getCommand()==command){
			if(parameter!=null){
				if(AbstractFormModel.class.isAssignableFrom(parameter.getClass())){
					((AbstractFormModel<?>)parameter).write();
				}
			}
		}
	}

	@Override
	public Boolean validate(UICommand command, Object parameter) {
		if(form.getSubmitCommandable().getCommand()==command){
			form.getSubmitCommandable().setRequested(Boolean.TRUE);
			return Boolean.TRUE;
		}
		return null;
	}
	
	@Override
	public void serve(UICommand command, Object parameter) {
		if(form.getSubmitCommandable().getCommand()==command){
			table.fetchData(0, null, null, null, null, null);
		}
		
	}

	@Override
	public Object succeed(UICommand command, Object parameter) {
		if(form.getSubmitCommandable().getCommand()==command){
			paginatorTemplate();
		}
		
		return null;
	}

	@Override
	public Object fail(UICommand command, Object parameter, Throwable throwable) {
		return null;
	}

	@Override
	public Boolean notifyAfterServe(UICommand command, AfterServeState state) {
		if(form.getSubmitCommandable().getCommand()==command){
			return !AfterServeState.SUCCEED.equals(state);
		}
		return null;
	}

	@Override
	public String notificationMessageIdAfterServe(UICommand command, Object parameter, AfterServeState state) {
		return null;
	}
	
	@Override
	public Boolean getShowContentMenu() {
		return Boolean.FALSE;
	}
	
	/**/
	
	@Override
	public Boolean canCreateRow(ControlSet<ENTITY, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> controlSet, Object object) {
		return Boolean.FALSE;
	}
	
	@Override
	public DynaFormControl createControl(ControlSet<ENTITY, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> controlSet,
			Control<DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> control, DynaFormRow row) {
		return null;
	}
	
	@Override
	public DynaFormLabel createLabel(ControlSet<ENTITY, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> controlSet,
			OutputLabel<DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> outputLabel, DynaFormRow row) {
		return null;
	}
	
	@Override
	public DynaFormModel createModel(ControlSet<ENTITY, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> controlSet) {
		return null;
	}
	
	@Override
	public DynaFormRow createRow(ControlSet<ENTITY, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> controlSet) {
		return null;
	}
	
	@Override
	public void setControlLabel(ControlSet<ENTITY, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> controlSet, DynaFormControl control,
			DynaFormLabel label) {
	}
	
}