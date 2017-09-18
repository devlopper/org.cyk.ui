package org.cyk.ui.web.primefaces;

import java.io.Serializable;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;

import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.impl.event.EventDetails;
import org.cyk.system.root.business.impl.event.EventMissedDetails;
import org.cyk.system.root.business.impl.event.EventPartyDetails;
import org.cyk.system.root.business.impl.file.FileDetails;
import org.cyk.system.root.business.impl.file.FileIdentifiableGlobalIdentifierDetails;
import org.cyk.system.root.business.impl.file.FileRepresentationTypeDetails;
import org.cyk.system.root.business.impl.file.ScriptDetails;
import org.cyk.system.root.business.impl.file.ScriptVariableDetails;
import org.cyk.system.root.business.impl.file.report.ReportTemplateDetails;
import org.cyk.system.root.business.impl.geography.CountryDetails;
import org.cyk.system.root.business.impl.geography.ElectronicMailDetails;
import org.cyk.system.root.business.impl.geography.LocalityDetails;
import org.cyk.system.root.business.impl.geography.LocalityTypeDetails;
import org.cyk.system.root.business.impl.geography.PhoneNumberDetails;
import org.cyk.system.root.business.impl.information.CommentDetails;
import org.cyk.system.root.business.impl.language.LanguageDetails;
import org.cyk.system.root.business.impl.mathematics.IntervalDetails;
import org.cyk.system.root.business.impl.mathematics.MetricCollectionDetails;
import org.cyk.system.root.business.impl.mathematics.MetricCollectionIdentifiableGlobalIdentifierDetails;
import org.cyk.system.root.business.impl.mathematics.MetricCollectionTypeDetails;
import org.cyk.system.root.business.impl.mathematics.MetricDetails;
import org.cyk.system.root.business.impl.mathematics.MetricValueDetails;
import org.cyk.system.root.business.impl.mathematics.MetricValueIdentifiableGlobalIdentifierDetails;
import org.cyk.system.root.business.impl.mathematics.MovementActionDetails;
import org.cyk.system.root.business.impl.mathematics.MovementCollectionDetails;
import org.cyk.system.root.business.impl.mathematics.MovementDetails;
import org.cyk.system.root.business.impl.mathematics.machine.FiniteStateMachineStateIdentifiableGlobalIdentifierDetails;
import org.cyk.system.root.business.impl.message.SmtpPropertiesDetails;
import org.cyk.system.root.business.impl.network.ComputerDetails;
import org.cyk.system.root.business.impl.network.ServiceDetails;
import org.cyk.system.root.business.impl.party.ApplicationDetails;
import org.cyk.system.root.business.impl.party.person.AbstractActorDetails;
import org.cyk.system.root.business.impl.party.person.MedicalInformationsAllergyDetails;
import org.cyk.system.root.business.impl.party.person.MedicalInformationsMedicationDetails;
import org.cyk.system.root.business.impl.party.person.PersonDetails;
import org.cyk.system.root.business.impl.party.person.PersonRelationshipDetails;
import org.cyk.system.root.business.impl.party.person.PersonRelationshipTypeRoleDetails;
import org.cyk.system.root.business.impl.pattern.tree.DataTreeDetails;
import org.cyk.system.root.business.impl.pattern.tree.DataTreeTypeDetails;
import org.cyk.system.root.business.impl.pattern.tree.NestedSetNodeDetails;
import org.cyk.system.root.business.impl.security.CredentialsDetails;
import org.cyk.system.root.business.impl.security.LicenseDetails;
import org.cyk.system.root.business.impl.security.RoleDetails;
import org.cyk.system.root.business.impl.security.SoftwareDetails;
import org.cyk.system.root.business.impl.security.UniformResourceLocatorDetails;
import org.cyk.system.root.business.impl.security.UserAccountDetails;
import org.cyk.system.root.business.impl.value.MeasureDetails;
import org.cyk.system.root.business.impl.value.ValueCollectionDetails;
import org.cyk.system.root.business.impl.value.ValueDetails;
import org.cyk.system.root.business.impl.value.ValuePropertiesDetails;
import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.event.EventMissed;
import org.cyk.system.root.model.event.EventParty;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.FileIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.file.FileRepresentationType;
import org.cyk.system.root.model.file.Script;
import org.cyk.system.root.model.file.ScriptVariable;
import org.cyk.system.root.model.file.report.ReportTemplate;
import org.cyk.system.root.model.geography.Country;
import org.cyk.system.root.model.geography.ElectronicMail;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.geography.LocalityType;
import org.cyk.system.root.model.geography.PhoneNumber;
import org.cyk.system.root.model.information.Comment;
import org.cyk.system.root.model.language.Language;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.Metric;
import org.cyk.system.root.model.mathematics.MetricCollection;
import org.cyk.system.root.model.mathematics.MetricCollectionIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.mathematics.MetricCollectionType;
import org.cyk.system.root.model.mathematics.MetricValue;
import org.cyk.system.root.model.mathematics.MetricValueIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.system.root.model.mathematics.MovementAction;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineStateIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.message.SmtpProperties;
import org.cyk.system.root.model.network.Computer;
import org.cyk.system.root.model.network.Service;
import org.cyk.system.root.model.network.UniformResourceLocator;
import org.cyk.system.root.model.party.Application;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.party.person.MedicalInformationsAllergy;
import org.cyk.system.root.model.party.person.MedicalInformationsMedication;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonRelationship;
import org.cyk.system.root.model.party.person.PersonRelationshipType;
import org.cyk.system.root.model.party.person.PersonRelationshipTypeGroup;
import org.cyk.system.root.model.party.person.PersonRelationshipTypeRole;
import org.cyk.system.root.model.pattern.tree.AbstractDataTree;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeType;
import org.cyk.system.root.model.pattern.tree.DataTree;
import org.cyk.system.root.model.pattern.tree.DataTreeType;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;
import org.cyk.system.root.model.security.Credentials;
import org.cyk.system.root.model.security.License;
import org.cyk.system.root.model.security.Role;
import org.cyk.system.root.model.security.Software;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.system.root.model.value.Measure;
import org.cyk.system.root.model.value.Value;
import org.cyk.system.root.model.value.ValueCollection;
import org.cyk.system.root.model.value.ValueProperties;
import org.cyk.ui.api.config.IdentifiableConfiguration;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.api.model.EnumerationForm;
import org.cyk.ui.api.model.party.AbstractActorEditFormModel;
import org.cyk.ui.api.model.party.AbstractActorQueryManyFormModel;
import org.cyk.ui.api.model.party.AbstractActorQueryOneFormModel;
import org.cyk.ui.api.model.party.PersonRelationshipTypeForm;
import org.cyk.ui.api.model.party.PersonRelationshipTypeGroupForm;
import org.cyk.ui.api.model.pattern.tree.AbstractDataTreeForm;
import org.cyk.ui.api.model.pattern.tree.AbstractDataTreeTypeForm;
import org.cyk.ui.web.api.AbstractServletContextListener;
import org.cyk.ui.web.api.ContextParam;
import org.cyk.ui.web.primefaces.page.AbstractIdentifiablePagesConfiguration;
import org.cyk.ui.web.primefaces.page.crud.AbstractConsultPage.ConsultPageListener;
import org.cyk.ui.web.primefaces.page.event.EventEditPage;
import org.cyk.ui.web.primefaces.page.event.EventMissedEditPage;
import org.cyk.ui.web.primefaces.page.event.EventPartyEditPage;
import org.cyk.ui.web.primefaces.page.file.DefaultReportBasedOnDynamicBuilderServletAdapter;
import org.cyk.ui.web.primefaces.page.file.FileEditPage;
import org.cyk.ui.web.primefaces.page.file.FileIdentifiableGlobalIdentifierEditPage;
import org.cyk.ui.web.primefaces.page.file.FileRepresentationTypeEditPage;
import org.cyk.ui.web.primefaces.page.file.ReportTemplateEditPage;
import org.cyk.ui.web.primefaces.page.file.ScriptEditPage;
import org.cyk.ui.web.primefaces.page.file.ScriptVariableEditPage;
import org.cyk.ui.web.primefaces.page.geography.CountryEditPage;
import org.cyk.ui.web.primefaces.page.geography.ElectronicMailEditPage;
import org.cyk.ui.web.primefaces.page.geography.LocalityEditPage;
import org.cyk.ui.web.primefaces.page.geography.LocalityTypeEditPage;
import org.cyk.ui.web.primefaces.page.geography.PhoneNumberEditPage;
import org.cyk.ui.web.primefaces.page.information.CommentEditPage;
import org.cyk.ui.web.primefaces.page.language.LanguageEditPage;
import org.cyk.ui.web.primefaces.page.mathematics.FiniteStateMachineStateIdentifiableGlobalIdentifierEditPage;
import org.cyk.ui.web.primefaces.page.mathematics.IntervalEditPage;
import org.cyk.ui.web.primefaces.page.mathematics.MetricCollectionEditPage;
import org.cyk.ui.web.primefaces.page.mathematics.MetricCollectionIdentifiableGlobalIdentifierEditPage;
import org.cyk.ui.web.primefaces.page.mathematics.MetricCollectionTypeEditPage;
import org.cyk.ui.web.primefaces.page.mathematics.MetricEditPage;
import org.cyk.ui.web.primefaces.page.mathematics.MetricValueEditPage;
import org.cyk.ui.web.primefaces.page.mathematics.MetricValueIdentifiableGlobalIdentifierEditPage;
import org.cyk.ui.web.primefaces.page.mathematics.MovementActionEditPage;
import org.cyk.ui.web.primefaces.page.mathematics.MovementCollectionEditPage;
import org.cyk.ui.web.primefaces.page.mathematics.MovementEditPage;
import org.cyk.ui.web.primefaces.page.message.mail.SmtpPropertiesEditPage;
import org.cyk.ui.web.primefaces.page.nestedset.DataTreeEditPage;
import org.cyk.ui.web.primefaces.page.nestedset.DataTreeTypeEditPage;
import org.cyk.ui.web.primefaces.page.nestedset.NestedSetNodeEditPage;
import org.cyk.ui.web.primefaces.page.network.ComputerEditPage;
import org.cyk.ui.web.primefaces.page.network.ServiceEditPage;
import org.cyk.ui.web.primefaces.page.party.ApplicationEditPage;
import org.cyk.ui.web.primefaces.page.party.MedicalInformationsAllergyEditPage;
import org.cyk.ui.web.primefaces.page.party.MedicalInformationsMedicationEditPage;
import org.cyk.ui.web.primefaces.page.party.PersonEditPage;
import org.cyk.ui.web.primefaces.page.party.PersonRelationshipEditPage;
import org.cyk.ui.web.primefaces.page.party.PersonRelationshipTypeRoleEditPage;
import org.cyk.ui.web.primefaces.page.security.CredentialsEditPage;
import org.cyk.ui.web.primefaces.page.security.LicenseEditPage;
import org.cyk.ui.web.primefaces.page.security.RoleEditPage;
import org.cyk.ui.web.primefaces.page.security.SoftwareEditPage;
import org.cyk.ui.web.primefaces.page.security.UniformResourceLocatorEditPage;
import org.cyk.ui.web.primefaces.page.security.UserAccountEditPage;
import org.cyk.ui.web.primefaces.page.value.MeasureEditPage;
import org.cyk.ui.web.primefaces.page.value.ValueCollectionEditPage;
import org.cyk.ui.web.primefaces.page.value.ValueEditPage;
import org.cyk.ui.web.primefaces.page.value.ValuePropertiesEditPage;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.NotificationHelper;
import org.cyk.utility.common.helper.NotificationHelper.Notification.Viewer;
import org.primefaces.model.TreeNode;

