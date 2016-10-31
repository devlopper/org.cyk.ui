package org.cyk.ui.web.primefaces.page.mathematics;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.impl.mathematics.MovementDetails;
import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.ui.web.primefaces.page.AbstractCollectionConsultPage;

@Named @ViewScoped @Getter @Setter
public class MovementCollectionConsultPage extends AbstractCollectionConsultPage<MovementCollection,MovementCollection,Movement,MovementDetails> implements Serializable {
	
	private static final long serialVersionUID = 3274187086682750183L;
	
	@Override
	protected MovementCollection getCollection() {
		return identifiable;
	}
	
}
