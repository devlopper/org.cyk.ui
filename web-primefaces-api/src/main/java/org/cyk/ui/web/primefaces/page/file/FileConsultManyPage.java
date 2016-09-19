package org.cyk.ui.web.primefaces.page.file;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;

import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.ui.web.primefaces.Exporter;
import org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage;

@Named @ViewScoped @Getter
public class FileConsultManyPage extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
 
	private Exporter exporter = new Exporter();
	
	@Override
	protected void initialisation() {
		super.initialisation();
		exporter.setFileUrl(navigationManager.url(navigationManager.getOutcomeFileServlet(), webManager.getRequestParameterMapAsArray(),Boolean.FALSE,Boolean.FALSE));
		exporter.setType(inject(FileBusiness.class).findMime(requestParameter(UniformResourceLocatorParameter.FILE_EXTENSION)));
	}
	
	@Override
	public Boolean getShowContentMenu() {
		return Boolean.FALSE;
	}
}
