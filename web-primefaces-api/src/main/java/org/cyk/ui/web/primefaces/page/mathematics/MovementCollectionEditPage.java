package org.cyk.ui.web.primefaces.page.mathematics;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.model.mathematics.MovementCollection;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class MovementCollectionEditPage extends AbstractMovementCollectionEditPage<MovementCollection> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	@Override
	protected MovementCollection getMovementCollection() {
		return identifiable;
	}
	
	@Getter @Setter
	public static class Form extends AbstractMovementCollectionForm<MovementCollection> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Override
		protected MovementCollection getMovementCollection() {
			return identifiable;
		}
		
	}

}
