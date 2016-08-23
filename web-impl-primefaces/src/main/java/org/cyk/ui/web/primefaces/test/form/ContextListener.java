package org.cyk.ui.web.primefaces.test.form;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import org.cyk.system.root.business.impl.BusinessServiceProvider;
import org.cyk.system.root.business.impl.BusinessServiceProvider.Service;
import org.cyk.system.root.business.impl.party.person.AbstractActorBusinessImpl;
import org.cyk.system.root.model.file.FileIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.information.Comment;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.system.test.business.MyWebManager;
import org.cyk.system.test.model.actor.Actor;
import org.cyk.system.test.model.actor.Actor.SearchCriteria;
import org.cyk.system.test.model.actor.ActorDetails;
import org.cyk.system.test.model.actor.ActorEditPage;
import org.cyk.system.test.model.actor.ActorProcessManyPageAdapter;
import org.cyk.system.test.model.actor.ActorQueryManyFormModel;
import org.cyk.system.test.model.actor.ActorQueryOneFormModel;
import org.cyk.system.test.model.actor.ActorSelectManyPageAdapter;
import org.cyk.system.test.model.actor.ActorSelectOnePageAdapter;
import org.cyk.ui.api.config.IdentifiableConfiguration;
import org.cyk.ui.web.primefaces.AbstractContextListener;
import org.cyk.ui.web.primefaces.page.AbstractProcessManyPage;
import org.cyk.ui.web.primefaces.page.AbstractSelectManyPage;
import org.cyk.ui.web.primefaces.page.AbstractSelectOnePage;
import org.cyk.utility.common.computation.DataReadConfiguration;

@WebListener
public class ContextListener extends AbstractContextListener {

	private static final long serialVersionUID = -3211898049670089807L;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		uiManager.registerApplicationUImanager(MyWebManager.getInstance());
		Comment.define(Actor.class);
		FileIdentifiableGlobalIdentifier.define(Actor.class);
		
	}
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		
		MyWebManager.getInstance().getListeners().add(new org.cyk.system.test.business.PrimefacesManager());
		
		AbstractSelectOnePage.Listener.COLLECTION.add(new ActorSelectOnePageAdapter());
		AbstractSelectManyPage.Listener.COLLECTION.add(new ActorSelectManyPageAdapter());
		AbstractProcessManyPage.Listener.COLLECTION.add(new ActorProcessManyPageAdapter());
		
		AbstractSelectManyPage.Listener.COLLECTION.add(new PersonSelectManyPageAdapter());

	}
	
	@Override
	protected void identifiableConfiguration(ServletContextEvent event) {
		super.identifiableConfiguration(event);
		
		uiManager.businessEntityInfos(UserAccount.class).getUserInterface().setEditViewId("useraccountcrudone");
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(Actor.class, ActorEditPage.Form.class, ActorDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(Actor.class, null);
		
		BusinessServiceProvider.Identifiable.COLLECTION.add(new AbstractActorBusinessImpl.BusinessServiceProviderIdentifiable<Actor,Actor.SearchCriteria>(Actor.class){
			private static final long serialVersionUID = 1322416788278558869L;
			
			@Override
			protected SearchCriteria createSearchCriteria(Service service,DataReadConfiguration dataReadConfiguration) {
				return new Actor.SearchCriteria(dataReadConfiguration.getGlobalFilter());
			}
        });
		
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
	
}
