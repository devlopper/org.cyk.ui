package org.cyk.ui.web.primefaces.page.crud;

import java.io.Serializable;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.CommonBusinessAction;
import org.cyk.system.root.business.api.language.LanguageBusiness.FindDoSomethingTextParameters;
import org.cyk.system.root.model.AbstractIdentifiable;
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

}