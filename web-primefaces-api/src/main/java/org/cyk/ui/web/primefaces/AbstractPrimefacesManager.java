package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import javax.inject.Inject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.ui.api.model.AbstractTree;
import org.cyk.ui.web.api.AbstractWebManager;
import org.cyk.ui.web.primefaces.page.DetailsConfiguration;
import org.primefaces.model.TreeNode;

public abstract class AbstractPrimefacesManager extends AbstractWebManager<TreeNode,HierarchyNode,UserSession> implements Serializable {

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
		
		languageBusiness.registerResourceBundle("org.cyk.system."+identifier+".ui.web.primefaces.api.resources.i18n",getClassLoader());
	}
	
	public String getLibraryName(){
		return "org.cyk.ui.web.primefaces."+identifier;
	}
	
	@Override
	protected AbstractTree<TreeNode, HierarchyNode> createNavigatorTree(UserSession userSession) {
		return new Tree();
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
	
	/**/
	
	public static interface AbstractPrimefacesManagerListener extends AbstractWebManagerListener<TreeNode,HierarchyNode,UserSession> {
		
		/**/
		
		public static class Adapter extends AbstractWebManagerListener.Adapter<TreeNode,HierarchyNode,UserSession> implements AbstractPrimefacesManagerListener{
			private static final long serialVersionUID = 3034803382486669232L;

			@SuppressWarnings("unchecked")
			protected void registerDetailsConfiguration(Class<?> detailsClass,DetailsConfiguration detailsConfiguration){
				PrimefacesManager.registerDetailsConfiguration((Class<? extends AbstractOutputDetails<?>>) detailsClass, detailsConfiguration);
			}
			
		}
	}
}