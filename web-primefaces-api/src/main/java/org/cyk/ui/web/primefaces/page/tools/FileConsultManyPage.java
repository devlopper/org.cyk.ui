package org.cyk.ui.web.primefaces.page.tools;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map.Entry;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import lombok.Getter;

import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.ui.web.primefaces.Exporter;
import org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage;
import org.omnifaces.util.Faces;

@Named @ViewScoped @Getter
public class FileConsultManyPage extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
 
	private Exporter exporter = new Exporter();
	
	@Override
	protected void initialisation() {
		super.initialisation();
		Collection<Object> parametersCollection = new ArrayList<Object>();
		HttpServletRequest request = Faces.getRequest();
		for(Entry<String, String[]> entry : request.getParameterMap().entrySet()){
			parametersCollection.add(entry.getKey());
			if(entry.getValue()!=null && entry.getValue().length>0)
				parametersCollection.add(entry.getValue()[0]);
		}
		Object[] parametersArray = parametersCollection.toArray();
		
		exporter.setFileUrl(navigationManager.url("fileservlet", parametersArray,Boolean.FALSE,Boolean.FALSE));
		exporter.setType(RootBusinessLayer.getInstance().getFileBusiness().findMime(requestParameter(uiManager.getFileExtensionParameter())));
	}
	
	@Override
	public Boolean getShowContentMenu() {
		return Boolean.FALSE;
	}
}
