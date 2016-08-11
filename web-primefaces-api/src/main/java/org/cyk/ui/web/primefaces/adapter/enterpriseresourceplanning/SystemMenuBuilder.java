package org.cyk.ui.web.primefaces.adapter.enterpriseresourceplanning;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.event.EventMissed;
import org.cyk.system.root.model.event.EventMissedReason;
import org.cyk.system.root.model.event.EventParty;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.FileIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.geography.Contact;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.Country;
import org.cyk.system.root.model.geography.ElectronicMail;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.geography.LocalityType;
import org.cyk.system.root.model.geography.LocationType;
import org.cyk.system.root.model.geography.PhoneNumber;
import org.cyk.system.root.model.geography.PhoneNumberType;
import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.system.root.model.party.person.JobFunction;
import org.cyk.system.root.model.party.person.JobTitle;
import org.cyk.system.root.model.party.person.MaritalStatus;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonTitle;
import org.cyk.system.root.model.party.person.Sex;
import org.cyk.ui.api.Icon;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.menu.SystemMenu;
import org.cyk.ui.web.primefaces.AbstractSystemMenuBuilder;
import org.cyk.ui.web.primefaces.Commandable;
import org.cyk.ui.web.primefaces.UserSession;

public class SystemMenuBuilder extends AbstractSystemMenuBuilder implements Serializable {

	private static final long serialVersionUID = 6995162040038809581L;

	private static SystemMenuBuilder INSTANCE;
	
	@Override
	public SystemMenu build(UserSession userSession) {
		SystemMenu systemMenu = new SystemMenu();
		addBusinessMenu(userSession,systemMenu,getEventCommandable(userSession, null));
		addBusinessMenu(userSession,systemMenu,getFileCommandable(userSession, null));
		addBusinessMenu(userSession,systemMenu,getGeographyCommandable(userSession, null));
		addBusinessMenu(userSession,systemMenu,getPersonCommandable(userSession, null));
		addBusinessMenu(userSession,systemMenu,getMathematicsCommandable(userSession, null));
		return systemMenu;
	}
	
	public Commandable getEventCommandable(UserSession userSession,Collection<UICommandable> mobileCommandables){
		Commandable module = createModuleCommandable(Event.class, Icon.THING_CALENDAR);
		module.addChild(createListCommandable(EventMissedReason.class, null));
		module.addChild(createListCommandable(Event.class, null));
		module.addChild(createListCommandable(EventParty.class, null));
		module.addChild(createListCommandable(EventMissed.class, null));
		/*module.addChild(createListCommandable(EventReminder.class, null));
		module.addChild(createListCommandable(EventRepetition.class, null));
		module.addChild(createListCommandable(NotificationTemplate.class, null));*/
		return module;
	}
	
	public Commandable getFileCommandable(UserSession userSession,Collection<UICommandable> mobileCommandables){
		Commandable module = createModuleCommandable(File.class, Icon.THING_FILE);
		module.addChild(createListCommandable(File.class, null));
		module.addChild(createListCommandable(FileIdentifiableGlobalIdentifier.class, null));
		/*module.addChild(createListCommandable(Script.class, null));
		module.addChild(createListCommandable(ScriptVariable.class, null));*/
		return module;
	}
	
	public Commandable getGeographyCommandable(UserSession userSession,Collection<UICommandable> mobileCommandables){
		Commandable module = createModuleCommandable(Contact.class, Icon.THING_LOCATION_ARROW);
		module.addChild(createListCommandable(LocalityType.class, null));
		module.addChild(createListCommandable(Locality.class, null));
		module.addChild(createListCommandable(Country.class, null));
		module.addChild(createListCommandable(ContactCollection.class, null));
		module.addChild(createListCommandable(ElectronicMail.class, null));
		module.addChild(createListCommandable(LocationType.class, null));
		//module.addChild(createListCommandable(Location.class, null));
		module.addChild(createListCommandable(PhoneNumberType.class, null));
		module.addChild(createListCommandable(PhoneNumber.class, null));
		//module.addChild(createListCommandable(PostalBox.class, null));
		//module.addChild(createListCommandable(Website.class, null));
		return module;
	}
	
	public Commandable getPersonCommandable(UserSession userSession,Collection<UICommandable> mobileCommandables){
		Commandable module = createModuleCommandable(Person.class, Icon.PERSON);
		module.addChild(createListCommandable(Person.class, null));
		module.addChild(createListCommandable(JobFunction.class, null));
		module.addChild(createListCommandable(JobTitle.class, null));
		module.addChild(createListCommandable(MaritalStatus.class, null));
		module.addChild(createListCommandable(PersonTitle.class, null));
		module.addChild(createListCommandable(Sex.class, null));
		return module;
	}
	
	public Commandable getMathematicsCommandable(UserSession userSession,Collection<UICommandable> mobileCommandables){
		Commandable module = createModuleCommandable("mathematiques", Icon.PERSON);
		module.addChild(createListCommandable(MovementCollection.class, null));
		module.addChild(createListCommandable(Movement.class, null));
		return module;
	}
	
	public static SystemMenuBuilder getInstance(){
		if(INSTANCE==null)
			INSTANCE = new SystemMenuBuilder();
		return INSTANCE;
	}
}
