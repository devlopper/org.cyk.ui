package org.cyk.ui.web.api;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Singleton;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.ui.api.UIManager;
import org.cyk.utility.common.builder.UrlStringBuilder;
import org.omnifaces.util.Faces;

@Singleton
public class UrlStringBuilderProvider extends org.cyk.system.root.business.api.UrlStringBuilderAdapter implements Serializable {
	private static final long serialVersionUID = 1L;

	public <IDENTIFIABLE extends AbstractIdentifiable> UrlStringBuilder getDynamicProcessManyPage(Class<IDENTIFIABLE> identifiableClass
			,Collection<IDENTIFIABLE> identifiables,String actionIdentifier){
		UrlStringBuilder builder = new UrlStringBuilder();
		builder.setHost(Faces.getRequest().getServerName()).setPort(Faces.getRequest().getServerPort())
			.setIdentifier(inject(WebNavigationManager.class).getOutcomeProcessMany());
		builder.getQueryStringBuilder().addParameter(UniformResourceLocatorParameter.IDENTIFIABLE, identifiables==null || identifiables.isEmpty() ? null 
				: WebManager.getInstance().encodeIdentifiablesAsRequestParameterValue(identifiables))
			.addParameter(UniformResourceLocatorParameter.CLASS,inject(UIManager.class).businessEntityInfos(identifiableClass).getIdentifier())
			.addParameter(UniformResourceLocatorParameter.ACTION_IDENTIFIER, actionIdentifier)
			.addParameter(UniformResourceLocatorParameter.ENCODED, UniformResourceLocatorParameter.IDENTIFIABLE)
		;
		return builder;
	}
	
}
