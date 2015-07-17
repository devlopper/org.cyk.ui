package org.cyk.ui.web.primefaces.page.crud;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;

@Named @ViewScoped @Getter @Setter
public class CrudManyPage extends AbstractCrudManyPage<AbstractIdentifiable> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

}
