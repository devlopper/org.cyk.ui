package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import javax.inject.Inject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.web.api.AbstractWebManager;

public abstract class AbstractPrimefacesManager extends AbstractWebManager implements Serializable {

	private static final long serialVersionUID = 5307129721480611811L;

	@Inject protected DefaultDesktopLayoutManager layoutManager;
	
	protected FileInfos logoFileInfos,loginBackgroundFileInfos,homeBackgroundFileInfos; 
	
	@Override
	protected void initialisation() {
		super.initialisation();
		logoFileInfos = new FileInfos("default", "png");
		loginBackgroundFileInfos = new FileInfos("default", "jpg");
		homeBackgroundFileInfos = new FileInfos("default", "jpg");
	}
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		if("root".equals(identifier))
			return;
		layoutManager.setLogoPath("/"+getLibraryName()+"/images/logo/"+logoFileInfos.getPath());
		layoutManager.setLoginBackgroundPath("/"+getLibraryName()+"/images/background/"+loginBackgroundFileInfos.getPath());
		if(homeBackgroundFileInfos!=null)
			layoutManager.setHomeBackgroundPath("/"+getLibraryName()+"/images/background/home/"+homeBackgroundFileInfos.getPath());
	}
	
	public String getLibraryName(){
		return "org.cyk.ui.web.primefaces."+identifier;
	}
	
	/**/
	
	@Getter @Setter @AllArgsConstructor
	protected class FileInfos implements Serializable {
		private static final long serialVersionUID = 6831225928321703226L;
		private String name,extension;
		
		public String getPath(){
			return name+"."+extension;
		}
	}
}