package org.cyk.ui.web.primefaces.data.collector.control;

import java.io.Serializable;

import javax.faces.model.SelectItem;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.ui.web.api.data.collector.control.WebOutputSeparator;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;

@Getter @Setter @NoArgsConstructor
public class OutputSeparator extends AbstractOutputText implements org.cyk.ui.api.data.collector.control.OutputSeparator<DynaFormModel,DynaFormRow,DynaFormLabel,DynaFormControl,SelectItem>,
 WebOutputSeparator<DynaFormModel,DynaFormRow,DynaFormLabel,DynaFormControl>,Serializable {

	private static final long serialVersionUID = 5671513590779656492L;
	
}
