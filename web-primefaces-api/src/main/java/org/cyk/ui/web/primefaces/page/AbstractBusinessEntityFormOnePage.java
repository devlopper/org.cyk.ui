package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.command.CommandListener;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.config.OutputDetailsConfiguration;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.web.api.AjaxListener;
import org.cyk.ui.web.api.AjaxListener.ListenValueMethod;
import org.cyk.ui.web.primefaces.Commandable;
import org.cyk.ui.web.primefaces.data.collector.control.ControlSetAdapter;
import org.cyk.ui.web.primefaces.data.collector.form.FormOneData;
import org.omnifaces.util.Faces;

@Getter
@Setter
public abstract class AbstractBusinessEntityFormOnePage<ENTITY extends AbstractIdentifiable> extends AbstractBusinessEntityPrimefacesPage<ENTITY> implements CommandListener, Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	protected Crud crud;
	protected FormOneData<Object> form;
	
	@Override
	protected void initialisation() { 
		super.initialisation();
		for(BusinessEntityFormOnePageListener<?> listener : getListeners())
			listener.initialisationStarted(this); 
		crud = crudFromRequestParameter();
		Object data = data(formModelClass==null?(identifiableConfiguration==null?businessEntityInfos.getClazz():identifiableConfiguration.getFormMap().getOne(crud)):formModelClass);

		form = (FormOneData<Object>) createFormOneData(data,crud);
		form.setShowCommands(Boolean.FALSE);
		form.getSubmitCommandable().getCommand().setConfirm(Crud.DELETE.equals(crud));
		form.getSubmitCommandable().getCommand().getCommandListeners().add(this);
		
		String outputDetailsKey = Faces.getRequestParameter(uiManager.getDetailsParameter());
		if(StringUtils.isNotBlank(outputDetailsKey)){
			final OutputDetailsConfiguration configuration = uiManager.getOutputDetailsConfigurationFromKey(outputDetailsKey);
			if(configuration.getEditFormConfiguration()==null)
				;
			else{
				form.getControlSetListeners().add(new ControlSetAdapter<Object>(){
					@Override
					public Boolean build(Field field) {
						return configuration.getEditFormConfiguration().getFieldNames().contains(field.getName());
					}
				});
			}
		}	
		
		if(uiManager.isMobileDevice(userDeviceType)){
			((Commandable)form.getSubmitCommandable()).setUpdate("");
		}
		
		if(StringUtils.isBlank(previousUrl)){
			//previousUrl = navigationManager.url(businessEntityInfos.getUiListViewId(),null,Boolean.FALSE,Boolean.FALSE);
			//System.out.println("AbstractBusinessEntityFormOnePage.initialisation() : "+businessEntityInfos.getUiListViewId());
			
		}
		
		for(BusinessEntityFormOnePageListener<?> listener : getListeners())
			listener.initialisationEnded(this); 
		
	}
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		for(BusinessEntityFormOnePageListener<?> listener : getListeners())
			listener.afterInitialisationStarted(this);
		
		// your Code gere
		
		for(BusinessEntityFormOnePageListener<?> listener : getListeners())
			listener.afterInitialisationEnded(this); 
	}
	
	@SuppressWarnings("unchecked")
	protected Object data(Class<?> aClass){
		try{
			if(identifiable==null)
				identifiable =  (ENTITY) businessEntityInfos.getClazz().newInstance();
			return identifiableFormData(aClass);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}	
	}
	
	protected Object identifiableFormData(Class<?> dataClass) throws InstantiationException, IllegalAccessException{
		if(AbstractFormModel.class.isAssignableFrom(dataClass))
			return AbstractFormModel.instance(dataClass,identifiable);
		else{
			return identifiable;
		}
	}
	
	@Override
	public Boolean getShowContentMenu() {
		return crud!=null && !Crud.READ.equals(crud);
	}
	
	private Collection<BusinessEntityFormOnePageListener<?>> getListeners(){
		return primefacesManager.getBusinessEntityFormOnePageListeners(businessEntityInfos.getClazz());
	}
	
	/**/
	
	@Override
	public void transfer(UICommand command, Object parameter) throws Exception {
		if(form.getSubmitCommandable().getCommand()==command){
			if(!Crud.READ.equals(crud) && !Crud.DELETE.equals(crud))
				if(parameter!=null){
					if(AbstractFormModel.class.isAssignableFrom(parameter.getClass())){
						((AbstractFormModel<?>)parameter).write();
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
	
	protected <TYPE> AjaxListener setAjaxListener(final String fieldName,String event,String[] crossFieldNames,String[] updatedFieldNames,final Class<TYPE> valueClass,ListenValueMethod<TYPE> method){
		return setAjaxListener(form, fieldName, event, crossFieldNames,updatedFieldNames,valueClass, method);
	}
	
	protected void setFieldValue(String inputName,Object value){
		setFieldValue(form, inputName, value);
	}
	
	protected <TYPE> TYPE fieldValue(String fieldName,Class<TYPE> typeClass,TYPE nullValue){
		return fieldValue(form,fieldName, typeClass,nullValue);
	}
	
	protected BigDecimal bigDecimalValue(String fieldName,BigDecimal nullValue){
		return bigDecimalValue(form,fieldName,nullValue);
	}
	protected BigDecimal bigDecimalValue(String fieldName){
		return bigDecimalValue(fieldName, BigDecimal.ZERO);
	}
	protected String stringValue(String fieldName,String nullValue){
		return stringValue(form,fieldName,nullValue);
	}
	protected String stringValue(String fieldName){
		return stringValue(fieldName,"");
	}
	protected Date dateValue(String fieldName,Date nullValue){
		return dateValue(form,fieldName,nullValue);
	}
	protected String inputRowVisibility(String fieldName,Boolean visible){
		return inputRowVisibility(form, fieldName, visible);
	}
}
