package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.impl.AbstractIdentifiableBusinessServiceImpl;
import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.business.impl.DetailsClassLocator;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.ui.api.IdentifierProvider;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.config.IdentifiableConfiguration;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.api.data.collector.form.FormConfiguration;
import org.cyk.ui.api.model.AbstractBusinessIdentifiedEditFormModel;
import org.cyk.ui.web.primefaces.FormClassLocator;
import org.cyk.ui.web.primefaces.PrimefacesManager;
import org.cyk.ui.web.primefaces.Table.ColumnAdapter;
import org.cyk.ui.web.primefaces.data.collector.control.ControlSetAdapter;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.Constant.Action;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.FieldHelper;

import lombok.Getter;

public abstract class AbstractIdentifiablePagesConfiguration<TYPE extends AbstractIdentifiable> extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 1L;

	public static enum ViewType{FORM,DETAILS}
	
	protected UIManager uiManager = inject(UIManager.class);
	
	@Getter protected Class<TYPE> identifiableClass;
	
	@SuppressWarnings("unchecked")
	public AbstractIdentifiablePagesConfiguration() {
		identifiableClass = (Class<TYPE>) new ClassHelper().getParameterAt(getClass(), 0,AbstractIdentifiable.class);
	}
	
	protected String[] getRequiredFieldNames(Constant.Action action){
		if(AbstractIdentifiableBusinessServiceImpl.isAutoSetPropertyValueClass(GlobalIdentifier.FIELD_CODE, getIdentifiableClass()))
			return null;
		return new String[]{AbstractBusinessIdentifiedEditFormModel.FIELD_CODE};
	}
	
	protected String[] getFieldNames(Constant.Action action){
		FieldHelper fieldHelper = new FieldHelper();
		Set<String> names = new HashSet<>();
		names.addAll(fieldHelper.getNamesWhereReferencedByStaticField(getIdentifiableClass(), Boolean.FALSE));
		names.add(AbstractBusinessIdentifiedEditFormModel.FIELD_NAME);
		return names.toArray(Constant.EMPTY_STRING_ARRAY);
	}
	
	public AbstractIdentifiablePagesConfiguration<TYPE> configure(){
		configurePages();
		return this;
	}
	
	public AbstractIdentifiablePagesConfiguration<TYPE> configurePages(){
		__configurePages__();
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(getIdentifiableClass(),  getEditPageForm(), getDetails(),null,null,null));
		uiManager.configBusinessIdentifiable(getIdentifiableClass(), null);
		return this;
	}
	
	public AbstractIdentifiablePagesConfiguration<TYPE> configure(ViewType viewType,Constant.Action action){
		__configure__(viewType, action);
		return this;
	}
	
	protected void __configurePages__(){
		processFormConfigurations();
		registerDetailsConfiguration(getDetails(), getDetailsConfiguration());
	}
	
	protected void __configure__(ViewType viewType,Constant.Action action){}
	
	protected void processFormConfigurations(){
		getFormConfiguration(getIdentifiableClass(),Crud.CREATE).addRequiredFieldNames(getRequiredFieldNames(Constant.Action.CREATE))
		.addFieldNames(getFieldNames(Constant.Action.CREATE));
	}
	
	protected DetailsConfiguration getDetailsConfiguration(){
		return new DetailsConfiguration(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			@Override
			public ControlSetAdapter.Details getFormControlSetAdapter(Class clazz) {
				return new DetailsConfiguration.DefaultControlSetAdapter(){
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean build(Object data,Field field) {
						return isFieldNameIn(field,getFormConfigurationFirstNotNull(getIdentifiableClass(),Constant.Action.CREATE, Constant.Action.READ).getFieldNames().toArray(new String[]{}));
					}
				};
			}
			
			@Override
			public ColumnAdapter getTableColumnAdapter(@SuppressWarnings("rawtypes") Class clazz,AbstractPrimefacesPage page) {
				return new DetailsConfiguration.DefaultColumnAdapter(){
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean isColumn(Field field) {
						return isFieldNameIn(field,getFormConfigurationFirstNotNull(getIdentifiableClass(),Constant.Action.CREATE, Constant.Action.READ).getFieldNames().toArray(new String[]{}));
					}
				};
			}
		};
	}
	
	/**/
	
	@SuppressWarnings("unchecked")
	protected void registerDetailsConfiguration(Class<?> detailsClass,DetailsConfiguration detailsConfiguration){
		PrimefacesManager.registerDetailsConfiguration((Class<? extends AbstractOutputDetails<?>>) detailsClass, detailsConfiguration);
	}
	
	protected FormConfiguration getFormConfiguration(Class<?> clazz, Crud crud, String type){
		return FormConfiguration.get(clazz, crud, type, Boolean.TRUE);
	}
	
	protected FormConfiguration getFormConfiguration(Class<?> clazz, Crud crud){
		return getFormConfiguration(clazz, crud, null);
	}
	
	protected FormConfiguration getFormConfigurationFirstNotNull(Class<?> clazz, Constant.Action...actions){
		for(Action action : actions){
			FormConfiguration formConfiguration = getFormConfiguration(clazz, Crud.valueOf(action.name()));
			if(formConfiguration!=null)
				return formConfiguration;
		}
		return null;
	}
	
	protected FormConfiguration getUpdateFormConfiguration(Class<?> entityClass, Class<?> aClass){
		return getFormConfiguration(entityClass, Crud.UPDATE,getTabIdentifier(aClass));
	}
	
	protected String getTabIdentifier(Class<?> aClass){
		return IdentifierProvider.Adapter.getTabOf(aClass);
	}
	
	@SuppressWarnings("unchecked")
	protected Class<? extends AbstractFormModel<TYPE>> getEditPageForm(){
		return (Class<? extends AbstractFormModel<TYPE>>) inject(FormClassLocator.class).locate(getIdentifiableClass());
	}
	
	@SuppressWarnings("unchecked")
	protected Class<? extends AbstractOutputDetails<TYPE>> getDetails(){
		return (Class<? extends AbstractOutputDetails<TYPE>>) inject(DetailsClassLocator.class).locate(getIdentifiableClass());
	}
}
