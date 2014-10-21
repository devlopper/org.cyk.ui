package org.cyk.ui.api.data.collector.form;

import org.cyk.system.root.model.AbstractIdentifiable;

public interface FormModelListener<ENTITY extends AbstractIdentifiable> {

	void read(AbstractFormModel<ENTITY> form);
	
	void write(AbstractFormModel<ENTITY> form);
	
}
