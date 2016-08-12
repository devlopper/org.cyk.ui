package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;

import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.impl.event.EventDetails;
import org.cyk.system.root.business.impl.event.EventMissedDetails;
import org.cyk.system.root.business.impl.event.EventPartyDetails;
import org.cyk.system.root.business.impl.file.FileDetails;
import org.cyk.system.root.business.impl.file.FileIdentifiableGlobalIdentifierDetails;
import org.cyk.system.root.business.impl.file.ScriptDetails;
import org.cyk.system.root.business.impl.file.ScriptVariableDetails;
import org.cyk.system.root.business.impl.geography.ContactCollectionDetails;
import org.cyk.system.root.business.impl.geography.CountryDetails;
import org.cyk.system.root.business.impl.geography.ElectronicMailDetails;
import org.cyk.system.root.business.impl.geography.PhoneNumberDetails;
import org.cyk.system.root.business.impl.information.CommentDetails;
import org.cyk.system.root.business.impl.mathematics.MovementCollectionDetails;
import org.cyk.system.root.business.impl.mathematics.MovementDetails;
import org.cyk.system.root.business.impl.mathematics.machine.FiniteStateMachineStateLogDetails;
import org.cyk.system.root.business.impl.party.ApplicationDetails;
import org.cyk.system.root.business.impl.party.person.AbstractActorDetails;
import org.cyk.system.root.business.impl.party.person.PersonDetails;
import org.cyk.system.root.business.impl.pattern.tree.NestedSetNodeDetails;
import org.cyk.system.root.business.impl.security.LicenseDetails;
import org.cyk.system.root.business.impl.security.RoleDetails;
import org.cyk.system.root.business.impl.security.UniformResourceLocatorDetails;
import org.cyk.system.root.business.impl.security.UserAccountDetails;
import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.event.EventMissed;
import org.cyk.system.root.model.event.EventParty;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.FileIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.file.Script;
import org.cyk.system.root.model.file.ScriptVariable;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.Country;
import org.cyk.system.root.model.geography.ElectronicMail;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.geography.LocalityType;
import org.cyk.system.root.model.geography.PhoneNumber;
import org.cyk.system.root.model.information.Comment;
import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.system.root.model.mathematics.machine.FiniteStateMachineStateLog;
import org.cyk.system.root.model.network.UniformResourceLocator;
import org.cyk.system.root.model.party.Application;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.pattern.tree.AbstractDataTree;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeType;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;
import org.cyk.system.root.model.security.License;
import org.cyk.system.root.model.security.Role;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.ui.api.config.IdentifiableConfiguration;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.api.model.EnumerationForm;
import org.cyk.ui.api.model.geography.LocalityForm;
import org.cyk.ui.api.model.geography.LocalityTypeForm;
import org.cyk.ui.api.model.party.AbstractActorEditFormModel;
import org.cyk.ui.api.model.party.AbstractActorQueryManyFormModel;
import org.cyk.ui.api.model.party.AbstractActorQueryOneFormModel;
import org.cyk.ui.api.model.pattern.tree.AbstractDataTreeForm;
import org.cyk.ui.api.model.pattern.tree.AbstractDataTreeTypeForm;
import org.cyk.ui.web.api.AbstractServletContextListener;
import org.cyk.ui.web.api.ContextParam;
import org.cyk.ui.web.primefaces.page.AbstractBusinessEntityFormManyPage.BusinessEntityFormManyPageListener;
import org.cyk.ui.web.primefaces.page.AbstractBusinessEntityFormOnePage.BusinessEntityFormOnePageListener;
import org.cyk.ui.web.primefaces.page.crud.AbstractConsultPage.ConsultPageListener;
import org.cyk.ui.web.primefaces.page.event.EventEditPage;
import org.cyk.ui.web.primefaces.page.event.EventListPage;
import org.cyk.ui.web.primefaces.page.event.EventMissedEditPage;
import org.cyk.ui.web.primefaces.page.event.EventMissedListPage;
import org.cyk.ui.web.primefaces.page.event.EventPartyEditPage;
import org.cyk.ui.web.primefaces.page.event.EventPartyListPage;
import org.cyk.ui.web.primefaces.page.file.DefaultReportBasedOnDynamicBuilderServletAdapter;
import org.cyk.ui.web.primefaces.page.file.FileEditPage;
import org.cyk.ui.web.primefaces.page.file.FileIdentifiableGlobalIdentifierEditPage;
import org.cyk.ui.web.primefaces.page.file.FileListPage;
import org.cyk.ui.web.primefaces.page.file.ScriptEditPage;
import org.cyk.ui.web.primefaces.page.file.ScriptVariableEditPage;
import org.cyk.ui.web.primefaces.page.geography.ContactCollectionEditPage;
import org.cyk.ui.web.primefaces.page.geography.ContactCollectionListPage;
import org.cyk.ui.web.primefaces.page.geography.CountryEditPage;
import org.cyk.ui.web.primefaces.page.geography.CountryListPage;
import org.cyk.ui.web.primefaces.page.geography.ElectronicMailEditPage;
import org.cyk.ui.web.primefaces.page.geography.ElectronicMailListPage;
import org.cyk.ui.web.primefaces.page.geography.PhoneNumberEditPage;
import org.cyk.ui.web.primefaces.page.geography.PhoneNumberListPage;
import org.cyk.ui.web.primefaces.page.information.CommentEditPage;
import org.cyk.ui.web.primefaces.page.mathematics.FiniteStateMachineStateLogEditPage;
import org.cyk.ui.web.primefaces.page.mathematics.MovementCollectionEditPage;
import org.cyk.ui.web.primefaces.page.mathematics.MovementCollectionListPage;
import org.cyk.ui.web.primefaces.page.mathematics.MovementEditPage;
import org.cyk.ui.web.primefaces.page.mathematics.MovementListPage;
import org.cyk.ui.web.primefaces.page.nestedset.NestedSetNodeEditPage;
import org.cyk.ui.web.primefaces.page.party.AbstractActorConsultPageAdapter;
import org.cyk.ui.web.primefaces.page.party.AbstractActorCrudManyPageAdapter;
import org.cyk.ui.web.primefaces.page.party.AbstractActorEditPage;
import org.cyk.ui.web.primefaces.page.party.AbstractActorListPage;
import org.cyk.ui.web.primefaces.page.party.AbstractPersonEditPage;
import org.cyk.ui.web.primefaces.page.party.AbstractPersonListPage;
import org.cyk.ui.web.primefaces.page.party.ApplicationEditPage;
import org.cyk.ui.web.primefaces.page.party.PersonEditPage;
import org.cyk.ui.web.primefaces.page.security.LicenseEditPage;
import org.cyk.ui.web.primefaces.page.security.RoleEditPage;
import org.cyk.ui.web.primefaces.page.security.UniformResourceLocatorEditPage;
import org.cyk.ui.web.primefaces.page.security.UserAccountEditPage;
import org.primefaces.model.TreeNode;

