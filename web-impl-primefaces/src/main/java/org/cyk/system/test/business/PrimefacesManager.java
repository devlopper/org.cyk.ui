package org.cyk.system.test.business;

import java.io.Serializable;

import org.cyk.system.root.business.impl.party.person.PersonDetails;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.test.model.actor.Actor;
import org.cyk.system.test.model.actor.ActorDetails;
import org.cyk.system.test.model.actor.ActorEditPage;
import org.cyk.ui.api.command.menu.SystemMenu;
import org.cyk.ui.web.primefaces.UserSession;
import org.cyk.ui.web.primefaces.data.collector.control.ControlSetAdapter;
import org.cyk.ui.web.primefaces.page.DetailsConfiguration;
import org.cyk.ui.web.primefaces.page.party.PersonEditPage;

public class PrimefacesManager extends org.cyk.ui.web.primefaces.adapter.enterpriseresourceplanning.PrimefacesManager implements Serializable {

	private static final long serialVersionUID = -8716834916609095637L;

	public PrimefacesManager() {
		configurePersonFormConfiguration(Person.class,null,new String[]{PersonEditPage.Form.FIELD_BLOOD_GROUP});
		registerDetailsConfiguration(PersonDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			@Override
			public ControlSetAdapter getFormControlSetAdapter(Class clazz) {
				return new PersonDetailsControlSetAdapter(new String[]{PersonDetails.FIELD_BLOOD_GROUP});
			}
		});
		
		configurePersonFormConfiguration(Actor.class,null,new String[]{ActorEditPage.Form.FIELD_BLOOD_GROUP});
		registerDetailsConfiguration(ActorDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			@Override
			public ControlSetAdapter getFormControlSetAdapter(Class clazz) {
				return new PersonDetailsControlSetAdapter(new String[]{ActorDetails.FIELD_BLOOD_GROUP});
			}
		});
	}
	
	@Override
	public SystemMenu getSystemMenu(UserSession userSession) {
		return SystemMenuBuilder.getInstance().build(userSession);
	}
	
	@Override
	protected Boolean isAutoConfigureClass(Class<?> actorClass) {
		return !actorClass.equals(Actor.class);
	}
}
