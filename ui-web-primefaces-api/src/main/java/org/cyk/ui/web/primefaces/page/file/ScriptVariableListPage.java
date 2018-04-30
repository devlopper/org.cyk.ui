package org.cyk.ui.web.primefaces.page.file;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.model.language.programming.ScriptVariable;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudManyPage;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class ScriptVariableListPage extends AbstractCrudManyPage<ScriptVariable> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
}