public abstract class AbstractContextListener extends AbstractServletContextListener<TreeNode,HierarchyNode,UserSession> implements Serializable {

	private static final long serialVersionUID = 592943227142026384L;
	
	@Inject protected DefaultDesktopLayoutManager layoutManager;
	@Inject protected PrimefacesManager primefacesManager;
 
	@Override 
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
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
		
		initializeEventModule();
		/*
		initializeFileModule();
		initializeFiniteStateMachineModule();
		initializeGeographyModule();
		initializeInformationModule();
		initializeMathematicsModule();
		initializeNestedSetModule();
		initializeNetworkModule();
		initializePartyModule();
		initializeSecurityModule();
		*/
		
		
	}
	
	protected void initializePartyModule(){
		uiManager.registerConfiguration(new IdentifiableConfiguration(Application.class, ApplicationEditPage.Form.class, ApplicationDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(Application.class, null);
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(Person.class, PersonEditPage.Form.class, PersonDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(Person.class, null);
		//BusinessEntityFormManyPageListener.COLLECTION.add(new AbstractPersonListPage.AbstractPersonListPageAdapter.AbstractDefault.Default<Person>(Person.class));
		//BusinessEntityFormOnePageListener.COLLECTION.add(new AbstractPersonEditPage.AbstractPageAdapter.Default<Person>(Person.class));
		ConsultPageListener.COLLECTION.add(new ConsultPageListener.Adapter.Default<Person>(Person.class));
	}
	
	protected void initializeFileModule(){
		uiManager.registerConfiguration(new IdentifiableConfiguration(File.class, FileEditPage.Form.class, FileDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(File.class, null);
		BusinessEntityFormOnePageListener.COLLECTION.add(new FileEditPage.Adapter());
		BusinessEntityFormManyPageListener.COLLECTION.add(new FileListPage.Adapter());
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(FileIdentifiableGlobalIdentifier.class, FileIdentifiableGlobalIdentifierEditPage.Form.class, FileIdentifiableGlobalIdentifierDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(FileIdentifiableGlobalIdentifier.class, null);
		uiManager.registerConfiguration(new IdentifiableConfiguration(Script.class, ScriptEditPage.Form.class, ScriptDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(Script.class, null);
		uiManager.registerConfiguration(new IdentifiableConfiguration(ScriptVariable.class, ScriptVariableEditPage.Form.class, ScriptVariableDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(ScriptVariable.class, null);
	}
	
	protected void initializeEventModule(){
		uiManager.registerConfiguration(new IdentifiableConfiguration(Event.class, EventEditPage.Form.class, EventDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(Event.class, null);
		//BusinessEntityFormManyPageListener.COLLECTION.add(new EventListPage.Adapter());
		//BusinessEntityFormOnePageListener.COLLECTION.add(new EventEditPage.Adapter());
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(EventParty.class, EventPartyEditPage.Form.class, EventPartyDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(EventParty.class, null);
		//BusinessEntityFormManyPageListener.COLLECTION.add(new EventPartyListPage.Adapter());
		//BusinessEntityFormOnePageListener.COLLECTION.add(new EventPartyEditPage.Adapter());
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(EventMissed.class, EventMissedEditPage.Form.class, EventMissedDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(EventMissed.class, null);
		//BusinessEntityFormManyPageListener.COLLECTION.add(new EventMissedListPage.Adapter());
		//BusinessEntityFormOnePageListener.COLLECTION.add(new EventMissedEditPage.Adapter());
	}
	
	protected void initializeGeographyModule(){
		uiManager.registerConfiguration(new IdentifiableConfiguration(LocalityType.class, LocalityTypeForm.class, LocalityTypeForm.class,null,null,null));
		uiManager.registerConfiguration(new IdentifiableConfiguration(Locality.class, LocalityForm.class, LocalityForm.class,null,null,null));
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(Country.class, CountryEditPage.Form.class, CountryDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(Country.class, null);
		BusinessEntityFormManyPageListener.COLLECTION.add(new CountryListPage.Adapter());
		BusinessEntityFormOnePageListener.COLLECTION.add(new CountryEditPage.Adapter());
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(ContactCollection.class, ContactCollectionEditPage.Form.class, ContactCollectionDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(ContactCollection.class, null);
		BusinessEntityFormManyPageListener.COLLECTION.add(new ContactCollectionListPage.Adapter());
		BusinessEntityFormOnePageListener.COLLECTION.add(new ContactCollectionEditPage.Adapter());
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(ElectronicMail.class, ElectronicMailEditPage.Form.class, ElectronicMailDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(ElectronicMail.class, null);
		BusinessEntityFormManyPageListener.COLLECTION.add(new ElectronicMailListPage.Adapter());
		BusinessEntityFormOnePageListener.COLLECTION.add(new ElectronicMailEditPage.Adapter());
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(PhoneNumber.class, PhoneNumberEditPage.Form.class, PhoneNumberDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(PhoneNumber.class, null);
		BusinessEntityFormManyPageListener.COLLECTION.add(new PhoneNumberListPage.Adapter());
		BusinessEntityFormOnePageListener.COLLECTION.add(new PhoneNumberEditPage.Adapter());
	}
	
	protected void initializeNetworkModule(){
		uiManager.registerConfiguration(new IdentifiableConfiguration(UniformResourceLocator.class, UniformResourceLocatorEditPage.Form.class
				, UniformResourceLocatorDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(UniformResourceLocator.class, null);
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
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(UserAccount.class, UserAccountEditPage.Form.class, UserAccountDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(UserAccount.class, null);
	}
	
	protected void initializeNestedSetModule(){
		uiManager.registerConfiguration(new IdentifiableConfiguration(NestedSetNode.class, NestedSetNodeEditPage.Form.class, NestedSetNodeDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(NestedSetNode.class, null);
	}
	
	protected void initializeMathematicsModule(){
		uiManager.registerConfiguration(new IdentifiableConfiguration(MovementCollection.class, MovementCollectionEditPage.Form.class, MovementCollectionDetails.class
				,null,null,null));
		uiManager.configBusinessIdentifiable(MovementCollection.class, null);
		BusinessEntityFormManyPageListener.COLLECTION.add(new MovementCollectionListPage.Adapter());
		BusinessEntityFormOnePageListener.COLLECTION.add(new MovementCollectionEditPage.Adapter());
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(Movement.class, MovementEditPage.Form.class, MovementDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(Movement.class, null);
		BusinessEntityFormManyPageListener.COLLECTION.add(new MovementListPage.Adapter());
		BusinessEntityFormOnePageListener.COLLECTION.add(new MovementEditPage.Adapter());
		
	}
	
	protected void initializeFiniteStateMachineModule(){
		uiManager.registerConfiguration(new IdentifiableConfiguration(FiniteStateMachineStateLog.class, FiniteStateMachineStateLogEditPage.Form.class, FiniteStateMachineStateLogDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(FiniteStateMachineStateLog.class, null);
	}
	
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
		
		uiManager.businessEntityInfos(actorClass).getUserInterface().setSelectOneViewId(webNavigationManager.getOutcomeDynamicSelectOne());
		uiManager.businessEntityInfos(actorClass).getUserInterface().setSelectManyViewId(webNavigationManager.getOutcomeDynamicSelectMany());
		
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
