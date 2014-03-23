package org.cyk.ui.api;

/**
 * A view container is views wrapper in order to interact with them.
 * @author Komenan Y .Christian
 *
 */
public interface UIContainer<FORM,OUTPUTLABEL,INPUT,SELECTITEM> {
	
	UIWindow<FORM,OUTPUTLABEL,INPUT,SELECTITEM> getWindow();
	
	void setWindow(UIWindow<FORM,OUTPUTLABEL,INPUT,SELECTITEM> aWindow);
	
	/**
	 * Get the title
	 * @return
	 */
	String getTitle();
	
	/**
	 * Get the views
	 * @return
	 */
	//Collection<UIView> getViews();
	
	Object getObjectModel();
	
	UIView<FORM,OUTPUTLABEL,INPUT,SELECTITEM> build(Object object);
	
}
