package org.cyk.ui.web.primefaces.resources.page.layout;

import java.io.Serializable;

import org.cyk.utility.common.Constant;
import org.cyk.utility.common.helper.FileHelper;
import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.container.Container;
import org.primefaces.extensions.model.layout.LayoutOptions;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class LayoutModel extends Container implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String INCLUDE_FORMAT = "%s%s.xhtml";
	private static final String ASSEMBLY_FILE_NAME = "__assembly__";
	private static final String STYLE_CLASS_CONTENT_PREFIX = "page_layout_";
	
	protected Component component;
	
	/**/
	
	public LayoutModel(Component component,String directory) {
		getPropertiesMap().setFullPage(Boolean.TRUE);
		getPropertiesMap().setOptions(createOptions(component));
		getPropertiesMap().setInclude(String.format(INCLUDE_FORMAT, directory,ASSEMBLY_FILE_NAME));
	}
	
	protected static String getDirectory(Container container){
		return FileHelper.getInstance().getPath((String)container.getPropertiesMap().getInclude());
	}
	
	protected static Container createContainer(Container parent,String position,Boolean assembly){
		//System.out.println("LayoutModel.createContainer() P : "+parent.getPropertiesMap().getStyleClassContent()+" :: "+position+" : "+assembly);
		Container container = new Container();
		container.getPropertiesMap().setInclude(String.format(INCLUDE_FORMAT
				, getDirectory(parent)+(Boolean.TRUE.equals(assembly) ? position+Constant.CHARACTER_SLASH : Constant.EMPTY_STRING)
				,Boolean.TRUE.equals(assembly) ? ASSEMBLY_FILE_NAME : position)).setPosition(position);
		container.getPropertiesMap().setStyleClassContent(parent == null || parent.getPropertiesMap().getStyleClassContent() == null ? STYLE_CLASS_CONTENT_PREFIX+position 
				: parent.getPropertiesMap().getStyleClassContent()+Constant.CHARACTER_UNDESCORE.toString()+position
				);
		//if(Boolean.TRUE.equals(assembly))
		//	container.getPropertiesMap().setStyleClassContent(container.getPropertiesMap().getStyleClassContent()+Constant.CHARACTER_UNDESCORE.toString()+"assembly");
		//System.out.println("LayoutModel.createContainer() : "+container.getPropertiesMap().getStyleClassContent());
		return container;
	}
	
	protected static Container createContainer(Container parent,String position){
		return createContainer(parent, position, Boolean.FALSE);
	}
	
	protected static Container createContainerNorth(Container parent){
		Container container = createContainer(parent,"north");
		parent.getPropertiesMap().setNorth(container);
		return container;
	}
	
	protected static Container createContainerEast(Container parent){
		Container container = createContainer(parent,"east");
		parent.getPropertiesMap().setEast(container);
		return container;
	}
	
	protected static Container createContainerSouth(Container parent){
		Container container = createContainer(parent,"south");
		parent.getPropertiesMap().setSouth(container);
		return container;
	}
	
	protected static Container createContainerWest(Container parent){
		Container container = createContainer(parent,"west");
		parent.getPropertiesMap().setWest(container);
		return container;
	}
	
	protected static Container createContainerCenter(Container parent){
		Container container = createContainer(parent,"center");
		parent.getPropertiesMap().setCenter(container);
		return container;
	}
	
	protected static Container createContainerAssembly(Container parent,String position){
		Container container = createContainer(parent, position, Boolean.TRUE);
		return container;
	}
	
	protected static Container createContainerAssemblyCenter(Container parent){
		Container container = createContainerAssembly(parent, "center");
		parent.getPropertiesMap().setCenter(container);
		return container;
	}
	
	protected static Container createContainerAssemblyWest(Container parent){
		Container container = createContainerAssembly(parent, "west");
		parent.getPropertiesMap().setWest(container);
		return container;
	}
	
	protected Container createContainerNorth(){
		return createContainerNorth(this);
	}
	
	protected Container createContainerSouth(){
		return createContainerSouth(this);
	}
	
	protected Container createContainerEast(){
		return createContainerEast(this);
	}
	
	protected Container createContainerWest(){
		return createContainerWest(this);
	}
	
	protected Container createContainerCenter(){
		return createContainerCenter(this);
	}
	
	protected Container createContainerAssemblyCenter(){
		return createContainerAssemblyCenter(this);
	}
	
	protected Container createContainerAssemblyWest(){
		return createContainerAssemblyWest(this);
	}
	
	protected void setAttribute(LayoutOptions options,String key,Object value){
		options.addOption(key, value);
	}
	
	protected void fixed(LayoutOptions layoutOptions){
		setAttribute(layoutOptions,"resizable", false);  
		setAttribute(layoutOptions,"closable", false);  
		setAttribute(layoutOptions,"spacing_open", 0);  
	}
	
	protected LayoutOptions createOptions(Component component){
		return null;
	}
	
}
