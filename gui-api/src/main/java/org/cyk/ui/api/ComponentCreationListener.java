package org.cyk.ui.api;

import org.cyk.ui.api.data.collector.form.FormOneData;

public interface ComponentCreationListener {

	void formOneDataCreated(FormOneData<?, ?, ?, ?, ?, ?> formOneData);
	
}
