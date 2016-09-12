package org.cyk.ui.web.api.data.collector.form;

import java.io.Serializable;

import javax.faces.model.SelectItem;

import org.cyk.ui.api.data.collector.form.AbstractFormOneData;

public abstract class AbstractWebFormOneData<DATA, MODEL, ROW, LABEL, CONTROL> extends AbstractFormOneData<DATA, MODEL, ROW, LABEL, CONTROL, SelectItem>
	implements WebFormOneData<DATA, MODEL, ROW, LABEL, CONTROL>,Serializable {

	private static final long serialVersionUID = 3363282642650783137L;

	public AbstractWebFormOneData(String submitCommandableLabelId) {
		super(submitCommandableLabelId);
	}
	
}
