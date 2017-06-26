package org.cyk.ui.web.primefaces.page.security;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.model.security.RoleUserInterfaceMenu;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudManyPage;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class RoleUserInterfaceMenuListPage extends AbstractCrudManyPage<RoleUserInterfaceMenu> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
}
