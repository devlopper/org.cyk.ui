package org.cyk.ui.web.primefaces.data.collector.control;

import java.io.Serializable;

import javax.faces.model.SelectItem;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.ui.web.api.data.collector.control.WebInputBooleanButton;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;

/**
 * 
 * @author Komenan.Christian
 *
 */
@Getter @Setter @NoArgsConstructor
public class InputBooleanButton extends AbstractInput<Boolean> implements WebInputBooleanButton<DynaFormModel,DynaFormRow,DynaFormLabel,DynaFormControl>,
org.cyk.ui.api.data.collector.control.InputBooleanButton<DynaFormModel,DynaFormRow,DynaFormLabel,DynaFormControl,SelectItem>,Serializable {

	private static final long serialVersionUID = 1390099136018097004L;

	private State onState=new State(text("oui"),"ui-icon-check"),offState=new State(text("non"),null);
	
}
