package org.cyk.ui.web.primefaces.test.form;

import java.lang.reflect.Field;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import org.cyk.system.root.business.api.language.LanguageBusiness.FindTextResult;
import org.cyk.system.root.business.api.language.LanguageCollectionBusiness;
import org.cyk.system.root.business.impl.language.LanguageBusinessImpl;
import org.cyk.system.root.model.file.FileIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.information.Comment;
import org.cyk.system.root.model.party.person.MedicalInformations;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.system.test.business.MyWebManager;
import org.cyk.system.test.business.impl.actor.ActorBusinessImpl;
import org.cyk.system.test.model.actor.Actor;
import org.cyk.system.test.model.actor.ActorDetails;
import org.cyk.system.test.model.actor.ActorEditPage;
import org.cyk.system.test.model.actor.ActorProcessManyPageAdapter;
import org.cyk.system.test.model.actor.ActorQueryManyFormModel;
import org.cyk.system.test.model.actor.ActorQueryOneFormModel;
import org.cyk.system.test.model.actor.ActorSelectManyPageAdapter;
import org.cyk.system.test.model.actor.ActorSelectOnePageAdapter;
import org.cyk.ui.api.config.IdentifiableConfiguration;
import org.cyk.ui.api.model.party.AbstractActorEditFormModel;
import org.cyk.ui.api.model.party.AbstractPersonEditFormModel;
import org.cyk.ui.web.primefaces.AbstractContextListener;
import org.cyk.ui.web.primefaces.InputAdapter;
import org.cyk.ui.web.primefaces.page.AbstractProcessManyPage;
import org.cyk.ui.web.primefaces.page.AbstractSelectManyPage;
import org.cyk.ui.web.primefaces.page.AbstractSelectOnePage;
import org.cyk.ui.web.primefaces.resources.PrimefacesResourcesManager;
import org.cyk.ui.web.primefaces.resources.ServletContextListener;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.userinterface.input.Input;

@WebListener
public class ContextListener extends AbstractContextListener {

	private static final long serialVersionUID = -3211898049670089807L;
	
	@Override
	protected void initialisation() {
		super.initialisation();  
		inject(PrimefacesResourcesManager.class).initialize();
		
		
		ClassHelper.getInstance().map(Input.Listener.Adapter.Default.class, InputAdapter.class);
		
		uiManager.registerApplicationUImanager(MyWebManager.getInstance());
		Comment.define(Actor.class); 
		FileIdentifiableGlobalIdentifier.define(Actor.class);
		
		ActorBusinessImpl.Listener.COLLECTION.add(new ActorBusinessImpl.Listener.Adapter.Default(){
			private static final long serialVersionUID = 4605368263736933413L;
			@Override
			public void afterInstanciateOne(UserAccount userAccount,Actor actor) {
				actor.getPerson().getExtendedInformations().setLanguageCollection(inject(LanguageCollectionBusiness.class).instanciateOne(userAccount));
				actor.getPerson().setMedicalInformations(new MedicalInformations(actor.getPerson()));
				super.afterInstanciateOne(userAccount, actor);
			}
		});
		
		/*AbstractWindow.WindowInstanceManager.INSTANCE = new PageInstanceManager(){
			private static final long serialVersionUID = 1L;
			@Override
			public Boolean isShowDetails(Class<?> detailsClass,AbstractIdentifiable identifiable,AbstractWindow<?, ?, ?, ?, ?, ?> window) {
				if(MedicalDetails.class.equals(detailsClass) || MedicalInformationsAllergyDetails.class.equals(detailsClass) 
						|| MedicalInformationsMedicationDetails.class.equals(detailsClass))
					return Boolean.FALSE;
				if(PersonRelationshipDetails.class.equals(detailsClass))
					return Boolean.TRUE;
				if(SignatureDetails.class.equals(detailsClass) && identifiable instanceof Person)
					return Boolean.FALSE;
				if(JobDetails.class.equals(detailsClass) && identifiable instanceof Actor)
					return Boolean.FALSE;
				return super.isShowDetails(detailsClass, identifiable,window);
			}
		};*/
		
		LanguageBusinessImpl.Listener.COLLECTION.add(new LanguageBusinessImpl.Listener.Adapter.Default.EnterpriseResourcePlanning(){
			private static final long serialVersionUID = 1L;

			@Override
			public FindTextResult afterFindFieldLabelText(Object object,Field field, FindTextResult findTextResult) {
				if(object instanceof AbstractActorEditFormModel<?>){
					if(AbstractPersonEditFormModel.FIELD_CODE.equals(field.getName()))
						findTextResult.setValue("THE ADMISSION NO");
					else if(AbstractPersonEditFormModel.FIELD_NAME.equals(field.getName()))
						findTextResult.setValue("MY SURNAME");
					else if(AbstractPersonEditFormModel.FIELD_LAST_NAMES.equals(field.getName()))
						findTextResult.setValue("ITS FORNAME(S)");
				}
				return super.afterFindFieldLabelText(object, field, findTextResult);
			}
		});
	}
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		
		MyWebManager.getInstance().getListeners().add(new org.cyk.system.test.business.PrimefacesManager());
		
		AbstractSelectOnePage.Listener.COLLECTION.add(new ActorSelectOnePageAdapter());
		AbstractSelectManyPage.Listener.COLLECTION.add(new ActorSelectManyPageAdapter());
		AbstractProcessManyPage.Listener.COLLECTION.add(new ActorProcessManyPageAdapter());
		
		AbstractSelectManyPage.Listener.COLLECTION.add(new PersonSelectManyPageAdapter());

		//ServletContextListener.CONTEXT = event.getServletContext().getContextPath();
		
		
	}
	
	@Override
	protected void identifiableConfiguration(ServletContextEvent event) {
		super.identifiableConfiguration(event);
		
		uiManager.businessEntityInfos(UserAccount.class).getUserInterface().setEditViewId("useraccountcrudone");
		
		uiManager.registerConfiguration(new IdentifiableConfiguration(Actor.class, ActorEditPage.Form.class, ActorDetails.class,null,null,null));
		uiManager.configBusinessIdentifiable(Actor.class, null);
		
		/*BusinessServiceProvider.Identifiable.COLLECTION.add(new AbstractActorBusinessImpl.BusinessServiceProviderIdentifiable<Actor,Actor.SearchCriteria>(Actor.class){
			private static final long serialVersionUID = 1322416788278558869L;
			
			@Override
			protected SearchCriteria createSearchCriteria(Service service,DataReadConfiguration dataReadConfiguration) {
				return new Actor.SearchCriteria(dataReadConfiguration.getGlobalFilter());
			}
        });*/
		
	}

	@Override
	protected Class<?> getQueryOneFormModelClass(Class<?> clazz) {
		if(Actor.class.equals(clazz))
			return ActorQueryOneFormModel.class;
		return super.getQueryOneFormModelClass(clazz);
	}
	
	@Override
	protected Class<?> getQueryManyFormModelClass(Class<?> clazz) {
		if(Actor.class.equals(clazz))
			return ActorQueryManyFormModel.class;
		return super.getQueryManyFormModelClass(clazz);
	}
	
	/**/
	
}
