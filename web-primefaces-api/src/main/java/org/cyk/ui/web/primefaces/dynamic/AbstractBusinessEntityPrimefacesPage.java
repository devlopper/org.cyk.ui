package org.cyk.ui.web.primefaces.dynamic;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.web.primefaces.AbstractPrimefacesPage;

@Getter
@Setter
public abstract class AbstractBusinessEntityPrimefacesPage<ENTITY extends AbstractIdentifiable> extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	protected BusinessEntityInfos businessEntityInfos;
	protected ENTITY identifiable;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void initialisation() { 
		super.initialisation();
		businessEntityInfos = fetchBusinessEntityInfos();
		identifiable = identifiableFromRequestParameter((Class<ENTITY>)businessEntityInfos.getClazz());
		contentTitle = businessEntityInfos.getUiLabel();
	}
	
	protected BusinessEntityInfos fetchBusinessEntityInfos(){
		return uiManager.classFromKey(requestParameter(webManager.getRequestParameterClass()));
	}
	
}
