package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;

import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.ui.api.config.IdentifiableConfiguration;
import org.cyk.ui.api.config.OutputDetailsConfiguration;
import org.cyk.ui.api.data.collector.form.FormConfiguration;
import org.cyk.ui.api.model.AbstractOutputDetails;
import org.cyk.ui.api.model.geography.ContactCollectionEditFormModel;
import org.cyk.ui.api.model.party.DefaultActorEditFormModel;
import org.cyk.ui.api.model.party.DefaultActorReadFormModel;
import org.cyk.ui.api.model.party.DefaultPersonEditFormModel;
import org.cyk.ui.web.api.AbstractServletContextListener;
import org.cyk.ui.web.api.ContextParam;
import org.cyk.ui.web.api.WebNavigationManager;
import org.cyk.ui.web.primefaces.page.BusinessEntityFormManyPageListener;
import org.cyk.ui.web.primefaces.page.BusinessEntityFormOnePageListener;
import org.cyk.ui.web.primefaces.page.crud.ConsultActorPage;
import org.cyk.ui.web.primefaces.page.tools.DefaultActorCrudManyPageAdapter;
import org.cyk.ui.web.primefaces.page.tools.DefaultActorCrudOnePageAdapter;
import org.cyk.ui.web.primefaces.page.tools.DefaultReportBasedOnDynamicBuilderServletAdapter;

public abstract class AbstractContextListener extends AbstractServletContextListener implements Serializable {

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
		
		OutputDetailsConfiguration outputDetailsConfiguration;
		
		registerOutputDetailsConfiguration(ConsultActorPage.MainDetails.class,DefaultPersonEditFormModel.FIELD_TITLE,DefaultPersonEditFormModel.FIELD_NAME
				,DefaultPersonEditFormModel.FIELD_LAST_NAME,DefaultPersonEditFormModel.FIELD_IMAGE,DefaultPersonEditFormModel.FIELD_BIRTH_DATE
				,DefaultPersonEditFormModel.FIELD_BIRTH_LOCATION,DefaultPersonEditFormModel.FIELD_SEX,DefaultPersonEditFormModel.FIELD_NATIONALITY);
		
		registerOutputDetailsConfiguration(ConsultActorPage.ContactDetails.class, ContactCollectionEditFormModel.FIELD_MOBILE_PHONE_NUMBER
				,ContactCollectionEditFormModel.FIELD_LAND_PHONE_NUMBER,ContactCollectionEditFormModel.FIELD_ELECTRONICMAIL
				,ContactCollectionEditFormModel.FIELD_HOME_LOCATION,ContactCollectionEditFormModel.FIELD_POSTALBOX);
		
		outputDetailsConfiguration = registerOutputDetailsConfiguration(ConsultActorPage.MedicalDetails.class);
		outputDetailsConfiguration.setUiEditViewId(webNavigationManager.getOutcomeEditActorMedicalInformations());
		
		outputDetailsConfiguration = registerOutputDetailsConfiguration(ConsultActorPage.RelationshipDetails.class);
		outputDetailsConfiguration.setUiEditViewId(webNavigationManager.getOutcomeEditActorRelationship());
		
		registerOutputDetailsConfiguration(ConsultActorPage.JobDetails.class,DefaultPersonEditFormModel.FIELD_COMPANY,DefaultPersonEditFormModel.FIELD_JOB_TITLE
				,DefaultPersonEditFormModel.FIELD_JOB_FUNCTION,DefaultPersonEditFormModel.FIELD_JOB_CONTACTS);
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
		IdentifiableConfiguration configuration = new IdentifiableConfiguration(actorClass,DefaultActorEditFormModel.class,DefaultActorReadFormModel.class);
		uiManager.registerConfiguration(configuration);
		uiManager.businessEntityInfos(actorClass).setUiConsultViewId("actorConsultView");
		
		registerBusinessEntityFormOnePageListener(actorClass,new DefaultActorCrudOnePageAdapter<ACTOR>(actorClass));
		registerBusinessEntityFormManyPageListener(actorClass,new DefaultActorCrudManyPageAdapter<ACTOR>(actorClass));
		
		logInfo("Actor {} forms registered", actorClass.getSimpleName());
	}
	
	protected <ACTOR extends AbstractActor> void registerBusinessEntityFormOnePageListener(Class<ACTOR> actorClass,BusinessEntityFormOnePageListener<?> listener){
		primefacesManager.getBusinessEntityFormOnePageListeners().add(listener);
	}
	protected <ACTOR extends AbstractActor> void registerBusinessEntityFormManyPageListener(Class<ACTOR> actorClass,BusinessEntityFormManyPageListener<?> listener){
		primefacesManager.getBusinessEntityFormManyPageListeners().add(listener);
	}
	
	protected OutputDetailsConfiguration registerOutputDetailsConfiguration(Class<? extends AbstractOutputDetails<?>> outputDetailsClass,String...fieldNames){
		OutputDetailsConfiguration detailsConfiguration = new OutputDetailsConfiguration(outputDetailsClass);
		//detailsConfiguration.setName(languageBusiness.findText("baseinformations"));
		detailsConfiguration.setEditFormConfiguration(new FormConfiguration(FormConfiguration.Type.DEFAULT));
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
