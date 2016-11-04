package org.cyk.ui.web.primefaces.page.mathematics;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.system.root.model.mathematics.MovementCollection;

@Named @ViewScoped @Getter @Setter
public class MovementListPage extends AbstractMovementListPage<Movement,MovementCollection> implements Serializable {
	private static final long serialVersionUID = 3274187086682750183L;

	
}
