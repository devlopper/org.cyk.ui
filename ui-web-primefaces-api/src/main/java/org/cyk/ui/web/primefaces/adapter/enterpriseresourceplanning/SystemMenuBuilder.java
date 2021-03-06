package org.cyk.ui.web.primefaces.adapter.enterpriseresourceplanning;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.business.api.security.BusinessServiceCollectionBusiness;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.event.EventMissed;
import org.cyk.system.root.model.event.EventMissedReason;
import org.cyk.system.root.model.event.EventParty;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.FileIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.file.FileRepresentationType;
import org.cyk.system.root.model.file.report.ReportFile;
import org.cyk.system.root.model.file.report.ReportTemplate;
import org.cyk.system.root.model.geography.Contact;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.Country;
import org.cyk.system.root.model.geography.ElectronicMailAddress;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.geography.LocalityType;
import org.cyk.system.root.model.geography.Location;
import org.cyk.system.root.model.geography.LocationType;
import org.cyk.system.root.model.geography.PhoneNumber;
import org.cyk.system.root.model.geography.PhoneNumberType;
import org.cyk.system.root.model.geography.PostalBox;
import org.cyk.system.root.model.geography.Website;
import org.cyk.system.root.model.language.Language;
import org.cyk.system.root.model.language.programming.Script;
import org.cyk.system.root.model.language.programming.ScriptEvaluationEngine;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.IntervalCollection;
import org.cyk.system.root.model.mathematics.Metric;
import org.cyk.system.root.model.mathematics.MetricCollection;
import org.cyk.system.root.model.mathematics.MetricCollectionType;
import org.cyk.system.root.model.mathematics.MetricValue;
import org.cyk.system.root.model.mathematics.movement.Movement;
import org.cyk.system.root.model.mathematics.movement.MovementAction;
import org.cyk.system.root.model.mathematics.movement.MovementCollection;
import org.cyk.system.root.model.message.SmtpProperties;
import org.cyk.system.root.model.network.Computer;
import org.cyk.system.root.model.network.Service;
import org.cyk.system.root.model.network.UniformResourceLocator;
import org.cyk.system.root.model.party.Application;
import org.cyk.system.root.model.party.person.Allergy;
import org.cyk.system.root.model.party.person.BloodGroup;
import org.cyk.system.root.model.party.person.JobFunction;
import org.cyk.system.root.model.party.person.JobTitle;
import org.cyk.system.root.model.party.person.MaritalStatus;
import org.cyk.system.root.model.party.person.Medication;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonRelationship;
import org.cyk.system.root.model.party.person.PersonRelationshipType;
import org.cyk.system.root.model.party.person.PersonRelationshipTypeGroup;
import org.cyk.system.root.model.party.person.PersonRelationshipTypeRole;
import org.cyk.system.root.model.party.person.PersonRelationshipTypeRoleName;
import org.cyk.system.root.model.party.person.PersonTitle;
import org.cyk.system.root.model.party.person.Sex;
import org.cyk.system.root.model.pattern.tree.DataTree;
import org.cyk.system.root.model.pattern.tree.DataTreeType;
import org.cyk.system.root.model.security.Credentials;
import org.cyk.system.root.model.security.License;
import org.cyk.system.root.model.security.LockCause;
import org.cyk.system.root.model.security.RoleUserInterfaceMenu;
import org.cyk.system.root.model.security.SecretQuestion;
import org.cyk.system.root.model.security.Software;
import org.cyk.system.root.model.security.UserAccountUserInterfaceMenu;
import org.cyk.system.root.model.time.Schedule;
import org.cyk.system.root.model.time.ScheduleIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.time.ScheduleItem;
import org.cyk.system.root.model.time.TimeDivisionType;
import org.cyk.system.root.model.userinterface.UserInterfaceCommand;
import org.cyk.system.root.model.userinterface.UserInterfaceComponent;
import org.cyk.system.root.model.userinterface.UserInterfaceMenu;
import org.cyk.system.root.model.userinterface.UserInterfaceMenuItem;
import org.cyk.system.root.model.userinterface.UserInterfaceMenuLocation;
import org.cyk.system.root.model.userinterface.UserInterfaceMenuNode;
import org.cyk.system.root.model.userinterface.UserInterfaceMenuNodeType;
import org.cyk.system.root.model.userinterface.UserInterfaceMenuRenderType;
import org.cyk.system.root.model.userinterface.UserInterfaceMenuType;
import org.cyk.system.root.model.value.Measure;
import org.cyk.system.root.model.value.MeasureType;
import org.cyk.system.root.model.value.NullString;
import org.cyk.system.root.model.value.Value;
import org.cyk.system.root.model.value.ValueCollection;
import org.cyk.system.root.model.value.ValueProperties;
import org.cyk.ui.api.Icon;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.command.AbstractCommandable.Builder;
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
		//addBusinessMenu(userSession,systemMenu,getEventCommandable(userSession, null));
		//addBusinessMenu(userSession,systemMenu,getFileCommandable(userSession, null));
		//addBusinessMenu(userSession,systemMenu,getGeographyCommandable(userSession, null));
		//addBusinessMenu(userSession,systemMenu,getPersonCommandable(userSession, null));
		//addBusinessMenu(userSession,systemMenu,getMathematicsCommandable(userSession, null));
		
		addReferences(userSession, systemMenu, null);
		initialiseNavigatorTree(userSession);
		return systemMenu;
	}
	
	public Commandable getEventCommandable(UserSession userSession,Collection<UICommandable> mobileCommandables){
		Commandable module = createModuleCommandable(Event.class, Icon.THING_CALENDAR);
		module.setLabel(inject(BusinessServiceCollectionBusiness.class).find(RootConstant.Code.BusinessServiceCollection.EVENT).getName());
		addChild(userSession,module,createListCommandable(EventMissedReason.class, null));
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
		module.setLabel(inject(BusinessServiceCollectionBusiness.class).find(RootConstant.Code.BusinessServiceCollection.FILE).getName());
		module.addChild(createListCommandable(File.class, null));
		module.addChild(createListCommandable(FileIdentifiableGlobalIdentifier.class, null));
		module.addChild(createListCommandable(Script.class, null));
		//module.addChild(createListCommandable(ScriptVariable.class, null));
		return module;
	}
	
	public Commandable getGeographyCommandable(UserSession userSession,Collection<UICommandable> mobileCommandables){
		Commandable module = createModuleCommandable(Contact.class, Icon.THING_LOCATION_ARROW);
		module.setLabel(inject(BusinessServiceCollectionBusiness.class).find(RootConstant.Code.BusinessServiceCollection.GEOGRAPHY).getName());
		module.addChild(createListCommandable(LocalityType.class, null));
		module.addChild(createListCommandable(Locality.class, null));
		module.addChild(createListCommandable(Country.class, null));
		module.addChild(createListCommandable(ContactCollection.class, null));
		module.addChild(createListCommandable(ElectronicMailAddress.class, null));
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
		module.setLabel(inject(BusinessServiceCollectionBusiness.class).find(RootConstant.Code.BusinessServiceCollection.PARTY).getName());
		module.addChild(createListCommandable(Person.class, null));
		module.addChild(createListCommandable(PersonRelationship.class, null));
		module.addChild(createListCommandable(PersonRelationshipTypeRoleName.class, null));
		module.addChild(createListCommandable(PersonRelationshipType.class, null));
		module.addChild(createListCommandable(PersonRelationshipTypeRole.class, null));
		module.addChild(createListCommandable(PersonRelationshipTypeGroup.class, null));
		module.addChild(createListCommandable(JobFunction.class, null));
		module.addChild(createListCommandable(JobTitle.class, null));
		module.addChild(createListCommandable(MaritalStatus.class, null));
		module.addChild(createListCommandable(PersonTitle.class, null));
		module.addChild(createListCommandable(Sex.class, null));
		module.addChild(createListCommandable(Allergy.class, null));
		module.addChild(createListCommandable(Medication.class, null));
		return module;
	}
	
	public Commandable getMathematicsCommandable(UserSession userSession,Collection<UICommandable> mobileCommandables){
		Commandable module = createModuleCommandable("mathematiques", null);
		module.setLabel(inject(BusinessServiceCollectionBusiness.class).find(RootConstant.Code.BusinessServiceCollection.MATHEMATICS).getName());
		module.addChild(createListCommandable(MovementCollection.class, null));
		module.addChild(createListCommandable(Movement.class, null));
		
		module.addChild(createListCommandable(IntervalCollection.class, null));
		module.addChild(createListCommandable(Interval.class, null));
		
		module.addChild(createListCommandable(MetricCollection.class, null));
		module.addChild(createListCommandable(Metric.class, null));
		return module;
	}
	
	public Commandable getMessageCommandable(UserSession userSession,Collection<UICommandable> mobileCommandables){
		Commandable module = createModuleCommandable("command.notification.management", null);
		module.addChild(createListCommandable(SmtpProperties.class, null));
		module.addChild(Builder.create("sendmail", null, "mailSendView"));
		
		return module;
	}
	
	/**/
	
	protected void addReferences(UserSession userSession,SystemMenu systemMenu,Collection<UICommandable> mobileCommandables){
		addReference(userSession, systemMenu, getReferenceGeographyCommandable(userSession, mobileCommandables));
		addReference(userSession, systemMenu, getReferencePartyCommandable(userSession, mobileCommandables));
		addReference(userSession, systemMenu, getReferenceLanguageCommandable(userSession, mobileCommandables));
		addReference(userSession, systemMenu, getReferenceSecurityCommandable(userSession, mobileCommandables));
		addReference(userSession, systemMenu, getReferenceNetworkCommandable(userSession, mobileCommandables));
		addReference(userSession, systemMenu, getReferenceTimeCommandable(userSession, mobileCommandables));
		addReference(userSession, systemMenu, getReferenceMathematicsCommandable(userSession, mobileCommandables));
		addReference(userSession, systemMenu, getReferenceValueCommandable(userSession, mobileCommandables));
		addReference(userSession, systemMenu, getReferenceFileCommandable(userSession, mobileCommandables));
		addReference(userSession, systemMenu, getReferenceMessageCommandable(userSession, mobileCommandables));
		addReference(userSession, systemMenu, getReferenceDataTreeCommandable(userSession, mobileCommandables));
		addReference(userSession, systemMenu, getReferenceUserInterfaceCommandable(userSession, mobileCommandables));
	}
	
	/**/
	
	public Commandable getReferenceEventCommandable(UserSession userSession,Collection<UICommandable> mobileCommandables){
		Commandable module = createModuleCommandable(UIManager.getInstance().businessEntityInfos(Event.class).getUserInterface().getLabelId(), null);
		
		return module;
	}
	
	public Commandable getReferencePartyCommandable(UserSession userSession,Collection<UICommandable> mobileCommandables){
		Commandable module = createModuleCommandable(UIManager.getInstance().businessEntityInfos(Person.class).getUserInterface().getLabelId(), null);
		module.addChild(createListCommandable(Allergy.class, null));
		module.addChild(createListCommandable(Medication.class, null));
		module.addChild(createListCommandable(BloodGroup.class, null));
		
		module.addChild(createListCommandable(JobFunction.class, null));
		module.addChild(createListCommandable(JobTitle.class, null));
		
		module.addChild(createListCommandable(Sex.class, null));
		module.addChild(createListCommandable(MaritalStatus.class, null));
		module.addChild(createListCommandable(PersonTitle.class, null));
		
		module.addChild(createListCommandable(PersonRelationshipTypeRoleName.class, null));
		module.addChild(createListCommandable(PersonRelationshipTypeRole.class, null));
		module.addChild(createListCommandable(PersonRelationshipType.class, null));
		module.addChild(createListCommandable(PersonRelationshipTypeGroup.class, null));
		
		module.addChild(createListCommandable(Person.class, null));
		module.addChild(createListCommandable(PersonRelationship.class, null));
		
		return module;
	}
	
	public Commandable getReferenceGeographyCommandable(UserSession userSession,Collection<UICommandable> mobileCommandables){
		Commandable module = createModuleCommandable(UIManager.getInstance().businessEntityInfos(Contact.class).getUserInterface().getLabelId(), null);
		module.addChild(createListCommandable(LocalityType.class, null));
		module.addChild(createListCommandable(Locality.class, null));
		module.addChild(createListCommandable(Country.class, null));
		module.addChild(createListCommandable(LocationType.class, null));
		module.addChild(createListCommandable(PhoneNumberType.class, null));
		
		module.addChild(createListCommandable(PhoneNumber.class, null));
		module.addChild(createListCommandable(ElectronicMailAddress.class, null));
		module.addChild(createListCommandable(Location.class, null));
		module.addChild(createListCommandable(Website.class, null));
		module.addChild(createListCommandable(PostalBox.class, null));
		
		module.addChild(createListCommandable(ContactCollection.class, null));
		return module;
	}
	
	public Commandable getReferenceSecurityCommandable(UserSession userSession,Collection<UICommandable> mobileCommandables){
		Commandable module = createModuleCommandable("security", null);
		module.addChild(createListCommandable(Application.class, null));
		module.addChild(createListCommandable(License.class, null));
		module.addChild(createListCommandable(Software.class, null));
		module.addChild(createListCommandable(LockCause.class, null));
		module.addChild(createListCommandable(SecretQuestion.class, null));
		module.addChild(createListCommandable(Credentials.class, null));
		module.addChild(createListCommandable(RoleUserInterfaceMenu.class, null));
		module.addChild(createListCommandable(UserAccountUserInterfaceMenu.class, null));
		return module;
	}
	
	public Commandable getReferenceFileCommandable(UserSession userSession,Collection<UICommandable> mobileCommandables){
		Commandable module = createModuleCommandable(UIManager.getInstance().businessEntityInfos(File.class).getUserInterface().getLabelId(), null);
		module.addChild(createListCommandable(FileRepresentationType.class, null));
		module.addChild(createListCommandable(File.class, null));
		module.addChild(createListCommandable(FileIdentifiableGlobalIdentifier.class, null));
		
		module.addChild(createListCommandable(Script.class, null));
		module.addChild(createListCommandable(ScriptEvaluationEngine.class, null));
		
		module.addChild(createListCommandable(ReportTemplate.class, null));
		module.addChild(createListCommandable(ReportFile.class, null));
		return module;
	}
	
	public Commandable getReferenceNetworkCommandable(UserSession userSession,Collection<UICommandable> mobileCommandables){
		Commandable module = createModuleCommandable("network", null);
		module.addChild(createListCommandable(Computer.class, null));
		module.addChild(createListCommandable(Service.class, null));
		module.addChild(createListCommandable(UniformResourceLocator.class, null));
		
		return module;
	}
	
	public Commandable getReferenceLanguageCommandable(UserSession userSession,Collection<UICommandable> mobileCommandables){
		Commandable module = createModuleCommandable(UIManager.getInstance().businessEntityInfos(Language.class).getUserInterface().getLabelId(), null);
		module.addChild(createListCommandable(Language.class, null));
		
		return module;
	}
	
	public Commandable getReferenceTimeCommandable(UserSession userSession,Collection<UICommandable> mobileCommandables){
		Commandable module = createModuleCommandable("time", null);
		module.addChild(createListCommandable(TimeDivisionType.class, null));
		module.addChild(createListCommandable(Schedule.class, null));
		module.addChild(createListCommandable(ScheduleItem.class, null));
		module.addChild(createListCommandable(ScheduleIdentifiableGlobalIdentifier.class, null));
		return module;
	}
	
	public Commandable getReferenceMathematicsCommandable(UserSession userSession,Collection<UICommandable> mobileCommandables){
		Commandable module = createModuleCommandable("mathematics", null);
		module.addChild(createListCommandable(MovementAction.class, null));
		module.addChild(createListCommandable(MovementCollection.class, null));
		module.addChild(createListCommandable(IntervalCollection.class, null));
		module.addChild(createListCommandable(MetricCollectionType.class, null));
		module.addChild(createListCommandable(MetricCollection.class, null));
		module.addChild(createListCommandable(MetricValue.class, null));
		return module;
	}
	
	public Commandable getReferenceValueCommandable(UserSession userSession,Collection<UICommandable> mobileCommandables){
		Commandable module = createModuleCommandable("value", null);
		module.addChild(createListCommandable(NullString.class, null));
		module.addChild(createListCommandable(MeasureType.class, null));
		module.addChild(createListCommandable(Measure.class, null));
		module.addChild(createListCommandable(ValueProperties.class, null));
		module.addChild(createListCommandable(ValueCollection.class, null));
		module.addChild(createListCommandable(Value.class, null));
		return module;
	}
	
	public Commandable getReferenceMessageCommandable(UserSession userSession,Collection<UICommandable> mobileCommandables){
		Commandable module = createModuleCommandable("command.notification.management", null);
		module.setLabel(inject(BusinessServiceCollectionBusiness.class).find(RootConstant.Code.BusinessServiceCollection.MESSAGE).getName());
		module.addChild(createListCommandable(SmtpProperties.class, null));
		
		return module;
	}
	
	public Commandable getReferenceDataTreeCommandable(UserSession userSession,Collection<UICommandable> mobileCommandables){
		Commandable module = createModuleCommandable("command.datatree.management", null);
		//module.setLabel(inject(BusinessServiceCollectionBusiness.class).find(RootConstant.Code.BusinessServiceCollection.d).getName());
		module.addChild(createListCommandable(DataTreeType.class, null));
		module.addChild(createListCommandable(DataTree.class, null));
		
		return module; 
	}
	
	public Commandable getReferenceUserInterfaceCommandable(UserSession userSession,Collection<UICommandable> mobileCommandables){
		Commandable module = createModuleCommandable("command.userinterface.management", null);
		//module.setLabel(inject(BusinessServiceCollectionBusiness.class).find(RootConstant.Code.BusinessServiceCollection.d).getName());
		module.addChild(createListCommandable(UserInterfaceMenu.class, null));
		module.addChild(createListCommandable(UserInterfaceMenuNode.class, null));
		module.addChild(createListCommandable(UserInterfaceMenuNodeType.class, null));
		module.addChild(createListCommandable(UserInterfaceCommand.class, null));
		
		module.addChild(createListCommandable(UserInterfaceMenuItem.class, null));
		module.addChild(createListCommandable(UserInterfaceComponent.class, null));
		
		module.addChild(createListCommandable(UserInterfaceMenuType.class, null));
		module.addChild(createListCommandable(UserInterfaceMenuLocation.class, null));
		module.addChild(createListCommandable(UserInterfaceMenuRenderType.class, null));
		return module;
	}
	
	public static SystemMenuBuilder getInstance(){
		if(INSTANCE==null)
			INSTANCE = new SystemMenuBuilder();
		return INSTANCE;
	}
}
