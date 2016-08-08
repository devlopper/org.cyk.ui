package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.CommonBusinessAction;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.language.LanguageBusiness.FindDoSomethingTextParameters;
import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.Identifiable;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.ui.api.CascadeStyleSheet;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.command.CommandAdapter;
import org.cyk.ui.api.command.CommandListener;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.api.data.collector.form.FormConfiguration;
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
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.model.table.Dimension.DimensionType;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter //TODO should extends Row , Column , Cell , Table Listener to avoid creating specific methods
public abstract class AbstractBusinessEntityFormManyPage<ENTITY extends AbstractIdentifiable> extends AbstractBusinessEntityPrimefacesPage<ENTITY> 
	implements CommandListener, Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	protected Table<Object> table;
	protected RowAdapter<Object> rowAdapter;
	protected ColumnAdapter columnAdapter;
	protected CellAdapter<Object> cellAdapter;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void initialisation() { 
		super.initialisation();
		for(BusinessEntityFormManyPageListener<?> listener : BusinessEntityFormManyPageListener.Adapter.getBusinessEntityFormManyPageListeners(businessEntityInfos))
			listener.initialisationStarted(this); 
		/*
		if(formModelClass==null)
			if(identifiableConfiguration!=null)
				formModelClass = identifiableConfiguration.getFormMap().get(Boolean.FALSE, Crud.READ);
		*/
		/*Class<?> aFormClass = __formModelClass__();
		if(aFormClass==null){
			// TODO Form can be provided by URL parameter registered in Form Map
			aFormClass = UIManager.FORM_MODEL_MAP.get(webManager.getRequestParameterFormModel());
		}*/
		table = (Table<Object>) createTable(businessEntityInfos.getClazz(),identifiableConfiguration,(Class<AbstractFormModel<?>>) formModelClass);
		
		table.getRowListeners().add(rowAdapter = new RowAdapter<Object>(){
			@Override
			public void created(Row<Object> row) {
				row.setType(getRowType(row));
				row.setCountable(row.getIsDetail());
				row.setOpenable(openable);
				//AbstractBusinessEntityFormManyPage.this.rowCreated(row);//TODO to be removed. implements listener instead
			}
			@Override
			public void added(Row<Object> row) {
				super.added(row);
				CascadeStyleSheet css = getRowCss(row.getType());
				if(css!=null){
					row.getCascadeStyleSheet().addClass(css.getClazz());
					row.getCascadeStyleSheet().addInline(css.getInline());
				}
			}
		});
		table.getColumnListeners().add(columnAdapter = new ColumnAdapter(){
			@Override
			public Boolean isColumn(Field field) {
				if(!AbstractEnumeration.class.isAssignableFrom(businessEntityInfos.getClazz()) && ArrayUtils.contains(new String[]{AbstractOutputDetails.FIELD_CODE,AbstractOutputDetails.FIELD_NAME,AbstractOutputDetails.FIELD_IMAGE
						,AbstractOutputDetails.FIELD_ABBREVIATION,AbstractOutputDetails.FIELD_DESCRIPTION}, field.getName()))
					return Boolean.FALSE;
				Input input = field.getAnnotation(Input.class);
				IncludeInputs includeInputs = field.getAnnotation(IncludeInputs.class);
				return input != null || includeInputs!=null;
			}
			@Override
			public void added(Column column) {
				super.added(column);
				column.getCascadeStyleSheet().addClass(
						column.getField().getDeclaringClass().getSimpleName().toLowerCase()+
						"-"+column.getField().getName().toLowerCase());
			}
		});
		table.getCellListeners().add(cellAdapter = new CellAdapter<Object>(){
			@Override
			public void added(Row<Object> row, Column column, Cell cell) {
				super.added(row, column, cell);
				
			}
		});
		
		table.getAddRowCommandable().getCommand().getCommandListeners().add(this);
		table.getApplyRowEditCommandable().getCommand().getCommandListeners().add(this);
		
		table.setMaster(identifiable);
		table.getUpdateRowCommandable().getCommand().getCommandListeners().add(new CommandAdapter(){
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
		
		for(BusinessEntityFormManyPageListener<?> listener : BusinessEntityFormManyPageListener.Adapter.getBusinessEntityFormManyPageListeners(businessEntityInfos))
			listener.initialisationEnded(this); 
	}
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		for(BusinessEntityFormManyPageListener<?> listener : BusinessEntityFormManyPageListener.Adapter.getBusinessEntityFormManyPageListeners(businessEntityInfos))
			listener.afterInitialisationStarted(this); 
		
		if(Boolean.TRUE.equals(table.getLazyLoad())){
			table.getOpenRowCommandable().getCommand().getCommandListeners().add(new CommandAdapter(){
				private static final long serialVersionUID = 2679004450545381808L;
				@Override
				public void serve(UICommand command, Object parameter) {
					@SuppressWarnings("unchecked")
					Object data = ((Row<Object>)parameter).getData();
					AbstractIdentifiable identifiable = __identifiable__(data);
					if(identifiable==null){
						BusinessEntityFormManyPageListener<?> redirectListener = null;
						for(BusinessEntityFormManyPageListener<?> listener : BusinessEntityFormManyPageListener.Adapter.getBusinessEntityFormManyPageListeners(businessEntityInfos))
							if(Boolean.TRUE.equals(listener.canRedirectToConsultView(data)))
								redirectListener = listener; 
						if(redirectListener==null){
							logWarning("No listener found to redirect to consult view {} , {} , {}"
									,businessEntityInfos.getUserInterface().getConsultViewId(),businessEntityInfos.getClazz().getSimpleName(),data);
						}else{
							redirectListener.redirectToConsultView(data);
						}
					}else{
						logTrace("Redirecting to consult view from page. {} , {} , {}"
								,businessEntityInfos.getUserInterface().getConsultViewId(),businessEntityInfos.getClazz().getSimpleName(),identifiable.getIdentifier());
					
						WebNavigationManager.getInstance().redirectTo(businessEntityInfos.getUserInterface().getConsultViewId(), 
								new Object[]{UniformResourceLocatorParameter.CLASS,UIManager.getInstance().keyFromClass(businessEntityInfos)
							,UniformResourceLocatorParameter.IDENTIFIABLE,identifiable.getIdentifier().toString(),
							UniformResourceLocatorParameter.CRUD,businessEntityInfos.getUserInterface().getEditViewId().equals(businessEntityInfos.getUserInterface().getConsultViewId())
							?UniformResourceLocatorParameter.CRUD_READ:null});
							
					}
				}
			});	
		}
		
		for(BusinessEntityFormManyPageListener<?> listener : BusinessEntityFormManyPageListener.Adapter.getBusinessEntityFormManyPageListeners(businessEntityInfos))
			listener.afterInitialisationEnded(this); 
	}
	
	@Override
	protected FindDoSomethingTextParameters getContentTitleDoSomethingTextParameters() {
		FindDoSomethingTextParameters parameters = super.getContentTitleDoSomethingTextParameters();
		parameters.setActionIdentifier(CommonBusinessAction.LIST);
		parameters.setOne(Boolean.FALSE);
		
		return parameters;
	}
	
	protected void redirectToCrudOne(Crud crud,Object data){
		BusinessEntityFormManyPageListener<?> redirectListener = null;
		for(BusinessEntityFormManyPageListener<?> listener : BusinessEntityFormManyPageListener.Adapter.getBusinessEntityFormManyPageListeners(businessEntityInfos))
			if(Boolean.TRUE.equals(listener.canRedirect(crud, data)))
				redirectListener = listener; 
		if(redirectListener==null){
			AbstractIdentifiable identifiable = __identifiable__(data);
			//(AbstractIdentifiable) (data instanceof AbstractIdentifiable ? data:((AbstractFormModel<?>)data).getIdentifiable());
			if(identifiable==null){
				logError("Identifiable cannot be found from {} : {}",data.getClass().getSimpleName(),data);
			}else{
				WebNavigationManager.getInstance().redirectToDynamicCrudOne(identifiable,crud);
			}
		}else{
			redirectListener.redirect(crud, data);
		}
	}
	
	protected AbstractIdentifiable __identifiable__(Object data){
		if(data instanceof AbstractFormModel<?>)
			return ((AbstractFormModel<?>)data).getIdentifiable();
		else if(data instanceof AbstractOutputDetails)
			return ((AbstractOutputDetails<?>) data).getMaster();
		else if(data instanceof AbstractIdentifiable)
			return (AbstractIdentifiable) data;
		else
			return null;
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
			return AbstractFormModel.instance(null,table.getRowDataClass(), entity);
		else
			try {
				Constructor<?> constructor = commonUtils.getConstructor(table.getRowDataClass(),new Class<?>[]{entity.getClass()});
				if(constructor==null)
					throw new IllegalArgumentException("No constructor found for "+table.getRowDataClass()+" with parameter "+entity.getClass());
				//return table.getRowDataClass().getConstructor(entity.getClass()).newInstance(entity);
				return constructor.newInstance(entity);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
	}
	
	
	/*
	protected void rowCreated(Row<Object> row){}
	protected void rowAdded(Row<Object> row){}
	protected void columnAdded(Column column){}
	protected void cellAdded(Row<Object> row, Column column, Cell cell){}
	*/
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
	/*
	protected ColumnAdapter getDefaultColumnAdapter(){
		
	}*/
	
	/**/
	/*
	protected UICommandable createCommandable(String labelId,Icon icon){
		return instanciateCommandableBuilder().setLabelFromId(labelId).setIcon(icon).create();
	}*/
	
	/**/
	
	public static interface BusinessEntityFormManyPageListener<ENTITY extends AbstractIdentifiable> extends BusinessEntityFormPageListener<ENTITY> {

		Collection<BusinessEntityFormManyPageListener<?>> COLLECTION = new ArrayList<>();
		
		/**/
		
		public static class Adapter<ENTITY_TYPE extends AbstractIdentifiable> extends BusinessEntityFormPageListener.Adapter<ENTITY_TYPE> implements BusinessEntityFormManyPageListener<ENTITY_TYPE>,Serializable {
			private static final long serialVersionUID = -7944074776241690783L;

			public Adapter(Class<ENTITY_TYPE> entityTypeClass) {
				super(entityTypeClass);
			}
			
			public static Collection<BusinessEntityFormManyPageListener<?>> getBusinessEntityFormManyPageListeners(Class<? extends Identifiable<?>> aClass){
				Collection<BusinessEntityFormManyPageListener<?>> results = new ArrayList<>();
				if(aClass!=null)
					for(BusinessEntityFormManyPageListener<?> listener : BusinessEntityFormManyPageListener.COLLECTION)
						if(listener.getEntityTypeClass().isAssignableFrom(aClass))
							results.add(listener);
				return results;
			}
			public static Collection<BusinessEntityFormManyPageListener<?>> getBusinessEntityFormManyPageListeners(BusinessEntityInfos businessEntityInfos){
				return getBusinessEntityFormManyPageListeners(businessEntityInfos==null ? null : businessEntityInfos.getClazz());
			}
			
			/**/
			
			public static class Default<ENTITY extends AbstractIdentifiable> extends BusinessEntityFormManyPageListener.Adapter<ENTITY> implements Serializable {
				private static final long serialVersionUID = -4255109770974601234L;

				public Default(Class<ENTITY> entityTypeClass) {
					super(entityTypeClass);
					FormConfiguration configuration = createFormConfiguration(Crud.READ, FormConfiguration.TYPE_INPUT_SET_SMALLEST);
					configuration.addExcludedFieldNames(AbstractOutputDetails.FIELD_CODE,AbstractOutputDetails.FIELD_NAME,AbstractOutputDetails.FIELD_IMAGE);
				}
				
				@Override
				public Boolean canRedirect(Crud crud, Object data) {
					return Boolean.TRUE;
				}
				
				@Override
				public Boolean canRedirectToConsultView(Object data) {
					return Boolean.TRUE;
				}
				
				@SuppressWarnings({ "unchecked" })
				@Override
				public void initialisationEnded(AbstractBean bean) {
					super.initialisationEnded(bean);
					final AbstractBusinessEntityFormManyPage<AbstractActor> page = (AbstractBusinessEntityFormManyPage<AbstractActor>) bean;
					page.getTable().getColumnListeners().add(new ColumnAdapter(){
						@Override
						public Boolean isColumn(Field field) {
							FormConfiguration configuration = getFormConfiguration(Crud.READ);
							if(configuration==null || configuration.getFieldNames()==null || CollectionUtils.isEmpty(configuration.getFieldNames()))
								return super.isColumn(field);
							return configuration.getFieldNames().contains(field.getName());
						}
						
					});	
				}		
			}
		}


	}


}
