package org.cyk.ui.web.primefaces.resources.input;

import java.util.Collection;

import org.primefaces.model.DualListModel;

public class InputChoiceManyPickList extends org.cyk.utility.common.userinterface.input.choice.InputChoiceManyPickList {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Object> getWritableValue() {
		return (Collection<Object>) ((DualListModel<?>)getValueObject()).getTarget();
	}
	
}
