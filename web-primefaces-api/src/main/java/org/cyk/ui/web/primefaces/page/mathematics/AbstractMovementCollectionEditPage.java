package org.cyk.ui.web.primefaces.page.mathematics;

import java.io.Serializable;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractMovementCollectionEditPage<MOVEMENT_COLLECTION extends AbstractIdentifiable> extends AbstractCrudOnePage<MOVEMENT_COLLECTION> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	protected abstract MovementCollection getMovementCollection();
	
	@Getter @Setter
	public static abstract class AbstractMovementCollectionForm<MOVEMENT_COLLECTION extends AbstractIdentifiable> extends AbstractFormModel<MOVEMENT_COLLECTION> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Input @InputText protected String code;
		@Input @InputText protected String name;
		
		protected abstract MovementCollection getMovementCollection();
		
		
	}

}
