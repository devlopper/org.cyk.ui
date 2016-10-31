package org.cyk.ui.web.primefaces.page.information;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.information.Comment;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudManyPage;

@Named @ViewScoped @Getter @Setter
public class CommentListPage extends AbstractCrudManyPage<Comment> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	@Override
	protected void initialisation() {
		super.initialisation();
		contextualMenu = userSession.getSecurityMenu();
		rowAdapter.setOpenable(Boolean.TRUE);
		rowAdapter.setUpdatable(Boolean.TRUE);
		rowAdapter.setDeletable(Boolean.TRUE);
		table.setShowHeader(Boolean.TRUE);
		table.setShowToolBar(Boolean.TRUE);
	}
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		//table.getAddRowCommandable().setRendered(Boolean.TRUE);
		table.setShowOpenCommand(Boolean.TRUE);
	}
	
}
