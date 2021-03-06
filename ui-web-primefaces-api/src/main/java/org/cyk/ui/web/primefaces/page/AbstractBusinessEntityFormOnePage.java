package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.faces.model.SelectItem;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.language.LanguageBusiness.FindDoSomethingTextParameters;
import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.Identifiable;
import org.cyk.system.root.model.value.Value;
import org.cyk.system.root.model.value.ValueCollection;
import org.cyk.ui.api.command.CommandListener;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.data.collector.control.Input;
import org.cyk.ui.api.data.collector.control.InputChoice;
import org.cyk.ui.api.data.collector.control.InputCollection;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.api.data.collector.form.ControlSet;
import org.cyk.ui.api.data.collector.form.FormConfiguration;
import org.cyk.ui.api.model.AbstractItemCollection;
import org.cyk.ui.api.model.AbstractItemCollectionItem;
import org.cyk.ui.web.api.AjaxBuilder;
import org.cyk.ui.web.api.data.collector.control.WebInput;
import org.cyk.ui.web.primefaces.AbstractMetricValueCollection;
import org.cyk.ui.web.primefaces.Commandable;
import org.cyk.ui.web.primefaces.ItemCollection;
import org.cyk.ui.web.primefaces.MetricValueCollection;
import org.cyk.ui.web.primefaces.data.collector.control.ControlSetAdapter;
import org.cyk.ui.web.primefaces.data.collector.control.InputManyPickList;
import org.cyk.ui.web.primefaces.data.collector.form.FormOneData;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.GridHelper;
import org.cyk.utility.common.helper.GridHelper.Grid;
import org.cyk.utility.common.helper.GridHelper.Grid.Column;
import org.cyk.utility.common.helper.MarkupLanguageHelper;
import org.cyk.utility.common.helper.MethodHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractBusinessEntityFormOnePage<ENTITY extends AbstractIdentifiable> extends AbstractBusinessEntityPrimefacesPage<ENTITY> implements CommandListener, Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	protected Crud crud;
	protected FormOneData<Object> form;
	
	@Override
	protected void initialisation() { 
		super.initialisation();
		for(BusinessEntityFormOnePageListener<?> listener : BusinessEntityFormOnePageListener.Adapter.getBusinessEntityFormOnePageListeners(businessEntityInfos))
			listener.initialisationStarted(this); 
		crud = crudFromRequestParameter();
		Object data = data(formModelClass==null?(identifiableConfiguration==null?businessEntityInfos.getClazz():identifiableConfiguration.getFormMap().getOne(crud)):formModelClass);
		
		form = (FormOneData<Object>) createFormOneData(data,crud,getSubmitCommandableLabelId());
		form.setShowCommands(Boolean.FALSE);
		form.getSubmitCommandable().getCommand().setConfirm(Crud.DELETE.equals(crud));
		form.getSubmitCommandable().getCommand().getCommandListeners().add(this);
		
		formConfiguration = getFormConfiguration(StringUtils.isBlank(formConfigurationIdentifier) ? selectedTabId : formConfigurationIdentifier);
		if(formConfiguration==null)
			if(Crud.CREATE.equals(crud))
				;
			else if(Crud.READ.equals(crud))
				formConfiguration = getFormConfiguration(Crud.CREATE,null);
			else if(Crud.UPDATE.equals(crud))
				formConfiguration = getFormConfiguration(Crud.CREATE,null);
			else if(Crud.DELETE.equals(crud)){
				formConfiguration = getFormConfiguration(Crud.READ,null);
				if(formConfiguration==null)
					formConfiguration = getFormConfiguration(Crud.CREATE,null);
			}
		
		form.getControlSetListeners().add(new ControlSetAdapter<Object>(){
			private static final long serialVersionUID = 2227224319108375650L;
			@Override
			public Boolean build(Object data,Field field) {
				if(FormConfiguration.hasExcludedFieldName(formConfiguration, field.getName()))
					return Boolean.FALSE;
				else if(FormConfiguration.hasNoFieldNames(formConfiguration))
					return super.build(data,field);
				else
					return FormConfiguration.hasFieldName(formConfiguration, field.getName());
			}
			@Override
			public void input(
					ControlSet<Object, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> controlSet,
					Input<?, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> input) {
				super.input(controlSet, input);
				if(Crud.isCreateOrUpdate(crud)){
					//if(Boolean.FALSE.equals(input.getRequired())){
						input.setRequired(FormConfiguration.hasRequiredFieldName(formConfiguration, input.getField().getName()));
					//}	
				}
			}
		});	
		FormConfiguration.addControlSetListeners(formConfiguration, form.getControlSetListeners());
		
		if(uiManager.isMobileDevice(userDeviceType)){
			((Commandable)form.getSubmitCommandable()).setUpdate("");
		}
		
		if(StringUtils.isBlank(previousUrl)){
			//previousUrl = navigationManager.url(businessEntityInfos.getUiListViewId(),null,Boolean.FALSE,Boolean.FALSE);
			//System.out.println("AbstractBusinessEntityFormOnePage.initialisation() : "+businessEntityInfos.getUiListViewId());
			
		}
		
		for(BusinessEntityFormOnePageListener<?> listener : BusinessEntityFormOnePageListener.Adapter.getBusinessEntityFormOnePageListeners(businessEntityInfos)){
			listener.initialisationEnded(this); 
		}
	}
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		for(BusinessEntityFormOnePageListener<?> listener : BusinessEntityFormOnePageListener.Adapter.getBusinessEntityFormOnePageListeners(businessEntityInfos))
			listener.afterInitialisationStarted(this);
		
		// your code here
		
		for(BusinessEntityFormOnePageListener<?> listener : BusinessEntityFormOnePageListener.Adapter.getBusinessEntityFormOnePageListeners(businessEntityInfos))
			listener.afterInitialisationEnded(this); 
	}
	
	public <DATA> FormOneData<DATA> createFormOneData(DATA data){
		if(Crud.DELETE.equals(crud))
			return null;
		return (FormOneData<DATA>) createFormOneData(data, crud);
	}
	
	protected String getSubmitCommandableLabelId(){
		return "command.execute";
	}
	
	@Override
	protected FindDoSomethingTextParameters getContentTitleDoSomethingTextParameters() {
		FindDoSomethingTextParameters parameters = super.getContentTitleDoSomethingTextParameters();
		parameters.setActionIdentifier(crud);
		return parameters;
	}
		
	protected Object data(Class<?> aClass){
		try{
			if(identifiable==null){
				identifiable = instanciateIdentifiable();
				identifiable.getProcessing().setIdentifier(actionIdentifier);
				identifiable.getProcessing().setParty(userSession.getUser());
			}
			return identifiableFormData(aClass);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}	
	}
	
	@SuppressWarnings("unchecked")
	protected ENTITY instanciateIdentifiable(){
		ENTITY entity = null;
		if(businessEntityInfos!=null){
			TypedBusiness<ENTITY> business = getBusiness(); //(TypedBusiness<ENTITY>) inject(BusinessInterfaceLocator.class).injectTyped((Class<ENTITY>)businessEntityInfos.getClazz());
			if(business!=null){
				entity = business.instanciateOne(userSession.getUserAccount());	
			}
		}
		
		if(entity==null)
			entity = (ENTITY) newInstance(businessEntityInfos.getClazz());
		return entity;
	}
	
	protected Object identifiableFormData(Class<?> dataClass) throws InstantiationException, IllegalAccessException{
		if(AbstractFormModel.class.isAssignableFrom(dataClass))
			return AbstractFormModel.instance(this,dataClass,identifiable);
		else if(AbstractOutputDetails.class.isAssignableFrom(dataClass)){
			return commonUtils.instanciate(dataClass,new Class<?>[]{businessEntityInfos.getClazz()},new Object[]{identifiable});
		}else
			return identifiable;
	}
	
	protected <TYPE extends AbstractItemCollectionItem<IDENTIFIABLE>, IDENTIFIABLE extends AbstractIdentifiable,COLLECTION extends AbstractIdentifiable> ItemCollection<TYPE, IDENTIFIABLE,COLLECTION> createItemCollection(
			Class<TYPE> aClass,Class<IDENTIFIABLE> identifiableClass,COLLECTION collectionIdentifiable,AbstractItemCollection.Listener<TYPE, IDENTIFIABLE,COLLECTION,SelectItem> listener) {
		Collection<IDENTIFIABLE> identifiables = Crud.CREATE.equals(crud) ? listener.create() : listener.load();
		ItemCollection<TYPE, IDENTIFIABLE,COLLECTION> collection = super.createItemCollection(form, "qwerty", aClass, identifiableClass,collectionIdentifiable,identifiables, listener);
		collection.getAddCommandable().getCommand().getCommandListeners().add(this);
		if(collection.getInputChoice()!=null){
			if(ClassHelper.getInstance().isInstanceOf(AbstractIdentifiable.class,collection.getInputChoice().getField().getType()))
				collection.setLabel( inject(LanguageBusiness.class).findClassLabelText(collection.getInputChoice().getField().getType()) );
			else
				collection.setLabel(text(uiManager.businessEntityInfos(identifiableClass).getUserInterface().getLabelId()));
		}
		collection.setShowAddCommandableAtBottom(Boolean.FALSE);
		collection.setContainerForm(form);
		return collection;
	}
	
	protected <TYPE extends AbstractItemCollectionItem<IDENTIFIABLE>, IDENTIFIABLE extends AbstractIdentifiable,COLLECTION extends AbstractIdentifiable> AbstractMetricValueCollection<TYPE, IDENTIFIABLE,COLLECTION> createMetricValueCollection(
			Object collection, Class<TYPE> aClass,Class<IDENTIFIABLE> identifiableClass,AbstractItemCollection.Listener<TYPE, IDENTIFIABLE,COLLECTION,SelectItem> listener) {
		@SuppressWarnings("unchecked")
		COLLECTION ic = (COLLECTION) collection;
		AbstractMetricValueCollection<TYPE,IDENTIFIABLE,COLLECTION> metricValueCollection = (AbstractMetricValueCollection<TYPE, IDENTIFIABLE,COLLECTION>) createItemCollection(aClass, identifiableClass,ic ,listener);
		metricValueCollection.setCollection(collection);
		metricValueCollection.getDeleteCommandable().setRendered(Boolean.FALSE);
		metricValueCollection.getAddCommandable().setRendered(Boolean.FALSE);
		return metricValueCollection;
	}
	
	protected AbstractMetricValueCollection<MetricValueCollection.Item, Value,ValueCollection> 
		createMetricValueCollection(Object collection,AbstractItemCollection.Listener<MetricValueCollection.Item, Value,ValueCollection,SelectItem> listener) {
		return createMetricValueCollection(collection, MetricValueCollection.Item.class, Value.class, listener);
	}
	
	@Override
	public Boolean getShowContentMenu() {
		return crud!=null && !Crud.READ.equals(crud);
	}
	
	protected FormConfiguration getFormConfiguration(String type){
		return getFormConfiguration(crud, type);
	}
	/*
	protected FormConfiguration getFormConfiguration(){
		return getFormConfiguration(selectedTabId);
	}*/
	
	/**/
	
	@Override
	public void transfer(UICommand command, Object parameter) throws Exception {
		if(form.getSubmitCommandable().getCommand()==command){
			if(!Crud.READ.equals(crud) && !Crud.DELETE.equals(crud))
				if(parameter!=null){
					if(AbstractFormModel.class.isAssignableFrom(parameter.getClass())){
						AbstractFormModel<?> formModel = (AbstractFormModel<?>) parameter;
						if(formModel.getIdentifiable()==null)
							;
						else
							formModel.write();
						for(AbstractItemCollection<?,?,?,?> itemCollection : form.getItemCollections()){
							if(Boolean.TRUE.equals(itemCollection.getAutoWrite())){
								itemCollection.write();
							}
							//TODO do write here in case we do not auto math input as fields
							/*for(AbstractItemCollectionItem<?> item : itemCollection.getItems()){
								System.out.println("2 : ");
								item.getForm().getSelectedFormData().applyValuesToFields();
								item.write();
							}*/
						}
						for(InputCollection<?, ?> inputCollection : form.getInputCollections())
							inputCollection.getCollection().write();
					}
					
					
				}
		}else{
			if(!Crud.READ.equals(crud) && !Crud.DELETE.equals(crud)){
				for(AbstractItemCollection<?,?,?,?> itemCollection : form.getItemCollections()){
					if(itemCollection.getAddCommandable().getCommand()==command && Boolean.TRUE.equals(itemCollection.getAutoApplyMasterFormFieldValues()) ){
						form.getSelectedFormData().applyValuesToFields();
					}
				}
			}
		}
	}

	@Override
	public Boolean validate(UICommand command, Object parameter) {
		return null;
	}

	@Override
	public Object succeed(UICommand command, Object parameter) {
		if(StringUtils.isNotBlank(previousUrl))
			messageDialogOkButtonOnClick=javaScriptHelper.windowHref(previousUrl);
		else
			messageDialogOkButtonOnClick = javaScriptHelper.windowBack();
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
	
	public AjaxBuilder createAjaxBuilder(String fieldName){
		return super.createAjaxBuilder(form, fieldName);
	}
	
	public void setFieldValue(String inputName,Object value){
		setFieldValue(form, inputName, value);
	}
	
	public <TYPE> TYPE fieldValue(String fieldName,Class<TYPE> typeClass,TYPE nullValue){
		return fieldValue(form,fieldName, typeClass,nullValue);
	}
	
	public BigDecimal bigDecimalValue(String fieldName,BigDecimal nullValue){
		return bigDecimalValue(form,fieldName,nullValue);
	}
	public BigDecimal bigDecimalValue(String fieldName){
		return bigDecimalValue(fieldName, BigDecimal.ZERO);
	}
	public String stringValue(String fieldName,String nullValue){
		return stringValue(form,fieldName,nullValue);
	}
	public String stringValue(String fieldName){
		return stringValue(fieldName,"");
	}
	public Date dateValue(String fieldName,Date nullValue){
		return dateValue(form,fieldName,nullValue);
	}
	public String inputRowVisibility(String fieldName,Boolean visible){
		return inputRowVisibility(form, fieldName, visible);
	}
	
	public void addInputListener(String fieldName,WebInput.Listener listener){
		addInputListener(form, fieldName, listener);
	}

	public void setChoices(String fieldName,Collection<?> collection,Object selected){
		if(Crud.isCreateOrUpdate(crud)){
			webManager.setChoices(form, fieldName, collection,selected);
			InputChoice<?, ?, ?, ?, ?, ?> inputChoice = (InputChoice<?, ?, ?, ?, ?, ?>) form.getInputByFieldName(fieldName);
			if(inputChoice instanceof org.cyk.ui.api.data.collector.control.InputManyPickList){
				((InputManyPickList<?>)inputChoice).updateDualListModel();
			}
		}
	}
	public void setChoices(String fieldName,Collection<?> collection){
		setChoices(fieldName, collection,null);
	}
	public Object setChoicesAndGetAutoSelected(String fieldName,Collection<?> collection){
		Object selected = null;
		if(collection!=null && collection.size() == 1)
			selected =  collection.iterator().next();
		setChoices(fieldName, collection,selected);
		return selected;
		//if(selected!=null)
		//	page.setFieldValue(classroomSessionDivisionFieldName, classroomSessionDivision);
	}
	
	
	public Object getChoice(String fieldName,Integer index){
		return webManager.getChoice(form, fieldName, index);
	}
	
	@SuppressWarnings({ "unchecked" })
	protected <T,IDENTIFIABLE extends AbstractIdentifiable> org.cyk.ui.web.primefaces.data.collector.control.InputCollection<T> instanciateInputCollection(Class<IDENTIFIABLE> identifiableClass,CollectionHelper.Instance<?> masterElementObjectCollection,Class<T> aClass,Class<?> sourceObjectClass,String...fieldNames){
		org.cyk.ui.web.primefaces.data.collector.control.InputCollection<T> inputCollection = (org.cyk.ui.web.primefaces.data.collector.control.InputCollection<T>) new GridHelper.Grid.Builder.Adapter.Default<T>()
				.setElementObjectClass(identifiableClass)
				.setElementClassContainerClass(getClass())
				.setElementClass(aClass)
				.setMasterObject(identifiable)
				.setGridCollection((java.util.Collection<Grid<T,?>>) MethodHelper.getInstance().callGet(form, java.util.Collection.class, "inputCollections"))
				.addListener(new GridHelper.Grid.Listener.Adapter<T>(){
					private static final long serialVersionUID = 1L;
					
					@Override
					public void addColumn(Grid<T, ?> grid, Column<?> column) {
						column.getFieldDescriptor().getPropertiesMap().set(MarkupLanguageHelper.Attributes.STYLE_CLASS, "cyk-ui-form-inputfield-relative");
					}
				})
				/*.addCollectionListener(new CollectionHelper.Instance.Listener.Adapter<T>(){
					private static final long serialVersionUID = 1L;
					@Override
					public void addOne(Instance<T> instance, T element, Object source, Object sourceObject) {
						super.addOne(instance, element, source, sourceObject);
					}
				})*/
				.setMasterElementObjectCollection(masterElementObjectCollection)
				.setOutput(new org.cyk.ui.web.primefaces.data.collector.control.InputCollection<>(StringHelper.getInstance().getClazz(identifiableClass),aClass,identifiableClass))
				.execute();	
		return inputCollection;
	}
	
	protected <T,IDENTIFIABLE extends AbstractIdentifiable> org.cyk.ui.web.primefaces.data.collector.control.InputCollection<T> instanciateInputCollection(Class<IDENTIFIABLE> identifiableClass,Class<T> aClass,Class<?> sourceObjectClass,String...fieldNames){
		return instanciateInputCollection(identifiableClass, ((AbstractCollection<?>)identifiable).getItems(), aClass, sourceObjectClass, fieldNames);
	}
	
	protected <T,IDENTIFIABLE extends AbstractIdentifiable> org.cyk.ui.web.primefaces.data.collector.control.InputCollection<T> instanciateInputCollection(Class<IDENTIFIABLE> identifiableClass,CollectionHelper.Instance<?> masterElementObjectCollection,Class<T> aClass,String...fieldNames){
		return instanciateInputCollection(identifiableClass,masterElementObjectCollection,aClass,null, fieldNames);
	}
	
	protected <T,IDENTIFIABLE extends AbstractIdentifiable> org.cyk.ui.web.primefaces.data.collector.control.InputCollection<T> instanciateInputCollection(Class<IDENTIFIABLE> identifiableClass,Class<T> aClass,String...fieldNames){
		return instanciateInputCollection(identifiableClass,((AbstractCollection<?>)identifiable).getItems(),aClass,null, fieldNames);
	}
	
	protected <T,IDENTIFIABLE extends AbstractIdentifiable> org.cyk.ui.web.primefaces.data.collector.control.InputCollection<T> instanciateInputCollection(Class<IDENTIFIABLE> identifiableClass,CollectionHelper.Instance<?> masterElementObjectCollection,String...fieldNames){
		return instanciateInputCollection(identifiableClass,masterElementObjectCollection, null, fieldNames);
	}
	
	protected <T,IDENTIFIABLE extends AbstractIdentifiable> org.cyk.ui.web.primefaces.data.collector.control.InputCollection<T> instanciateInputCollection(Class<IDENTIFIABLE> identifiableClass,String...fieldNames){
		return instanciateInputCollection(identifiableClass,((AbstractCollection<?>)identifiable).getItems(), null, fieldNames);
	}
	
	/**/
	
	public static interface BusinessEntityFormOnePageListener<ENTITY extends AbstractIdentifiable> extends BusinessEntityFormPageListener<ENTITY> {

		public static Collection<BusinessEntityFormOnePageListener<?>> COLLECTION = new ArrayList<>();
		
		void onSucceed(AbstractBusinessEntityFormOnePage<? extends AbstractIdentifiable> page);
		
		/**/
		
		public static class Adapter<ENTITY_TYPE extends AbstractIdentifiable> extends BusinessEntityFormPageListener.Adapter<ENTITY_TYPE> implements BusinessEntityFormOnePageListener<ENTITY_TYPE>,Serializable {

			private static final long serialVersionUID = -7944074776241690783L;

			public Adapter(Class<ENTITY_TYPE> entityTypeClass) {
				super(entityTypeClass);
			}
			
			@Override
			public void onSucceed(AbstractBusinessEntityFormOnePage<? extends AbstractIdentifiable> page) {
				
			}
			
			public static Collection<BusinessEntityFormOnePageListener<?>> getBusinessEntityFormOnePageListeners(Class<? extends Identifiable<?>> aClass){
				Collection<BusinessEntityFormOnePageListener<?>> results = new ArrayList<>();
				if(aClass!=null)
					for(BusinessEntityFormOnePageListener<?> listener : BusinessEntityFormOnePageListener.COLLECTION)
						if(listener.getEntityTypeClass().isAssignableFrom(aClass))
							results.add(listener);
				return results;
			}
			public static Collection<BusinessEntityFormOnePageListener<?>> getBusinessEntityFormOnePageListeners(BusinessEntityInfos businessEntityInfos){
				return getBusinessEntityFormOnePageListeners(businessEntityInfos==null ? null : businessEntityInfos.getClazz());
			}

			/**/
			
			public static class Default<ENTITY extends AbstractIdentifiable> extends BusinessEntityFormOnePageListener.Adapter<ENTITY> implements Serializable {

				private static final long serialVersionUID = -4255109770974601234L;

				public Default(Class<ENTITY> entityTypeClass) {
					super(entityTypeClass);
				}
					
			}
		}
		
	}

}
