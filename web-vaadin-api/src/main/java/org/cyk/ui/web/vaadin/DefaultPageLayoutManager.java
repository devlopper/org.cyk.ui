package org.cyk.ui.web.vaadin;

import java.io.Serializable;

import javax.inject.Inject;
import javax.inject.Singleton;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.InternalApplicationModuleType;
import org.cyk.ui.api.UIManager;
import org.cyk.utility.common.cdi.AbstractBean;

import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@Getter @Setter @Singleton
public class DefaultPageLayoutManager extends AbstractBean implements Serializable {
	
	private static final long serialVersionUID = 46704270411434647L;
	
	private static DefaultPageLayoutManager INSTANCE;
	
	@Inject protected UIManager uiManager;
	
	
	protected VerticalLayout page,west,east,center;
	protected HorizontalLayout top,middle,bottom;
	protected HorizontalSplitPanel middleHorizontalSplitPanel;
	
	//TODO is it really needed?
	protected InternalApplicationModuleType internalApplicationModuleType;
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	public void initialize(InternalApplicationModuleType internalApplicationModuleType){
		this.internalApplicationModuleType = internalApplicationModuleType;
		page = config(null, new VerticalLayout(), "page", null, null,null);
		top();
		middle();
		bottom();
	}
	
	/**/
	
	protected void top(){
		top = config(page, new HorizontalLayout(), "top", null, null,"40px");
	}
	
	protected void middle(){
		middle = config(page, new HorizontalLayout(), "middle", 1f, null,null);
		middleHorizontalSplitPanel = config(middle, new HorizontalSplitPanel(), "hs", 1f, null,null);
		
		middleWest();
		
		middleCenter();
		
		east = config(null, new VerticalLayout(), "east", null, "40px",null);
		
		middleHorizontalSplitPanel.setFirstComponent(west);
		middleHorizontalSplitPanel.setSecondComponent(center);
		middleHorizontalSplitPanel.setSplitPosition(20f);
	}
	
	protected void middleWest() {
		west = config(null, new VerticalLayout(), "west", null, null, null);
	}
	
	protected void middleCenter(){
		center = config(null, new VerticalLayout(), "center", 1f, null,null);
	}

	protected void bottom(){
		bottom = config(page, new HorizontalLayout(), "bottom", null, null,"15px");
		Label label = new Label(uiManager.getWindowFooter());
		label.setSizeUndefined();
		bottom.addComponent(label);
		bottom.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
	}
	
	/**/
	
	protected <COMPONENT extends AbstractComponent> COMPONENT config(AbstractLayout parent,COMPONENT component,String styleName,Float expandratio,String width,String height){
		component.setSizeFull();
		component.addStyleName("cyk"+styleName);
		if(height!=null)
			component.setHeight(height);
		if(width!=null)
			component.setWidth(width);
		if(parent!=null){
			parent.addComponent(component);
			if(parent instanceof AbstractOrderedLayout){
				((AbstractOrderedLayout)parent).setExpandRatio(component, expandratio==null?0f:expandratio);
			}
		}
		
		return component;
	}
	
	public static DefaultPageLayoutManager getInstance() {
		return INSTANCE;
	}
}
