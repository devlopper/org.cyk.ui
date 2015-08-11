package org.cyk.ui.web.primefaces.data.collector.control;

import java.io.Serializable;

import javax.faces.model.SelectItem;

import org.cyk.ui.web.api.data.collector.control.WebOutputText;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OutputText extends AbstractOutputText implements org.cyk.ui.api.data.collector.control.OutputText<DynaFormModel,DynaFormRow,DynaFormLabel,DynaFormControl,SelectItem>,
WebOutputText<DynaFormModel,DynaFormRow,DynaFormLabel,DynaFormControl>,Serializable {

	private static final long serialVersionUID = 1390099136018097004L;
	
}
