package org.cyk.ui.web.primefaces.page.nestedset;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.model.pattern.tree.DataTreeType;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudManyPage;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class DataTreeTypeListPage extends AbstractCrudManyPage<DataTreeType> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
}
