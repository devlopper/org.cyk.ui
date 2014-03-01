package org.cyk.ui.api.form;

import java.io.Serializable;
import java.util.Collection;

import lombok.Getter;

import org.cyk.ui.api.AbstractView;

public abstract class AbstractForm/*<DTO>*/ extends AbstractView implements IForm/*<DTO>*/ , Serializable {

	private static final long serialVersionUID = -3666969590470758214L;

	@Getter protected Collection<IFormField> fields;
	
	@Getter protected Collection<IFormCommand> commands;
	
	
}
