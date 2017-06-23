package org.cyk.ui.web.primefaces.page.userinterface;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.impl.userinterface.UserInterfaceMenuItemDetails;
import org.cyk.system.root.model.userinterface.UserInterfaceMenu;
import org.cyk.system.root.model.userinterface.UserInterfaceMenuItem;
import org.cyk.ui.api.IdentifierProvider;
import org.cyk.ui.web.primefaces.page.AbstractCollectionConsultPage;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class UserInterfaceMenuConsultPage extends AbstractCollectionConsultPage.Extends<UserInterfaceMenu,UserInterfaceMenuItem,UserInterfaceMenuItemDetails> implements Serializable {
	
	private static final long serialVersionUID = 3274187086682750183L;
	
	@Override
	protected void consultInitialisation() {
		super.consultInitialisation();
		itemTable.setTitle(inject(LanguageBusiness.class).findClassLabelText(UserInterfaceMenuItem.class));
		itemTable.getAddRowCommandable().addParameter(identifiable);
		itemTable.setShowToolBar(Boolean.FALSE);
		itemTable.setShowActionsColumn(Boolean.FALSE);
	}
	
	@Override
	protected Boolean getEnableItemTableInDefaultTab() {
		return Boolean.TRUE;
	}
	
	@Override
	protected String getItemTableTabId() {
		return IdentifierProvider.Adapter.getTabOf(UserInterfaceMenu.class);
	}
	
	@Override
	protected Crud[] getItemTableCruds() {
		return new Crud[]{};
	}
}
