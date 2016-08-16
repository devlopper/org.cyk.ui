package org.cyk.ui.web.primefaces.page.crud;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.CommonBusinessAction;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.language.LanguageBusiness.FindDoSomethingTextParameters;
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.business.impl.file.FileIdentifiableGlobalIdentifierDetails;
import org.cyk.system.root.business.impl.information.CommentDetails;
import org.cyk.system.root.business.impl.information.GlobalIdentifierDetails;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.Identifiable;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.ui.api.Icon;
import org.cyk.ui.api.command.AbstractCommandable.Builder;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.web.primefaces.data.collector.control.ControlSetAdapter;
import org.cyk.ui.web.primefaces.data.collector.form.FormOneData;
import org.cyk.ui.web.primefaces.page.AbstractBusinessEntityPrimefacesPage;
import org.cyk.ui.web.primefaces.page.file.FileIdentifiableGlobalIdentifiers;
import org.cyk.ui.web.primefaces.page.information.Comments;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractConsultPage<IDENTIFIABLE extends AbstractIdentifiable> extends AbstractBusinessEntityPrimefacesPage<IDENTIFIABLE> implements Serializable {

	private static final long serialVersionUID = 9040359120893077422L;
	
	protected FormOneData<? extends AbstractOutputDetails<IDENTIFIABLE>> details;
	protected Comments comments;
	protected FileIdentifiableGlobalIdentifiers fileIdentifiableGlobalIdentifiers;
	@SuppressWarnings("rawtypes")
	protected FormOneData<GlobalIdentifierDetails> globalIdentifierDetails;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void initialisation() { 
		super.initialisation();
		for(ConsultPageListener<?> listener : ConsultPageListener.Adapter.getConsultPageListeners(businessEntityInfos))
			listener.initialisationStarted(this); 
		
		consultInitialisation();
		
		comments = new Comments(this, CommentDetails.class,identifiable);
		fileIdentifiableGlobalIdentifiers = new FileIdentifiableGlobalIdentifiers(this, FileIdentifiableGlobalIdentifierDetails.class,identifiable);
		
		if(Boolean.TRUE.equals(userSession.getIsAdministrator())){
			globalIdentifierDetails = createDetailsForm(GlobalIdentifierDetails.class, identifiable, new DetailsConfigurationListener.Form.Adapter<IDENTIFIABLE,GlobalIdentifierDetails>((Class<IDENTIFIABLE>) identifiable.getClass(), GlobalIdentifierDetails.class){
				private static final long serialVersionUID = 1L;
				@Override
				public String getTitleId() {
					return "model.entity.globalIdentifier";
				}
			});
		}
		
		for(ConsultPageListener<?> listener :ConsultPageListener.Adapter.getConsultPageListeners(businessEntityInfos)){
			listener.initialisationEnded(this); 
		}
	}
	
	@SuppressWarnings("unchecked")
	protected void consultInitialisation(){
		@SuppressWarnings("rawtypes")
		DetailsConfigurationListener.Form.Adapter adapter = getDetailsConfiguration(getDetailsClass()).getFormConfigurationAdapter(identifiable.getClass(),getDetailsClass());
		adapter.setEnabledInDefaultTab(Boolean.TRUE);
		details = createDetailsForm(getDetailsClass(), identifiable, adapter);
		details.addControlSetListener(getDetailsConfiguration(getDetailsClass()).getFormControlSetAdapter(identifiable.getClass()));
	}
	
	@SuppressWarnings("unchecked")
	protected Class<AbstractOutputDetails<IDENTIFIABLE>> getDetailsClass(){
		return (Class<AbstractOutputDetails<IDENTIFIABLE>>) businessEntityInfos.getUserInterface().getDetailsClass();
	}
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		for(ConsultPageListener<?> listener : ConsultPageListener.Adapter.getConsultPageListeners(businessEntityInfos))
			listener.afterInitialisationStarted(this);
		
		
		
		for(ConsultPageListener<?> listener : ConsultPageListener.Adapter.getConsultPageListeners(businessEntityInfos))
			listener.afterInitialisationEnded(this); 
	}
	
	@Override
	protected FindDoSomethingTextParameters getContentTitleDoSomethingTextParameters() {
		FindDoSomethingTextParameters parameters = super.getContentTitleDoSomethingTextParameters();
		parameters.setActionIdentifier(CommonBusinessAction.CONSULT);
		return parameters;
	}
	
	protected Boolean showContextualHierarchyConsultCommandables(){
		return Boolean.TRUE;
	}
	protected Boolean showContextualEditCommandable(){
		return Boolean.TRUE;
	}
	protected Boolean showContextualDeleteCommandable(){
		return Boolean.TRUE;
	}
	
	protected Boolean showContextualCreateCommandables(){
		return Boolean.FALSE;
	}
	
	protected void processIdentifiableContextualCommandable(UICommandable commandable) {}
	
	protected Collection<Class<? extends Identifiable<?>>> getManyToOneClasses(){
		return businessEntityInfos.getManyToOneClasses();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected Collection<UICommandable> contextualCommandables() {
		UICommandable contextualMenu = instanciateCommandableBuilder().setLabel(formatUsingBusiness(identifiable)).create(); 
		
		if(Boolean.TRUE.equals(showContextualHierarchyConsultCommandables())){
			List<Object> parents = RootBusinessLayer.getInstance().getClazzBusiness().findParentsOf(businessEntityInfos.getUserInterface().getHierarchyHighestAncestorClass(),identifiable);
			Collections.reverse(parents);
			for(Object ancestor : parents)
				contextualMenu.getChildren().add(Builder.createConsult((AbstractIdentifiable) ancestor, null));
		}
		
		if(Boolean.TRUE.equals(showContextualEditCommandable())){
			if(globalIdentifierDetails!=null && Boolean.TRUE.equals(globalIdentifierDetails.getRendered())){
				contextualMenu.getChildren().add(Builder.createUpdateGlobalIdentifier(identifiable));
			}else
				contextualMenu.getChildren().add(Builder.createCrud(Crud.UPDATE, identifiable,"command.edit", Icon.ACTION_UPDATE));
		}
		if(Boolean.TRUE.equals(showContextualDeleteCommandable())){
			contextualMenu.getChildren().add(Builder.createCrud(Crud.DELETE, identifiable,"command.delete", Icon.ACTION_DELETE));
		}
		
		if(Boolean.TRUE.equals(showContextualCreateCommandables()))
			for(Class<?> clazz : getManyToOneClasses())
				contextualMenu.getChildren().add(Builder.createCreate(identifiable,(Class<? extends AbstractIdentifiable>)clazz
						,inject(ApplicationBusiness.class).findBusinessEntityInfos((Class<? extends AbstractIdentifiable>) clazz).getUserInterface().getLabelId() ,null));
		
		processIdentifiableContextualCommandable(contextualMenu);
		
		for(UICommandable commandable : contextualMenu.getChildren())
			if(StringUtils.isEmpty(selectedTabId))
				;
			else
				commandable.setParameter(UniformResourceLocatorParameter.TAB_ID, selectedTabId);
		
		return contextualMenu.getChildren().isEmpty() ? null : Arrays.asList(contextualMenu);
	}
	
	/**/

	public static interface ConsultPageListener<ENTITY extends AbstractIdentifiable> extends AbstractBusinessEntityPrimefacesPage.BusinessEntityPrimefacesPageListener<ENTITY> {

		Collection<ConsultPageListener<?>> COLLECTION = new ArrayList<>();
		
		public <DETAILS> ControlSetAdapter<DETAILS> getControlSetAdapter(Class<DETAILS> detailsClass);
		
		/**/
		
		public static class Adapter<ENTITY_TYPE extends AbstractIdentifiable> extends AbstractBusinessEntityPrimefacesPage.BusinessEntityPrimefacesPageListener.Adapter<ENTITY_TYPE> implements ConsultPageListener<ENTITY_TYPE>,Serializable {

			private static final long serialVersionUID = -7944074776241690783L;

			public Adapter(Class<ENTITY_TYPE> entityTypeClass) {
				super(entityTypeClass);
			}
			
			public <DETAILS> ControlSetAdapter<DETAILS> getControlSetAdapter(Class<DETAILS> detailsClass){
				return null;
			}
			
			public static Collection<ConsultPageListener<?>> getConsultPageListeners(Class<? extends Identifiable<?>> aClass){
				Collection<ConsultPageListener<?>> results = new ArrayList<>();
				if(aClass!=null)
					for(ConsultPageListener<?> listener : ConsultPageListener.COLLECTION)
						if(listener.getEntityTypeClass().isAssignableFrom(aClass))
							results.add(listener);
				return results;
			}
			public static Collection<ConsultPageListener<?>> getConsultPageListeners(BusinessEntityInfos businessEntityInfos){
				return getConsultPageListeners(businessEntityInfos==null ? null : businessEntityInfos.getClazz());
			}

			/**/
			
			public static class Default<ENTITY extends AbstractIdentifiable> extends ConsultPageListener.Adapter<ENTITY> implements Serializable {

				private static final long serialVersionUID = -4255109770974601234L;

				public Default(Class<ENTITY> entityTypeClass) {
					super(entityTypeClass);
				}
					
			}
		}
	}
	
}