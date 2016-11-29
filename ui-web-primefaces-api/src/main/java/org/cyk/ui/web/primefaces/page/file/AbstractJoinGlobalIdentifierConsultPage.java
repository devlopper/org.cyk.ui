package org.cyk.ui.web.primefaces.page.file;

import java.io.Serializable;

import org.cyk.system.root.model.globalidentification.AbstractJoinGlobalIdentifier;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.web.primefaces.page.crud.AbstractConsultPage;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractJoinGlobalIdentifierConsultPage<IDENTIFIABLE extends AbstractJoinGlobalIdentifier> extends AbstractConsultPage<IDENTIFIABLE> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	@Override
	protected void processIdentifiableContextualCommandable(UICommandable commandable) {
		super.processIdentifiableContextualCommandable(commandable);
		
	}
	
}
