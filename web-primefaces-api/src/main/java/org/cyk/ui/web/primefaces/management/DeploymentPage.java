package org.cyk.ui.web.primefaces.management;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;

import org.cyk.system.root.business.api.BusinessLayer;
import org.cyk.system.root.business.api.BusinessManager;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.impl.AbstractBusinessLayer;
import org.cyk.ui.api.command.CommandAdapter;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.web.primefaces.AbstractPrimefacesPage;
import org.cyk.ui.web.primefaces.data.collector.form.FormOneData;

@Named @RequestScoped
public class DeploymentPage extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = -3563847253553434464L;
	
	@Inject protected BusinessManager businessManager;
	@Getter private FormOneData<Object> editor;
	
	@Getter private List<AbstractBusinessLayer> businessLayers = new ArrayList<>();
	
	@Override
	protected void initialisation() {
		super.initialisation();
		title=text("deployment");
		for(BusinessLayer businessLayer : businessManager.findBusinessLayers())
			businessLayers.add((AbstractBusinessLayer) businessLayer); 
		editor = (FormOneData<Object>) createFormOneData(new Object(),Crud.READ);
		editor.getSubmitCommandable().setLabel(text("execute"));
		editor.getSubmitCommandable().getCommand().getCommandListeners().add(new CommandAdapter(){
			private static final long serialVersionUID = 1L;
			@Override
			public void serve(UICommand command, Object parameter) {
				businessManager.createData();
			}
		});
		
	}

}
