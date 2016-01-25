package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.command.UICommandable.IconType;
import org.cyk.ui.api.data.collector.control.Control;
import org.cyk.ui.api.data.collector.control.Input;
import org.cyk.ui.api.data.collector.control.OutputLabel;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.api.data.collector.form.ControlSet;
import org.cyk.ui.api.data.collector.form.ControlSetListener;
import org.cyk.ui.api.model.table.RowAdapter;
import org.cyk.ui.web.api.WebManager;
import org.cyk.ui.web.primefaces.Commandable;
import org.cyk.ui.web.primefaces.data.collector.form.FormOneData;
import org.cyk.utility.common.computation.DataReadConfiguration;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractBusinessQueryPage<ENTITY extends AbstractIdentifiable,QUERY,RESULT> extends AbstractBusinessEntityFormManyPage<ENTITY> 
	implements ControlSetListener<ENTITY, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem>,Serializable {

	private static final long serialVersionUID = -5996159828897377343L;

	protected Class<ENTITY> entityClass;
	protected Class<QUERY> queryClass;
	protected Class<RESULT> resultClass;
	protected Boolean showGraphics=Boolean.FALSE;
	
	protected QUERY query;
	protected Long queryFirst=0l;
	protected String querySortField, queryGlobalFilter;
	protected Map<String, Object> queryFilters;
	protected Boolean queryAscendingOrder;
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
		table.setEditable(Boolean.FALSE);
		table.setGlobalFilter(Boolean.FALSE);
		table.setShowToolBar(Boolean.FALSE);
		//table.getAddRowCommandable().setRendered(Boolean.FALSE);
		table.setShowOpenCommand(Boolean.TRUE);
		table.setIdentifiableClass(__entityClass__());
		
		form = (FormOneData<QUERY>) createFormOneData(query = newInstance(queryClass), Crud.CREATE);
		form.getControlSetListeners().add((ControlSetListener<QUERY, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem>) this);
		form.setShowCommands(Boolean.FALSE);
		
		form.getSubmitCommandable().setIconType(IconType.ACTION_SEARCH);
		form.getSubmitCommandable().setLabel(text("command.search"));
		form.getSubmitCommandable().getCommand().setMessageManager(messageManager);
		form.getSubmitCommandable().getCommand().getCommandListeners().add(this);
		form.setFieldsRequiredMessage(null);
		((Commandable)form.getSubmitCommandable()).getButton().setUpdate(":"+WebManager.getInstance().getFormId()+":"+componentId()+":resultsOutputPanel");
		
		table.getRowListeners().add(new RowAdapter<Object>(){
			@Override
			public Collection<Object> load(DataReadConfiguration configuration) {
				table.setNumberOfNullUiIndex(null);
				queryFirst = configuration.getFirstResultIndex()==null?0l:configuration.getFirstResultIndex();
				if(Boolean.TRUE.equals(form.getSubmitCommandable().getRequested()))
					return (Collection<Object>) __results__(__query__());
				return null;
			}
			
			@Override
			public Long count(DataReadConfiguration configuration) {
				if(Boolean.TRUE.equals(form.getSubmitCommandable().getRequested()))
					return __count__();
				return null;
			}
			
		});
		
		//TODO in order to handle pagination with summary row , extract all non indexed row and render them using JQuery
		
	}
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		form.getSubmitCommandable().setRequested(autoLoad());
		if(Boolean.TRUE.equals(form.getSubmitCommandable().getRequested()))
			serve(form.getSubmitCommandable().getCommand(), null);
		
		
		table.setShowEditColumn(table.getEditable());
		table.setInplaceEdit(Boolean.FALSE);
		table.setShowAddRemoveColumn(Boolean.FALSE);
		
		((Commandable)table.getAddRowCommandable()).getButton().setRendered(table.getAddRowCommandable().getRendered());
		
	}
	
	protected abstract Class<ENTITY> __entityClass__();
	protected abstract Class<QUERY> __queryClass__();
	protected abstract Class<RESULT> __resultClass__();
	
	protected abstract Collection<ENTITY> __query__();
	protected abstract Long __count__();
	
	protected Boolean isCountableRow(RESULT result){
		return Boolean.TRUE;
	}
		
	@SuppressWarnings("unchecked")
	protected Collection<RESULT> __results__(Collection<ENTITY> identifiables){
		Collection<RESULT> collection = (Collection<RESULT>) datas(identifiables);
		
		return collection;
	}
	
	protected Boolean autoLoad(){
		return Boolean.FALSE;
	}
	
	protected String componentId(){
		return "query";
	}
	
	@Override
	protected BusinessEntityInfos fetchBusinessEntityInfos() {
		return uiManager.businessEntityInfos(entityClass);
	}
	
	@Override
	protected Class<?> __formModelClass__() {
		return __resultClass__();
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
			table.load(new DataReadConfiguration(0l, null, null, null, null, null));
			table.setFetch(Boolean.FALSE);
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
	
	@Override
	public void sort(List<Field> fields) {
		
	}
	
	@Override
	public Boolean build(Field field) {
		return Boolean.TRUE;
	}
	
	@Override
	public void labelBuilt(
			ControlSet<ENTITY, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> controlSet,
			Field field, DynaFormLabel label) {
		
	}
	
	@Override
	public String fiedLabel(
			ControlSet<ENTITY, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> controlSet,
			Field field) {
		return null;
	}
	
	@Override
	public void input(
			ControlSet<ENTITY, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> controlSet,
			Input<?, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> input) {
		
	}
	
	@Override
	public Boolean showFieldLabel(ControlSet<ENTITY, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> controlSet,Field field) {
		return null;
	}
	
}
