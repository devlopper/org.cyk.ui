package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;

import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.business.impl.file.FileDetails;
import org.cyk.system.root.business.impl.file.FileIdentifiableGlobalIdentifierDetails;
import org.cyk.system.root.business.impl.information.CommentDetails;
import org.cyk.system.root.business.impl.mathematics.MovementCollectionDetails;
import org.cyk.system.root.business.impl.mathematics.MovementDetails;
import org.cyk.system.root.business.impl.mathematics.machine.FiniteStateMachineStateLogDetails;
import org.cyk.system.root.business.impl.party.ApplicationDetails;
import org.cyk.system.root.business.impl.pattern.tree.NestedSetNodeDetails;
import org.cyk.system.root.business.impl.security.LicenseDetails;
import org.cyk.system.root.business.impl.security.RoleDetails;
import org.cyk.system.root.business.impl.security.UniformResourceLocatorDetails;
import org.cyk.system.root.business.impl.security.UserAccountDetails;
import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.FileIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.geography.LocalityType;
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
import org.cyk.ui.api.config.OutputDetailsConfiguration;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.api.data.collector.form.FormConfiguration;
import org.cyk.ui.api.model.EnumerationForm;
import org.cyk.ui.api.model.geography.ContactCollectionEditFormModel;
import org.cyk.ui.api.model.geography.ContactDetails;
import org.cyk.ui.api.model.geography.LocalityForm;
import org.cyk.ui.api.model.geography.LocalityTypeForm;
import org.cyk.ui.api.model.party.AbstractActorEditFormModel;
import org.cyk.ui.api.model.party.AbstractActorQueryManyFormModel;
import org.cyk.ui.api.model.party.AbstractActorQueryOneFormModel;
import org.cyk.ui.api.model.party.DefaultActorOutputDetails;
import org.cyk.ui.api.model.party.DefaultPersonEditFormModel;
import org.cyk.ui.api.model.pattern.tree.AbstractDataTreeForm;
import org.cyk.ui.api.model.pattern.tree.AbstractDataTreeTypeForm;
import org.cyk.ui.web.api.AbstractServletContextListener;
import org.cyk.ui.web.api.ContextParam;
import org.cyk.ui.web.api.WebNavigationManager;
import org.cyk.ui.web.primefaces.page.AbstractBusinessEntityFormManyPage;
import org.cyk.ui.web.primefaces.page.AbstractBusinessEntityFormOnePage;
import org.cyk.ui.web.primefaces.page.FileEditPage;
import org.cyk.ui.web.primefaces.page.crud.AbstractConsultPage;
import org.cyk.ui.web.primefaces.page.crud.AbstractConsultPage.ConsultPageListener;
import org.cyk.ui.web.primefaces.page.file.DefaultReportBasedOnDynamicBuilderServletAdapter;
import org.cyk.ui.web.primefaces.page.file.FileIdentifiableGlobalIdentifierEditPage;
import org.cyk.ui.web.primefaces.page.information.CommentEditPage;
import org.cyk.ui.web.primefaces.page.mathematics.FiniteStateMachineStateLogEditPage;
import org.cyk.ui.web.primefaces.page.mathematics.MovementCollectionEditPage;
import org.cyk.ui.web.primefaces.page.mathematics.MovementEditPage;
import org.cyk.ui.web.primefaces.page.nestedset.NestedSetNodeEditPage;
import org.cyk.ui.web.primefaces.page.party.AbstractActorConsultPage;
import org.cyk.ui.web.primefaces.page.party.AbstractActorConsultPageAdapter;
import org.cyk.ui.web.primefaces.page.party.AbstractActorCrudManyPageAdapter;
import org.cyk.ui.web.primefaces.page.party.AbstractActorCrudOnePageAdapter;
import org.cyk.ui.web.primefaces.page.party.ApplicationEditPage;
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
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(File.class, FileEditPage.Form.class, FileDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(File.class, null);
		
		//OutputDetailsConfiguration outputDetailsConfiguration;
		
		registerOutputDetailsConfiguration(AbstractActorConsultPage.MainDetails.class,DefaultPersonEditFormModel.FIELD_TITLE,DefaultPersonEditFormModel.FIELD_NAME
				,DefaultPersonEditFormModel.FIELD_LAST_NAME,DefaultPersonEditFormModel.FIELD_IMAGE,DefaultPersonEditFormModel.FIELD_BIRTH_DATE
				,DefaultPersonEditFormModel.FIELD_BIRTH_LOCATION,DefaultPersonEditFormModel.FIELD_SEX,DefaultPersonEditFormModel.FIELD_NATIONALITY);
		
		registerOutputDetailsConfiguration(ContactDetails.class, ContactCollectionEditFormModel.FIELD_MOBILE_PHONE_NUMBER
				,ContactCollectionEditFormModel.FIELD_LAND_PHONE_NUMBER,ContactCollectionEditFormModel.FIELD_ELECTRONICMAIL
				,ContactCollectionEditFormModel.FIELD_HOME_LOCATION,ContactCollectionEditFormModel.FIELD_POSTALBOX);
		
		/*
		outputDetailsConfiguration = registerOutputDetailsConfiguration(ConsultActorPage.MedicalDetails.class);
		outputDetailsConfiguration.setUiEditViewId(webNavigationManager.getOutcomeEditActorMedicalInformations());
		
		outputDetailsConfiguration = registerOutputDetailsConfiguration(ConsultActorPage.RelationshipDetails.class);
		outputDetailsConfiguration.setUiEditViewId(webNavigationManager.getOutcomeEditActorRelationship());
		
		registerOutputDetailsConfiguration(ConsultActorPage.JobDetails.class,DefaultPersonEditFormModel.FIELD_COMPANY,DefaultPersonEditFormModel.FIELD_JOB_TITLE
				,DefaultPersonEditFormModel.FIELD_JOB_FUNCTION,DefaultPersonEditFormModel.FIELD_JOB_CONTACTS);
		*/
	
		IdentifiableConfiguration identifiableConfiguration;
		
		uiManager.registerConfiguration(identifiableConfiguration = new IdentifiableConfiguration(AbstractDataTree.class, AbstractDataTreeForm.Default.class, AbstractDataTreeForm.Default.class,null,null,null));
		identifiableConfiguration.setUsableByChild(Boolean.TRUE);
		
		uiManager.registerConfiguration(identifiableConfiguration = new IdentifiableConfiguration(AbstractDataTreeType.class, AbstractDataTreeTypeForm.Default.class, AbstractDataTreeTypeForm.Default.class,null,null,null));
		identifiableConfiguration.setUsableByChild(Boolean.TRUE);
		
		uiManager.registerConfiguration(identifiableConfiguration = new IdentifiableConfiguration(AbstractEnumeration.class, EnumerationForm.class, EnumerationForm.class,null,null,null));
		identifiableConfiguration.setUsableByChild(Boolean.TRUE);
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(LocalityType.class, LocalityTypeForm.class, LocalityTypeForm.class,null,null,null));
		uiManager.registerConfiguration(new IdentifiableConfiguration(Locality.class, LocalityForm.class, LocalityForm.class,null,null,null));
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(Application.class, ApplicationEditPage.Form.class, ApplicationDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(Application.class, null);
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(License.class, LicenseEditPage.Form.class, LicenseDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(License.class, null);
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(UniformResourceLocator.class, UniformResourceLocatorEditPage.Form.class
				, UniformResourceLocatorDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(UniformResourceLocator.class, null);
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(Role.class, RoleEditPage.Form.class, RoleDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(Role.class, null);
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(UserAccount.class, UserAccountEditPage.Form.class, UserAccountDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(UserAccount.class, null);
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(MovementCollection.class, MovementCollectionEditPage.Form.class, MovementCollectionDetails.class
				,null,null,null));
		uiManager.configBusinessIdentifiable(MovementCollection.class, null);
		uiManager.registerConfiguration(new IdentifiableConfiguration(Movement.class, MovementEditPage.Form.class, MovementDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(Movement.class, null);
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(FiniteStateMachineStateLog.class, FiniteStateMachineStateLogEditPage.Form.class, FiniteStateMachineStateLogDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(FiniteStateMachineStateLog.class, null);
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(NestedSetNode.class, NestedSetNodeEditPage.Form.class, NestedSetNodeDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(NestedSetNode.class, null);
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(Comment.class, CommentEditPage.Form.class, CommentDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(Comment.class, null);
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(FileIdentifiableGlobalIdentifier.class, FileIdentifiableGlobalIdentifierEditPage.Form.class, FileIdentifiableGlobalIdentifierDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(FileIdentifiableGlobalIdentifier.class, null);
		
		ConsultPageListener.COLLECTION.add(new ConsultPageListener.Adapter.Default<Person>(Person.class));
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
		uiManager.businessEntityInfos(actorClass).getUserInterface().setConsultViewId("actorConsultView");
		uiManager.businessEntityInfos(actorClass).getUserInterface().setSelectOneViewId(webNavigationManager.getOutcomeDynamicSelectOne());
		uiManager.businessEntityInfos(actorClass).getUserInterface().setSelectManyViewId(webNavigationManager.getOutcomeDynamicSelectMany());
		
		AbstractBusinessEntityFormOnePage.BusinessEntityFormOnePageListener.COLLECTION.add(getActorCrudOnePageAdapter(actorClass));
		AbstractBusinessEntityFormManyPage.BusinessEntityFormManyPageListener.COLLECTION.add(getActorCrudManyPageAdapter(actorClass));
		AbstractConsultPage.ConsultPageListener.COLLECTION.add(getActorConsultPageAdapter(actorClass));
		
		logInfo("Actor {} forms registered", actorClass.getSimpleName());
	}
	
	protected <ACTOR extends AbstractActor> AbstractActorCrudOnePageAdapter<ACTOR> getActorCrudOnePageAdapter(Class<ACTOR> actorClass){
		return new AbstractActorCrudOnePageAdapter.Default<ACTOR>(actorClass);
	}
	protected <ACTOR extends AbstractActor> AbstractActorCrudManyPageAdapter<ACTOR> getActorCrudManyPageAdapter(Class<ACTOR> actorClass){
		return new AbstractActorCrudManyPageAdapter.Default<ACTOR>(actorClass);
	}
	protected <ACTOR extends AbstractActor> AbstractActorConsultPageAdapter<ACTOR> getActorConsultPageAdapter(Class<ACTOR> actorClass){
		return new AbstractActorConsultPageAdapter.Default<ACTOR>(actorClass);
	}
	
	protected Class<? extends AbstractFormModel<?>> getEditFormModelClass(Class<?> clazz){
		if(AbstractActor.class.isAssignableFrom(clazz))
			return AbstractActorEditFormModel.Default.class;
		return null;
	}
	
	protected Class<?> getReadFormModelClass(Class<?> clazz){
		if(AbstractActor.class.isAssignableFrom(clazz))
			return DefaultActorOutputDetails.class;
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
	
	protected OutputDetailsConfiguration registerOutputDetailsConfiguration(Class<? extends AbstractOutputDetails<?>> outputDetailsClass,String...fieldNames){
		OutputDetailsConfiguration detailsConfiguration = new OutputDetailsConfiguration(outputDetailsClass);
		//detailsConfiguration.setName(languageBusiness.findText("baseinformations"));
		detailsConfiguration.setEditFormConfiguration(new FormConfiguration(FormConfiguration.TYPE_INPUT_SET_DEFAULT));
		detailsConfiguration.getEditFormConfiguration().addFieldNames(fieldNames);
		detailsConfiguration.setUiEditViewId(WebNavigationManager.getInstance().getOutcomeDynamicCrudOne());
		uiManager.registerOutputDetailsConfiguration(detailsConfiguration);
		return detailsConfiguration;
	}

	
	protected void businessAdapters(ServletContextEvent event) {
		
	}
	
	protected void applicationUImanagers(ServletContextEvent event) {
		
	}
}
