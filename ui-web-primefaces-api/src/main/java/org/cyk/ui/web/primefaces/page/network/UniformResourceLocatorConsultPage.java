package org.cyk.ui.web.primefaces.page.network;

import java.io.Serializable;
import java.util.Collection;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.network.UniformResourceLocatorParameterBusiness;
import org.cyk.system.root.business.impl.network.UniformResourceLocatorParameterDetails;
import org.cyk.system.root.model.network.UniformResourceLocator;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.ui.api.IdentifierProvider;
import org.cyk.ui.web.primefaces.page.AbstractCollectionConsultPage;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class UniformResourceLocatorConsultPage extends AbstractCollectionConsultPage<UniformResourceLocator,UniformResourceLocatorParameter,UniformResourceLocatorParameterDetails> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	@Override
	protected void consultInitialisation() {
		super.consultInitialisation();
		itemTable.setTitle(inject(LanguageBusiness.class).findClassLabelText(UniformResourceLocatorParameter.class));
		itemTable.getAddRowCommandable().addParameter(identifiable);
		itemTable.setShowToolBar(Boolean.FALSE);
		itemTable.setShowActionsColumn(Boolean.FALSE);
	}
	
	@Override
	protected Collection<UniformResourceLocatorParameter> findByCollection(UniformResourceLocator uniformResourceLocator) {
		return inject(UniformResourceLocatorParameterBusiness.class).findByUniformResourceLocator(uniformResourceLocator);
	}
	
	@Override
	protected Boolean getEnableItemTableInDefaultTab() {
		return Boolean.TRUE;
	}
	
	@Override
	protected String getItemTableTabId() {
		return IdentifierProvider.Adapter.getTabOf(UniformResourceLocator.class);
	}
	
	@Override
	protected Crud[] getItemTableCruds() {
		return new Crud[]{};
	}
}
