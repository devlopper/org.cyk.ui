package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.config.IdentifiableConfiguration;

@Getter
@Setter
public abstract class AbstractBusinessEntityPrimefacesPage<ENTITY extends AbstractIdentifiable> extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	protected BusinessEntityInfos businessEntityInfos;
	protected IdentifiableConfiguration identifiableConfiguration ;
	protected ENTITY identifiable;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void initialisation() { 
		super.initialisation();
		businessEntityInfos = fetchBusinessEntityInfos();
		identifiableConfiguration = uiManager.findConfiguration((Class<? extends AbstractIdentifiable>) businessEntityInfos.getClazz());
		identifiable = identifiableFromRequestParameter((Class<ENTITY>)businessEntityInfos.getClazz());
		contentTitle = businessEntityInfos.getUiLabel();
	}
	
	protected BusinessEntityInfos fetchBusinessEntityInfos(){
		return uiManager.classFromKey(requestParameter(webManager.getRequestParameterClass()));
	}
	
}
