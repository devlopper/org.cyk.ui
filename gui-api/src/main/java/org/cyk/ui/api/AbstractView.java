package org.cyk.ui.api;

import java.io.Serializable;

import lombok.Getter;

import org.cyk.ui.api.layout.AbstractLayout;

public abstract class AbstractView extends AbstractLayout implements IView , Serializable {

	private static final long serialVersionUID = -3666969590470758214L;

	@Getter protected String title;
		
}
