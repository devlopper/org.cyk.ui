package org.cyk.ui.web.primefaces.test.form;

import java.lang.reflect.Field;
import java.util.Collection;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import org.cyk.system.root.business.api.BusinessAdapter;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonSearchCriteria;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.system.root.ui.web.primefaces.api.RootWebManager;
import org.cyk.ui.api.data.collector.form.FormConfiguration;
import org.cyk.ui.api.model.party.AbstractActorEditFormModel;
import org.cyk.ui.api.model.party.DefaultActorReadFormModel;
import org.cyk.ui.api.model.party.DefaultPersonEditFormModel;
import org.cyk.ui.test.model.Actor;
import org.cyk.ui.web.primefaces.AbstractContextListener;
import org.cyk.ui.web.primefaces.data.collector.control.ControlSetAdapter;
import org.cyk.ui.web.primefaces.page.BusinessEntityFormManyPageListener;
import org.cyk.ui.web.primefaces.page.BusinessEntityFormOnePageListener;
import org.cyk.ui.web.primefaces.page.crud.AbstractActorConsultPage;
import org.cyk.ui.web.primefaces.page.crud.AbstractActorConsultPage.MainDetails;
import org.cyk.ui.web.primefaces.page.tools.AbstractActorConsultPageAdapter;
import org.cyk.ui.web.primefaces.test.business.ActorBusiness;
import org.cyk.ui.web.primefaces.test.business.ActorQueryFormModel;
import org.cyk.ui.web.primefaces.test.business.MyWebManager;
import org.cyk.utility.common.computation.DataReadConfiguration;

@WebListener
public class ContextListener extends AbstractContextListener {

	private static final long serialVersionUID = -3211898049670089807L;

	@Inject private PersonBusiness personBusiness;
	@Inject private ActorBusiness actorBusiness;
	@Inject private FileBusiness fileBusiness;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		uiManager.registerApplicationUImanager(MyWebManager.getInstance());
		uiManager.registerApplicationUImanager(RootWebManager.getInstance());	
		
	}
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		primefacesManager.getSelectPageListeners().add(new ActorSelectPageAdapter());
	}
	
	@Override
	protected void addUrls(ServletContextEvent event) {
		super.addUrls(event);
		//uniformResourceLocatorBusiness.setFilteringEnabled(Boolean.FALSE);
		//addUrl(rootBusinessLayer.getUserRole().getCode(),"/private/index.jsf");
		//addCrudUrl(rootBusinessLayer.getUserRole().getCode(), Actor.class, Boolean.TRUE,Crud.CREATE);
	}
	
	@Override
	protected void identifiableConfiguration(ServletContextEvent event) {
		super.identifiableConfiguration(event);
		
		uiManager.businessEntityInfos(UserAccount.class).getUserInterface().setEditViewId("useraccountcrudone");
		
		uiManager.getBusinesslisteners().add(new BusinessAdapter(){
			private static final long serialVersionUID = 4605368263736933413L;
			@SuppressWarnings("unchecked")
			@Override
			public <T extends AbstractIdentifiable> Collection<T> find(Class<T> dataClass, DataReadConfiguration configuration) {
				if(Person.class.equals(dataClass)){
					PersonSearchCriteria p = new PersonSearchCriteria(configuration.getGlobalFilter());
					p.getReadConfig().set(configuration);
					return (Collection<T>) personBusiness.findByCriteria(p);
				}else if(Actor.class.equals(dataClass)){
					return (Collection<T>) actorBusiness.findAll();
				}else if(File.class.equals(dataClass)){
					return (Collection<T>) fileBusiness.findAll();
				}
				return super.find(dataClass, configuration);
			}
			
			@Override
			public <T extends AbstractIdentifiable> Long count(Class<T> dataClass, DataReadConfiguration configuration) {
				if(Person.class.equals(dataClass)){
					return personBusiness.countByCriteria(new PersonSearchCriteria(configuration.getGlobalFilter()));
				}else if(Actor.class.equals(dataClass)){
					return actorBusiness.countAll();
				}else if(File.class.equals(dataClass)){
					return fileBusiness.countAll();
				}
				return super.count(dataClass, configuration);
			}
		});	
	}
		
	@Override
	protected <IDENTIFIABLE extends AbstractIdentifiable> void registerBusinessEntityFormOnePageListener(Class<IDENTIFIABLE> aClass,BusinessEntityFormOnePageListener<?> listener) {
		super.registerBusinessEntityFormOnePageListener(aClass, listener);
		if(aClass.equals(Actor.class)){
			listener.getFormConfigurationMap().get(Crud.CREATE).get(FormConfiguration.TYPE_INPUT_SET_SMALLEST).addFieldNames(DefaultPersonEditFormModel.FIELD_SURNAME
					,DefaultPersonEditFormModel.FIELD_TITLE,DefaultPersonEditFormModel.FIELD_BIRTH_DATE,DefaultPersonEditFormModel.FIELD_BIRTH_LOCATION
					,DefaultPersonEditFormModel.FIELD_SEX,DefaultPersonEditFormModel.FIELD_IMAGE,DefaultPersonEditFormModel.FIELD_SIGNATURE_SPECIMEN);
			listener.getFormConfigurationMap().get(Crud.CREATE).get(FormConfiguration.TYPE_INPUT_SET_SMALLEST).addRequiredFieldNames(
					AbstractActorEditFormModel.FIELD_REGISTRATION_CODE);
			
			
		}
	}
	
	@Override
	protected <IDENTIFIABLE extends AbstractIdentifiable> void registerBusinessEntityFormManyPageListener(Class<IDENTIFIABLE> aClass,BusinessEntityFormManyPageListener<?> listener) {
		if(aClass.equals(Actor.class)){
			listener.getFormConfigurationMap().get(Crud.READ).get(FormConfiguration.TYPE_INPUT_SET_SMALLEST).addRequiredFieldNames(DefaultActorReadFormModel.FIELD_REGISTRATION_CODE);
		}
		
		super.registerBusinessEntityFormManyPageListener(aClass, listener);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected <ACTOR extends AbstractActor> AbstractActorConsultPageAdapter<ACTOR> getActorConsultPageAdapter(Class<ACTOR> actorClass) {
		if(actorClass.equals(Actor.class)){
			return (AbstractActorConsultPageAdapter<ACTOR>) new ActorConsultPageAdapter();
		}
		return super.getActorConsultPageAdapter(actorClass);
	}
	
	@Override
	protected Class<?> getQueryFormModelClass(Class<?> clazz) {
		if(Actor.class.equals(clazz))
			return ActorQueryFormModel.class;
		return super.getQueryFormModelClass(clazz);
	}
	
	/**/
	
	private static class ActorConsultPageAdapter extends AbstractActorConsultPage.Adapter<Actor>{

		private static final long serialVersionUID = -5657492205127185872L;

		public ActorConsultPageAdapter() {
			super(Actor.class);
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public <DETAILS> ControlSetAdapter<DETAILS> getControlSetAdapter(Class<DETAILS> detailsClass) {
			if(MainDetails.class.equals(detailsClass)){
				return (ControlSetAdapter<DETAILS>) new ControlSetAdapter<MainDetails>(){
					@Override
					public Boolean build(Field field) {
						return true;//field.getName().equals("photo");
					}
				};
			}
			return super.getControlSetAdapter(detailsClass);
		}
		
	}
	
}
