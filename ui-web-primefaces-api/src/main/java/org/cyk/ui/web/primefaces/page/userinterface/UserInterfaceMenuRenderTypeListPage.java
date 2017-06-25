package org.cyk.ui.web.primefaces.page.userinterface;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.model.userinterface.UserInterfaceMenuRenderType;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudManyPage;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class UserInterfaceMenuRenderTypeListPage extends AbstractCrudManyPage<UserInterfaceMenuRenderType> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
}
