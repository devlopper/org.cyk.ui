package org.cyk.ui.web.primefaces.resources.page.layout;

import java.io.Serializable;

import org.cyk.utility.common.CardinalPoint;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.MethodHelper;
import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.container.Container;
import org.primefaces.extensions.model.layout.LayoutOptions;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class NorthEastSouthWestCenter extends LayoutModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public NorthEastSouthWestCenter(Component component) {
		super(component,"/org.cyk.ui.web.primefaces.resources/include/page/layout/northeastsouthwestcenter_westnorth_centersouth/");
		
		createContainerNorth().getPropertiesMap().setRendered(InstanceHelper.getInstance()
				.getIfNotNullElseDefault(component.getPropertiesMap().getLayoutCardinalPointNorthRendered(),Boolean.TRUE));
		createContainerEast().getPropertiesMap().setRendered(InstanceHelper.getInstance()
				.getIfNotNullElseDefault(component.getPropertiesMap().getLayoutCardinalPointEastRendered(),Boolean.TRUE));
		createContainerSouth().getPropertiesMap().setRendered(InstanceHelper.getInstance()
				.getIfNotNullElseDefault(component.getPropertiesMap().getLayoutCardinalPointSouthRendered(),Boolean.TRUE));
		
		createContainerWest().getPropertiesMap().setRendered(InstanceHelper.getInstance()
				.getIfNotNullElseDefault(component.getPropertiesMap().getLayoutCardinalPointWestRendered(),Boolean.TRUE));
		Container west = createContainerAssemblyWest();
		west.getPropertiesMap().setRendered(InstanceHelper.getInstance()
				.getIfNotNullElseDefault(component.getPropertiesMap().getLayoutCardinalPointWestRendered(),Boolean.TRUE));
		createContainerNorth(west);
		createContainerCenter(west);
		
		createContainerCenter();
		Container center = createContainerAssemblyCenter();
		createContainerCenter(center);
		createContainerSouth(center);
	}

	private LayoutOptions createOptions(LayoutOptions parent,CardinalPoint cardinalPoint){
		LayoutOptions options = new LayoutOptions(); 
        if(parent!=null)
			MethodHelper.getInstance().callSet(parent, cardinalPoint.name().toLowerCase()+"Options", LayoutOptions.class, options);
		return options;
	}

	@Override
	protected LayoutOptions createOptions(Component component){
		LayoutOptions options = createOptions(null, null);
		
        // options for all panes  
        LayoutOptions panes = new LayoutOptions();  
        setAttribute(panes, "slidable", Boolean.FALSE);  
        options.setPanesOptions(panes);    
        
        LayoutOptions north = createOptions(options, CardinalPoint.NORTH);
        setAttribute(north, "size", 44);  
        setAttribute(north, "rendered", component.getPropertiesMap().getLayoutCardinalPointNorthRendered() == null 
        		|| Boolean.TRUE.equals(component.getPropertiesMap().getLayoutCardinalPointNorthRendered()));  
        fixed(north);
         
        LayoutOptions west = createOptions(options, CardinalPoint.WEST);  
        setAttribute(west, "size", 220);  
        setAttribute(west, "rendered", component.getPropertiesMap().getLayoutCardinalPointNorthRendered() == null 
        		|| Boolean.TRUE.equals(component.getPropertiesMap().getLayoutCardinalPointNorthRendered()));  
        configureWest(west);
        
  		
        LayoutOptions center = createOptions(options, CardinalPoint.CENTER);
        setAttribute(center, "resizable", Boolean.FALSE);  
        setAttribute(center, "closable", Boolean.FALSE);  
        configureCenter(center);
        
        LayoutOptions south = createOptions(options, CardinalPoint.SOUTH);
        setAttribute(south, "size", 15);  
        setAttribute(south, "rendered", component.getPropertiesMap().getLayoutCardinalPointNorthRendered() == null 
        		|| Boolean.TRUE.equals(component.getPropertiesMap().getLayoutCardinalPointNorthRendered()));  
        fixed(south);
        
        return options;
	}
	
	protected void configureNorthCenter(LayoutOptions parent){
		// options for nested layout  
        LayoutOptions childOptions = new LayoutOptions();  
        parent.setChildOptions(childOptions);  
		
		// options for north-center-west pane  
        LayoutOptions west = new LayoutOptions();
        fixed(west);
        west.addOption("size", "40");  
        childOptions.setWestOptions(west);  
  
        // options for north-center-center pane  
        LayoutOptions center = new LayoutOptions();  
        childOptions.setCenterOptions(center);
	}
	
	protected void configureCenter(LayoutOptions parent){
		// options for nested layout  
        LayoutOptions childOptions = new LayoutOptions();  
        parent.setChildOptions(childOptions);  
  
        // options for center-north pane  
        //LayoutOptions north = new LayoutOptions();
        //childOptions.setNorthOptions(north);  
        
        createOptions(childOptions, CardinalPoint.CENTER);
        
        // options for center-south pane  
        LayoutOptions south = createOptions(childOptions, CardinalPoint.SOUTH);
        setAttribute(south, "size", "34");
        fixed(south);
        
        //childOptions.setSouthOptions(south);
        
        
	}
	
	protected void configureWest(LayoutOptions parent){
		// options for nested layout  
        LayoutOptions childOptions = new LayoutOptions();  
        parent.setChildOptions(childOptions);  
  
        LayoutOptions north = createOptions(childOptions, CardinalPoint.NORTH);  
        setAttribute(north, "size", "150");
        fixed(north);
        createOptions(childOptions, CardinalPoint.CENTER);    
	}
	
}
