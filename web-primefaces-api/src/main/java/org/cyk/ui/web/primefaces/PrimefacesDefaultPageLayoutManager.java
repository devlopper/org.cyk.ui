package org.cyk.ui.web.primefaces;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.web.api.AbstractWebPageLayoutManager;
import org.primefaces.extensions.model.layout.LayoutOptions;

@Named("webPageLayoutManager") @SessionScoped @Getter @Setter
public class PrimefacesDefaultPageLayoutManager extends AbstractWebPageLayoutManager {

	private static final long serialVersionUID = 2282543573812258638L;

	private LayoutOptions options;
	
	
	@Override
	protected void initialisation() {
		super.initialisation();
		configureMain();
	}
	
	private void fixed(LayoutOptions pane){
		pane.addOption("resizable", false);  
        pane.addOption("closable", false);  
        pane.addOption("spacing_open", 0);  
	}
	
	protected void configureMain(){
		options = new LayoutOptions();  
		  
        // options for all panes  
        LayoutOptions panes = new LayoutOptions();  
        panes.addOption("slidable", false);  
        options.setPanesOptions(panes);    
  
        // north pane  
        LayoutOptions north = new LayoutOptions(); 
        fixed(north);
        north.addOption("size", 49);
        options.setNorthOptions(north);  
        //configureNorth(north);
        
        // west pane  
        LayoutOptions west = new LayoutOptions();   
        west.addOption("size", 350);
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
  
        // options for center-center pane  
        LayoutOptions center = new LayoutOptions();
        childOptions.setCenterOptions(center);  
        
        // options for center-south pane  
        LayoutOptions south = new LayoutOptions();  
        south.addOption("size", "40"); 
        fixed(south);
        childOptions.setSouthOptions(south);
	}
	
}
