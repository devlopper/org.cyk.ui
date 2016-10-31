package org.cyk.ui.web.primefaces.data.collector.control;

import java.io.Serializable;

import javax.faces.model.SelectItem;

import org.cyk.ui.api.data.collector.control.Output;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor 
public abstract class AbstractOutput extends AbstractControl implements Output<DynaFormModel,DynaFormRow,DynaFormLabel,DynaFormControl,SelectItem>,Serializable {

	private static final long serialVersionUID = 5671513590779656492L;

	private String label;
}
