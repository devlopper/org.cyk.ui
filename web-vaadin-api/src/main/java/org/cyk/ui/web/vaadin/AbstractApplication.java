package org.cyk.ui.web.vaadin;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import javax.inject.Inject;

import lombok.Getter;

import org.cyk.ui.api.InternalApplicationModuleType;
import org.cyk.ui.api.MenuManager;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.editor.input.UIInputComponent;
import org.cyk.ui.web.api.WebManager;
import org.cyk.ui.web.vaadin.editor.Input;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

@Theme("mytheme") 
public abstract class AbstractApplication<START_VIEW extends AbstractView<?, ?, ?>> extends UI implements Serializable {

	private static final long serialVersionUID = -5464828835159257761L;
	
	@Inject @Getter protected DefaultPageLayoutManager pageLayoutManager;
	@Getter protected InternalApplicationModuleType internalApplicationModuleType = InternalApplicationModuleType.CRUD;
	@Inject protected UIManager uiManager;
	@Inject @Getter protected MenuManager menuManager;
	@Inject protected WebManager webManager;
	protected Navigator navigator;
	
	@Override
	protected final void init(VaadinRequest request) {
		
		pageLayoutManager.initialize(internalApplicationModuleType = getInternalApplicationModuleType());
		initialize(request);
		
		navigator = new Navigator(this, this);
		try {
			navigator.addView("",startView());
		} catch (Exception e) {
			e.printStackTrace();
		}
		views();
	}
	
	protected void initialize(VaadinRequest request) {
		
	}
	
	protected void views(){
		
	}
	
	@SuppressWarnings("unchecked")
	protected START_VIEW startView(){
		try {
			return (((Class<START_VIEW>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0])).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/* Short cuts */

	
	protected void addView(Class<? extends View> aViewClass){
		navigator.addView(aViewClass.getSimpleName(), aViewClass);
	}
	
	protected void addView(View aView){
		addView(aView.getClass().getSimpleName(), aView);
	}
	
	protected void addView(String name,View aView){
		navigator.addView(name, aView);
	}
	
	/**/

	/**/
	
	static{
		UIManager.componentCreateMethod = new UIManager.ComponentCreateMethod() {
			private static final long serialVersionUID = -6005484639897008871L;
			@Override
			protected UIInputComponent<?> component(org.cyk.ui.api.editor.EditorInputs<?, ?, ?, ?> anEditorInputs,UIInputComponent<?> aComponent) {
				Input component = new Input((org.cyk.ui.web.vaadin.editor.EditorInputs) anEditorInputs,aComponent);
				return component;
			}
		};
	}
}
