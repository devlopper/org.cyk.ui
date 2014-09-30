package org.cyk.ui.web.primefaces.data.collector.control;

import java.io.Serializable;

import javax.faces.model.SelectItem;

import lombok.Getter;
import lombok.Setter;

import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;

@Getter @Setter
public class OutputLabel extends AbstractOutputText implements org.cyk.ui.api.data.collector.control.OutputLabel<DynaFormModel,DynaFormRow,DynaFormLabel,DynaFormControl,SelectItem>, Serializable {

	private static final long serialVersionUID = 1390099136018097004L;

	public OutputLabel() {
		super();
	}

	public OutputLabel(String value,Boolean escape) {
		super(value,Boolean.TRUE.equals(escape));
	}
	
	public OutputLabel(String value) {
		super(value,Boolean.FALSE);
	}
	
}
