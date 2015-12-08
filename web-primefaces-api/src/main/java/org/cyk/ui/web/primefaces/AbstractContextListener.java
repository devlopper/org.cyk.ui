package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;

import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.ui.api.config.IdentifiableConfiguration;
import org.cyk.ui.api.config.OutputDetailsConfiguration;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.api.data.collector.form.FormConfiguration;
import org.cyk.ui.api.model.geography.ContactCollectionEditFormModel;
import org.cyk.ui.api.model.party.AbstractActorEditFormModel;
import org.cyk.ui.api.model.party.DefaultActorReadFormModel;
import org.cyk.ui.api.model.party.DefaultPersonEditFormModel;
import org.cyk.ui.web.api.AbstractServletContextListener;
import org.cyk.ui.web.api.ContextParam;
import org.cyk.ui.web.api.WebNavigationManager;
import org.cyk.ui.web.primefaces.page.BusinessEntityFormManyPageListener;
import org.cyk.ui.web.primefaces.page.BusinessEntityFormOnePageListener;
import org.cyk.ui.web.primefaces.page.ConsultPageListener;
import org.cyk.ui.web.primefaces.page.crud.ConsultActorPage;
import org.cyk.ui.web.primefaces.page.tools.AbstractActorConsultPageAdapter;
import org.cyk.ui.web.primefaces.page.tools.AbstractActorCrudManyPageAdapter;
import org.cyk.ui.web.primefaces.page.tools.AbstractActorCrudOnePageAdapter;
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
		IdentifiableConfiguration configuration = new IdentifiableConfiguration(actorClass,getEditFormModelClass(actorClass),DefaultActorReadFormModel.class);
		uiManager.registerConfiguration(configuration);
		uiManager.businessEntityInfos(actorClass).setUiConsultViewId("actorConsultView");
		
		registerBusinessEntityFormOnePageListener(actorClass,getActorCrudOnePageAdapter(actorClass));
		registerBusinessEntityFormManyPageListener(actorClass,getActorCrudManyPageAdapter(actorClass));
		registerConsultPageListener(actorClass, getActorConsultPageAdapter(actorClass));
		
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
	
	protected <IDENTIFIABLE extends AbstractIdentifiable> void registerBusinessEntityFormOnePageListener(Class<IDENTIFIABLE> aClass,BusinessEntityFormOnePageListener<?> listener){
		primefacesManager.getBusinessEntityFormOnePageListeners().add(listener);
	}
	protected <IDENTIFIABLE extends AbstractIdentifiable> void registerBusinessEntityFormManyPageListener(Class<IDENTIFIABLE> aClass,BusinessEntityFormManyPageListener<?> listener){
		primefacesManager.getBusinessEntityFormManyPageListeners().add(listener);
	}
	protected <IDENTIFIABLE extends AbstractIdentifiable> void registerConsultPageListener(Class<IDENTIFIABLE> aClass,ConsultPageListener<?> listener){
		primefacesManager.getConsultPageListeners().add(listener);
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
