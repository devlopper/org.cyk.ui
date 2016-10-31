package org.cyk.ui.api.data.collector.form;

import org.cyk.system.root.model.AbstractIdentifiable;

public interface ReadFormModelListener<ENTITY extends AbstractIdentifiable> {

	void read(AbstractReadFormModel<ENTITY> form);
	
}
