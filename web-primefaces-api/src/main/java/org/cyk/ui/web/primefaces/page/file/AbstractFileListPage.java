package org.cyk.ui.web.primefaces.page.file;

import java.io.Serializable;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudManyPage;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractFileListPage<FILE extends AbstractIdentifiable> extends AbstractCrudManyPage<FILE> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	/**/
	
}
