package org.cyk.ui.web.primefaces.page.geography;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.geography.Contact;
import org.cyk.ui.web.primefaces.page.crud.AbstractConsultPage;

@Getter @Setter
public abstract class AbstractContactConsultPage<IDENTIFIABLE extends Contact> extends AbstractConsultPage<IDENTIFIABLE> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	
}
