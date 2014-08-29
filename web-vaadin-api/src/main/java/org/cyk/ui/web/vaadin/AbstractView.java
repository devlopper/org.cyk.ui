package org.cyk.ui.web.vaadin;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Layout;
import com.vaadin.ui.SingleComponentContainer;

public abstract class AbstractView<CONTAINER extends SingleComponentContainer,LAYOUT extends Layout,APPLICATION extends AbstractApplication<?>> extends AbstractCustomComponent<CONTAINER,LAYOUT> implements View, Serializable {

	private static final long serialVersionUID = -5464828835159257761L;
	
	protected APPLICATION application;
	
	@SuppressWarnings("unchecked")
	@Override
	protected Component compositionRoot() {
		Class<?> clazz = (Class<CONTAINER>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[2];
		try {
			application = (APPLICATION) clazz.getMethod("getInstance").invoke(clazz);
			application.getPageLayoutManager().getCenter().addComponent(container);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return application.getPageLayoutManager().getPage();
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		
	}
	
	/* Short cuts */
	
	
	
	/**/
	
	
}
