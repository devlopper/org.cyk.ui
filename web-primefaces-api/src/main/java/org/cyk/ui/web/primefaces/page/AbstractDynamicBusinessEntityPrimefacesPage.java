package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.web.primefaces.AbstractPrimefacesPage;

@Getter
@Setter
public abstract class AbstractDynamicBusinessEntityPrimefacesPage extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	protected BusinessEntityInfos businessEntityInfos;
	protected AbstractIdentifiable identifiable;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void initialisation() { 
		super.initialisation();
		businessEntityInfos = fetchBusinessEntityInfos(); //uiManager.classFromKey(requestParameter(webManager.getRequestParameterClass()));
		identifiable = identifiableFromRequestParameter((Class<AbstractIdentifiable>)businessEntityInfos.getClazz());
		contentTitle = text(businessEntityInfos.getUiLabelId());
	}
	
	protected BusinessEntityInfos fetchBusinessEntityInfos(){
		return uiManager.classFromKey(requestParameter(webManager.getRequestParameterClass()));
	}
	
}
