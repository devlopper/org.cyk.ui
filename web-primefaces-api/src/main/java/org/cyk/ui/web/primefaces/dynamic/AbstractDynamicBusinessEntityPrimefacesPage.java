package org.cyk.ui.web.primefaces.dynamic;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.ui.web.primefaces.AbstractPrimefacesPage;

@Getter
@Setter
public abstract class AbstractDynamicBusinessEntityPrimefacesPage extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	protected BusinessEntityInfos businessEntityInfos;
	
	@Override
	protected void initialisation() { 
		super.initialisation();
		businessEntityInfos = uiManager.classFromKey(requestParameter(webManager.getRequestParameterClass()));
		contentTitle = text(businessEntityInfos.getUiLabelId());
	}
	
}
