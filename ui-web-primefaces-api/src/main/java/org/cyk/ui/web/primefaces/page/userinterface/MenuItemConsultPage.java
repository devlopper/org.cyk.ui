package org.cyk.ui.web.primefaces.page.userinterface;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.business.impl.userinterface.MenuItemDetails;
import org.cyk.system.root.model.userinterface.MenuItem;
import org.cyk.ui.web.primefaces.page.nestedset.AbstractDataTreeNodeConsultPage;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class MenuItemConsultPage extends AbstractDataTreeNodeConsultPage<MenuItem,MenuItemDetails> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	
}
