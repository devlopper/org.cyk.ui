package org.cyk.ui.web.primefaces.test.form;

import java.lang.reflect.Field;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.impl.BusinessServiceProvider;
import org.cyk.system.root.business.impl.BusinessServiceProvider.Service;
import org.cyk.system.root.business.impl.party.person.AbstractActorBusinessImpl;
import org.cyk.system.root.model.file.FileIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.information.Comment;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.system.root.ui.web.primefaces.api.RootWebManager;
import org.cyk.ui.api.data.collector.form.FormConfiguration;
import org.cyk.ui.api.model.party.AbstractActorEditFormModel;
import org.cyk.ui.api.model.party.DefaultActorOutputDetails;
import org.cyk.ui.api.model.party.DefaultPersonEditFormModel;
import org.cyk.ui.test.model.Actor;
import org.cyk.ui.test.model.Actor.SearchCriteria;
import org.cyk.ui.web.primefaces.AbstractContextListener;
import org.cyk.ui.web.primefaces.data.collector.control.ControlSetAdapter;
import org.cyk.ui.web.primefaces.page.AbstractProcessManyPage;
import org.cyk.ui.web.primefaces.page.AbstractSelectManyPage;
import org.cyk.ui.web.primefaces.page.AbstractSelectOnePage;
import org.cyk.ui.web.primefaces.page.party.AbstractActorConsultPage;
import org.cyk.ui.web.primefaces.page.party.AbstractActorConsultPage.MainDetails;
import org.cyk.ui.web.primefaces.page.party.AbstractActorConsultPageAdapter;
import org.cyk.ui.web.primefaces.page.party.AbstractActorCrudManyPageAdapter;
import org.cyk.ui.web.primefaces.page.party.AbstractActorCrudOnePageAdapter;
import org.cyk.ui.web.primefaces.test.business.ActorQueryManyFormModel;
import org.cyk.ui.web.primefaces.test.business.ActorQueryOneFormModel;
import org.cyk.ui.web.primefaces.test.business.MyWebManager;
import org.cyk.utility.common.computation.DataReadConfiguration;

@WebListener
public class ContextListener extends AbstractContextListener {

	private static final long serialVersionUID = -3211898049670089807L;

	@Override
	protected void initialisation() {
		super.initialisation();
		uiManager.registerApplicationUImanager(MyWebManager.getInstance());
		uiManager.registerApplicationUImanager(RootWebManager.getInstance());	
		Comment.define(Actor.class);
		FileIdentifiableGlobalIdentifier.define(Actor.class);
	}
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		AbstractSelectOnePage.Listener.COLLECTION.add(new ActorSelectOnePageAdapter());
		AbstractSelectManyPage.Listener.COLLECTION.add(new ActorSelectManyPageAdapter());
		AbstractProcessManyPage.Listener.COLLECTION.add(new ActorProcessManyPageAdapter());
		
		AbstractSelectManyPage.Listener.COLLECTION.add(new PersonSelectManyPageAdapter());
		
		/*
		uiManager.registerConfiguration(new IdentifiableConfiguration(Actor.class, AbstractActorQueryOneFormModel.Default.class
				, ActorOutputDetails.class,null,AbstractActorQueryManyFormModel.Default.class));
		
		uiManager.configBusinessIdentifiable(Actor.class, null);
		webNavigationManager.useDynamicSelectView(Actor.class);
		*/
	}
	
	@Override
	protected void identifiableConfiguration(ServletContextEvent event) {
		super.identifiableConfiguration(event);
		
		uiManager.businessEntityInfos(UserAccount.class).getUserInterface().setEditViewId("useraccountcrudone");
		
		BusinessServiceProvider.Identifiable.COLLECTION.add(new AbstractActorBusinessImpl.BusinessServiceProviderIdentifiable<Actor,Actor.SearchCriteria>(Actor.class){
			private static final long serialVersionUID = 1322416788278558869L;
			
			@Override
			protected SearchCriteria createSearchCriteria(Service service,DataReadConfiguration dataReadConfiguration) {
				return new Actor.SearchCriteria(dataReadConfiguration.getGlobalFilter());
			}
        });
		
	}
	
	@Override
	protected <ACTOR extends AbstractActor> AbstractActorCrudOnePageAdapter<ACTOR> getActorCrudOnePageAdapter(Class<ACTOR> actorClass) {
		AbstractActorCrudOnePageAdapter<ACTOR> listener = super.getActorCrudOnePageAdapter(actorClass);
		if(listener.getEntityTypeClass().equals(Actor.class)){
			listener.getFormConfigurationMap().get(Crud.CREATE).get(FormConfiguration.TYPE_INPUT_SET_SMALLEST).addFieldNames(DefaultPersonEditFormModel.FIELD_SURNAME
					,DefaultPersonEditFormModel.FIELD_TITLE,DefaultPersonEditFormModel.FIELD_BIRTH_DATE,DefaultPersonEditFormModel.FIELD_BIRTH_LOCATION
					,DefaultPersonEditFormModel.FIELD_SEX,DefaultPersonEditFormModel.FIELD_IMAGE,DefaultPersonEditFormModel.FIELD_SIGNATURE_SPECIMEN);
			listener.getFormConfigurationMap().get(Crud.CREATE).get(FormConfiguration.TYPE_INPUT_SET_SMALLEST).addRequiredFieldNames(
					AbstractActorEditFormModel.FIELD_REGISTRATION_CODE);
			
			
		}
		return listener;
	}
	
	@Override
	protected <ACTOR extends AbstractActor> AbstractActorCrudManyPageAdapter<ACTOR> getActorCrudManyPageAdapter(Class<ACTOR> actorClass) {
		AbstractActorCrudManyPageAdapter<ACTOR> listener = super.getActorCrudManyPageAdapter(actorClass);
		if(listener.getEntityTypeClass().equals(Actor.class)){
			listener.getFormConfigurationMap().get(Crud.READ).get(FormConfiguration.TYPE_INPUT_SET_SMALLEST).addRequiredFieldNames(DefaultActorOutputDetails.FIELD_CODE);
		}
		return listener;
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
	protected Class<?> getQueryOneFormModelClass(Class<?> clazz) {
		if(Actor.class.equals(clazz))
			return ActorQueryOneFormModel.class;
		return super.getQueryOneFormModelClass(clazz);
	}
	
	@Override
	protected Class<?> getQueryManyFormModelClass(Class<?> clazz) {
		if(Actor.class.equals(clazz))
			return ActorQueryManyFormModel.class;
		return super.getQueryManyFormModelClass(clazz);
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