public abstract class AbstractContextListener extends AbstractServletContextListener<TreeNode,HierarchyNode,UserSession> implements Serializable {
     
	private static final long serialVersionUID = 592943227142026384L;
	
	protected static final Map<Class<?>,AbstractIdentifiablePagesConfiguration<?>> IDENTIFIABLE_CONFIGURATON_MAP = new HashMap<>();
	
	@Inject protected DefaultDesktopLayoutManager layoutManager;
	@Inject protected PrimefacesManager primefacesManager; 
 
	@SuppressWarnings("unchecked")
	@Override 
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		NotificationHelper.Notification.Viewer.Adapter.Default.DEFAULT_CLASS = (Class<Viewer>) ClassHelper.getInstance().getByName(org.cyk.ui.web.primefaces.NotificationHelper.Viewer.class);
		layoutManager.setLogoPath(stringContextParameter(ContextParam.LOGO_PATH, event,layoutManager.getLogoPath()));
		layoutManager.setLoginBackgroundPath(stringContextParameter(ContextParam.LOGIN_BACKGROUN_DPATH, event,layoutManager.getLoginBackgroundPath()));
		layoutManager.setHomeBackgroundPath(stringContextParameter(ContextParam.HOME_BACKGROUND_PATH, event,layoutManager.getHomeBackgroundPath()));
		
