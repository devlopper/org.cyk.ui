package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;

import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.ui.api.config.IdentifiableConfiguration;
import org.cyk.ui.api.model.party.DefaultActorEditFormModel;
import org.cyk.ui.api.model.party.DefaultActorReadFormModel;
import org.cyk.ui.web.api.AbstractServletContextListener;
import org.cyk.ui.web.api.ContextParam;
import org.cyk.ui.web.primefaces.page.tools.DefaultActorCrudManyPageListener;
import org.cyk.ui.web.primefaces.page.tools.DefaultActorCrudOnePageListener;
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
		
		primefacesManager.getBusinessEntityFormOnePageListeners().add(new DefaultActorCrudOnePageListener<ACTOR>(actorClass));
		primefacesManager.getBusinessEntityFormManyPageListeners().add(new DefaultActorCrudManyPageListener<ACTOR>(actorClass));
		
		logInfo("Actor {} forms registered", actorClass.getSimpleName());
	}
	
}
