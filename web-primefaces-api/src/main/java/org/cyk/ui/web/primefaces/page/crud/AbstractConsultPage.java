package org.cyk.ui.web.primefaces.page.crud;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.CommonBusinessAction;
import org.cyk.system.root.business.api.language.LanguageBusiness.FindDoSomethingTextParameters;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.Identifiable;
import org.cyk.ui.api.UIProvider;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UICommandable.IconType;
import org.cyk.ui.web.primefaces.page.AbstractBusinessEntityPrimefacesPage;
import org.cyk.ui.web.primefaces.page.ConsultPageListener;

@Getter @Setter
public abstract class AbstractConsultPage<IDENTIFIABLE extends AbstractIdentifiable> extends AbstractBusinessEntityPrimefacesPage<IDENTIFIABLE> implements Serializable {

	private static final long serialVersionUID = 9040359120893077422L;

	@Override
	protected void initialisation() { 
		super.initialisation();
		for(ConsultPageListener<?> listener : getListeners())
			listener.initialisationStarted(this); 
		
		consultInitialisation();
		
		for(ConsultPageListener<?> listener : getListeners()){
			listener.initialisationEnded(this); 
		}
	}
	
	protected void consultInitialisation(){}
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		for(ConsultPageListener<?> listener : getListeners())
			listener.afterInitialisationStarted(this);
		
		for(ConsultPageListener<?> listener : getListeners())
			listener.afterInitialisationEnded(this); 
	}
	
	@Override
	protected FindDoSomethingTextParameters getContentTitleDoSomethingTextParameters() {
		FindDoSomethingTextParameters parameters = super.getContentTitleDoSomethingTextParameters();
		parameters.setActionIdentifier(CommonBusinessAction.CONSULT);
		return parameters;
	}
	
	private Collection<ConsultPageListener<?>> getListeners(){
		return primefacesManager.getConsultPageListeners(businessEntityInfos.getClazz());
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
		UICommandable contextualMenu = UIProvider.getInstance().createCommandable("button", null);
		contextualMenu.setLabel(formatUsingBusiness(identifiable)); 
		
		if(Boolean.TRUE.equals(showContextualHierarchyConsultCommandables())){
			List<Object> parents = RootBusinessLayer.getInstance().getClazzBusiness().findParentsOf(businessEntityInfos.getUserInterface().getHierarchyHighestAncestorClass(),identifiable);
			Collections.reverse(parents);
			for(Object ancestor : parents)
				contextualMenu.getChildren().add(navigationManager.createConsultCommandable((AbstractIdentifiable) ancestor, null));
		}
		
		if(Boolean.TRUE.equals(showContextualEditCommandable())){
			contextualMenu.getChildren().add(navigationManager.createUpdateCommandable(identifiable, "command.edit", IconType.ACTION_UPDATE));
		}
		if(Boolean.TRUE.equals(showContextualDeleteCommandable())){
			contextualMenu.getChildren().add(navigationManager.createDeleteCommandable(identifiable, "command.delete", IconType.ACTION_DELETE));
		}
		
		if(Boolean.TRUE.equals(showContextualCreateCommandables()))
			for(Class<?> clazz : getManyToOneClasses())
				contextualMenu.getChildren().add(navigationManager.createCreateCommandable(identifiable,(Class<? extends AbstractIdentifiable>)clazz
						,RootBusinessLayer.getInstance().getApplicationBusiness().findBusinessEntityInfos((Class<? extends AbstractIdentifiable>) clazz).getUserInterface().getLabelId() ,null));
		
		processIdentifiableContextualCommandable(contextualMenu);
		
		return contextualMenu.getChildren().isEmpty() ? null : Arrays.asList(contextualMenu);
	}

}