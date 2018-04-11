package org.cyk.ui.web.primefaces.page.mathematics;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.impl.mathematics.movement.MovementDetails;
import org.cyk.system.root.model.mathematics.movement.Movement;
import org.cyk.system.root.model.mathematics.movement.MovementCollection;

@Named @ViewScoped @Getter @Setter
public class MovementCollectionConsultPage extends AbstractMovementCollectionConsultPage.Extends<MovementCollection,Movement,MovementDetails> implements Serializable {
	
	private static final long serialVersionUID = 3274187086682750183L;
	
}
