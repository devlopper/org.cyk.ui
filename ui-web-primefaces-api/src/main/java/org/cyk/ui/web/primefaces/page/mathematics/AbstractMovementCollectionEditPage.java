package org.cyk.ui.web.primefaces.page.mathematics;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.ui.web.primefaces.page.AbstractCollectionEditPage;

@Getter @Setter
public abstract class AbstractMovementCollectionEditPage<COLLECTION extends AbstractIdentifiable> extends AbstractCollectionEditPage<COLLECTION> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	protected abstract MovementCollection getMovementCollection();
	
	@Override
	protected AbstractCollection<?> getCollection() {
		return getMovementCollection();
	}
	
	@Getter @Setter
	public static abstract class AbstractMovementCollectionForm<COLLECTION extends AbstractIdentifiable> extends AbstractForm<COLLECTION> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		protected abstract MovementCollection getMovementCollection();
		
		@Override
		protected AbstractCollection<?> getCollection() {
			return getMovementCollection();
		}
		
		/**/
		
		@Getter @Setter
		public static abstract class Default<COLLECTION extends AbstractCollection<?>> extends AbstractMovementCollectionForm<COLLECTION> implements Serializable{
			private static final long serialVersionUID = -4741435164709063863L;
			
			
			
		}
		
	}
	
	@Getter @Setter
	public static abstract class AbstractDefaultForm<COLLECTION extends AbstractCollection<?>> extends AbstractForm.AbstractDefault<COLLECTION> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;

		
	}

}
