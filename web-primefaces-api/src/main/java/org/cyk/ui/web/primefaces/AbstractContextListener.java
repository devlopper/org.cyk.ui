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
		
		registerOutputDetailsConfiguration(ConsultActorPage.MainDetails.class,DefaultPersonEditFormModel.FIELD_TITLE,DefaultPersonEditFormModel.FIELD_NAME
				,DefaultPersonEditFormModel.FIELD_LAST_NAME,DefaultPersonEditFormModel.FIELD_IMAGE,DefaultPersonEditFormModel.FIELD_BIRTH_DATE
				,DefaultPersonEditFormModel.FIELD_BIRTH_LOCATION,DefaultPersonEditFormModel.FIELD_SEX,DefaultPersonEditFormModel.FIELD_NATIONALITY);
		
		registerOutputDetailsConfiguration(ConsultActorPage.ContactDetails.class, ContactCollectionEditFormModel.FIELD_MOBILE_PHONE_NUMBER
				,ContactCollectionEditFormModel.FIELD_LAND_PHONE_NUMBER,ContactCollectionEditFormModel.FIELD_ELECTRONICMAIL
				,ContactCollectionEditFormModel.FIELD_HOME_LOCATION,ContactCollectionEditFormModel.FIELD_POSTALBOX);
		
		registerOutputDetailsConfiguration(ConsultActorPage.MedicalDetails.class,DefaultPersonEditFormModel.FIELD_BLOOD_GROUP
				,DefaultPersonEditFormModel.FIELD_OTHER_MEDICAL_INFORMATIONS);
		
		registerOutputDetailsConfiguration(ConsultActorPage.JobDetails.class,DefaultPersonEditFormModel.FIELD_COMPANY,DefaultPersonEditFormModel.FIELD_JOB_TITLE
				,DefaultPersonEditFormModel.FIELD_JOB_FUNCTION,DefaultPersonEditFormModel.FIELD_JOB_CONTACTS);
	}
	
	@Override
	protected void identifiableConfiguration(ServletContextEvent event) {
		super.identifiableConfiguration(event);
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
		
		primefacesManager.getBusinessEntityFormOnePageListeners().add(new DefaultActorCrudOnePageAdapter<ACTOR>(actorClass));
		primefacesManager.getBusinessEntityFormManyPageListeners().add(new DefaultActorCrudManyPageAdapter<ACTOR>(actorClass));
		
		logInfo("Actor {} forms registered", actorClass.getSimpleName());
	}
	
	protected void registerOutputDetailsConfiguration(Class<? extends AbstractOutputDetails<?>> outputDetailsClass,String...fieldNames){
		OutputDetailsConfiguration detailsConfiguration = new OutputDetailsConfiguration(outputDetailsClass);
		//detailsConfiguration.setName(languageBusiness.findText("baseinformations"));
		detailsConfiguration.setEditFormConfiguration(new FormConfiguration(FormConfiguration.Type.DEFAULT));
		detailsConfiguration.getEditFormConfiguration().addFieldNames(fieldNames);
		uiManager.registerOutputDetailsConfiguration(detailsConfiguration);
	}
	
}
