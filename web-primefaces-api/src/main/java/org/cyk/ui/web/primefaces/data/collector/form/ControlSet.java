package org.cyk.ui.web.primefaces.data.collector.form;

import java.io.Serializable;

import org.cyk.ui.web.api.data.collector.form.AbstractWebControlSet;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;

public class ControlSet<DATA> extends AbstractWebControlSet<DATA,DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl> implements Serializable {

	private static final long serialVersionUID = -3180633522615710480L;

	{
		templateId = "/org.cyk.ui.web.primefaces/template/controlset/default.xhtml";
	}
	
}
