package org.cyk.ui.web.primefaces.page.userinterface;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.business.impl.userinterface.UserInterfaceMenuNodeBusinessImpl;
import org.cyk.system.root.model.userinterface.UserInterfaceMenuNode;
import org.cyk.ui.api.command.menu.DefaultMenu;
import org.cyk.ui.api.command.menu.UIMenu;
import org.cyk.ui.web.primefaces.CommandBuilder;
import org.cyk.ui.web.primefaces.CommandableBuilder;
import org.cyk.ui.web.primefaces.page.nestedset.AbstractDataTreeNodeConsultPage;
import org.primefaces.model.menu.MenuModel;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class UserInterfaceMenuNodeConsultPage extends AbstractDataTreeNodeConsultPage<UserInterfaceMenuNode,UserInterfaceMenuNodeBusinessImpl.Details> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	@Getter @Setter private MenuModel sampleMenuModel;
	@Getter @Setter private UIMenu sampleMenu;
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		sampleMenu = new DefaultMenu();
		sampleMenu.setRenderType(UIMenu.RenderType.BAR);
		sampleMenu.addCommandable(inject(CommandableBuilder.class).get(identifiable));
		sampleMenuModel = CommandBuilder.getInstance().menuModel(sampleMenu, getClass(), "sampleMenuModel");
	}
	
}
