package org.cyk.ui.api.model.mathematics;

import java.io.Serializable;

import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.ui.api.model.AbstractEnumerationDetails;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

public class MovementCollectionDetails extends AbstractEnumerationDetails<MovementCollection> implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputText private String value,increment,decrement;
	
	public MovementCollectionDetails(MovementCollection movementCollection) {
		super(movementCollection);
		value = formatNumber(movementCollection.getValue());
		increment = formatUsingBusiness(movementCollection.getIncrementAction());
		decrement = formatUsingBusiness(movementCollection.getDecrementAction());
	}
}