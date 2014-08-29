package org.cyk.ui.web.vaadin.application.crud;

import java.io.Serializable;

import org.cyk.ui.web.vaadin.AbstractApplication;

import com.vaadin.annotations.Title;
import com.vaadin.cdi.CDIUI;
import com.vaadin.server.VaadinRequest;

@CDIUI
@Title("Crud application")
public class CrudApplication extends AbstractApplication<StartView> implements Serializable {

	private static final long serialVersionUID = -7756474536072042178L;
	
	private static CrudApplication INSTANCE;
	
	@Override
	protected void initialize(VaadinRequest request) {
		INSTANCE = this;
		super.initialize(request);
		
	}
	
	@Override
	protected StartView startView() {
		return new StartView();
	}
	
	public static CrudApplication getInstance() {
		return INSTANCE;
	}

}
