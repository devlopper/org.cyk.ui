package org.cyk.ui.web.primefaces.test.business;

import java.io.Serializable;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import lombok.Getter;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.business.impl.AbstractBusinessLayer;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.test.model.Actor;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;

@Singleton @Deployment(initialisationType=InitialisationType.EAGER)
public class GuiBusinessLayer extends AbstractBusinessLayer implements Serializable {

	private static final long serialVersionUID = -462780912429013933L;

	@Inject private ActorBusiness actorBusiness;
	@Inject @Getter private GuiReportRepository reportRepository;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void registerTypedBusinessBean(Map<Class<AbstractIdentifiable>, TypedBusiness<AbstractIdentifiable>> beansMap) {
        beansMap.put((Class)Actor.class, (TypedBusiness)actorBusiness);
        
        
        
    }
	
	@Override
	protected void fakeTransactions() {
		
	}

	@Override
	protected void persistData() {
		
	}

	@Override
	protected void setConstants() {
		
	}

}
