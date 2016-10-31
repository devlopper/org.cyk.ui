package org.cyk.ui.web.primefaces.page.mathematics;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.mathematics.MovementBusiness;
import org.cyk.system.root.business.api.mathematics.MovementCollectionBusiness;
import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.system.root.model.mathematics.MovementCollection;

@Named @ViewScoped @Getter @Setter
public class MovementEditPage extends AbstractMovementEditPage<Movement> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	@Override
	protected Movement getMovement() {
		return identifiable;
	}
	
	@Override
	protected Movement instanciateIdentifiable() {
		Long collectionIdentifier = requestParameterLong(MovementCollection.class);
		if(collectionIdentifier==null)
			return super.instanciateIdentifiable();
		return inject(MovementBusiness.class).instanciateOne(inject(MovementCollectionBusiness.class).find(collectionIdentifier), Boolean.TRUE);
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
