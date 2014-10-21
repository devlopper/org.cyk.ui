package org.cyk.ui.web.primefaces.test.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.annotation.user.interfaces.InputTextarea;

@Getter @Setter
public class MyIdentifiable extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = 2551782857718212950L;
	
	@Input @InputText
	private String textOneLine;

	@Input @InputTextarea
	private String textManyLine;
}
