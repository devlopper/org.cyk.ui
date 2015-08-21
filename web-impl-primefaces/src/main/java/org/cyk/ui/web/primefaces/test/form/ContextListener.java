package org.cyk.ui.web.primefaces.test.form;

import java.util.Collection;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import org.cyk.system.root.business.api.BusinessAdapter;
import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonSearchCriteria;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.system.root.ui.web.primefaces.api.RootWebManager;
import org.cyk.ui.test.model.Actor;
import org.cyk.ui.web.primefaces.AbstractContextListener;
import org.cyk.ui.web.primefaces.page.tools.DefaultActorCrudManyPageListener;
import org.cyk.ui.web.primefaces.page.tools.DefaultActorCrudOnePageListener;
import org.cyk.ui.web.primefaces.test.business.ActorBusiness;
import org.cyk.ui.web.primefaces.test.business.MyWebManager;

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
	protected void identifiableConfiguration(ServletContextEvent event) {
		super.identifiableConfiguration(event);
		/*UIManager.FORM_MODEL_MAP.put("pfm1", PersonFormModel.class);
		UIManager.FORM_MODEL_MAP.put("pfm2", PersonFormModel2.class);
		UIManager.FORM_MODEL_MAP.put("pfm3", PersonFormModel3.class);*/
		
		//UIManager.FORM_MODEL_MAP.put("ltfm1", LocalityTypeFormModel.class);
		//UIManager.DEFAULT_MANY_FORM_MODEL_MAP.put(Actor.class, ActorConsultFormModel.class);
		//uiManager.businessEntityInfos(Actor.class).setUiListViewId("pfm1");
		
		//uiManager.businessEntityInfos(Person.class).setUiEditViewId("crudperson");
		
		/*
		IdentifiableConfiguration config = new IdentifiableConfiguration(Person.class, PersonFormModel.class);
		config.setFileSupport(Boolean.TRUE);
		uiManager.registerConfiguration(config);
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(Actor.class, ActorFormModel.class));
		*/
		
		uiManager.businessEntityInfos(UserAccount.class).setUiEditViewId("useraccountcrudone");
		
		uiManager.getBusinesslisteners().add(new BusinessAdapter(){
			private static final long serialVersionUID = 4605368263736933413L;
			@SuppressWarnings("unchecked")
			@Override
			public <T extends AbstractIdentifiable> Collection<T> find(Class<T> dataClass, Integer first, Integer pageSize, String sortField, Boolean ascendingOrder,
					String filter) {
				if(Person.class.equals(dataClass)){
					PersonSearchCriteria p = new PersonSearchCriteria(filter);
					p.getReadConfig().setFirstResultIndex(first.longValue());
					p.getReadConfig().setMaximumResultCount(pageSize.longValue());
					return (Collection<T>) personBusiness.findByCriteria(p);
				}else if(Actor.class.equals(dataClass)){
					return (Collection<T>) actorBusiness.findAll();
				}
				return super.find(dataClass, first, pageSize, sortField, ascendingOrder, filter);
			}
			
			@Override
			public <T extends AbstractIdentifiable> Long count(Class<T> dataClass, String filter) {
				if(Person.class.equals(dataClass)){
					return personBusiness.countByCriteria(new PersonSearchCriteria((String) filter));
				}else if(Actor.class.equals(dataClass)){
					return actorBusiness.countAll();
				}
				return super.count(dataClass, filter);
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
		
		primefacesManager.getBusinessEntityFormOnePageListeners().add(new DefaultActorCrudOnePageListener<Actor>(Actor.class));
		primefacesManager.getBusinessEntityFormManyPageListeners().add(new DefaultActorCrudManyPageListener<Actor>(Actor.class));
	}
			
}
