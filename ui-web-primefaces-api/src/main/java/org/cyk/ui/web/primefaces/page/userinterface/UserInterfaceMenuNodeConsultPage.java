package org.cyk.ui.web.primefaces.page.userinterface;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.business.impl.userinterface.UserInterfaceMenuNodeDetails;
import org.cyk.system.root.model.userinterface.UserInterfaceMenuNode;
import org.cyk.ui.web.primefaces.page.nestedset.AbstractDataTreeNodeConsultPage;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class UserInterfaceMenuNodeConsultPage extends AbstractDataTreeNodeConsultPage<UserInterfaceMenuNode,UserInterfaceMenuNodeDetails> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	
}
