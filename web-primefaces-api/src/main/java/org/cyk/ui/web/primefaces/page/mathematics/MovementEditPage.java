package org.cyk.ui.web.primefaces.page.mathematics;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.model.mathematics.Movement;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class MovementEditPage extends AbstractMovementEditPage<Movement> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	@Override
	protected Movement getMovement() {
		return identifiable;
	}
	
	@Getter @Setter
	public static class Form extends AbstractMovementForm<Movement> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Override
		public Movement getMovement() {
			return identifiable;
		}
	}

}
