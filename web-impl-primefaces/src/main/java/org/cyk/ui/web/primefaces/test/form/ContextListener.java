package org.cyk.ui.web.primefaces.test.form;

import java.util.Collection;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import org.cyk.system.root.business.api.BusinessAdapter;
import org.cyk.system.root.business.api.party.PersonBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.party.Person;
import org.cyk.system.root.model.party.PersonSearchCriteria;
import org.cyk.ui.api.MenuManager.Type;
import org.cyk.ui.api.UserSession;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UICommandable.IconType;
import org.cyk.ui.api.command.UIMenu;
import org.cyk.ui.api.config.IdentifiableConfiguration;
import org.cyk.ui.web.primefaces.AbstractContextListener;

@WebListener
public class ContextListener extends AbstractContextListener {

	private static final long serialVersionUID = -3211898049670089807L;

	@Inject private PersonBusiness personBusiness;
	
	@Override
	protected void identifiableConfiguration(ServletContextEvent event) {
		super.identifiableConfiguration(event);
		IdentifiableConfiguration config = new IdentifiableConfiguration(Person.class, PersonFormModel.class);
		uiManager.registerConfiguration(config);
		
		uiManager.getBusinesslisteners().add(new BusinessAdapter(){
			private static final long serialVersionUID = 4605368263736933413L;
			@SuppressWarnings("unchecked")
			@Override
			public <T extends AbstractIdentifiable> Collection<T> find(Class<T> dataClass, Integer first, Integer pageSize, String sortField, Boolean ascendingOrder,
					String filter) {
				if(Person.class.equals(dataClass)){
					return (Collection<T>) personBusiness.findByCriteria(new PersonSearchCriteria(filter));
				}
				return super.find(dataClass, first, pageSize, sortField, ascendingOrder, filter);
			}
			
			@Override
			public <T extends AbstractIdentifiable> Long count(Class<T> dataClass, String filter) {
				if(Person.class.equals(dataClass)){
					return personBusiness.countByCriteria(new PersonSearchCriteria((String) filter));
				}
				return super.count(dataClass, filter);
			}
		});
		
	}
	
	@Override
	public void menu(UserSession userSession, UIMenu menu, Type type) {
		UICommandable commandable;
		menu.getCommandables().add(menuManager.crudMany(Person.class, IconType.PERSON));
		menu.getCommandables().add(commandable = menuManager.commandable("command.search", IconType.ACTION_SEARCH));
		commandable.setViewId("personsearch");
	}
	
	@Override
	public String homeUrl(UserSession userSession) {
		return webNavigationManager.createManyUrl(uiManager.businessEntityInfos(Person.class),Boolean.FALSE,Boolean.FALSE);
	}

}
