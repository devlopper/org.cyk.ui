package org.cyk.ui.web.primefaces.page.geography;

import java.io.Serializable;

import org.cyk.system.root.model.geography.Contact;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudManyPage;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractContactListPage<IDENTIFIABLE extends Contact> extends AbstractCrudManyPage<IDENTIFIABLE> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	/**/
	
	
}
