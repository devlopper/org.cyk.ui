package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.web.api.WebManager;
import org.cyk.ui.web.api.WebNavigationManager;
import org.cyk.utility.common.cdi.AbstractBean;

@Getter @Setter
public class Exporter extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 3803745350859325894L;

	private String type,fileUrl,height="700px",width="100%",linkMessage,clickToDownload;
	private WebNavigationManager navigationManager = inject(WebNavigationManager.class);
	private WebManager webManager = inject(WebManager.class);
	
	public Exporter() {
		linkMessage = UIManager.getInstance().text("exporter.link");
		clickToDownload = UIManager.getInstance().text("exporter.clickToDownload");
	}
	
	public void setFileUrlFromRequestParameters(Object[] requestParameters){
		fileUrl = navigationManager.url(navigationManager.getOutcomeFileServlet(),requestParameters,Boolean.FALSE,Boolean.FALSE);
	}
	
	public void setFileUrlFromRequestParameters(File file){
		setFileUrlFromRequestParameters(new Object[]{UniformResourceLocatorParameter.IDENTIFIABLE,file
				,UniformResourceLocatorParameter.FILE_EXTENSION,file.getExtension()});
		setType(file.getMime());
	}
}
