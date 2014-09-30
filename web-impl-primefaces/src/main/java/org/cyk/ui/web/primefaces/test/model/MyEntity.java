package org.cyk.ui.web.primefaces.test.model;

import java.io.Serializable;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.annotation.UIField;

import lombok.Setter;
import lombok.Getter;

@Getter @Setter
public class MyEntity extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = 2551782857718212950L;
	
	@UIField
	private String textOneLine;

	private String textManyLine;
}
