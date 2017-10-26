package org.cyk.ui.web.primefaces.resources.page.layout;

import java.io.Serializable;

import org.cyk.utility.common.userinterface.container.Container;
import org.primefaces.extensions.model.layout.LayoutOptions;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class NorthEastSouthWestCenter extends LayoutModel implements Serializable {

	private static final long serialVersionUID = 1L;

	public NorthEastSouthWestCenter() {
		super("/org.cyk.ui.web.primefaces.resources/include/page/layout/northeastsouthwestcenter_centersouth/");
		
		createContainerNorth();
		createContainerEast();
		createContainerSouth();
		createContainerWest();
		createContainerCenter();
		
		Container center = createContainerAssemblyCenter();
		createContainerSouth(center);
		createContainerCenter(center);
	}
	
	@Override
	protected LayoutOptions createOptions(){
		LayoutOptions options = new LayoutOptions();  
		  
        // options for all panes  
        LayoutOptions panes = new LayoutOptions();  
        panes.addOption("slidable", false);  
        options.setPanesOptions(panes);    
  
        // north pane  
        LayoutOptions north = new LayoutOptions(); 
        fixed(north);
        north.addOption("size", 44);
        options.setNorthOptions(north);  
        //configureNorth(north);
        
        // west pane  
        LayoutOptions west = new LayoutOptions();   
        west.addOption("size", 220);
        options.setWestOptions(west);  
  		
        //center pane
        LayoutOptions center = new LayoutOptions();  
        center.addOption("resizable", false);  
        center.addOption("closable", false);  
        options.setCenterOptions(center);
        configureCenter(center);
        
        // south pane  
        LayoutOptions south = new LayoutOptions();  
        fixed(south);
        south.addOption("size", 15);    
        options.setSouthOptions(south);  
        
        return options;
	}
	
	protected void configureNorth(LayoutOptions parent){
		// options for nested layout  
        LayoutOptions childOptions = new LayoutOptions();  
        parent.setChildOptions(childOptions);  
        
        // options for north-center pane  
        LayoutOptions center = new LayoutOptions();
        fixed(center);
        //center.addOption("size", "45");  
        childOptions.setCenterOptions(center);  
        //configureNorthCenter(center);
  
        // options for north-south pane  
        LayoutOptions south = new LayoutOptions();  
        south.addOption("size", "30"); 
        fixed(south);
        childOptions.setSouthOptions(south);
        
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
        
        // options for center-center pane  
        LayoutOptions center = new LayoutOptions();
        childOptions.setCenterOptions(center);  
        
        // options for center-south pane  
        LayoutOptions south = new LayoutOptions();  
        south.addOption("size", "34"); 
        fixed(south);
        childOptions.setSouthOptions(south);
        
        
	}
	
}
