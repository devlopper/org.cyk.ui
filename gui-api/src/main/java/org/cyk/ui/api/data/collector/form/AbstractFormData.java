package org.cyk.ui.api.data.collector.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.AbstractView;

public abstract class AbstractFormData<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM> extends AbstractView implements FormData<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM>, 
	ControlSetListener<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM>,Serializable {

	private static final long serialVersionUID = 7441937769450315724L;

	@Getter protected Collection<FormDataListener<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM>> formDataListeners = new ArrayList<>();
	@Getter @Setter protected DATA data;
	@Getter protected Collection<ControlSet<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM>> controlSets = new ArrayList<>();

	@Override
	public void applyValuesToFields() throws Exception {
		for(ControlSet<DATA, MODEL, ROW, LABEL, CONTROL, SELECTITEM> set : controlSets)
			set.applyValuesToFields();
	}
	
}
