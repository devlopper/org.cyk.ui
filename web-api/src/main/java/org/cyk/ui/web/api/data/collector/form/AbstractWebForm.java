package org.cyk.ui.web.api.data.collector.form;

import java.io.Serializable;

import javax.faces.model.SelectItem;

import org.cyk.ui.api.data.collector.form.AbstractForm;

public abstract class AbstractWebForm<DATA, MODEL, ROW, LABEL, CONTROL> extends AbstractForm<DATA, MODEL, ROW, LABEL, CONTROL, SelectItem>
	implements WebForm<DATA, MODEL, ROW, LABEL, CONTROL>,Serializable {

	private static final long serialVersionUID = 3363282642650783137L;

}
