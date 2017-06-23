package org.cyk.ui.web.primefaces.page.userinterface;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.model.userinterface.UserInterfaceMenu;
import org.cyk.system.root.model.userinterface.UserInterfaceMenuItem;
import org.cyk.ui.web.primefaces.page.AbstractCollectionListPage;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class UserInterfaceMenuListPage extends AbstractCollectionListPage.Extends<UserInterfaceMenu,UserInterfaceMenuItem> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	/**/
	
}
