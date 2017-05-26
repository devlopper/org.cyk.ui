package org.cyk.ui.web.primefaces.page.security;

import java.io.Serializable;
import java.util.Collection;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.security.RoleUniformResourceLocatorBusiness;
import org.cyk.system.root.business.impl.security.RoleUniformResourceLocatorDetails;
import org.cyk.system.root.model.security.Role;
import org.cyk.system.root.model.security.RoleUniformResourceLocator;
import org.cyk.ui.api.IdentifierProvider;
import org.cyk.ui.web.primefaces.page.AbstractCollectionConsultPage;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class RoleConsultPage extends AbstractCollectionConsultPage<Role,RoleUniformResourceLocator,RoleUniformResourceLocatorDetails> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	@Override
	protected void consultInitialisation() {
		super.consultInitialisation();
		//itemTable.setTitle(inject(LanguageBusiness.class).findClassLabelText(RoleUniformResourceLocator.class));
		itemTable.getAddRowCommandable().addParameter(identifiable);
		itemTable.setShowToolBar(Boolean.FALSE);
		itemTable.setShowActionsColumn(Boolean.FALSE);
	}
	
	@Override
	protected Collection<RoleUniformResourceLocator> findByCollection(Role collection) {
		return inject(RoleUniformResourceLocatorBusiness.class).findByRole(collection);
	}
	
	@Override
	protected Boolean getEnableItemTableInDefaultTab() {
		return Boolean.TRUE;
	}
	
	@Override
	protected String getItemTableTabId() {
		return IdentifierProvider.Adapter.getTabOf(Role.class);
	}
	
	@Override
	protected Crud[] getItemTableCruds() {
		return new Crud[]{};
	}
	
}