		webManager.getReportBasedOnDynamicBuilderServletListeners().add(new DefaultReportBasedOnDynamicBuilderServletAdapter<>());
		
		IdentifiableConfiguration identifiableConfiguration;
		
		uiManager.registerConfiguration(identifiableConfiguration = new IdentifiableConfiguration(AbstractDataTree.class, AbstractDataTreeForm.Default.class, AbstractDataTreeForm.Default.class,null,null,null));
		identifiableConfiguration.setUsableByChild(Boolean.TRUE);
		
		uiManager.registerConfiguration(identifiableConfiguration = new IdentifiableConfiguration(AbstractDataTreeType.class, AbstractDataTreeTypeForm.Default.class, AbstractDataTreeTypeForm.Default.class,null,null,null));
		identifiableConfiguration.setUsableByChild(Boolean.TRUE);
		
		uiManager.registerConfiguration(identifiableConfiguration = new IdentifiableConfiguration(AbstractEnumeration.class, EnumerationForm.class, EnumerationForm.class,null,null,null));
		identifiableConfiguration.setUsableByChild(Boolean.TRUE);
		
		registerIdentifiablePagesConfigurations();
		
		executIdentifiablePagesConfigurations();
		
		initializeEventModule();
		initializeFileModule();
		initializeFiniteStateMachineModule();
		initializeGeographyModule();
		initializeInformationModule();
		initializeLanguageModule();
		initializeMathematicsModule();
		initializeNestedSetModule();
		initializeNetworkModule();
		initializePartyModule();
		initializeSecurityModule();
		initializeMessageModule();
		initializeValueModule();
		initializeUserInterfaceModule();
		/*
		MetricCollectionIdentifiableGlobalIdentifier.define(DataTree.class);
		MetricCollectionIdentifiableGlobalIdentifier.define(DataTreeType.class);
		MetricValueIdentifiableGlobalIdentifier.define(DataTree.class);
		MetricValueIdentifiableGlobalIdentifier.define(DataTreeType.class);
		*/
	}  
	
	protected void registerIdentifiablePagesConfigurations(){
		for(Class<?> clazz : new ClassHelper().get("org.cyk" /*"org.cyk.ui.web.primefaces.page"*/, AbstractIdentifiablePagesConfiguration.class)){
			if(Modifier.isAbstract(clazz.getModifiers()))
				continue;
			AbstractIdentifiablePagesConfiguration<?> configuration = (AbstractIdentifiablePagesConfiguration<?>) ClassHelper.getInstance().instanciateOne(clazz);
			IDENTIFIABLE_CONFIGURATON_MAP.put(configuration.getIdentifiableClass(), configuration);
		}
	}
	
	protected void executIdentifiablePagesConfigurations(){
		for(Entry<Class<?>, AbstractIdentifiablePagesConfiguration<?>> entry : IDENTIFIABLE_CONFIGURATON_MAP.entrySet())
			entry.getValue().configure();
	}
	
	protected void initializePartyModule(){
		uiManager.registerConfiguration(new IdentifiableConfiguration(Application.class, ApplicationEditPage.Form.class, ApplicationDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(Application.class, null);
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(PersonRelationshipTypeGroup.class, PersonRelationshipTypeGroupForm.class, PersonRelationshipTypeGroupForm.class,null,null,null));
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(PersonRelationshipType.class, PersonRelationshipTypeForm.class, PersonRelationshipTypeForm.class,null,null,null));		
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(PersonRelationshipTypeRole.class, PersonRelationshipTypeRoleEditPage.Form.class, PersonRelationshipTypeRoleDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(PersonRelationshipTypeRole.class, null);
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(PersonRelationship.class, PersonRelationshipEditPage.Form.class, PersonRelationshipDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(PersonRelationship.class, null);
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(Person.class, PersonEditPage.Form.class, PersonDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(Person.class, null);
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(MedicalInformationsAllergy.class, MedicalInformationsAllergyEditPage.Form.class, MedicalInformationsAllergyDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(MedicalInformationsAllergy.class, null);
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(MedicalInformationsMedication.class, MedicalInformationsMedicationEditPage.Form.class, MedicalInformationsMedicationDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(MedicalInformationsMedication.class, null);
		
		//BusinessEntityFormManyPageListener.COLLECTION.add(new AbstractPersonListPage.AbstractPersonListPageAdapter.AbstractDefault.Default<Person>(Person.class));
		//BusinessEntityFormOnePageListener.COLLECTION.add(new AbstractPersonEditPage.AbstractPageAdapter.Default<Person>(Person.class));
		//ConsultPageListener.COLLECTION.add(new ConsultPageListener.Adapter.Default<Person>(Person.class));
	}
	
	protected void initializeFileModule(){
		uiManager.registerConfiguration(new IdentifiableConfiguration(File.class, FileEditPage.Form.class, FileDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(File.class, null);
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(FileRepresentationType.class, FileRepresentationTypeEditPage.Form.class, FileRepresentationTypeDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(FileRepresentationType.class, null);
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(FileIdentifiableGlobalIdentifier.class, FileIdentifiableGlobalIdentifierEditPage.Form.class, FileIdentifiableGlobalIdentifierDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(FileIdentifiableGlobalIdentifier.class, null);
		uiManager.registerConfiguration(new IdentifiableConfiguration(Script.class, ScriptEditPage.Form.class, ScriptDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(Script.class, null);
		uiManager.registerConfiguration(new IdentifiableConfiguration(ScriptVariable.class, ScriptVariableEditPage.Form.class, ScriptVariableDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(ScriptVariable.class, null);
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(ReportTemplate.class, ReportTemplateEditPage.Form.class, ReportTemplateDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(ReportTemplate.class, null);
		
	}
	
	protected void initializeEventModule(){
		uiManager.registerConfiguration(new IdentifiableConfiguration(Event.class, EventEditPage.Form.class, EventDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(Event.class, null);
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(EventParty.class, EventPartyEditPage.Form.class, EventPartyDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(EventParty.class, null);
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(EventMissed.class, EventMissedEditPage.Form.class, EventMissedDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(EventMissed.class, null);
	}
	
	protected void initializeGeographyModule(){
		//uiManager.registerConfiguration(new IdentifiableConfiguration(LocalityType.class, LocalityTypeForm.class, LocalityTypeForm.class,null,null,null));
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(LocalityType.class, LocalityTypeEditPage.Form.class, LocalityTypeDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(LocalityType.class, null);
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(Locality.class, LocalityEditPage.Form.class, LocalityDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(Locality.class, null);
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(Country.class, CountryEditPage.Form.class, CountryDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(Country.class, null);
		
		//uiManager.registerConfiguration(new IdentifiableConfiguration(ContactCollection.class, ContactCollectionEditPage.Form.class, ContactCollectionDetails.class,null,null,null));
		//uiManager.configBusinessIdentifiable(ContactCollection.class, null);
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(ElectronicMail.class, ElectronicMailEditPage.Form.class, ElectronicMailDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(ElectronicMail.class, null);
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(PhoneNumber.class, PhoneNumberEditPage.Form.class, PhoneNumberDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(PhoneNumber.class, null);
	}
	
	protected void initializeLanguageModule(){
		uiManager.registerConfiguration(new IdentifiableConfiguration(Language.class, LanguageEditPage.Form.class, LanguageDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(Language.class, null);
	}
	
	protected void initializeNetworkModule(){
		uiManager.registerConfiguration(new IdentifiableConfiguration(UniformResourceLocator.class, UniformResourceLocatorEditPage.Form.class
				, UniformResourceLocatorDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(UniformResourceLocator.class, null);
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(Computer.class, ComputerEditPage.Form.class, ComputerDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(Computer.class, null);
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(Service.class, ServiceEditPage.Form.class, ServiceDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(Service.class, null);
	}
	
	protected void initializeInformationModule(){
		uiManager.registerConfiguration(new IdentifiableConfiguration(Comment.class, CommentEditPage.Form.class, CommentDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(Comment.class, null);
	}
	
	protected void initializeSecurityModule(){
		uiManager.registerConfiguration(new IdentifiableConfiguration(Role.class, RoleEditPage.Form.class, RoleDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(Role.class, null);
		uiManager.registerConfiguration(new IdentifiableConfiguration(License.class, LicenseEditPage.Form.class, LicenseDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(License.class, null);
		uiManager.registerConfiguration(new IdentifiableConfiguration(Software.class, SoftwareEditPage.Form.class, SoftwareDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(Software.class, null);
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(UserAccount.class, UserAccountEditPage.Form.class, UserAccountDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(UserAccount.class, null);
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(Credentials.class, CredentialsEditPage.Form.class, CredentialsDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(Credentials.class, null);
	}
	
	protected void initializeMessageModule(){
		uiManager.registerConfiguration(new IdentifiableConfiguration(SmtpProperties.class, SmtpPropertiesEditPage.Form.class, SmtpPropertiesDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(SmtpProperties.class, null);
		
	}
	
	protected void initializeNestedSetModule(){
		uiManager.registerConfiguration(new IdentifiableConfiguration(NestedSetNode.class, NestedSetNodeEditPage.Form.class, NestedSetNodeDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(NestedSetNode.class, null);
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(DataTreeType.class, DataTreeTypeEditPage.Form.class, DataTreeTypeDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(DataTreeType.class, null);
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(DataTree.class, DataTreeEditPage.Form.class, DataTreeDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(DataTree.class, null);
	}
	
	protected void initializeMathematicsModule(){
		uiManager.registerConfiguration(new IdentifiableConfiguration(MovementAction.class, MovementActionEditPage.Form.class, MovementActionDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(MovementAction.class, null);
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(MovementCollection.class, MovementCollectionEditPage.Form.class, MovementCollectionDetails.class
				,null,null,null));
		uiManager.configBusinessIdentifiable(MovementCollection.class, null);
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(Movement.class, MovementEditPage.Form.class, MovementDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(Movement.class, null);
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(Interval.class, IntervalEditPage.Form.class, IntervalDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(Interval.class, null);
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(MetricCollectionType.class, MetricCollectionTypeEditPage.Form.class, MetricCollectionTypeDetails.class
				,null,null,null));
		uiManager.configBusinessIdentifiable(MetricCollectionType.class, null);
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(MetricCollection.class, MetricCollectionEditPage.Form.class, MetricCollectionDetails.class
				,null,null,null));
		uiManager.configBusinessIdentifiable(MetricCollection.class, null);
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(MetricCollectionIdentifiableGlobalIdentifier.class, MetricCollectionIdentifiableGlobalIdentifierEditPage.Form.class
				, MetricCollectionIdentifiableGlobalIdentifierDetails.class
				,null,null,null));
		uiManager.configBusinessIdentifiable(MetricCollectionIdentifiableGlobalIdentifier.class, null);
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(MetricValue.class, MetricValueEditPage.Form.class, MetricValueDetails.class
				,null,null,null));
		uiManager.configBusinessIdentifiable(MetricValue.class, null);
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(MetricValueIdentifiableGlobalIdentifier.class, MetricValueIdentifiableGlobalIdentifierEditPage.Form.class
				, MetricValueIdentifiableGlobalIdentifierDetails.class
				,null,null,null));
		uiManager.configBusinessIdentifiable(MetricValueIdentifiableGlobalIdentifier.class, null);
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(Metric.class, MetricEditPage.Form.class, MetricDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(Metric.class, null);
		
		/*
		uiManager.registerConfiguration(new IdentifiableConfiguration(MetricValue.class, MetricValueEditPage.Form.class, MetricValueDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(MetricValue.class, null);
		*/
	}
	
	protected void initializeFiniteStateMachineModule(){
		uiManager.registerConfiguration(new IdentifiableConfiguration(FiniteStateMachineStateIdentifiableGlobalIdentifier.class
				, FiniteStateMachineStateIdentifiableGlobalIdentifierEditPage.Form.class, FiniteStateMachineStateIdentifiableGlobalIdentifierDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(FiniteStateMachineStateIdentifiableGlobalIdentifier.class, null);
	}
	
	protected void initializeValueModule(){
		uiManager.registerConfiguration(new IdentifiableConfiguration(Value.class, ValueEditPage.Form.class, ValueDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(Value.class, null);
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(Measure.class, MeasureEditPage.Form.class, MeasureDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(Measure.class, null);
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(ValueProperties.class, ValuePropertiesEditPage.Form.class, ValuePropertiesDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(ValueProperties.class, null);
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(ValueCollection.class, ValueCollectionEditPage.Form.class, ValueCollectionDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(ValueCollection.class, null);
	}
	
	protected void initializeUserInterfaceModule(){}
	
	@Override
	protected void identifiableConfiguration(ServletContextEvent event) {
		super.identifiableConfiguration(event);
		businessAdapters(event);
		applicationUImanagers(event);
		registerActorForm();
	}
	
	@SuppressWarnings("unchecked")
	protected void registerActorForm(){
		for(BusinessEntityInfos businessEntityInfos : applicationBusiness.findBusinessEntitiesInfos()){
			if(AbstractActor.class.isAssignableFrom(businessEntityInfos.getClazz())){
				registerActorForm((Class<? extends AbstractActor>)businessEntityInfos.getClazz());
			}
		}
	}
	
	protected <ACTOR extends AbstractActor> void registerActorForm(Class<ACTOR> actorClass){
		IdentifiableConfiguration configuration = new IdentifiableConfiguration(actorClass,getEditFormModelClass(actorClass),getReadFormModelClass(actorClass)
				,getQueryOneFormModelClass(actorClass),null,getQueryManyFormModelClass(actorClass));
		uiManager.registerConfiguration(configuration);
		
		uiManager.businessEntityInfos(actorClass).getUserInterface().setConsultViewId(webNavigationManager.getOutcomeAnyActorTypeConsult());
		uiManager.businessEntityInfos(actorClass).getUserInterface().setListViewId(webNavigationManager.getOutcomeAnyActorTypeList());
		uiManager.businessEntityInfos(actorClass).getUserInterface().setEditViewId(webNavigationManager.getOutcomeAnyActorTypeEdit());
		
		webNavigationManager.useDynamicSelectView(actorClass);
		//uiManager.businessEntityInfos(actorClass).getUserInterface().setSelectOneViewId(webNavigationManager.getOutcomeDynamicSelectOne());
		//uiManager.businessEntityInfos(actorClass).getUserInterface().setSelectManyViewId(webNavigationManager.getOutcomeDynamicSelectMany());
		
		//BusinessEntityFormManyPageListener.COLLECTION.add(new AbstractActorListPage.AbstractActorListPageAdapter.AbstractDefault.Default<ACTOR>(actorClass));
		//BusinessEntityFormOnePageListener.COLLECTION.add(new AbstractActorEditPage.AbstractPageAdapter.Default<ACTOR>(actorClass));
		ConsultPageListener.COLLECTION.add(new ConsultPageListener.Adapter.Default<ACTOR>(actorClass));
		
		//AbstractBusinessEntityFormOnePage.BusinessEntityFormOnePageListener.COLLECTION.add(getActorCrudOnePageAdapter(actorClass));
		//AbstractBusinessEntityFormManyPage.BusinessEntityFormManyPageListener.COLLECTION.add(getActorCrudManyPageAdapter(actorClass));
		//AbstractConsultPage.ConsultPageListener.COLLECTION.add(getActorConsultPageAdapter(actorClass));
		
		//logInfo("Actor {} forms registered", actorClass.getSimpleName());
	}
	/*
	protected <ACTOR extends AbstractActor> AbstractActorEditPage.AbstractPageAdapter<ACTOR> getActorCrudOnePageAdapter(Class<ACTOR> actorClass){
		return new AbstractActorEditPage.AbstractPageAdapter.Default<ACTOR>(actorClass);
	}
	protected <ACTOR extends AbstractActor> AbstractActorCrudManyPageAdapter<ACTOR> getActorCrudManyPageAdapter(Class<ACTOR> actorClass){
		return new AbstractActorCrudManyPageAdapter.Default<ACTOR>(actorClass);
	}
	protected <ACTOR extends AbstractActor> AbstractActorConsultPageAdapter<ACTOR> getActorConsultPageAdapter(Class<ACTOR> actorClass){
		return new AbstractActorConsultPageAdapter.Default<ACTOR>(actorClass);
	}*/
	
	public static Class<? extends AbstractFormModel<?>>  ACTOR_EDIT_FORM_MODEL_CLASS;
	
	@SuppressWarnings("unchecked")
	protected Class<? extends AbstractFormModel<?>> getEditFormModelClass(Class<?> clazz){
		if(AbstractActor.class.isAssignableFrom(clazz))
			try {
				return ACTOR_EDIT_FORM_MODEL_CLASS == null 
				? ACTOR_EDIT_FORM_MODEL_CLASS = (Class<? extends AbstractFormModel<?>>) Class.forName(AbstractActorEditFormModel.AbstractDefault.Default.class.getName()) 
				: ACTOR_EDIT_FORM_MODEL_CLASS;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		return null;
	}
	
	protected Class<?> getReadFormModelClass(Class<?> clazz){
		if(AbstractActor.class.isAssignableFrom(clazz))
			return AbstractActorDetails.AbstractDefault.Default.class;
		return null;
	}
	
	protected Class<?> getQueryOneFormModelClass(Class<?> clazz){
		if(AbstractActor.class.isAssignableFrom(clazz))
			return AbstractActorQueryOneFormModel.Default.class;
		return null;
	}
	
	protected Class<?> getQueryManyFormModelClass(Class<?> clazz){
		if(AbstractActor.class.isAssignableFrom(clazz))
			return AbstractActorQueryManyFormModel.Default.class;
		return null;
	}
	
	protected void businessAdapters(ServletContextEvent event) {
		
	}
	
	protected void applicationUImanagers(ServletContextEvent event) {
		
	}
}
