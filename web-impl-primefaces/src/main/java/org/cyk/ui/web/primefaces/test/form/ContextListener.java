package org.cyk.ui.web.primefaces.test.form;

import java.util.Collection;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import org.cyk.system.root.business.api.BusinessAdapter;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonSearchCriteria;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.system.root.ui.web.primefaces.api.RootWebManager;
import org.cyk.ui.test.model.Actor;
import org.cyk.ui.web.primefaces.AbstractContextListener;
import org.cyk.ui.web.primefaces.test.business.ActorBusiness;
import org.cyk.ui.web.primefaces.test.business.MyWebManager;
import org.cyk.utility.common.computation.DataReadConfiguration;

@WebListener
public class ContextListener extends AbstractContextListener {

	private static final long serialVersionUID = -3211898049670089807L;

	@Inject private PersonBusiness personBusiness;
	@Inject private ActorBusiness actorBusiness;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		uiManager.registerApplicationUImanager(MyWebManager.getInstance());
		uiManager.registerApplicationUImanager(RootWebManager.getInstance());	
		
	}
	
	@Override
	protected void addUrls(ServletContextEvent event) {
		super.addUrls(event);
		uniformResourceLocatorBusiness.setFilteringEnabled(Boolean.TRUE);
		addUrl(rootBusinessLayer.getUserRole().getCode(),"/private/index.jsf");
		//addUrl(rootBusinessLayer.getUserRole().getCode(),"/private/__tools__/crud/crudmany.jsf",uiManager.getClassParameter(),uiManager.businessEntityInfos(Actor.class).getIdentifier());
		addCrudUrl(rootBusinessLayer.getUserRole().getCode(), Actor.class, Boolean.TRUE,Crud.CREATE);
		/*
		addUrl(RootBusinessLayer.getInstance().getUserRole().getCode(),"/private/__tools__/crud/");
		
		addUrl(RootBusinessLayer.getInstance().getUserRole().getCode(),"/index.jsf");
		
		UniformResourceLocator uniformResourceLocator = new UniformResourceLocator("/private/__tools__/crud/crudone.jsf");
		uniformResourceLocator.addParameter(uiManager.getCrudParameter(),uiManager.getCrudCreateParameter());
		uniformResourceLocator.addParameter(uiManager.getClassParameter(),uiManager.businessEntityInfos(Person.class).getIdentifier());
		addUrl(RootBusinessLayer.getInstance().getUserRole().getCode(),uniformResourceLocator);
				
		addUrl(RootBusinessLayer.getInstance().getUserRole().getCode(),"/private/__tools__/crud/crudmany.jsf",uiManager.getClassParameter(),uiManager.businessEntityInfos(Actor.class).getIdentifier());
		*/
	}
	
	@Override
	protected void identifiableConfiguration(ServletContextEvent event) {
		super.identifiableConfiguration(event);
		
		//IdentifiableConfiguration configuration = new IdentifiableConfiguration(Actor.class,DefaultActorEditFormModel.class,DefaultActorReadFormModel.class);
		//uiManager.registerConfiguration(configuration);
		
		uiManager.businessEntityInfos(UserAccount.class).setUiEditViewId("useraccountcrudone");
		
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
				}
				return super.find(dataClass, configuration);
			}
			
			@Override
			public <T extends AbstractIdentifiable> Long count(Class<T> dataClass, DataReadConfiguration configuration) {
				if(Person.class.equals(dataClass)){
					return personBusiness.countByCriteria(new PersonSearchCriteria(configuration.getGlobalFilter()));
				}else if(Actor.class.equals(dataClass)){
					return actorBusiness.countAll();
				}
				return super.count(dataClass, configuration);
			}
		});	
		/*
		primefacesManager.getBusinessEntityFormOnePageListeners().add(new BusinessEntityFormOnePageAdapter<Person>(){
			private static final long serialVersionUID = 7115590731648449187L;
			@Override
			public Class<Person> getEntityTypeClass() {
				return Person.class;
			}
			@Override
			public void initialised(AbstractBusinessEntityFormOnePage<? extends AbstractIdentifiable> page) {
				page.getForm().getControlSetListeners().add(new ControlSetAdapter<Object>(){
					@Override
					public Boolean build(Field field) {
						return field.getName().equals("name");
					}
				});
			}
			
		});
		*/
		
		//primefacesManager.getBusinessEntityFormOnePageListeners().add(new DefaultActorCrudOnePageListener<Actor>(Actor.class));
		//primefacesManager.getBusinessEntityFormManyPageListeners().add(new DefaultActorCrudManyPageListener<Actor>(Actor.class));
	}

	
	
}
