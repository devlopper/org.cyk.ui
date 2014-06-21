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
import org.cyk.ui.web.primefaces.AbstractPrimefacesPage;
import org.cyk.ui.web.primefaces.PrimefacesEditor;
import org.cyk.utility.common.AbstractMethod;

@Named @RequestScoped
public class DeploymentController extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = -3563847253553434464L;
	
	@Inject protected BusinessManager businessManager;
	@Getter private PrimefacesEditor editor;
	
	@Getter private List<AbstractBusinessLayer> businessLayers = new ArrayList<>();;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		title=text("deployment");
		for(BusinessLayer businessLayer : businessManager.findBusinessLayers())
			businessLayers.add((AbstractBusinessLayer) businessLayer); 
		editor = (PrimefacesEditor) editorInstance(new Object(),Crud.READ);
		editor.getSubmitCommand().setLabel(text("execute"));
		editor.setSubmitMethodMain(new AbstractMethod<Object, Object>() {
			private static final long serialVersionUID = -2421175279479434675L;
			@Override
			protected Object __execute__(Object parameter) {
				businessManager.createData();
				return null;
			}
		});
	}

}
