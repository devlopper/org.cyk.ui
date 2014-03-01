package org.cyk.ui.web.primefaces.controller;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.ui.api.component.IComponent;
import org.cyk.ui.web.api.AbstractWebPage;

public class AbstractController extends AbstractWebPage implements Serializable {

	private static final long serialVersionUID = -1367372077209082614L;
	
	@Inject protected MessageManager messageManager;
	//@Inject protected ViewBuilder viewBuilder;
	
	@Override
	public void createRow() {}
	
	@Override
	public void add(IComponent<?> component) {}

	
}
