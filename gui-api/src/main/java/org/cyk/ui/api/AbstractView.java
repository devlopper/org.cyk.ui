package org.cyk.ui.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.cyk.utility.common.cdi.AbstractBean;

@Getter @Setter
public abstract class AbstractView extends AbstractBean implements View,Serializable {

	private static final long serialVersionUID = 898216365028456783L;
	
	/**/
	protected UIManager uiManager = UIManager.getInstance();
	
	protected String title,template;
	protected Collection<View> children = new ArrayList<>();
	
	protected Collection<ViewListener> viewListeners = new ArrayList<>();
	
	/**/ 
	
	public final void build(){
		//parent first
		__build__();
		
		for(ViewListener listener : viewListeners)
			listener.build(this);
		//then children
		for(View child : children)
			child.build();
	}
	
	public void __build__(){
		
	}
	
	protected String text(String code){
		return UIManager.getInstance().text(code);
	}
	
	@Override
	public String toString() {
		return title;
	}
}
