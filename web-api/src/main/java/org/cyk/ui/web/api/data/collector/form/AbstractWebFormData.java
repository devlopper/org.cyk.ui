package org.cyk.ui.web.api.data.collector.form;

import java.io.Serializable;

import javax.faces.model.SelectItem;

import org.cyk.ui.api.data.collector.form.AbstractFormData;

public abstract class AbstractWebFormData<DATA, MODEL, ROW, LABEL, CONTROL> extends AbstractFormData<DATA, MODEL, ROW, LABEL, CONTROL, SelectItem>
	implements WebFormData<DATA, MODEL, ROW, LABEL, CONTROL>,Serializable {

	private static final long serialVersionUID = 3363282642650783137L;

}
