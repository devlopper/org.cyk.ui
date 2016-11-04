package org.cyk.ui.web.primefaces.page.mathematics;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.system.root.model.mathematics.MovementCollection;

@Named @ViewScoped @Getter @Setter
public class MovementCollectionEditPage extends AbstractMovementCollectionEditPage.Extends<MovementCollection,Movement> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	@Override
	protected MovementCollection getMovementCollection() {
		return identifiable;
	}
	
	@Getter @Setter
	public static class Form extends AbstractMovementCollectionEditPage.Form.Extends<MovementCollection,Movement> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;

		@Override
		protected MovementCollection getMovementCollection() {
			return identifiable;
		}
				
	}

}
