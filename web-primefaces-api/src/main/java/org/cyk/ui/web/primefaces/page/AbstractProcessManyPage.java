package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.command.CommandListener;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.command.menu.MenuManager;
import org.cyk.ui.web.primefaces.Table;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.cdi.AbstractBean;

@Getter @Setter
public abstract class AbstractProcessManyPage<ENTITY extends AbstractIdentifiable> extends AbstractBusinessEntityFormOnePage<ENTITY> implements CommandListener,Serializable {

	private static final long serialVersionUID = -7392513843271510254L;

	private String actionIdentifier;
	private Table<ProcessItem> table;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void initialisation() {
		super.initialisation();
		
		for(AbstractProcessManyPage.Listener<?,?> processPageListener : getListeners())
			processPageListener.initialisationStarted(this);
		Class<ENTITY> entityClass = (Class<ENTITY>) businessEntityInfos.getClazz();
		actionIdentifier = requestParameter(uiManager.getActionIdentifierParameter());
		@SuppressWarnings("rawtypes")
		DetailsConfigurationListener.Table.Adapter listener = new DetailsConfigurationListener.Table.Adapter<ENTITY,ProcessItem>(entityClass
				, ProcessItem.class){
			private static final long serialVersionUID = 1L;
			@Override
			public Collection<ENTITY> getIdentifiables() {
				Collection<Long> identifiers = webManager.decodeIdentifiersRequestParameter();
				return (Collection<ENTITY>) genericBusiness.use(identifiableClass).findByIdentifiers(identifiers);
			}
			
			@Override
			public Boolean getEnabledInDefaultTab() {
				return Boolean.TRUE;
			}
			@Override
			public String getTabId() {
				return "1";
			}
		};
		table = createDetailsTable(entityClass, listener);
		
		/*
		Object formData = null;
		for(AbstractProcessManyPage.Listener<?,?> processPageListener : getListeners()){
			Object v = processPageListener.getFormData(this, actionIdentifier);
			if(v!=null)
				formData = v;
		}*/
		/*
		form = (FormOneData<Object>) createFormOneData(formData,Crud.CREATE);
		form.setDynamic(Boolean.TRUE);
		form.getSubmitCommandable().setLabel(text("command.ok"));
		*/
		for(AbstractProcessManyPage.Listener<?,?> processPageListener : getListeners())
			processPageListener.initialisationEnded(this);
	}
	
	@Override
	public String getContentTitle() {
		return MenuManager.getInstance().getCommandableLabel(businessEntityInfos, actionIdentifier);
	}
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		
		for(AbstractProcessManyPage.Listener<?,?> processPageListener : getListeners())
			processPageListener.afterInitialisationStarted(this);
		
		for(AbstractProcessManyPage.Listener<?,?> processPageListener : getListeners())
			processPageListener.afterInitialisationEnded(this);
	}
	
	@Override
	protected <T extends AbstractIdentifiable> T identifiableFromRequestParameter(Class<T> aClass, String identifierId) {
		return null;
	}
	
	@Override
	protected Crud crudFromRequestParameter() {
		return Crud.CREATE;
	}

	@Override
	protected Class<?> __formModelClass__() {
		Class<?> formDataClass = null;
		for(AbstractProcessManyPage.Listener<?,?> processPageListener : getListeners()){
			Class<?> v = processPageListener.getFormDataClass(this, actionIdentifier);
			if(v!=null)
				formDataClass = v;
		}
		return formDataClass;
	}
	
	@Override
	public void serve(UICommand command, Object parameter) {
		for(AbstractProcessManyPage.Listener<?,?> processPageListener : getListeners())
			processPageListener.serve(this,parameter,actionIdentifier);
	}
	
	private Collection<AbstractProcessManyPage.Listener<?,Object>> getListeners(){
		return primefacesManager.getProcessManyPageListeners(businessEntityInfos.getClazz());
	}
		
	/**/
	
	public interface Listener<ENTITY extends AbstractIdentifiable,IDENTIFIER_TYPE> extends BusinessEntityFormOnePageListener<ENTITY> {

		/**/
		Class<?> getFormDataClass(AbstractProcessManyPage<?> processManyPage, String actionIdentifier);
		Object getFormData(AbstractProcessManyPage<?> processManyPage, String actionIdentifier);
		void serve(AbstractProcessManyPage<?> processManyPage,Object data, String actionIdentifier);
		
		@Getter @Setter
		public static class Adapter<ENTITY_TYPE extends AbstractIdentifiable,IDENTIFIER_TYPE> extends BusinessEntityFormOnePageListener.Adapter<ENTITY_TYPE> implements Listener<ENTITY_TYPE,IDENTIFIER_TYPE>,Serializable {

			private static final long serialVersionUID = -7944074776241690783L;
			
			public Adapter(Class<ENTITY_TYPE> entityTypeClass) {
				super(entityTypeClass);
			}
			@Override
			public Class<?> getFormDataClass(AbstractProcessManyPage<?> processManyPage,String actionIdentifier) {
				return null;
			}
			@Override
			public Object getFormData(AbstractProcessManyPage<?> processManyPage,String actionIdentifier) {
				return null;
			}
			
			@Override
			public void afterInitialisationEnded(AbstractBean bean) {
				super.afterInitialisationEnded(bean);
				final AbstractProcessManyPage<?> processPage = (AbstractProcessManyPage<?>) bean;
				initialiseProcess(processPage);
			}
			
			protected void initialiseProcess(AbstractProcessManyPage<?> processPage){}
						
			@Override
			public void serve(AbstractProcessManyPage<?> processManyPage,Object data, String actionIdentifier) {}
			
			/**/
			
			public static class Default<ENTITY extends AbstractIdentifiable,IDENTIFIER_TYPE> extends Listener.Adapter<ENTITY,IDENTIFIER_TYPE> implements Serializable {

				private static final long serialVersionUID = -4255109770974601234L;

				public Default(Class<ENTITY> entityTypeClass) {
					super(entityTypeClass);
				}
					
			}
		}
		
	}

	/**/
	
	@Getter @Setter
	public static class ProcessItem extends AbstractOutputDetails<AbstractIdentifiable> implements Serializable{
		private static final long serialVersionUID = 2840997923510834856L;
		
		@Input @InputText private String name;
		
		public ProcessItem(AbstractIdentifiable identifiable) {
			super(identifiable);
			name = rootBusinessLayer.getFormatterBusiness().format(identifiable);
		}
	}
}
