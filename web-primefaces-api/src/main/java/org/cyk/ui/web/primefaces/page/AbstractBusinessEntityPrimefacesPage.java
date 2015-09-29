package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.config.IdentifiableConfiguration;
import org.cyk.ui.api.model.AbstractOutputDetails;
import org.cyk.ui.api.model.DetailsBlock;
import org.primefaces.model.menu.MenuModel;

@Getter
@Setter
public abstract class AbstractBusinessEntityPrimefacesPage<ENTITY extends AbstractIdentifiable> extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	protected BusinessEntityInfos businessEntityInfos;
	protected IdentifiableConfiguration identifiableConfiguration ;
	protected ENTITY identifiable;
	protected String formModelClassId;
	protected Class<?> formModelClass;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void initialisation() { 
		super.initialisation();
		businessEntityInfos = fetchBusinessEntityInfos();
		identifiableConfiguration = uiManager.findConfiguration((Class<? extends AbstractIdentifiable>) businessEntityInfos.getClazz());
		identifiable = identifiableFromRequestParameter((Class<ENTITY>)businessEntityInfos.getClazz());
		formModelClassId = __formModelClassId__();
		formModelClass = __formModelClass__();
		contentTitle = text(businessEntityInfos.getUiLabelId());
		
		logDebug("Web page created I={} , M={}", businessEntityInfos==null?"":businessEntityInfos.getClazz().getSimpleName(),
				formModelClass==null?"":formModelClass.getSimpleName());
	}
	
	protected BusinessEntityInfos fetchBusinessEntityInfos(){
		return uiManager.classFromKey(requestParameter(webManager.getRequestParameterClass()));
	}
	
	protected String __formModelClassId__(){
		return requestParameter(webManager.getRequestParameterFormModel());
	}
	
	protected Class<?> __formModelClass__(){
		if(StringUtils.isNotBlank(formModelClassId))
			formModelClass = UIManager.FORM_MODEL_MAP.get(formModelClassId);
		return formModelClass;
	}
	
	protected DetailsBlock<MenuModel> createDetailsBlock(AbstractOutputDetails<?> details, String editOutcome,UICommandable... links) {
		return super.createDetailsBlock(identifiable, details, editOutcome, links);
	}
	protected DetailsBlock<MenuModel> createDetailsBlock(AbstractOutputDetails<?> details,UICommandable... links) {
		return createDetailsBlock(details, null,links);
	}
	
}
