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
import org.cyk.ui.api.AbstractUserSession;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.config.IdentifiableConfiguration;
import org.cyk.ui.api.model.PersonFormModel;
import org.cyk.ui.test.model.Actor;
import org.cyk.ui.web.primefaces.AbstractContextListener;
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
		UIManager.FORM_MODEL_MAP.put("pfm1", PersonFormModel.class);
		UIManager.FORM_MODEL_MAP.put("pfm2", PersonFormModel2.class);
		UIManager.FORM_MODEL_MAP.put("pfm3", PersonFormModel3.class);
		
		UIManager.FORM_MODEL_MAP.put("ltfm1", LocalityTypeFormModel.class);
		//uiManager.businessEntityInfos(Person.class).setUiEditViewId("crudperson");
		IdentifiableConfiguration config = new IdentifiableConfiguration(Person.class, PersonFormModel.class);
		config.setFileSupport(Boolean.TRUE);
		uiManager.registerConfiguration(config);
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(Actor.class, ActorFormModel.class));
		
		uiManager.businessEntityInfos(UserAccount.class).setUiEditViewId("useraccountcrudone");
		
		uiManager.getBusinesslisteners().add(new BusinessAdapter(){
			private static final long serialVersionUID = 4605368263736933413L;
			@SuppressWarnings("unchecked")
			@Override
			public <T extends AbstractIdentifiable> Collection<T> find(Class<T> dataClass, Integer first, Integer pageSize, String sortField, Boolean ascendingOrder,
					String filter) {
				if(Person.class.equals(dataClass)){
					return (Collection<T>) personBusiness.findByCriteria(new PersonSearchCriteria(filter));
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
	}
	
	@Override
	protected Long alarmScanningPeriod() {
		return 1000 * 15l;
	}
	
	@Override
	public String homeUrl(AbstractUserSession userSession) {
		//return webNavigationManager.url("test",new Object[]{},Boolean.FALSE,Boolean.FALSE);
		return null;//webNavigationManager.createManyUrl(uiManager.businessEntityInfos(Person.class),Boolean.FALSE,Boolean.FALSE);
	}
	
}
