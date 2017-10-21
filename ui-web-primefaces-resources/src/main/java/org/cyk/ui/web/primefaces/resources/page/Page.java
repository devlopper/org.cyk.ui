package org.cyk.ui.web.primefaces.resources.page;

import java.io.Serializable;

import org.cyk.utility.common.userinterface.container.Container;
import org.cyk.utility.common.userinterface.container.Window;
import org.primefaces.extensions.model.layout.LayoutOptions;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Page extends Window implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String DEFAULT_CONTRACTS = "defaultDesktop";
	public static final String DEFAULT_TEMPLATE = "/template/default.xhtml";
	public static final String DEFAULT_DECORATED_TEMPLATE = "/org.cyk.ui.web.primefaces/template/page/desktop/default.xhtml";
	
	protected String contracts = DEFAULT_CONTRACTS;
	protected String decoratedTemplate = DEFAULT_DECORATED_TEMPLATE;

	protected LayoutOptions options;
	protected Container north,east,south,west,center;
		
	@Override
	protected void initialisation() {
		super.initialisation();
		templateIdentifier = DEFAULT_TEMPLATE;
		getPropertiesMap().setInclude("/org.cyk.ui.web.primefaces/include/page/default.xhtml");
		north = new Container();
		north.getPropertiesMap().setInclude("/org.cyk.ui.web.primefaces/include/dummycontent.xhtml").setPosition("north");
		east = new Container();
		east.getPropertiesMap().setInclude("/org.cyk.ui.web.primefaces/include/dummycontent.xhtml").setPosition("east");
		south = new Container();
		south.getPropertiesMap().setInclude("/org.cyk.ui.web.primefaces/include/dummycontent.xhtml").setPosition("south");
		west = new Container();
		west.getPropertiesMap().setInclude("/org.cyk.ui.web.primefaces/include/dummycontent.xhtml").setPosition("west");
		center = new Container();
		center.getPropertiesMap().setInclude("/org.cyk.ui.web.primefaces/include/dummycontent.xhtml").setPosition("center");
		
		configureMain();
	}
	
	public void listenPreRenderView(){
		
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
        south.addOption("size", "34"); 
        fixed(south);
        childOptions.setSouthOptions(south);
	}
}
