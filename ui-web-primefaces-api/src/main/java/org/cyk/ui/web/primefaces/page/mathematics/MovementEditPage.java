package org.cyk.ui.web.primefaces.page.mathematics;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.mathematics.movement.Movement;
import org.cyk.system.root.model.mathematics.movement.MovementCollection;
import org.cyk.utility.common.annotation.FieldOverride;

@Named @ViewScoped @Getter @Setter
public class MovementEditPage extends AbstractMovementEditPage.Extends<Movement,MovementCollection> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	@Override
	protected Movement getMovement() {
		return identifiable;
	}
	
	@Override
	protected MovementCollection getCollection(Movement item) {
		return item.getCollection();
	}
	
	@Override
	protected MovementCollection getMovementCollection(MovementCollection collection) {
		return collection;
	}
	
	@Getter @Setter @FieldOverride(name=AbstractMovementForm.FIELD_COLLECTION,type=MovementCollection.class)
	public static class Form extends AbstractMovementForm.AbstractDefaultMovementForm<Movement,MovementCollection> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Override
		protected Movement getMovement() {
			return identifiable;
		}
		
	}
	

}
