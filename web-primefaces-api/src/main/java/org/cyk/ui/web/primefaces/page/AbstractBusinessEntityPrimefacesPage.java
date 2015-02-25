package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.config.IdentifiableConfiguration;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.web.api.WebManager;

@Getter
@Setter
public abstract class AbstractBusinessEntityPrimefacesPage<ENTITY extends AbstractIdentifiable> extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	protected BusinessEntityInfos businessEntityInfos;
	protected IdentifiableConfiguration identifiableConfiguration ;
	protected ENTITY identifiable;
	protected String formModelClassId;
	protected Class<? extends AbstractFormModel<?>> formModelClass;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void initialisation() { 
		super.initialisation();
		businessEntityInfos = fetchBusinessEntityInfos();
		identifiableConfiguration = uiManager.findConfiguration((Class<? extends AbstractIdentifiable>) businessEntityInfos.getClazz());
		identifiable = identifiableFromRequestParameter((Class<ENTITY>)businessEntityInfos.getClazz());
		formModelClassId = requestParameter(WebManager.getInstance().getRequestParameterFormModel());
		if(StringUtils.isNotBlank(formModelClassId))
			formModelClass = UIManager.FORM_MODEL_MAP.get(__formModelClassId__());
		contentTitle = text(businessEntityInfos.getUiLabelId());
	}
	
	protected BusinessEntityInfos fetchBusinessEntityInfos(){
		return uiManager.classFromKey(requestParameter(webManager.getRequestParameterClass()));
	}
	
	protected String __formModelClassId__(){
		return formModelClassId;
	}
	
	protected Class<? extends AbstractFormModel<?>> __formModelClass__(){
		return formModelClass;
	}
	
}
