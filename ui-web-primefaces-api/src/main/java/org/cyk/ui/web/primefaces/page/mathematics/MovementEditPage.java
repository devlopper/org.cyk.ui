package org.cyk.ui.web.primefaces.page.mathematics;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.business.impl.BusinessInterfaceLocator;
import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.utility.common.annotation.FieldOverride;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class MovementEditPage extends AbstractMovementEditPage.AbstractDefault<Movement,MovementCollection> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	@Override
	protected Movement getMovement() {
		return identifiable;
	}
	
	/*@Override
	protected Movement instanciateIdentifiable() {
		Long collectionIdentifier = requestParameterLong(MovementCollection.class);
		if(collectionIdentifier==null)
			return super.instanciateIdentifiable();
		return inject(MovementBusiness.class).instanciateOne(inject(MovementCollectionBusiness.class).find(collectionIdentifier), Boolean.TRUE);
	}*/
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected Movement instanciateIdentifiable() {
		Movement identifiable = super.instanciateIdentifiable();
		Long collectionIdentifier = requestParameterLong(MovementCollection.class);
		if(collectionIdentifier==null){
			
		}else{
			MovementCollection collection = inject(BusinessInterfaceLocator.class).injectTyped(MovementCollection.class).find(collectionIdentifier);
			if(identifiable instanceof AbstractCollectionItem)
				((AbstractCollectionItem)identifiable).setCollection(collection);
		}
		return identifiable;
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
