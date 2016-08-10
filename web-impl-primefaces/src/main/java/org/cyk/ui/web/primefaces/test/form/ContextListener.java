package org.cyk.ui.web.primefaces.test.form;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import org.cyk.system.root.business.impl.BusinessServiceProvider;
import org.cyk.system.root.business.impl.BusinessServiceProvider.Service;
import org.cyk.system.root.business.impl.party.person.AbstractActorBusinessImpl;
import org.cyk.system.root.model.file.FileIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.information.Comment;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.ui.test.model.Actor;
import org.cyk.ui.test.model.Actor.SearchCriteria;
import org.cyk.ui.web.primefaces.AbstractContextListener;
import org.cyk.ui.web.primefaces.page.AbstractProcessManyPage;
import org.cyk.ui.web.primefaces.page.AbstractSelectManyPage;
import org.cyk.ui.web.primefaces.page.AbstractSelectOnePage;
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
		Comment.define(Actor.class);
		FileIdentifiableGlobalIdentifier.define(Actor.class);
		
	}
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		
		MyWebManager.getInstance().getListeners().add(new org.cyk.ui.web.primefaces.adapter.enterpriseresourceplanning.PrimefacesManagerAdapter());
		
		AbstractSelectOnePage.Listener.COLLECTION.add(new ActorSelectOnePageAdapter());
		AbstractSelectManyPage.Listener.COLLECTION.add(new ActorSelectManyPageAdapter());
		AbstractProcessManyPage.Listener.COLLECTION.add(new ActorProcessManyPageAdapter());
		
		AbstractSelectManyPage.Listener.COLLECTION.add(new PersonSelectManyPageAdapter());
		
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
