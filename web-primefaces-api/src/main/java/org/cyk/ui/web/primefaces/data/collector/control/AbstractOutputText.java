package org.cyk.ui.web.primefaces.data.collector.control;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.ui.web.api.data.collector.control.WebOutputText;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;

@Getter @Setter @NoArgsConstructor
public abstract class AbstractOutputText extends AbstractOutput implements WebOutputText<DynaFormModel,DynaFormRow,DynaFormLabel,DynaFormControl>,Serializable {

	private static final long serialVersionUID = 5671513590779656492L;

	protected String value;
	protected Boolean escape=Boolean.TRUE;
	
	public AbstractOutputText(String value, Boolean escape) {
		super();
		this.value = value;
		this.escape = escape;
	} 
	
	
}
