package org.cyk.ui.web.primefaces.page.information;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.information.Comment;
import org.cyk.ui.web.primefaces.page.crud.AbstractConsultPage;

@Named @ViewScoped @Getter @Setter
public class CommentConsultPage extends AbstractConsultPage<Comment> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

}
