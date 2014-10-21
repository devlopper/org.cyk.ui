package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.CrudConfig;
import org.cyk.ui.api.command.CommandListener;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.web.primefaces.data.collector.form.FormOneData;

@Getter
@Setter
public abstract class AbstractBusinessEntityFormOnePage<ENTITY extends AbstractIdentifiable> extends AbstractBusinessEntityPrimefacesPage<ENTITY> implements CommandListener, Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	protected Crud crud;
	protected FormOneData<Object> form;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void initialisation() { 
		super.initialisation();
		crud = crudFromRequestParameter();
		CrudConfig config = uiManager.crudConfig((Class<? extends AbstractIdentifiable>) businessEntityInfos.getClazz());
		Object data = data(config==null?businessEntityInfos.getClazz():config.getFormClass());
		//if(AbstractIdentifiableForm.class.isAssignableFrom(data.getClass())){
		//	((AbstractIdentifiableForm<?>)data).read();
		//}
		form = (FormOneData<Object>) createFormOneData(data,crud);
		form.setShowCommands(Boolean.FALSE);
		form.getSubmitCommandable().getCommand().getCommandListeners().add(this);
	}
	
	//protected abstract void __serve__(Object parameter);
	
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
		if(AbstractFormModel.class.isAssignableFrom(dataClass)){
			/*
			@SuppressWarnings("unchecked")
			AbstractFormModel<AbstractIdentifiable> data = (AbstractFormModel<AbstractIdentifiable>) dataClass.newInstance();
			data.setIdentifiable(identifiable);
			data.read();
			*/
			return AbstractFormModel.instance(dataClass,identifiable);
		}else{
			return identifiable;
		}
	}
	
	@Override
	public Boolean getShowContentMenu() {
		return crud!=null && !Crud.READ.equals(crud);
	}
	
	/**/
	
	@Override
	public void transfer(UICommand command, Object parameter) throws Exception {
		if(form.getSubmitCommandable().getCommand()==command){
			if(AbstractFormModel.class.isAssignableFrom(parameter.getClass())){
				((AbstractFormModel<?>)parameter).write();
			}
		}
	}

	@Override
	public Boolean validate(UICommand command, Object parameter) {
		return null;
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
}
